app.controller('bartenderController', ['$scope', '$window', '$location', 'bartenderService', '$stateParams', '$state',
	function ($scope, $window, $location, bartenderService, $stateParams, $state) {
	
	function init(){
		
		 if($window.localStorage.getItem("user") == undefined){
             $state.go('login'); 
             return;
        }
	};

}]);