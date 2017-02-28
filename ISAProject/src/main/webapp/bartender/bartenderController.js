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
		 $scope.repeatedPassword=$scope.user.password;
		 console.log($scope.repeatedPassword);
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

}]);