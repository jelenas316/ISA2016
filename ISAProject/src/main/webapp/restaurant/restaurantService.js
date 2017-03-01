app.service('restaurantService', ['$http', function($http){


    var url='/restaurants';
	
	this.findAll = function(){
		return $http.get(url);
	}
    
    this.findOne = function(id){
		return $http.get(url+"/"+id);
	}
    
    this.save = function(resId){
		return $http.post(url, resId);
	}

    this.getFoodStuff = function(id){
		return $http.get(url+"/getFoodStuff/"+id);
	}
    this.getDrinkGroceries = function(id){
		return $http.get(url+"/getDrinkGroceries/"+id);
	}
    this.findRestaurantsWithoutManagers = function(){
		return $http.get(url+"/findRestaurantsWithoutManagers");
	}
}]);