app.controller('invitationController', ['$scope', '$window', '$location', '$state','$stateParams', 'invitationService',
	function ($scope, $window, $location, $state, $stateParams, invitationService) {

	function init(){
		$scope.user={};
		$scope.reservation={};
		$scope.password="";
		$scope.showInvitation=false;
		var id=$stateParams.id;
		var email=$stateParams.email;
		invitationService.findOne(id).then(
				function(response){
					$scope.reservation=response.data;
					isInvited(email);
				},
				function(response){
					alert('Error');
				}
		);
	}

	init();
	
	function isInvited(email){
		for(index in $scope.reservation.invitedFriends){
			if($scope.reservation.invitedFriends[index].email==email){
				$scope.user=$scope.reservation.invitedFriends[index];
				break;
			}
		}
	}
	
	$scope.reject = function(){
		invitationService.reject($scope.reservation.id, $scope.user.email).then(
				function(response){
					alert('The invitation has been rejected.');
					$state.go('guest',{ email : $scope.user.email });
				}
		);
	}
	
	$scope.accept = function(){
		invitationService.accept($scope.reservation.id, $scope.user.email).then(
				function(response){
					alert('The invitation has been accepted.');
					$state.go('guest',{ email : $scope.user.email });
				}
		);
	}
	
	$scope.checkPassword = function(){
		if($scope.password==$scope.user.password && $scope.password!=""){
			$scope.showInvitation=true;
		}else
			alert('Wrong password.');
	}
	
	
	
	

}]);