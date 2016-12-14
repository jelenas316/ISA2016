var app = angular.module('app', ['ngRoute','ui.bootstrap']);

app.config(['$routeProvider', function ($routeProvider) {
	$routeProvider
			.when('/login', {
						controller: 'loginController',
						templateUrl: 'home/login.html',
			})
			.when('/signup', {
						controller: 'signupController',
						templateUrl: 'home/signup.html',
			})
			.when('/other', {
						templateUrl: 'home.html',
			})
			.when('/systemManager', {
                        controller: 'systemManagerController',
						templateUrl: 'systemManager/systemManager.html',
			})
		    .otherwise({
		        redirectTo: '/login'
		    });
}]);
