app.service('systemManagerService', ['$http', function($http){


    var url='/systemmanagers';
	
	this.findAll = function(){
		return $http.get(url);
	}
    this.update = function(data){
		return $http.put(url,data);
	}    
    this.save = function(user){
		return $http.post(url, user);
	}

}]);