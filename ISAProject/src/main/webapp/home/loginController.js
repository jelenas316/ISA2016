app.controller('loginController', ['$scope', '$window', '$location', '$state', 'loginService',
	function ($scope, $window, $location, $state, loginService) {

	function init(){
		$scope.loginData={};
	}

	init();


	$scope.logIn = function(){

		loginService.logIn($scope.loginData).then(
			function(response){
				$scope.loginData={};
				if(response.data.role=="GUEST"){
					$state.go('guest',{ email : response.data.email });
				}else
					$state.transitionTo('other');
			},
			function(response){
				alert("Unsuccessful login.");
			}
		);

	}

}]);