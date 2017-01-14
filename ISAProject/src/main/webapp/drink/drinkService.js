app.service('drinkService', ['$http', function($http){


    var url='/drinks';
	
	this.findAll = function(){
		return $http.get(url);
	}
    
    this.findOne = function(id){
		return $http.get(url, id);
	}    
    this.save = function(drink){
		return $http.post(url, drink);
	}
     this.delete = function(id){
		return $http.delete(url+"/"+id);
	}

}]);