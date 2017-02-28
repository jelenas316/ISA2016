app.controller('bidderController', ['$scope', '$window', '$location', 'bidderService', 'loginService','restaurantService','$state',
	function ($scope, $window, $location, bidderService, loginService, restaurantService, $state) {
        
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
        $scope.getAllBidders($scope.user.restaurant.id);
        $scope.food = {};	
        $scope.drink = {};
        $scope.inputType = 'password';
        $scope.flag = false;
        $scope.state = "undefined";
        $scope.bidder = {};
    }
     $scope.getAllBidders = function(restaurant){
        bidderService.findAll(restaurant).then(
            function(response){                
                $scope.bidders = response.data;
            },
            function(response){
                alert("Error while fetching bidders.");
            }
        );
    }
	init();
        
    $scope.expandOffers = function(bidder){
        $scope.offers=bidder.offers;
        $scope.state = "offers";
    }
    $scope.addBidder = function(){
        $scope.state = "newBidder";
    }
        
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
      // Hide & show password function
    $scope.hideShowPassword = function(){
        if ($scope.inputType == 'password')
          $scope.inputType = 'text';
        else
          $scope.inputType = 'password';
    };

    $scope.updateManager = function(){
        restaurantManagerService.update($scope.user).then(
            function(response){
                $scope.user = response.data;
                alert("Successfuly updated.");
            },
            function(response){
                alert("Error wbidderhile updating user.");
            }
        );
    }
    $scope.exit = function(){
        $scope.state = "undefined";
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
        if(!$scope.emailValidate($scope.bidder.email) || $scope.bidder.email == undefined){
            alert("Enter valid email.");
            $scope.flag = false;
            return;
        }
        if(!$scope.validatePass($scope.bidder.password) || $scope.bidder.password == undefined){
            alert("Enter valid password (Between 4 and 30 letters and numbers).");
            $scope.flag = false;
            return;
        }
        if(!$scope.validateText($scope.bidder.name) || $scope.bidder.name == undefined){
            alert("Enter valid name (Between 2 and 30 letters).");
            $scope.flag = false;
            return;
        }
        $scope.bidder.restaurant = $scope.user.restaurant;
        $scope.bidder.offers = [];
        $scope.bidder.passwordStatus = 0;
        $scope.bidder.role = "BIDDER";
        bidderService.save($scope.bidder).then(
            function(response){
                if(response.data == ""){
                   alert("Bidder with email \""+ $scope.bidder.email +"\" alredy exist.");
                   $scope.flag = false;
                   return;
                }
                $scope.getAllBidders($scope.user.restaurant.id);
                alert("Bidder \""+ $scope.bidder.email +"\" is created.");
                $scope.state = "undefined";
                $scope.flag = false;
            },
            function(response){
                alert("Error while creating bidder.");
                $scope.flag = false;
            }
        );
    }
        
}]);