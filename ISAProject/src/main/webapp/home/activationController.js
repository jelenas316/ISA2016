app.controller('activationController', ['$scope', '$window', '$location', '$state','$stateParams', 'activationService',
	function ($scope, $window, $location, $state, $stateParams, activationService) {

	function init(){
		$scope.password="";
		var id=$stateParams.id;
		activationService.findOne(id).then(
				function(response){
					$scope.profile=response.data;
				},
				function(response){
					alert('Request doesn\'t exist.');
				}
		);
	}

	init();

	$scope.activate = function(){
		if($scope.password==$scope.profile.password && $scope.password!=""){
			$scope.user={};
			$scope.user.email=$scope.profile.email;
			$scope.user.password=$scope.profile.password;
			$scope.user.name=$scope.profile.name;
			$scope.user.surname=$scope.profile.surname;
			$scope.user.role="GUEST";
			activationService.saveGuest($scope.user).then(
					function(response){
						activationService.deleteProfile($scope.profile.id).then(
								function(response2){
									$scope.profile={};
									$scope.user={};
									$state.go('guest',{ email : response.data.email });
								}
						);
					}
			);
		}else
			alert('Error.');
	}
	
}]);