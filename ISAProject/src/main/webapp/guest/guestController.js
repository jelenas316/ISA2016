app.controller('guestController', ['$scope', '$window', '$location', 'guestService', '$stateParams', '$state',
	function ($scope, $window, $location, guestService, $stateParams, $state) {
	
	function init(){
		var email=$stateParams.email;
		if(email!=null && $state.current.name=="guest"){
			
			$state.go('.restaurants',{ email : email });
			
			initializeData(email);
	
		}else{
			$state.go('login');
		}
	};
	
	init();
	
	function initializeData(email){
		guestService.findOne(email).then(
				function(response){
					$scope.user=response.data;
					$scope.copyOfUser=$scope.user;
					$scope.repeatedPassword=$scope.user.password;
					$scope.friend={};
					$scope.friend.email="";
					guestService.findAll().then(
						function(response){
							$scope.guests=response.data;
							deleteGuestsThatExists();
						}
					);
					
				},
				function(response){
					$state.go('login');
				}
		);
	}
	
	function deleteGuestsThatExists(){
		var tempGuestList=[];
		for(guestIndex in $scope.guests){
			var add=true;
			if($scope.guests[guestIndex].email!=$scope.user.email){
				for(friendIndex in $scope.user.friends){
					if($scope.guests[guestIndex].email==$scope.user.friends[friendIndex].email){
						add=false;
						break;
					}
				}
			}else{
				add=false;
			}
			if(add){
				tempGuestList.push($scope.guests[guestIndex]);
			}
		}
		$scope.guests=tempGuestList;
		
	}
	
	$scope.changeProfile = function(){
		
		if($scope.copyOfUser.password == $scope.repeatedPassword){
			update();

		}else{
			alert("Wrong password.");
		}
		
	};
	
	$scope.addFriend = function(){
		for(guestIndex in $scope.guests){
			if($scope.guests[guestIndex].email==$scope.friend.email){
				$scope.friend=$scope.guests[guestIndex];
				$scope.copyOfUser.friends.push($scope.friend);
				update();
			}
		}
	};
	
	function update(){
		guestService.update($scope.copyOfUser).then(
			function(response){
				alert("Successfuly changed.");
				initializeData($scope.user.email);
			},
			function(response){
				alert("Error while registering.");
			}
		);
	}
	
	$scope.deleteFriend = function(friend){
		for(friendIndex in $scope.copyOfUser.friends){
			if($scope.copyOfUser.friends[friendIndex].email==friend.email){
				$scope.copyOfUser.friends.splice(friendIndex,1);
				break;
			}
		}
		update();
	}

}]);