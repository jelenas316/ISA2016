app.controller('rmwaitersController', ['$scope', '$window', '$location', 'waiterService', 'loginService','restaurantService','$state',
	function ($scope, $window, $location, waiterService, loginService, restaurantService, $state) {
        
	function init(){
        if($window.localStorage.getItem("user") == undefined){
             $state.go('login'); 
             return;
        }
              
        var result = JSON.parse(localStorage.getItem("user"));
        $scope.user = result[0];
        $scope.getAllWaiters();
        $scope.inputType = 'password';
        $scope.flag = false;
        $scope.flagDelete = false;
        $scope.state = 'undefined';
        $scope.newWaiter = {};
        $scope.maxDate = new Date();
        $scope.tablesForShift = [];
    }       
    $scope.getAllWaiters = function(){
        waiterService.findAll($scope.user.restaurant.id).then(
            function(response){
                $scope.waiters = response.data;
            },
            function(response){
                alert("Error wile fetching waiters.");
            }
        );
    }

	init();
        
    $scope.getDate = function(date,time){
        var year   = parseInt(date.substring(0,4));
        var month  = parseInt(date.substring(5,7));
        var day   = parseInt(date.substring(8,10));
        var hours = parseInt(time.substring(0,2));
        var minutes = parseInt(time.substring(3,5));
        var date1 = new Date(year, month-1, day+1, hours, minutes);
        return date1;
    } 
    
   /* $scope.getSchedule = function(waiter) {
        
        var events = [];
        $scope.email = waiter.email;
        
        for(var i = 0; i< waiter.shifts.length; i++){
            var startTime;
            var endTime;
            startTime = $scope.getDate(waiter.shifts[i].startDate, waiter.shifts[i].beginOfShift);
            endTime = $scope.getDate(waiter.shifts[i].endDate, waiter.shifts[i].endOfShift);
            events.push({
                title: 'Worker - ' + $scope.email,
                startTime: startTime,
                endTime: endTime,
                allDay: false
            });
        }                
        return events;
    }   */ 
        
    $scope.workSchedule = function(waiter){
        $scope.state = "schedule";
        $scope.getWaiter(waiter.email);
        //$scope.eventSource = $scope.getSchedule(waiter);
    }
    $scope.exitSchedule = function(){
        $scope.state = "undefined";
    }
    $scope.shift = function(){
        $scope.state = "shift";
    }
    $scope.getWaiter = function(email){
        waiterService.findOne(email).then(
            function(response){
                $scope.waiter = response.data;
            },
            function(response){
                alert("Error while fetching waiter.");
            }
        );
    }
    $scope.addShift = function(){    
        $scope.flag = true;
        if($scope.tablesForShift.length ==0){
            alert("Choose at least one table.");
            $scope.flag = false;
            return;
        }
        var shift = {};
        shift.startDate = $scope.myDatetimeRange.date.from;
        shift.endDate = $scope.myDatetimeRange.date.to;
        shift.beginOfShift = $scope.myDatetimeRange.time.from;
        shift.endOfShift = $scope.myDatetimeRange.time.to;
        shift.worker = $scope.waiter.email;        
        shift.reon = $scope.tablesForShift;
        waiterService.addShift(shift).then(
            function(response){
                $scope.flag = false;
                if(response.data == false){                    
                    alert("Waiter \""+ $scope.waiter.email +"\" alredy have schedule for that period.");
                    $scope.state = "undefined";
                    return;
                }
                $scope.getWaiter($scope.waiter.email);
                alert("Shift is created.");
            },
            function(response){
                $scope.flag = false;
                alert("Error while adding shift.");
            }
        );
    }
    $scope.myDatetimeRange = {
		date: {
			from: new Date(),
			to: new Date(),
            min: new Date()
		},
		time: {
			from: 480, // default low value
			to: 1020, // default high value
            step: 30, // step width
            minRange: 60, // min range
            hours24: false // true for 24hrs based time | false for PM and AM
		}
	};
    $scope.myDatetimeLabels = {
        date: {
            from: 'Start date',
            to: 'End date'
        }
    };
    $scope.deleteShift = function(shift){
        $scope.flag = true;
        var requestParams = {};
        requestParams.email = $scope.waiter.email;
        requestParams.shift = shift.id;
        waiterService.deleteShift(requestParams).then(
            function(response){
                var index = $scope.waiter.shifts.indexOf(shift);
                $scope.waiter.shifts.splice(index,1);
                $scope.flag = false;
            },
            function(response){
                alert("Error while deleting shift.");
                $scope.flag = false;
            }
        );
    } 
    $scope.addWaiter = function(){
        $scope.state = "newWaiter";
        $scope.waiter = {};
    }
     // Hide & show password function
    $scope.hideShowPassword = function(){
        if ($scope.inputType == 'password')
          $scope.inputType = 'text';
        else
          $scope.inputType = 'password';
    };
    $scope.validateSize = function(text){
        return /^[1-9][0-9]{0,1}(\.[1-9]){0,1}$/.test(text);        
    }
    $scope.validatePass = function (text) {
        return /^[A-Za-z0-9\_\-]{4,30}$/.test(text);
    }
    $scope.validateText = function(text){
        return /^[A-Za-z]{2,30}$/.test(text);
    }
    $scope.emailValidate = function (email) {
        return /^[A-Za-z]+[A-Za-z0-9\.\-\_]*\@[A-Za-z0-9]+\.[A-Za-z]{2,4}$/.test(email);
    }
     $scope.create = function(){ 
        $scope.flag = true;
        if(!$scope.emailValidate($scope.waiter.email) || $scope.waiter.email == undefined){
            alert("Enter valid email.");
            $scope.flag = false;
            return;
        }
        if(!$scope.validatePass($scope.waiter.password) || $scope.waiter.password == undefined){
            alert("Enter valid password (Between 4 and 30 letters and numbers).");
            $scope.flag = false;
            return;
        }
        if(!$scope.validateText($scope.waiter.name) || $scope.waiter.name == undefined){
            alert("Enter valid name (Between 2 and 30 letters).");
            $scope.flag = false;
            return;
        }
        if(!$scope.validateText($scope.waiter.surname) || $scope.waiter.surname == undefined){
            alert("Enter valid surname (Between 2 and 30 letters).");
            $scope.flag = false;
            return;
        }
        if($scope.waiter.dateOfBirth=="" || $scope.waiter.dateOfBirth == undefined){
            alert("Select date of birth.");
            $scope.flag = false;
            return;
        }
        if(!$scope.validateSize($scope.waiter.dressSize) || $scope.waiter.dressSize == undefined){
            alert("Dress size is number 1-99.");
            $scope.flag = false;
            return;
        }
        if(!$scope.validateSize($scope.waiter.shoesSize) || $scope.waiter.shoesSize == undefined){
            alert("Shoes size is number 1-99.");
            $scope.flag = false;
            return;
        }
        $scope.waiter.restaurant = $scope.user.restaurant;
        $scope.waiter.role = "WAITER";
        var month = $scope.waiter.dateOfBirth.getMonth()+1;
        var date = $scope.waiter.dateOfBirth.getFullYear()+"-"+month+"-"+$scope.waiter.dateOfBirth.getDate();
        $scope.waiter.dateOfBirth = date;
        waiterService.save($scope.waiter).then(
            function(response){
                $scope.flag = false;
                if(response.data == ""){
                   alert("Waiter with email \""+ $scope.waiter.email +"\" alredy exist.");
                   $scope.waiter.dateOfBirth = undefined;
                   return;
                }
                $scope.getAllWaiters();
                alert("Waiter \""+ $scope.waiter.email +"\" is created.");
                $scope.state = "undefined";                
            },
            function(response){
                alert("Error while creating waiter.");
                $scope.flag = false;
                $scope.waiter.dateOfBirth = undefined;
            }
        );
    } 
     $scope.exitAddShift = function(){
         $scope.state = "schedule";
     }
     $scope.table = function(table){ 
        var index = $scope.tablesForShift.indexOf(table); 
        if(index!=-1){
          $scope.tablesForShift.splice(index, 1);
        }else{
            $scope.tablesForShift.push(table);
        }
     }
     $scope.expandTablesForShift = function(shift){
         $scope.state = "expandTablesForShift";
         $scope.expandTables = shift.reon;
     }
   
}]);