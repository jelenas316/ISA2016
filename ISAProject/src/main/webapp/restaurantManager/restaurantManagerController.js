app.controller('restaurantManagerController', ['$scope', '$window', '$location', 'restaurantManagerService', 'loginService','restaurantService','foodService','$state','drinkService','restaurantTableService',
	function ($scope, $window, $location, restaurantManagerService, loginService, restaurantService, foodService, $state, drinkService,restaurantTableService) {
        
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
        $scope.food = {};	
        $scope.drink = {};
        $scope.restaurantTables = [];
        $scope.tables = {};
        $scope.table = {};
        $scope.configuration = {};
        $scope.tablesForDelete = [];
        $scope.inputType = 'password';
        $scope.flag = false;
        $scope.flagDelete = false;
        $scope.restaurantOpen();   
    }
             
    $scope.createRandomEvents = function() {
        var events = [];
            var date = new Date();
            var startTime;
            var endTime;
            var startMinute = Math.floor(Math.random() * 24 * 60);
            var endMinute = Math.floor(Math.random() * 180) + startMinute;
            startTime = new Date(date.getFullYear(), date.getMonth(), date.getDate() , date.getHours(), date.getMinutes());
            endTime = new Date(date.getFullYear(), date.getMonth(), date.getDate() + 1, date.getHours()+4, date.getMinutes());
            events.push({
                title: 'Radnik - ' + $scope.user.username,
                startTime: startTime,
                endTime: endTime,
                allDay: false
            });
        return events;
    }
     $scope.restaurantOpen = function(){
        restaurantManagerService.findOne($scope.user.email).then(
            function(response){                
                $scope.res = response.data.restaurant;
            },
            function(response){
                alert("Error wile adding.");
            }
        );
    }
	init();
    $scope.updateManagerData = function(){
            restaurantManagerService.update($scope.user).then(
            function(response){
                $scope.user = response.data;
                var user = [];
                user.push(response.data);
                $window.localStorage.setItem("user", JSON.stringify(user));
            },
            function(response){
                alert("Error while updating user.");
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

    $scope.whentimechangedata = {};

    $scope.whenTimeChange = function (data) {
        console.log('schedule changes', data);
        $scope.whentimechangedata = data;
    };

    // show only time slider
    $scope.timeRangePicker = {
        time: {
            from: 480, // default low value
            to: 1020, // default high value
            step: 60, // step width
            minRange: 60, // min range
            hours24: false // true for 24hrs based time | false for PM and AM
        }
    };

    // show only date pickers
    $scope.dateRangePicker = {
        date: {
            from: new Date(),
            to: new Date()
        }
    };

    $scope.validateSize = function(text){
        return /^[1-9][0-9]{0,1}(\.[1-9]){0,1}$/.test(text);        
    }
    $scope.validatePass = function (text) {
        return /^[A-Za-z0-9\_\-]{4,30}$/.test(text);
    }
    $scope.validateText = function(text){
        return /^[A-Za-z0-9]{2,30}$/.test(text);
    }
    $scope.validateAddress = function(text){
        return /^[A-Za-z][a-zA-Z0-9 ]{1,50}$/.test(text);
    }
    $scope.emailValidate = function (email) {
        return /^[A-Za-z]+[A-Za-z0-9\.\-\_]*\@[A-Za-z0-9]+\.[A-Za-z]{2,4}$/.test(email);
    }
    $scope.mixRangeDate = 1; //days
        
   
    $scope.deleteFood = function(food){
        $scope.flagDelete = true;
        var index = $scope.res.menu.indexOf(food);
        foodService.delete(food.id).then(
            function(response){
                $scope.res.menu.splice(index, 1);
                 $scope.flagDelete = false;
            },
            function(response){
                alert("Error wile deleting.");
                 $scope.flagDelete = false;
            }
        );
    }
    $scope.createFood = function(){
         $scope.flag = true;
         $scope.food.resId = $scope.user.restaurant.id;
         foodService.save($scope.food).then(
            function(response){
                $scope.food = {};                
                $scope.res = response.data;
                alert("Added.");      
                 $scope.flag = false;
            },
            function(response){
                alert("Error wile adding.");
                $scope.flag = false;
            }
        );
    }
    $scope.editFood = function(food){
        $scope.food.id = food.id;
        $scope.food.name = food.name;
        $scope.food.description = food.description;
        $scope.food.price = food.price;
    }
    $scope.deleteDrink = function(drink){
        $scope.flagDelete = true;
        var index = $scope.res.drinks.indexOf(drink);
        drinkService.delete(drink.id).then(
            function(response){
                $scope.res.drinks.splice(index, 1);
                $scope.flagDelete = false;
            },
            function(response){
                alert("Error wile deleting.");
                $scope.flagDelete = false;s
            }
        );
    }
    $scope.createDrink = function(){
         $scope.flag = true;
         $scope.drink.resId = $scope.user.restaurant.id;
         drinkService.save($scope.drink).then(
            function(response){
                $scope.drink = {};    
                $scope.res = response.data;
                alert("Added.");  
                $scope.flag = false;
            },
            function(response){
                alert("Error wile adding.");
                $scope.flag = false;
            }
        );
    }
    $scope.editDrink = function(drink){
        $scope.drink.id = drink.id;
        $scope.drink.name = drink.name;
        $scope.drink.description = drink.description;
        $scope.drink.price = drink.price;
    }
    $scope.updateAbout = function(){
        if(!$scope.validateText($scope.user.restaurant.name) || $scope.user.restaurant.name == undefined){
            alert("Enter restaurant name.");
            $scope.flag = false;
            return;
        }
        if(!$scope.validateAddress($scope.user.restaurant.description) || $scope.user.restaurant.description == undefined){
            alert("Enter restaurant description.");
            $scope.flag = false;
            return;
        }
        if(!$scope.validateAddress($scope.user.restaurant.address) || $scope.user.restaurant.address == undefined){
            alert("Enter restaurant address.");
            $scope.flag = false;
            return;
        }
        restaurantService.save($scope.user.restaurant).then(
            function(response){
                alert("Updated.");                                   
            },
            function(response){
                alert("Error wile updating.");
            }
        );
    }
      
      // Hide & show password function
    $scope.hideShowPassword = function(){
        if ($scope.inputType == 'password')
          $scope.inputType = 'text';
        else
          $scope.inputType = 'password';
    };
    $scope.addTables = function(){   
        $scope.flag = true;
        if($scope.res.tables.length+$scope.tables.total>50){
            alert("Restaurant max table number is 50.");
            $scope.flag = false;
            return;
        }
        if($scope.tables.numberOfSeats > 20){
            alert("Max seats per table is 20.");
            $scope.flag = false;
            return;
        }
        for (i = 0; i < $scope.tables.total; i++) { 
            $scope.table = {};  
            $scope.table.number = i+1;
            $scope.table.position = $scope.tables.segment;
            $scope.restaurantTables.push($scope.table);
        }
        $scope.configuration.tables = $scope.restaurantTables;
        $scope.configuration.restaurant = $scope.user.restaurant.id;
      
        restaurantTableService.save($scope.configuration).then(
            function(response){
                $scope.res = response.data;
                alert("Added tables.");  
                $scope.flag = false;
                $scope.restaurantTables = [];
                $scope.updateManagerData();
            },
            function(response){
                alert("Error wile adding.");
                $scope.flag = false;
                $scope.restaurantTables = [];
            }
        );
    }
    $scope.clickTable = function(table){ 
        if(table.status == 'REMOVE'){
            table.status = "FREE";
            var index = $scope.tablesForDelete.indexOf(table.id);            
            $scope.tablesForDelete.splice(index, 1);
        }else if(table.status == "FREE"){
            $scope.tablesForDelete.push(table.id);
            table.status = "REMOVE";
        }else if(table.status == "RESERVED"){
            alert("Table is reserved.");
        }
    }
    $scope.deleteTables = function(){
        $scope.flagDelete = true;
        $scope.tablesForDelete.push($scope.res.id);
        restaurantTableService.delete($scope.tablesForDelete).then(
            function(response){
                alert("Tables removed.");                   
                $scope.restaurantOpen();
                $scope.tablesForDelete = [];
                $scope.flagDelete = false;
            },
            function(response){
                alert("Error while removing tables.");
                $scope.tablesForDelete = [];
                $scope.flagDelete = false;                
            }
        );
    }
   
    $scope.updateManager = function(){
        if(!$scope.emailValidate($scope.user.email) || $scope.user.email == undefined){
            alert("Enter valid email.");
            $scope.flag = false;
            return;
        }
        if(!$scope.validatePass($scope.user.password) || $scope.user.password == undefined){
            alert("Enter valid password (Between 4 and 30 letters and numbers).");
            $scope.flag = false;
            return;
        }
        if(!$scope.validateText($scope.user.surname) || $scope.user.surname == undefined){
            alert("Enter valid surname (Between 2 and 30 letters).");
            $scope.flag = false;
            return;
        }
        if(!$scope.validateText($scope.user.name) || $scope.user.name == undefined){
            alert("Enter valid name (Between 2 and 30 letters).");
            $scope.flag = false;
            return;
        }
        $scope.updateManagerData();
    }
        
}]);