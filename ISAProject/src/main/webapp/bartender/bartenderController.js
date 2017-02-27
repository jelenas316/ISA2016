app.controller('bartenderController', ['$scope', '$window', '$location', 'bartenderService', '$stateParams', '$state',
	function ($scope, $window, $location, bartenderService, $stateParams, $state) {
	
	function init(){
		
		 if($window.localStorage.getItem("user") == undefined){
             $state.go('login'); 
             return;
        }
		 
		 var result = JSON.parse(localStorage.getItem("user"));
		 $scope.user = result[0];
		 console.log($scope.user);
	};
	
	init();

}]);