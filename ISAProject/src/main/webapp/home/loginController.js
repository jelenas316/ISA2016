app.controller('loginController', ['$scope', '$window', '$location', 'loginService', 
	function ($scope, $window, $location, loginService) {

	function init(){
		$scope.loginData={};
	}

	init();


	$scope.logIn = function(){

		loginService.logIn($scope.loginData).then(
			function(response){
				console.log(response.data);
				$scope.loginData={};
                $location.path('/other');
			},
			function(response){
				alert("Unsuccessful login.");
			}
		);

	}

}]);