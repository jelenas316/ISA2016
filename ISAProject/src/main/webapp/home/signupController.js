app.controller('signupController', ['$scope', '$window', '$location', 'signupService', 
	function ($scope, $window, $location, signupService) {

	function init(){

		$scope.user={};
		$scope.repeatedPassword="";

	};

	init();

	$scope.signUp = function(){

		if($scope.user.password == $scope.repeatedPassword){
			$scope.user.role="GUEST";
			signupService.signUp($scope.user).then(
				function(response){
					$scope.user={};
					$scope.repeatedPassword="";
					alert("Successfuly registered.");
	                $location.path('/other');
				},
				function(response){
					alert("Error while registering.");
				}
			);

		}else{

			alert("Wrong password.");

		}

	};

}]);