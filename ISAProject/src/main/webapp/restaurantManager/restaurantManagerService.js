app.service('restaurantManagerService', ['$http', function($http){


    var url='/restaurantmanagers';
	
	this.findAll = function(){
		return $http.get(url);
	}
    this.findOne = function(id){
		return $http.get(url,id);
	}
    
    
    this.save = function(user, restaurant){
		return $http.post(url, user, restaurant);
	}

}]);