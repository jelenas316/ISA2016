app.service('systemManagerService', ['$http', function($http){


    var url='/restaurants';
	
	this.findAll = function(){
		return $http.get(url);
	}

}]);