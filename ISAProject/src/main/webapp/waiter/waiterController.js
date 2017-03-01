app.controller('waiterController', ['$scope', '$window', '$location', 'waiterService', '$stateParams', '$state',
	function ($scope, $window, $location, waiterService, $stateParams, $state) {
	
	function init(){
		
		 if($window.localStorage.getItem("user") == undefined){
             $state.go('login'); 
             return;
        }
		 
		var result = JSON.parse(localStorage.getItem("user"));
	    $scope.user = result[0];
	    $scope.copyOfUser={};
	    $scope.repeatedPassword=$scope.user.password;
	    $scope.repeatedPassword1=$scope.user.password;
	    $scope.currentOrder=undefined;
	    $scope.reservationOrder={};
	    
	    waiterService.findAllOrders().then (
	    		function(response) {
	    			console.log(response.data);
	    			$scope.orders=response.data;
	    		}
	    );
	    
	    waiterService.findOneRestaurant($scope.user.restaurant.id).then (
	    		function(response) {
	    			$scope.restaurant=response.data;
	    			console.log($scope.restaurant);
	    		}
	    );
	    
	    waiterService.findAll($scope.user.restaurant.id).then(
	    	       function(response){
	    	    	   $scope.allWaiters=response.data;
	    	       }
	    );
	    
	    $scope.check=false;
	    $scope.totalCheck=0;
	    $scope.orderForCheck={};
	    
	    if($scope.user.activated==false){
	    	console.log(" korisnik je false");
	    }
	    else{
	    	console.log("korisnik je true");
	    }
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
					waiterService.saveOrder($scope.currentOrder).then (
						function(response){
						alert("Food has been ordered");
						}
					);
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
					waiterService.saveOrder($scope.currentOrder).then (
							function(response){
							alert("Drink has been ordered");
							}
					);
				}
		);
	}
	
	$scope.changePassword = function() {

		console.log($scope.user);
		console.log($scope.copyOfUser);
		if($scope.user.password != $scope.copyOfUser.password && $scope.copyOfUser.password == $scope.copyOfUser.repeatedPassword){
			$scope.user.password=$scope.copyOfUser.password;
			$scope.user.activated=true;
			waiterService.update($scope.user).then(
					function(response){
						alert("Password successfuly changed.");
					}
			);
		}else{
			alert("Wrong password:");
		}
	}
	
	 $scope.showShedule = function(){
		  if($scope.sheduleDate!=undefined){
			  var date = dateToString($scope.sheduleDate)
			  $scope.sheduleForDate=[];
			  for(waiter in $scope.allWaiters){
				  	var oneWaiter={};
				  	oneWaiter.name=$scope.allWaiters[waiter].name;
				  	oneWaiter.surname=$scope.allWaiters[waiter].surname;
				  	oneWaiter.shifts=[];
				  	for(shift in $scope.allWaiters[waiter].shifts){
				  			if($scope.allWaiters[waiter].shifts[shift].startDate==date || 
				  			$scope.allWaiters[waiter].shifts[shift].endDate==date)
				  					oneWaiter.shifts.push($scope.allWaiters[waiter].shifts[shift]);
				  	}	
				  	if(oneWaiter.shifts.length>0)
				  			$scope.sheduleForDate.push(oneWaiter);
			  }
		  }
	 }

		 function dateToString(date){
			 var convertedDate = date.getFullYear() + "-";
			 if((date.getMonth()+1)<10){
				 	convertedDate += "0" + (date.getMonth()+1) + "-"; 
			 }else{
				 	convertedDate += date.getMonth()+1 + "-"; 
			 }
			 if(date.getDate()<10){
				 	convertedDate += "0" + date.getDate();
			 }else{
				 	convertedDate += date.getDate();
			 }
			 return convertedDate;
		 }
		 
		 $scope.check = function(order){
			 $scope.currentOrder = order;
			 waiterService.findOneOrder($scope.currentOrder.id)(
					 function(response){
						
					 }
			 );
		 }
		 
		 $scope.check = function(order){
			 $scope.totalCheck=calculate(order);
			 $scope.orderForCheck=order;
			 $scope.check=true;
			 waiterService.deleteOrder($scope.orderForCheck.id).then (
					 function(response){
						 alert("deleted");
						 waiterService.findAllOrders().then (
							       function(response) {
							    	   console.log(response.data);
							    	   $scope.orders=response.data;
							       }
						 );
					 }
			 );
			  
		 } 
			 
		 $scope.backToOrder=function(){
			  $scope.check=false;
			  waiterService.findAllOrders().then (
				       function(response) {
				    	   console.log(response.data);
				    	   $scope.orders=response.data;
				       }
			  );
		 }
		 
		 function calculate (order){
			  var calculated = 0;
			  for(drink in order.drinks){
				  calculated+=order.drinks[drink].quantity * order.drinks[drink].drink.price;
			  }
			  for(f in order.food){
				  calculated+=order.food[f].quantity * order.food[f].food.price;
			  }
			  
			  return calculated;
		 }

}]);