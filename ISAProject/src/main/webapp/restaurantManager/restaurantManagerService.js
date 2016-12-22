app.service('restaurantManagerService', ['$http', function($http){


    var url='/restaurantmanagers';
	
	this.findAll = function(){
		return $http.get(url);
	}
    
    this.save = function(user){
		return $http.post(url, user);
	}

}]);