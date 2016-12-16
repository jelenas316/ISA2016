app.controller('systemManagerController', ['$scope', '$window', '$location', 'systemManagerService', 'loginService',
	function ($scope, $window, $location, systemManagerService, loginService) {

	function init(){

		$scope.user={};
		$scope.repeatedPassword="";
        $scope.user = loginService.getUser();
        
        systemManagerService.findAll().then(
            function(response){
                    $scope.restaurants=response.data;
                

                }
            );

	};

	init();
        
    function setUser(user){
        console.log(user);
    }

}]);