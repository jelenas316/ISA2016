app.controller('rmwaitersController', ['$scope', '$window', '$location', 'waiterService', 'loginService','restaurantService','$state',
	function ($scope, $window, $location, waiterService, loginService, restaurantService, $state) {
        
	function init(){
        if($window.localStorage.getItem("user") == undefined){
             $state.go('login'); 
             return;
        }
              
        var result = JSON.parse(localStorage.getItem("user"));
        $scope.user = result[0];
        if($scope.user.role != 'RESTAURANT_MANAGER'){
             $state.go('login'); 
             return; 
        }
        $scope.getAllWaiters();
        $scope.inputType = 'password';
        $scope.flag = false;
        $scope.flagDelete = false;
        $scope.state = 'undefined';
        $scope.newWaiter = {};
        $scope.maxDate = new Date();
        $scope.tablesForShift = [];
        $scope.shiftId = -1;
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
        
    $scope.workSchedule = function(waiter){
        $scope.state = "schedule";
        $scope.getWaiter(waiter.email);
    }
    $scope.exitSchedule = function(){
        $scope.state = "undefined";
    }
    $scope.shift = function(){
        $scope.shiftId = -1;
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
        if( $scope.shiftId !=undefined && $scope.shiftId != -1){
            shift.id = $scope.shiftId;
        }else{
            shift.id = -1;
        }
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
                    $scope.state = "schedule";
                    $scope.tablesForShift = [];
                    $scope.shiftId = -1;
                    return;
                }
                $scope.getWaiter($scope.waiter.email);
                alert("Shift is created.");
                $scope.tablesForShift = [];
                $scope.shiftId = -1;
                $scope.state = "schedule";
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
        $scope.waiter.activated = false;
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
         $scope.tablesForShift = [];
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
     $scope.getDateForShift = function(date){
        var year   = parseInt(date.substring(0,4));
        var month  = parseInt(date.substring(5,7));
        var day   = parseInt(date.substring(8,10));
        var date1 = new Date(year, month-1, day);
        return date1;
    } 
    $scope.parseTime = function(time){
        var hours   = parseInt(time.substring(0,2));
        var minutes  = parseInt(time.substring(3,5));
        return hours*60+minutes;
    } 
     $scope.editShift = function(shift){
         $scope.shiftId = shift.id;
         $scope.myDatetimeRange.date.from = $scope.getDateForShift(shift.startDate);
         $scope.myDatetimeRange.date.to = $scope.getDateForShift(shift.endDate);
         $scope.myDatetimeRange.date.min = new Date();
         $scope.myDatetimeRange.time.from = $scope.parseTime(shift.beginOfShift);
         $scope.myDatetimeRange.time.to = $scope.parseTime(shift.endOfShift);
         $scope.myDatetimeRange.time.minRange = 60;
         $scope.tablesForShift = shift.reon;
         $scope.state = "shift";
     }
   
}]);