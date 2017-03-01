app.controller('bartenderController', ['$scope', '$window', '$location', 'bartenderService', '$stateParams', '$state',
	function ($scope, $window, $location, bartenderService, $stateParams, $state) {
	
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
		 $scope.currentOrderedDrink = undefined;
		 //console.log($scope.repeatedPassword);
		 console.log($scope.user.activated);
		 
		 bartenderService.findAllDrink().then (
		    		function(response) {
		    			console.log(response.data);
		    			$scope.drink=response.data;
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
		bartenderService.findOne($scope.user.email).then(
				function(response){
					//$scope.copyOfUser.friends=response.data.friends;
					console.log("trece");
					bartenderService.update($scope.user).then(
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
	
	$scope.changePassword = function() {

		console.log($scope.user);
		console.log($scope.copyOfUser);
		if($scope.user.password != $scope.copyOfUser.password && $scope.copyOfUser.password == $scope.copyOfUser.repeatedPassword){
			$scope.user.password=$scope.copyOfUser.password;
			$scope.user.activated=true;
			bartenderService.update($scope.user).then(
					function(response){
						alert("Password successfuly changed.");
					}
			);
		}else{
			alert("Wrong password:");
		}
	}
	
	$scope.changeStatus = function(drink) {
		$scope.currentOrderedDrink = drink;
		bartenderService.findOneDrink($scope.currentOrderedDrink.id).then (
			function(response){
				$scope.currentOrderedDrink.foodStatus = 'PREPARED';
				$scope.currentOrderedDrink.bartender = $scope.user;
				bartenderService.saveDrink($scope.currentOrderedDrink).then (
						function(response){
							alert("Successfuly changed.");
						}
				);
			}	
		);
	}

}]);