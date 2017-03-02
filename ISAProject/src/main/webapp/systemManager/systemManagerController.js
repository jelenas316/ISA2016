app.controller('systemManagerController', ['$scope', '$window', '$location', 'systemManagerService', 'loginService', 'restaurantService',
                                           'restaurantManagerService',
	function ($scope, $window, $location, systemManagerService, loginService, restaurantService, restaurantManagerService) {

	function init(){
        
        if($window.localStorage.getItem("user") == undefined){
             $state.go('login'); 
             return;
        }              
        var result = JSON.parse(localStorage.getItem("user"));
        $scope.user = result[0];
        if($scope.user.role != 'SYSTEM_MANAGER'){
             $state.go('login'); 
             return; 
        }
        $scope.restaurant={};
        $scope.restaurantManager={};
        $scope.systemManager={};
		$scope.repeatedPassword="";
        $scope.register = "undefined";
        $scope.inputType ='password';
        
        findAllSystemManagers();
        findAllRestaurantManagers();
        findAllRestaurants();
        findAllRestaurantsWithoutManagers();

	};

	init();
    $scope.emailValidate = function (email) {
        return /^[A-Za-z]+[A-Za-z0-9\.\-\_]*\@[A-Za-z0-9]+\.[A-Za-z]{2,4}$/.test(email);
    }        
    $scope.validateSize = function(text){
        return /^[1-9][0-9]{0,1}(\,[1-9]){0,1}$/.test(text);        
    }
    $scope.validatePass = function (text) {
        return /^[A-Za-z0-9\_\-]{4,30}$/.test(text);
    }
    $scope.validateText = function(text){
        return /^[A-Za-z0-9 ]{2,30}$/.test(text);
    }
    $scope.emailValidate = function (email) {
        return /^[A-Za-z]+[A-Za-z0-9\.\-\_]*\@[A-Za-z0-9]+\.[A-Za-z]{2,4}$/.test(email);
    }   
    $scope.validateUser = function(user){
         if(!$scope.emailValidate(user.email) || user.email == undefined){
            alert("Enter valid email.");
            $scope.flag = false;
            return false;
        }
        if(!$scope.validatePass(user.password) || user.password == undefined){
            alert("Enter valid password (Between 4 and 30 letters and numbers).");
            $scope.flag = false;
            return false;
        }
        if(!$scope.validateText(user.name) || user.name == undefined){
            alert("Enter valid name (Between 2 and 30 letters).");
            $scope.flag = false;
            return false;
        }
        if(!$scope.validateText(user.surname) || user.surname == undefined){
            alert("Enter valid surname (Between 2 and 30 letters).");
            $scope.flag = false;
            return false;
        }
        return true;
    }
    $scope.signUp = function(type){
        if(type == 'systemManager'){
            if($scope.systemManager.password == $scope.systemManager.repeatedPassword 
               && $scope.validateUser($scope.systemManager)){
                $scope.systemManager.role = "SYSTEM_MANAGER";
                systemManagerService.save($scope.systemManager).then(
                    function(response){
                        alert("Registration successful.");  
                        $scope.register = "undefined";
                        $scope.systemManager = {};
                        findAllSystemManagers();
                    },
                    function(response){
                        alert("Registration failed.");                   
                    }
                );
            }else{
                alert("Password and repeated password not match.");
            }
        }else if(type == 'restaurantManager'){
            if($scope.restaurantManager.password == $scope.restaurantManager.repeatedPassword && $scope.validateUser($scope.restaurantManager)){
                restaurantManagerService.save($scope.restaurantManager).then(
                    function(response){
                        alert("Registration successful.");  
                        $scope.register = "undefined";
                        $scope.restaurantManager = {};
                        findAllRestaurantManagers(); 
                        findAllRestaurantsWithoutManagers();
                    },
                    function(response){
                        alert("Registration failed.");                   
                    }
                );
            }else{
                alert("Password and repeated password not match.");
            }
        }else if(type == 'restaurant'){
            if(!$scope.validateText($scope.restaurant.name) || $scope.restaurant.name == undefined){
                alert("Enter valid name (Between 2 and 30 letters).");
                $scope.flag = false;
                return;
            }
            if(!$scope.validateText($scope.restaurant.description) || $scope.restaurant.description == undefined){
                alert("Enter valid description (Between 2 and 30 letters).");
                $scope.flag = false;
                return;
            }
            if(!$scope.validateText($scope.restaurant.address) || $scope.restaurant.address == undefined){
                alert("Enter valid address (Between 2 and 30 letters).");
                $scope.flag = false;
                return;
            }
            restaurantService.save($scope.restaurant).then(
                function(response){
                    alert("Registration successful.");    
                    $scope.register = "undefined";
                    $scope.restaurant = {};
                    findAllRestaurants();
                    findAllRestaurantsWithoutManagers();
                },
                function(response){
                    alert("Registration failed.");                   
                }
            );
        }else{
             alert("Error while registring.");      
        }
    }

    $scope.registration = function(){
        $scope.register = "register";
    }    
    
    $scope.list = function(){
        $scope.register = "undefined";
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
        systemManagerService.update($scope.user).then(
            function(response){
                alert("Updated.");  
                var user = [];
                user.push(response.data);
                $window.localStorage.setItem("user", JSON.stringify(user));
                $scope.user = response.data;
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
    function findAllRestaurantsWithoutManagers(){
        restaurantService.findRestaurantsWithoutManagers().then(
            function(response){
                    $scope.restaurantsWihoutManagers=response.data;                 
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
          // Hide & show password function
    $scope.hideShowPassword = function(){
        if ($scope.inputType == 'password')
          $scope.inputType = 'text';
        else
          $scope.inputType = 'password';
    };

}]);