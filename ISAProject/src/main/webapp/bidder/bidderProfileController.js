app.controller('bidderProfileController', ['$scope', '$window', '$location', 'bidderService', 'loginService','restaurantService','$state','groceryService','offerService',
	function ($scope, $window, $location, bidderService, loginService, restaurantService, $state, groceryService, offerService) {
        
	function init(){
        if($window.localStorage.getItem("user") == undefined){
             $state.go('login'); 
             return;
        }
              
        var result = JSON.parse(localStorage.getItem("user"));
        $scope.user = result[0];
        if($scope.user.role != 'BIDDER'){
             $state.go('login'); 
             return; 
        }
        if($scope.user.passwordStatus == 0){
            $scope.state="changePass";
            $scope.inputType = 'password';
            $scope.newPassword = "";
        }else{
            $scope.purchaseList = {};
            $scope.flag = false;
            $scope.state = "undefined";
            $scope.bidder = {};
            $scope.inputType = 'password';
            $scope.newPassword = "";
            $scope.getOffer($scope.user.email);  
            $scope.expandPastOffer = {};
        }
    }
        
     // Hide & show password function
    $scope.hideShowPassword = function(){
        if ($scope.inputType == 'password')
          $scope.inputType = 'text';
        else
          $scope.inputType = 'password';
    };
    $scope.getDate = function(date){
        var year   = parseInt(date.substring(0,4));
        var month   = parseInt(date.substring(5,7));
        var day  = parseInt(date.substring(8,10));       
        var date1 = new Date(year, month-1, day);
        return date1;
    }
     $scope.getPurchaseList = function(id){
        $scope.food = [];
        $scope.drinks = [];
        $scope.futurePurchase = {};
        var today = new Date();
        groceryService.findAll(id).then(
            function(response){  
                var list = response.data;
                if( list !="" && list.accepted==false){   
                    var groceryStartDate = $scope.getDate(list.startDate);
                    if(groceryStartDate <= today){
                        $scope.purchaseList = response.data;
                        for(var i =0;i < $scope.purchaseList.food.length; i++){
                            $scope.food.push($scope.purchaseList.food[i]);
                        }
                        for(var i =0;i < $scope.purchaseList.drinks.length; i++){
                            $scope.drinks.push($scope.purchaseList.drinks[i]);
                        }
                    }else{
                        $scope.futurePurchase = response.data;
                    }
                }
            },
            function(response){
                alert("Error while fetching bidders.");
            }
        );
    }
     $scope.getPastOffers = function(email){
         offerService.getPastOffers(email).then(
            function(response){  
                $scope.pastOffers = response.data;
            },
            function(response){
                alert("Error while fetching offer.");
            }
        );
     }
     $scope.getOffer = function(email){
         offerService.getActiveOffer(email).then(
            function(response){
                $scope.getPastOffers($scope.user.email);
                if(response.data != ""){
                    $scope.offer = response.data;
                    $scope.state = "activeOffer";                   
                   
                }else{
                    $scope.state = "undefined";
                    $scope.getPurchaseList($scope.user.restaurant.id);
                }
            },
            function(response){
                alert("Error while fetching offer.");
            }
        );
     }
    
	init();
    $scope.validatePass = function (text) {
        return /^[A-Za-z0-9\_\-]{4,30}$/.test(text);
    }
        
    $scope.changePassword = function(){
        if($scope.newPassword == $scope.user.password){
            alert("Old and new password are equal.");
            return;
        }
        if(!$scope.validatePass($scope.newPassword) || $scope.newPassword == undefined){
            alert("Enter valid password (Between 4 and 30 letters and numbers).");
            return;
        }
        $scope.user.password = $scope.newPassword;
        $scope.user.passwordStatus = 1;
        bidderService.update($scope.user).then(
            function(response){             
                $state.go('login'); 
            },
            function(response){
                alert("Error while fetching bidders.");
            }
        );
    }
        
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
                alert("Error while updating user.");
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
        bidderService.save($scope.bidder).then(
            function(response){
                if(response.data == ""){
                   alert("Bidder with email \""+ $scope.bidder.email +"\" alredy exist.");
                   $scope.flag = false;
                   return;
                }
                $scope.getAllBidders($scope.user.restaurant);
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
    $scope.updateManager = function(){
         if(!$scope.emailValidate($scope.user.email) || $scope.user.email == undefined){
            alert("Enter valid email.");
            return;
        }
        if(!$scope.validatePass($scope.user.password) || $scope.user.password == undefined){
            alert("Enter valid password (Between 4 and 30 letters and numbers).");
            return;
        }
        if(!$scope.validateText($scope.user.name) || $scope.user.name == undefined){
            alert("Enter valid name (Between 2 and 30 letters).");
            return;
        }
        bidderService.update($scope.user).then(
            function(response){
                $scope.user = response.data;
                var user = [];
                user.push(response.data);
                $window.localStorage.setItem("user", JSON.stringify(user));
                alert("Successfuly updated.");
            },
            function(response){
                alert("Error while updating user.");
            }
        );
    }
    $scope.validatePrice = function(text){
        return /^[1-9][0-9]{0,3}(\.[0-9]{1,2}){0,1}$/.test(text);
    }
    $scope.validateTime = function(text){
        return /^[1-9][0-9]{0,1}$/.test(text);
    }
    $scope.purchase = function(){
        var totalPrice = 0;
        for(var i =0;i < $scope.purchaseList.food.length; i++){
            if($scope.purchaseList.food[i].price == undefined || !$scope.validatePrice($scope.purchaseList.food[i].price)){
                alert("Price for food \""+$scope.purchaseList.food[i].name+"\" is not valid.");
                return;
            }else{
                totalPrice += $scope.purchaseList.food[i].quantity*$scope.purchaseList.food[i].price;
            }
        }
        for(var i =0;i < $scope.purchaseList.drinks.length; i++){
            if($scope.purchaseList.drinks[i].price == undefined || !$scope.validatePrice($scope.purchaseList.drinks[i].price)){
                alert("Price for drink \""+$scope.purchaseList.drinks[i].name+"\" is not valid.");
                return;
            }else{
                totalPrice += $scope.purchaseList.drinks[i].quantity*$scope.purchaseList.drinks[i].price;
            }
        }
        if(!$scope.validateTime($scope.offer.deliveryTime) || $scope.offer.deliveryTime==undefined){
            alert("Enter delivery time.(Between 1 and 99)");
            return;
        }
        $scope.offer.food = $scope.purchaseList.food;
        $scope.offer.drinks = $scope.purchaseList.drinks;
        $scope.offer.groceryList = $scope.purchaseList;
        $scope.offer.accepted = "WAITING";
        $scope.offer.totalPrice = totalPrice;
        $scope.offer.bidder = $scope.user;
        $scope.state = "offer";
    }
    $scope.getGroceryList = function(id){
        groceryService.findOne(id).then(
            function(response){                
                $scope.purchaseList = response.data;
                $scope.purchaseList.food = $scope.offer.food;
                $scope.purchaseList.drinks = $scope.offer.drinks;
            },
            function(response){
                alert("Error wile adding.");
            }
        );
    }
    $scope.editOffer = function(offer){
        $scope.state = "undefined";
        $scope.offer = offer;
        $scope.getGroceryList($scope.offer.groceryList.id);      
    }
    $scope.deleteOffer = function(offer){        
        offerService.delete(offer.id).then(
            function(response){                
                alert("Offer deleted.");
                $scope.offer = {};
                init();
            },
            function(response){
                alert("Error wile adding.");
            }
        );
    }   
    $scope.addOffer = function(){
        offerService.save($scope.offer).then(
            function(response){                
                alert("Offer created.");
                init();
            },
            function(response){
                alert("Error wile adding offer.");
            }
        );
    }
    $scope.expandItems = function(offer){
        $scope.expandPastOffer = offer;
    }
}]);