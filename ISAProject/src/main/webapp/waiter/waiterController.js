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
	    console.log($scope.user);
	    waiterService.findAllOrders().then (
	    		function(response) {
	    			console.log(response.data);
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

}]);