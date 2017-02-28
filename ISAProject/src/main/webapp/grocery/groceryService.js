app.service('groceryService', ['$http', function($http){


    var url='/groceries';
	
	this.findAll = function(){
		return $http.get(url);
	}
    this.findAll = function(id){
		return $http.get(url+"/findAll/"+id);
	}
    this.findOne = function(id){
		return $http.get(url+"/"+id);
	}  
    this.update = function(user){
		return $http.put(url, user);
	}
    this.save = function(groceryList){
		return $http.post(url, groceryList);
	}
    this.delete = function(id){
		return $http.delete(url+"/"+id);
	}
    this.getPastOffers = function(id){
		return $http.get(url+"/findPastOffers/"+id);
	}
}]);