app.controller('restaurantManagerController', ['$scope', '$window', '$location', 'restaurantManagerService', 'loginService','restaurantService',
                                               'loginService','foodService','$state','drinkService','restaurantTableService',
	function ($scope, $window, $location, restaurantManagerService, loginService, restaurantService, loginService, foodService, $state, drinkService,restaurantTableService) {
        
	function init(){
       
        $scope.user = loginService.getUser(); 
        $scope.food = {};	
        $scope.drink = {};
        $scope.restaurantTables = [];
        $scope.tables = {};
        $scope.table = {};
        $scope.configuration = {};
        $scope.tablesForDelete = [];
        $scope.inputType = 'password';
	};

	init();
        
    $scope.restaurantOpen = function(){
        restaurantManagerService.findOne($scope.user.id).then(
            function(response){                
                $scope.res = response.data[0].restaurant;
            },
            function(response){
                alert("Error wile adding.");
            }
        );
    }
    $scope.deleteFood = function(food){
        var index = $scope.res.menu.indexOf(food);
        foodService.delete(food.id).then(
            function(response){
                $scope.res.menu.splice(index, 1);
            },
            function(response){
                alert("Error wile deleting.");
            }
        );
    }
    $scope.createFood = function(){
         $scope.food.resId = $scope.user.restaurant.id;
         foodService.save($scope.food).then(
            function(response){
                $scope.food = {};                
                $scope.res = response.data;
                alert("Added.");                                   
            },
            function(response){
                alert("Error wile adding.");
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
        var index = $scope.res.drinks.indexOf(drink);
        drinkService.delete(drink.id).then(
            function(response){
                $scope.res.drinks.splice(index, 1);
            },
            function(response){
                alert("Error wile deleting.");
            }
        );
    }
    $scope.createDrink = function(){
         $scope.drink.resId = $scope.user.restaurant.id;
         drinkService.save($scope.drink).then(
            function(response){
                $scope.drink = {};    
                $scope.res = response.data;
                alert("Added.");                                   
            },
            function(response){
                alert("Error wile adding.");
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
            },
            function(response){
                alert("Error wile adding.");
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
        $scope.tablesForDelete.push($scope.res.id);
        restaurantTableService.delete($scope.tablesForDelete).then(
            function(response){
                alert("Tables removed.");                   
                $scope.restaurantOpen();
                $scope.tablesForDelete = [];
            },
            function(response){
                alert("Error wile removing tables.");
            }
        );
    }
        
}]);