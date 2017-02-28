app.controller('waiterController', ['$scope', '$window', '$location', 'waiterService', '$stateParams', '$state',
	function ($scope, $window, $location, waiterService, $stateParams, $state) {
	
	function init(){
		
		 if($window.localStorage.getItem("user") == undefined){
             $state.go('login'); 
             return;
        }
		 
		var result = JSON.parse(localStorage.getItem("user"));
	    $scope.user = result[0];
	    $scope.copyOfUser=$scope.user;
	    $scope.repeatedPassword=$scope.user.password;
	    $scope.currentOrder=undefined;
	    $scope.reservationOrder={};
	    //console.log($scope.user);
	    
	    waiterService.findAllOrders().then (
	    		function(response) {
	    			//console.log(response.data);
	    			$scope.orders=response.data;
	    		}
	    );
	    //console.log($scope.repeatedPassword);
	   
	   
	};
	
	init();
	
	$scope.changeProfile = function(){
		
		console.log("prvo");
		if($scope.user.password == $scope.repeatedPassword){
			update();

		}else{
			alert("Wrong password.");
		}
		
	};
	
	function update(){
		console.log("drugo");
		waiterService.findOne($scope.user.email).then(
				function(response){
					//$scope.copyOfUser.friends=response.data.friends;
					console.log("trece");
					waiterService.update($scope.user).then(
						function(response){
							alert("Successfuly changed.");
							//initializeData($scope.user.email);
						},
						function(response){
							alert("Error while registering.");
						}
					);
				}
		);
	}
	
	$scope.change = function(order){
		$scope.currentOrder=order;
		console.log($scope.currentOrder);
		console.log($scope.currentOrder.restaurantTable);
	}
	
	$scope.back = function() {
		init();
		//console.log("Posle back: " + $scope.currentOrder);
	}
	
	$scope.cancelF = function(food) {
		waiterService.cancelFood($scope.currentOrder.id, food.id).then(
				function(){
					alert('Canceled ' + food.food.name + ".")
					waiterService.findOneOrder($scope.currentOrder.id).then(
						function(response){
							$scope.currentOrder=response.data;
						}
					);
				}
		);
	}
	
	$scope.cancelD = function(drink) {
		waiterService.cancelDrink($scope.currentOrder.id, drink.id).then(
				function(){
					alert('Canceled ' + drink.drink.name + ".")
					waiterService.findOneOrder($scope.currentOrder.id).then(
						function(response){
							$scope.currentOrder=response.data;
						}
					);
				}
		);
	}
	
	$scope.addFood = function(food) {
		var orderFood={};
		orderFood.quantity=1;
		orderFood.food=food;
		orderFood.foodStatus='WAITING';
		console.log(orderFood);
		waiterService.saveFood(orderFood).then(
				function(response){
					$scope.currentOrder.food.push(response.data);
					console.log($scope.currentOrder);
				}
		);
	}
	
	$scope.addDrink = function(drink) {
		var orderDrink={};
		orderDrink.quantity=1;
		orderDrink.drink=drink;
		orderDrink.foodStatus='WAITING';
		console.log(orderDrink);
		waiterService.saveDrink(orderDrink).then(
				function(response){
					$scope.currentOrder.drinks.push(response.data);
					console.log($scope.currentOrder);
				}
		);
	}

}]);