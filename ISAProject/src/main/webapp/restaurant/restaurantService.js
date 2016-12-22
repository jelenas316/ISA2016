app.service('restaurantService', ['$http', function($http){


    var url='/restaurants';
	
	this.findAll = function(){
		return $http.get(url);
	}
    
    this.save = function(restaurant){
		return $http.post(url, restaurant);
	}

}]);