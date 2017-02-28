app.controller('rmcooksController', ['$scope', '$window', '$location', 'cookService', 'loginService','restaurantService','$state',
	function ($scope, $window, $location, cookService, loginService, restaurantService, $state) {
        
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
        $scope.getAllCooks();
        $scope.inputType = 'password';
        $scope.flag = false;
        $scope.flagDelete = false;
        $scope.state = 'undefined';
        $scope.newCook = {};
        $scope.maxDate = new Date();
        $scope.shiftId = -1;
    }       
    $scope.getAllCooks = function(){
        cookService.findAll($scope.user.restaurant.id).then(
            function(response){
                $scope.cooks = response.data;
            },
            function(response){
                alert("Error wile fetching cooks.");
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
        
    $scope.workSchedule = function(cook){
        $scope.state = "schedule";
        $scope.getCook(cook.email);
        //$scope.eventSource = $scope.getSchedule(waiter);
    }
    $scope.exitSchedule = function(){
        $scope.state = "undefined";
    }
    $scope.shift = function(){
        $scope.state = "shift";
    }
    $scope.getCook = function(email){
        cookService.findOne(email).then(
            function(response){
                $scope.cook = response.data;
            },
            function(response){
                alert("Error while fetching cook.");
            }
        );
    }
    $scope.addShift = function(){   
        $scope.flag = true;
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
        shift.worker = $scope.cook.email;        
        shift.reon = [];
        cookService.addShift(shift).then(
            function(response){
                $scope.flag = false;
                if(response.data == false){                   
                    alert("Cook \""+ $scope.cook.email +"\" alredy have schedule for that period."); 
                    $scope.state = "schedule";
                    $scope.shiftId = -1;
                    return;
                }
                $scope.getCook($scope.cook.email);
                alert("Shift is created.");
                $scope.shiftId = -1;
                $scope.state = "schedule";
            },
            function(response){
                alert("Error while adding shift.");
                $scope.flag = false;
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
        requestParams.email = $scope.cook.email;
        requestParams.shift = shift.id;
        cookService.deleteShift(requestParams).then(
            function(response){
                var index = $scope.cook.shifts.indexOf(shift);
                $scope.cook.shifts.splice(index,1);
                $scope.flag = false;
            },
            function(response){
                alert("Error while deleting shift.");
                $scope.flag = false;
            }
        );
    } 
    $scope.addCook = function(){
        $scope.state = "newCook";
        $scope.cook = {};
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
        if(!$scope.emailValidate($scope.cook.email) || $scope.cook.email == undefined){
            alert("Enter valid email.");
            $scope.flag = false;
            return;
        }
        if(!$scope.validatePass($scope.cook.password) || $scope.cook.password == undefined){
            alert("Enter valid password (Between 4 and 30 letters and numbers).");
            $scope.flag = false;
            return;
        }
        if(!$scope.validateText($scope.cook.name) || $scope.cook.name == undefined){
            alert("Enter valid name (Between 2 and 30 letters).");
            $scope.flag = false;
            return;
        }
        if(!$scope.validateText($scope.cook.surname) || $scope.cook.surname == undefined){
            alert("Enter valid surname (Between 2 and 30 letters).");
            $scope.flag = false;
            return;
        }
        if($scope.cook.dateOfBirth=="" || $scope.cook.dateOfBirth == undefined){
            alert("Select date of birth.");
            $scope.flag = false;
            return;
        }
        if(!$scope.validateSize($scope.cook.dressSize) || $scope.cook.dressSize == undefined){
            alert("Dress size is number 1-99.");
            $scope.flag = false;
            return;
        }
        if(!$scope.validateSize($scope.cook.shoesSize) || $scope.cook.shoesSize == undefined){
            alert("Shoes size is number 1-99.");
            $scope.flag = false;
            return;
        }
        $scope.cook.activated = false;
        $scope.cook.restaurant = $scope.user.restaurant;
        $scope.cook.role = "COOK";
        var month = $scope.cook.dateOfBirth.getMonth()+1;
        var date = $scope.cook.dateOfBirth.getFullYear()+"-"+month+"-"+$scope.cook.dateOfBirth.getDate();
        $scope.cook.dateOfBirth = date;
        cookService.save($scope.cook).then(
            function(response){
                if(response.data == ""){
                   alert("Cook with email \""+ $scope.cook.email +"\" alredy exist.");
                   $scope.cook.dateOfBirth = undefined;
                   return;
                }
                $scope.getAllCooks();
                alert("Cook \""+ $scope.cook.email +"\" is created.");
                $scope.state = "undefined";
                $scope.flag = false;
            },
            function(response){
                alert("Error while creating cook.");
                $scope.flag = false;
                $scope.cook.dateOfBirth = undefined;
            }
        );
    } 
     $scope.exitAddShift = function(){
         $scope.state = "schedule";
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