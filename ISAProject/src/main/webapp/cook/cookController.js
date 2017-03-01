app.controller('cookController', ['$scope', '$window', '$location', 'cookService', '$stateParams', '$state',
	function ($scope, $window, $location, cookService, $stateParams, $state) {
	
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
		 $scope.currentOrder = undefined;
		 $scope.currentOrderedFood=undefined;
		 
		 cookService.findAllFood().then (
		    		function(response) {
		    			console.log("Response data " + response.data);
		    			$scope.food=response.data;
		    		}
		 );
		 
		 cookService.findAll($scope.user.restaurant.id).then(
			       function(response){
			    	   $scope.allCooks=response.data;
			    	   console.log($scope.allCooks);
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
		cookService.findOne($scope.user.email).then(
				function(response){
					//$scope.copyOfUser.friends=response.data.friends;
					console.log("trece");
					cookService.update($scope.user).then(
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
	
	$scope.changeStatus = function(food) {
		$scope.currentOrderedFood = food
		console.log($scope.currentOrderedFood.id);
		cookService.findOneFood($scope.currentOrderedFood.id).then (  // ili currentOrder.id
				function(response){
					$scope.currentOrderedFood.foodStatus = 'ACCEPTED';
					$scope.currentOrderedFood.cook = $scope.user;
					cookService.saveFood($scope.currentOrderedFood).then (
							function(response){
								alert("Successfuly changed.");
							}
					);
				}
		);
		
	}
	
	$scope.finishOrder = function(food) {
		$scope.currentOrderedFood = food;
		cookService.findOneFood($scope.currentOrderedFood.id).then (
				function(response){
					if($scope.currentOrderedFood.cook.email == $scope.user.email){
						$scope.currentOrderedFood.foodStatus = 'PREPARED';
						cookService.saveFood($scope.currentOrderedFood).then (
								function(response){
									alert("Successfuly changed.");
								}
						);
						
					}
					else {
						alert("This is not your order");
					}
				}
		);
		
	}
	
	$scope.changePassword = function() {

		console.log($scope.user);
		console.log($scope.copyOfUser);
		if($scope.user.password != $scope.copyOfUser.password && $scope.copyOfUser.password == $scope.copyOfUser.repeatedPassword){
			$scope.user.password=$scope.copyOfUser.password;
			$scope.user.activated=true;
			cookService.update($scope.user).then(
					function(response){
						alert("Password successfuly changed.");
					}
			);
		}else{
			alert("Wrong password:");
		}
	}
	
	 $scope.showShedule = function(){

		  if($scope.sheduleDate!=undefined){
			  var date = dateToString($scope.sheduleDate)
			  $scope.sheduleForDate=[];
			  for(cook in $scope.allCooks){
				  var oneCook={};
				  oneCook.name=$scope.allCooks[cook].name;
				  oneCook.surname=$scope.allCooks[cook].surname;
				  oneCook.shifts=[];
				  for(shift in $scope.allCooks[cook].shifts){
					  if($scope.allCooks[cook].shifts[shift].startDate==date || 
						$scope.allCooks[cook].shifts[shift].endDate==date)
						  oneCook.shifts.push($scope.allCooks[cook].shifts[shift]);
				  }
				  if(oneCook.shifts.length>0)
					  $scope.sheduleForDate.push(oneCook);
			  }	
		  }
	}

		 function dateToString(date){
			 var convertedDate = date.getFullYear() + "-";
			 if((date.getMonth()+1)<10){
				 convertedDate += "0" + (date.getMonth()+1) + "-"; 
			 }else{
				 convertedDate += date.getMonth()+1 + "-"; 
			 }
			 if(date.getDate()<10){
				 convertedDate += "0" + date.getDate();
			 }else{
				 convertedDate += date.getDate();
			 }
			 return convertedDate;
		 }


}]);