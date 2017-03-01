app.controller('cookController', ['$scope', '$window', '$location', 'cookService', '$stateParams', '$state',
	function ($scope, $window, $location, cookService, $stateParams, $state) {
	
	function init(){
		
		 if($window.localStorage.getItem("user") == undefined){
             $state.go('login'); 
             return;
        }
		 
		 var result = JSON.parse(localStorage.getItem("user"));
		 $scope.user = result[0];
		 //console.log($scope.user);
		 $scope.copyOfUser={};
		 $scope.repeatedPassword=$scope.user.password;
		 $scope.currentOrder = undefined;
		 $scope.currentOrderedFood=undefined;
		 
		 cookService.findAllFood().then (
		    		function(response) {
		    			console.log("Response data " + response.data);
		    			$scope.food=response.data;
		    		}
		 );
		 
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
		cookService.findOne($scope.user.email).then(
				function(response){
					//$scope.copyOfUser.friends=response.data.friends;
					console.log("trece");
					cookService.update($scope.user).then(
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
	
	$scope.changeStatus = function(food) {
		$scope.currentOrderedFood = food;
		console.log("CurrentOrderedFood: " + $scope.currentOrderedFood)
		//$scope.currentOrderedFo.foodStatus = "ACCEPTED";
		cookService.findOneFood(food.id).then (  // ili currentOrder.id
				function(response){
//					$scope.orderedFood = response.data;
//					$scope.orderedFood.foodStatus = "ACTIVE";
//					alert("Status changed to active");
					console.log("Iz response: " + response.data);
				}
		);
		
	}
	
	$scope.finishOrder = function() {
		
	}
	
	$scope.changePassword = function() {

		console.log($scope.user);
		console.log($scope.copyOfUser);
		if($scope.user.password != $scope.copyOfUser.password && $scope.copyOfUser.password == $scope.copyOfUser.repeatedPassword){
			$scope.user.password=$scope.copyOfUser.password;
			$scope.user.activated=true;
			cookService.update($scope.user).then(
					function(response){
						alert("Password successfuly changed.");
					}
			);
		}else{
			alert("Wrong password:");
		}
	}


}]);