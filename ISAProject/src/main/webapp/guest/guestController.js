app.controller('guestController', ['$scope', '$window', '$location', 'guestService', '$stateParams', '$state',
	function ($scope, $window, $location, guestService, $stateParams, $state) {
	
	function init(){
		var email=$stateParams.email;
		if(email!=null && $state.current.name=="guest"){
			
			$state.go('.visitedRestaurants',{ email : email });
			
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
					console.log($scope.user);
					$scope.copyOfUser=$scope.user;
					$scope.search="";
					$scope.repeatedPassword=$scope.user.password;
					$scope.friend={};
					$scope.friend.email="";
					$scope.friends=$scope.user.friends;
					$scope.requestsForFriends=[];
					guestService.findAll().then(
						function(response){
							$scope.guests=response.data;
							addFriends();
							deleteGuestsThatExists();
						}
					);
					
					guestService.getRestaurantsWithGrades($scope.user.email).then(
							function(response){
								$scope.restaurants=response.data;
								addStars();
								$scope.copyOfRestaurants=$scope.restaurants;
							}
					);
					
					guestService.getVisitedRestaurants($scope.user.email).then(
							function(response){
								$scope.visitedRestaurants=response.data;
								addStarsForVisitedRestaurants();
							}
					);
				},
				function(response){
					$state.go('login');
				}
		);
	}
	
	function addStarsForVisitedRestaurants(){
		for(index in $scope.visitedRestaurants){
			$scope.visitedRestaurants[index].restaurant.reitingStars = getStars($scope.visitedRestaurants[index].restaurant.reiting);
		}
	}
	
	function addStars(){
		for(restaurant in $scope.restaurants){
			$scope.restaurants[restaurant].reitingStars=getStars($scope.restaurants[restaurant].reiting);
			$scope.restaurants[restaurant].friendsReitingStars=getStars($scope.restaurants[restaurant].friendsReiting);
		}
	}
	
	function getStars(grade){
		var stars="";
		for(var i=0; i<grade ; i++){
			stars=stars+"*";
		}
		return stars;
	}
	
	function addFriends(){
		for(guestIndex in $scope.guests){
			for(friendIndex in $scope.guests[guestIndex].friends){
				if($scope.user.email==$scope.guests[guestIndex].friends[friendIndex].email){
					$scope.friends.push($scope.guests[guestIndex]);
					break;
				}
			}
		}
	};
	
	function deleteGuestsThatExists(){
		var tempGuestList=[];
		for(guestIndex in $scope.guests){
			var add=true;
			var guestEmail=$scope.guests[guestIndex].email;
			if($scope.guests[guestIndex].email!=$scope.user.email){
				for(friendIndex in $scope.user.friends){
					if(guestEmail==$scope.user.friends[friendIndex].email){
						add=false;
						break;
					}
				}
				for(friendIndex in $scope.user.friendRequests){
					if(guestEmail==$scope.user.friendRequests[friendIndex].email){
						add=false;
						break;
					}
				}
				
				for(friendRequestIndex in $scope.guests[guestIndex].friendRequests){
					if($scope.user.email==$scope.guests[guestIndex].friendRequests[friendRequestIndex].email){
						$scope.requestsForFriends.push($scope.guests[guestIndex]);
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
	
	function update(){
		guestService.findOne($scope.user.email).then(
				function(response){
					$scope.copyOfUser.friends=response.data.friends;
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
		);
	}
	
	$scope.addFriend = function(){
		if($scope.friend.email!=""){
			guestService.addFriend($scope.user.email, $scope.friend.email).then(
				function(response){
					initializeData($scope.user.email);
				}
			);
		}
	};
	
	$scope.deleteFriend = function(friend){
		guestService.deleteFriend($scope.user.email, friend.email).then(
			function(response){
				initializeData($scope.user.email);
			}
		);
	}

	$scope.acceptRequest = function(request){
		guestService.acceptRequest($scope.user.email, request.email).then(
			function(response){
				initializeData($scope.user.email);
			}
		);
	};
	
	$scope.deleteRequest = function(request){
		guestService.deleteRequest($scope.user.email, request.email).then(
			function(response){
				initializeData($scope.user.email);
			}
		);
	}
	
	$scope.reserve = function(restaurant){
		console.log("reserve");
	}
	
	$scope.searchRestaurants = function(){
		$scope.copyOfRestaurants=[];
		for(index in $scope.restaurants){
			if($scope.restaurants[index].name.indexOf($scope.search) !== -1 ||
					$scope.restaurants[index].description.indexOf($scope.search) !== -1)
				$scope.copyOfRestaurants.push($scope.restaurants[index]);
		}
	}
	
}]);