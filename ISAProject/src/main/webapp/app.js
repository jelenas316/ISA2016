var app = angular.module('app', ['ui.router']);

app.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
	$urlRouterProvider.otherwise('/login');
    
    $stateProvider
	    .state('login', {
	    	url : '/login',
	      	templateUrl : 'home/login.html',
	        controller : 'loginController'
	    })
	    .state('signup', {
	    	url : '/signup',
	      	templateUrl : 'home/signup.html',
	        controller : 'signupController'
	    })
	    .state('guest', {
	    	url : '/guest?email',
	      	templateUrl : 'guest/guest.html',
	        controller : 'guestController'
	    })
	    .state('guest.restaurants', {
	    	url : '/restaurants',
	    	templateUrl : 'guest/restaurants.html'
	    })
	    .state('guest.friends', {
	    	url : '/friends',
	        templateUrl : 'guest/friends.html'
	    })
		.state('guest.account', {
			url : '/account',
		  	templateUrl : 'guest/account.html'
		 })
 		.state('systemManager', {
            url: '/systemManager',
            controller: 'systemManagerController',
            templateUrl: 'systemManager/systemManager.html'             
        })
        .state('systemManager.restaurants', {
			url : '/Restaurants',
		  	templateUrl : 'systemManager/restaurants.html'
		 })
        .state('systemManager.restaurantManagers', {
			url : '/RestaurantManagers',
		  	templateUrl : 'systemManager/restaurantManagers.html'
		 })
        .state('systemManager.systemManagers', {
			url : '/SystemManagers',
		  	templateUrl : 'systemManager/systemManagers.html'
		 })
        .state('systemManager.systemManagerProfile', {
			url : '/systemManagerProfile',
		  	templateUrl : 'systemManager/systemManagerProfile.html'
		 })
        .state('restaurantManager', {
			url : '/restaurantManager',
            controller: 'restaurantManagerController',
		  	templateUrl : 'restaurantManager/restaurantManager.html'
		 })
	    .state('other', {
	    	url : '/other',
	      	templateUrl : 'home.html'
	    }); 
}]);
app.directive('convertToNumber', function() {
  return {
    require: 'ngModel',
    link: function(scope, element, attrs, ngModel) {
      ngModel.$parsers.push(function(val) {
        return parseInt(val, 10);
      });
      ngModel.$formatters.push(function(val) {
        return '' + val;
      });
    }
  };
});
