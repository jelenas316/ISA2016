'use strict';
var app = angular.module('app', ['ui.router','ngAnimate','ngTouch','ngSanitize','rgkevin.datetimeRangePicker','ui.bootstrap']);
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
	    .state('activation', {
	    	url : '/activation?id',
	      	templateUrl : 'home/activation.html',
	        controller : 'activationController'
	    })
	    .state('invitation', {
	    	url : '/invitation/:id?email',
	      	templateUrl : 'guest/invitation.html',
	        controller : 'invitationController'
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
	    .state('guest.reservations', {
	    	url : '/reservations',
	    	templateUrl : 'guest/reservations.html'
	    })
	    .state('guest.reservations.ordered', {
	    	url : '/ordered',
	    	templateUrl : 'guest/orderedFoodAndDrinks.html'
	    })
	    .state('guest.reservations.food', {
	    	url : '/food',
	    	templateUrl : 'guest/food.html'
	    })
	    .state('guest.reservations.drink', {
	    	url : '/drink',
	    	templateUrl : 'guest/drink.html'
	    })
		.state('guest.account', {
			url : '/account',
		  	templateUrl : 'guest/account.html'
		 })
		 .state('guest.visitedRestaurants', {
	    	url : '/visitedRestaurants',
	    	templateUrl : 'guest/visitedRestaurants.html'
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
        .state('systemManager.userProfile', {
			url : '/userProfile',
		  	templateUrl : 'templates/userProfile.html'
		 })
        .state('restaurantManager', {
			url : '/restaurantManager',
            controller: 'restaurantManagerController',
		  	templateUrl : 'restaurantManager/restaurantManager.html'
		 })
        .state('restaurantManager.restaurant', {
			url : '/restaurant',
            controller: 'restaurantManagerController',
		  	templateUrl : 'restaurantManager/restaurant.html'
		 })
        .state('restaurantManager.userProfile', {
			url : '/profile',
            controller: 'restaurantManagerController',
		  	templateUrl : 'templates/userProfile.html'
		 })
        .state('restaurantManager.menu', {
			url : '/menu',
            controller: 'restaurantManagerController',
		  	templateUrl : 'restaurantManager/menu.html'
		 })    
        .state('restaurantManager.drinks', {
			url : '/drinks',
            controller: 'restaurantManagerController',
		  	templateUrl : 'restaurantManager/drinks.html'
		 })  
        .state('restaurantManager.tables', {
			url : '/tables',
            controller: 'restaurantManagerController',
		  	templateUrl : 'restaurantManager/tables.html'
		 })
        .state('restaurantManager.waiters', {
			url : '/Waiters',
            controller: 'rmwaitersController',
		  	templateUrl : 'restaurantManager/waiters.html'
		 })
        .state('restaurantManager.bartenders', {
			url : '/bartenders',
            controller: 'rmbartendersController',
		  	templateUrl : 'restaurantManager/bartenders.html'
		 })
        .state('restaurantManager.cooks', {
			url : '/cooks',
            controller: 'rmcooksController',
		  	templateUrl : 'restaurantManager/cooks.html'
		 })
       .state('restaurantManager.purchaseGrocery', {
			url : '/purchaseGrocery',
		  	templateUrl : 'restaurantManager/purchaseGrocery.html'
		 })
		.state('waiter', {
	    	url : '/waiter',
	      	templateUrl : 'waiter/waiter.html',
	        controller : 'waiterController'
		 })
		.state('bartender', {
			url : '/bartender',
			templateUrl : 'bartender/bartender.html',
			controller : 'bartenderController'
		})
		.state('cook', {
			url : '/cook',
			templateUrl : 'cook/cook.html',
			controller : 'cookController'
		})
	    .state('other', {
	    	url : '/other',
	      	templateUrl : 'home.html'
	    })
	    .state('notification', {
	    	url : '/notification',
	      	templateUrl : 'home/notification.html'
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
app.directive('userProfile', function() {
		var directive = {};
    directive.restrict = 'E';
    directive.templateUrl = "templates/userProfile.html";

    return directive;
});
app.directive('dp', function() {
	var directive = {};
    directive.restrict = 'E';
    directive.templateUrl = "templates/datePickerTemplate.html";
		directive.link = function($scope, element, attrs) {
			$scope.isOpened = false;
			$scope.open = function() { $scope.isOpened = true;}
		}
		directive.scope =  {
        model:'=ngModel',	minDate: '=min'
    }

    return directive;
});
