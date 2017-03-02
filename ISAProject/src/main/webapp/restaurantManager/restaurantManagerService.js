app.service('restaurantManagerService', ['$http', function($http){


    var url='/restaurantmanagers';
	
	this.findAll = function(){
		return $http.get(url);
	}
    this.findOne = function(email){
		return $http.get(url+"/?email="+email);
	}  
    this.update = function(user){
		return $http.put(url, user);
	}
    this.save = function(user, restaurant){
		return $http.post(url, user, restaurant);
	}

}]);