app.controller('groceryController', ['$scope', '$window', '$location', 'loginService','restaurantService','$state','groceryService','offerService',
	function ($scope, $window, $location, loginService, restaurantService,$state, groceryService, offerService) {
        
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
        $scope.flag = false;
        $scope.flagDelete = false;
        $scope.getFoodStuff($scope.user.restaurant.id);
        $scope.getDrinkGroceries($scope.user.restaurant.id);
        $scope.state = "undefined";
        $scope.itemType = 'text';
        $scope.item = {};
        $scope.editing = "false";
        $scope.minDate = "2017-02-28";
        $scope.purchaseGrocery = {};
        $scope.state = "undefined";
        $scope.state = "undefined";
        $scope.type = "undefined";
        $scope.expandOffer = {};
        $scope.purchaseFood = [];
        $scope.purchaseDrinks = [];  
        $scope.getPastOffers($scope.user.restaurant.id);
    }
    
    $scope.getDate = function(date){
        var year   = parseInt(date.substring(0,4));
        var month   = parseInt(date.substring(5,7));
        var day  = parseInt(date.substring(8,10));       
        var date1 = new Date(year, month-1, day);
        return date1;
    }
        
    $scope.getPurchaseList = function(id){        
        groceryService.findAll(id).then(
            function(response){     
                $scope.purchaseDrinks = [];
                $scope.purchaseFood = [];
                $scope.startDate = "";
                $scope.endDate = "";
                $scope.purchaseList = response.data;
                if($scope.purchaseList.length != 0 && $scope.purchaseList.accepted == false){
                    for(var i = 0; i < $scope.purchaseList.food.length; i++){
                         $scope.purchaseFood.push( $scope.purchaseList.food[i]);
                    }
                    for(var i = 0; i < $scope.purchaseList.drinks.length; i++){
                         $scope.purchaseDrinks.push($scope.purchaseList.drinks[i]);
                    }                    
                    $scope.startDate = $scope.getDate($scope.purchaseList.startDate);
                    $scope.endDate = $scope.getDate($scope.purchaseList.endDate);
                    $scope.purchaseGrocery.id = $scope.purchaseList.id;
                    $scope.state = "groceryList";
                }else{
                    $scope.state = "newPurchase";
                }
            },
            function(response){
                alert("Error while fetching purchase list.");
            }
        );
    }
        
    $scope.getFoodStuff = function(id){
        restaurantService.getFoodStuff(id).then(
            function(response){                
                $scope.foodStuff = response.data;                
            },
            function(response){
                alert("Error while fetching foodstuff.");
            }
        );
    }
    $scope.getDrinkGroceries= function(id){
        restaurantService.getDrinkGroceries(id).then(
            function(response){                
                $scope.drinkGroceries = response.data;                
            },
            function(response){
                alert("Error while fetching drink groceries.");
            }
        );
    }
    $scope.getPastOffers = function(id){
        groceryService.getPastOffers(id).then(
            function(response){                
                $scope.pastOffers = response.data;                
            },
            function(response){
                alert("Error wile fetching past offers.");
            }
        );
    }
             
	init();   
    
    $scope.newPurchaseList = function(){        
        $scope.getPurchaseList($scope.user.restaurant.id);        
        $scope.state = "newPurchase";
        $scope.editing = "false";
    }    
    $scope.findDesc = function(name){
        for(var i =0; i<$scope.drinkGroceries.length; i++){
            if($scope.drinkGroceries[i].name == name){
                $scope.item.drinkGrocery = $scope.drinkGroceries[i].id;
                return $scope.drinkGroceries[i];
            }
        }
        for(var i =0; i<$scope.foodStuff.length; i++){
            if($scope.foodStuff[i].name == name){
                $scope.item.foodStuff = $scope.foodStuff[i].id;
                return $scope.foodStuff[i];
            }
        }
        return "";
    }
    $scope.DropDownChanged = function(item1){
        if(item1 == ""){
            $scope.itemType = 'text';
            $scope.item.description = "";            
        }else{
            var itemm = $scope.findDesc(item1);
            $scope.item.description = itemm.description;           
            $scope.itemType = 'hidden';
        }
    }
    $scope.validateQuantity = function(text){
        return /^[1-9][0-9]{0,3}$/.test(text);        
    }
    $scope.validateText = function(text){
        return /^[A-Za-z][A-Za-z0-9 \_]{2,30}$/.test(text);
    }
    $scope.validateDesc = function(text){
        return /^[A-Za-z][A-Za-z0-9 ]{2,50}$/.test(text);
    }
    $scope.addItem = function(){
        $scope.flag = true;
        if($scope.item.name == undefined || !$scope.validateText($scope.item.name)){
            alert("Enter valid name (Between 2 and 30 letters).");
            $scope.flag = false;
            return;
        }
        if($scope.item.name == undefined || !$scope.validateDesc($scope.item.description)){
            alert("Enter valid description (Between 2 and 50 letters or numbers).");
            $scope.flag = false;
            return;
        }
        if($scope.item.quantity == undefined || !$scope.validateQuantity($scope.item.quantity)){
            alert("Enter valid quantity (Whole number between 1 and 9999).");
            $scope.flag = false;
            return;
        }
        if($scope.type  == "food"){
            for(var i =0; i<$scope.purchaseFood.length; i++){
                if($scope.purchaseFood[i].name == $scope.item.name && $scope.purchaseFood[i].description == $scope.item.description){
                    $scope.purchaseFood[i] = $scope.item;
                    $scope.flag = false;
                    $scope.item = {};
                    $scope.editing = "false";
                    return;
                }
            }
            $scope.item.restaurant = $scope.user.restaurant;
            $scope.purchaseFood.push($scope.item);
            $scope.item = {};
            $scope.flag = false;
            $scope.itemType = 'text';
            $scope.editing = "false";
        }else if($scope.type == "drink"){
            for(var i =0; i<$scope.purchaseDrinks.length; i++){
                if($scope.purchaseDrinks[i].name == $scope.item.name && $scope.purchaseDrinks[i].description == $scope.item.description){
                    $scope.purchaseDrinks[i] = $scope.item;
                    $scope.flag = false;
                    $scope.item = {};
                    $scope.editing = "false";
                    return;
                }
            }
            $scope.item.restaurant = $scope.user.restaurant;
            $scope.purchaseDrinks.push($scope.item);
            $scope.item = {};
            $scope.flag = false;
            $scope.itemType = 'text';
            $scope.editing = "false";
        }
    }
    $scope.editItem = function(item1,type){
        $scope.state = "addingItems";
        $scope.item = angular.copy(item1);
        $scope.itemType = 'text';
        $scope.editing = "true";
        if(type == "drink"){
           $scope.type = "drink";
           $scope.groceries = $scope.drinkGroceries;
        }else{
             $scope.type = "food";
             $scope.groceries = $scope.foodStuff;
        }
    }
    $scope.deleteItem = function(item1, type){
        if(type == 'food'){
            var index = $scope.purchaseFood.indexOf(item1);
            $scope.purchaseFood.splice(index,1);
        }else{
            var index = $scope.purchaseDrinks.indexOf(item1);
            $scope.purchaseDrinks.splice(index,1);
        }
    }
    $scope.create = function(){
        if($scope.startDate == undefined || $scope.startDate == ""){
            alert("Enter begin date.");
            return;
        }
        if($scope.endDate == undefined || $scope.endDate == ""){
            alert("Enter expire date.");
            return;
        }
        if($scope.startDate > $scope.endDate){
            alert("Date range is not valid.");
            return;
        }
        if($scope.purchaseDrinks.length == 0 && $scope.purchaseFood.length == 0){
            alert("At least one item must be in list.");
            return;
        }
        if( $scope.purchaseList &&  $scope.purchaseList.id){
            $scope.purchaseGrocery.id = $scope.purchaseList.id;
        }
        $scope.purchaseGrocery.endDate = $scope.endDate;
        $scope.purchaseGrocery.food = $scope.purchaseFood;
        $scope.purchaseGrocery.drinks = $scope.purchaseDrinks;
        $scope.purchaseGrocery.accepted =false;
        $scope.purchaseGrocery.restaurant = $scope.user.restaurant;
        var month = $scope.startDate.getMonth()+1;
        var date = $scope.startDate.getFullYear()+"-"+month+"-"+$scope.startDate.getDate();
        $scope.purchaseGrocery.startDate = date;
        var month1 = $scope.endDate.getMonth()+1;
        var date1 = $scope.endDate.getFullYear()+"-"+month1+"-"+$scope.endDate.getDate();
        $scope.purchaseGrocery.endDate = date1;
        $scope.purchaseGrocery.startDate = date;
        $scope.purchaseGrocery.endDate = date1;
        groceryService.save($scope.purchaseGrocery).then(
            function(response){  
                $scope.getPurchaseList($scope.user.restaurant.id);
                $scope.state = "groceryList";
            },
            function(response){
                alert("Error wile creating grocery list.");
            }
        );
    }
    $scope.addItems = function(type){
         $scope.state = "addingItems";
        if(type == "drink"){
           $scope.type = "drink";
           $scope.groceries = $scope.drinkGroceries;
        }else{
             $scope.type = "food";
             $scope.groceries = $scope.foodStuff;
        }
    }
    $scope.exitAdding = function(){
        $scope.state = "newPurchase";
    }
    $scope.deletePurchaseList = function(){
        groceryService.delete($scope.purchaseGrocery.id).then(
            function(response){                
                $scope.purchaseGroceries = {};
                $scope.startDate = "";
                $scope.endDate = "";
                $scope.purchaseGrocery = {};
                $scope.getPurchaseList($scope.user.restaurant.id);
                $scope.state = "undefined";
            },
            function(response){
                alert("Error wile fetching groceries.");
            }
        );
    }
    $scope.back = function(){
        $scope.state = "undefined";
        $scope.expandOffer = {};
    }
    $scope.expandOffers = function(){
        offerService.getOffers($scope.purchaseGrocery.id).then(
            function(response){                
                $scope.offers = response.data;
                $scope.state = "offers";
            },
            function(response){
                alert("Error wile fetching offers.");
            }
        );
    }
    $scope.expand = function(index){
        $scope.expandOffer =  $scope.offers[index];
    }
    $scope.acceptOffer = function(offer){
        offerService.acceptOffer(offer.id).then(
            function(response){    
                init();
                $scope.state = "undefined";
            },
            function(response){
                alert("Error while accepting offer.");
            }
        );
    }
    $scope.rejectOffer = function(offer){
        offerService.rejectOffer(offer.id).then(
            function(response){                
                $scope.state = "undefined";
            },
            function(response){
                alert("Error while rejecting offer.");
            }
        );
    }
    $scope.editList = function(){
        $scope.state = "newPurchase";
    }
    $scope.backGroceryList = function(){
        if($scope.purchaseList && $scope.purchaseList.id){
            $scope.state = "groceryList";
        }else{
            $scope.state = "undefined";
        }
    }
    $scope.expandPastItems = function(offer){
        $scope.expandPastOffer = offer;
    }
}]);