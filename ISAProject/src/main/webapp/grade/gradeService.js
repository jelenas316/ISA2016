app.service('gradeService', ['$http', function($http){


    var url='/grades';
	
	this.findAll = function(){
		return $http.get(url);
	}
    this.findOne = function(id){
		return $http.get(url,id);
	}  
    this.update = function(user){
		return $http.put(url, user);
	}
    this.save = function(user, restaurant){
		return $http.post(url, user, restaurant);
	}
    this.findByWaiter = function(email){
		return $http.get(url+"/findGradeForWaiter?email="+email);
	}
    this.findByRestaurant = function(id){
		return $http.get(url+"/findGradeForRestaurant/"+id);
	}
    this.findByFood = function(id){
		return $http.get(url+"/findGradeForFood/"+id);
	}

}]);