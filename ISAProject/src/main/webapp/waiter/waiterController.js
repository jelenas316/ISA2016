app.controller('waiterController', ['$scope', '$window', '$location', 'waiterService', '$stateParams', '$state',
	function ($scope, $window, $location, waiterService, $stateParams, $state) {
	
	function init(){
		
		 if($window.localStorage.getItem("user") == undefined){
             $state.go('login'); 
             return;
        }
	};

}]);