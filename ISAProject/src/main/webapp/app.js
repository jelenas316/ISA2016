var app = angular.module('app', ['ui.router','ui.bootstrap']);

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
	    	url : '/guest',
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
	    .state('other', {
	    	url : '/other',
	      	templateUrl : 'home.html'
	    }); 
		
}]);
