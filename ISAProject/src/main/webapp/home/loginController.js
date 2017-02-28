app.controller('loginController', ['$scope', '$window', '$location', '$state', 'loginService',
	function ($scope, $window, $location, $state, loginService) {

	function init(){
		$scope.loginData={};
        $window.localStorage.removeItem("user");   
	}

	init();

	$scope.logIn = function(){

		loginService.logIn($scope.loginData).then(
			function(response){
				$scope.loginData={};
				if(response.data.role=="GUEST"){                    
					$state.go('guest',{ email : response.data.email });
				}else if(response.data.role=="SYSTEM_MANAGER"){    
                    var user = [];
                    user.push(response.data);
                    $window.localStorage.setItem("user", JSON.stringify(user));
                    $state.go('systemManager');                   
                }else if(response.data.role=="RESTAURANT_MANAGER"){                     
                    var user = [];
                    user.push(response.data);
                    $window.localStorage.setItem("user", JSON.stringify(user));
                    $state.go('restaurantManager');     
                }else if(response.data.role=="BIDDER"){                     
                    var user = [];
                    user.push(response.data);
                    $window.localStorage.setItem("user", JSON.stringify(user));
                    $state.go('bidder'); 
                }else if(response.data.role=="WAITER"){
                	 var user = [];
                	 user.push(response.data);
                     $window.localStorage.setItem("user", JSON.stringify(user));
                     $state.go('waiter');
                }else if(response.data.role=="BARTENDER"){
                	var user = [];
                	user.push(response.data);
                	$window.localStorage.setItem("user", JSON.stringify(user));
                    $state.go('bartender');
                }else if(response.data.role=="COOK"){
                	var user = [];
                	user.push(response.data);
                	$window.localStorage.setItem("user", JSON.stringify(user));
                    $state.go('cook');
                }
                else
					$state.transitionTo('other');
			},
			function(response){
				alert("Unsuccessful login.");
			}
		);

	}

}]);