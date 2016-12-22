app.controller('systemManagerController', ['$scope', '$window', '$location', 'systemManagerService', 'loginService', 'restaurantService',
                                           'restaurantManagerService','$state',
	function ($scope, $window, $location, systemManagerService, loginService, restaurantService, restaurantManagerService, $state) {

	function init(){

		$scope.user={};
        $scope.restaurant={};
        $scope.restaurantManager={};
        $scope.systemManager={};
        $scope.user={};
		$scope.repeatedPassword="";
        $scope.user = loginService.getUser();
        $scope.register = "undefined";
        
        findAllSystemManagers();
        findAllRestaurantManagers();
        findAllRestaurants();

	};

	init();
        
    function setUser(user){
    }
    $scope.signUp = function(type){
        if(type == 'systemManager'){            
            $scope.systemManager.role = "SYSTEM_MANAGER";
            systemManagerService.save($scope.systemManager).then(
                function(response){
                    alert("Uspesna registracija.");  
                    $scope.register = "undefined";
                    $scope.systemManager = {};
                    findAllSystemManagers();
                },
                function(response){
                    alert("Neuspesna registracija.");                   
                }
            );
        }else if(type == 'restaurantManager'){
            restaurantManagerService.save($scope.restaurantManager).then(
                function(response){
                    alert("Uspesna registracija.");  
                    $scope.register = "undefined";
                    $scope.restaurantManager = {};
                    findAllRestaurantManagers();   
                },
                function(response){
                    alert("Neuspesna registracija.");                   
                }
            );
        }else if(type == 'restaurant'){
            restaurantService.save($scope.restaurant).then(
                function(response){
                    alert("Uspesna registracija.");    
                    $scope.register = "undefined";
                    $scope.restaurant = {};
                    findAllRestaurants();
                },
                function(response){
                    alert("Neuspesna registracija.");                   
                }
            );
        }else{
             alert("Greska.");      
        }
    }

    $scope.registration = function(){
        $scope.register = "register";
    }    
    
    $scope.list = function(){
        $scope.register = "undefined";
    } 
    $scope.update = function(){
        systemManagerService.update($scope.user).then(
            function(response){
                alert("Updated.");                 
            },
            function(response){
                alert("Update failed.");
            }
        );
    } 
       
        
    function findAllRestaurants(){
        restaurantService.findAll().then(
            function(response){
                    $scope.restaurants=response.data;                 
            }
        );
    }    
                   
    function findAllSystemManagers(){
        systemManagerService.findAll().then(
            function(response){
                    $scope.systemManagers=response.data;                 
            }
        );
    } 
    function findAllRestaurantManagers(){
        restaurantManagerService.findAll().then(
            function(response){
                $scope.restaurantManagers=response.data;  
            }
        );
    } 

}]);