app.controller('systemManagerController', ['$scope', '$window', '$location', 'systemManagerService', 
	function ($scope, $window, $location, systemManagerService) {

	function init(){

		$scope.user={};
		$scope.repeatedPassword="";

	};

	init();



}]);