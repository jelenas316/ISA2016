app.service('foodService', ['$http', function($http){


    var url='/foods';
	
	this.findAll = function(){
		return $http.get(url);
	}
    
    this.findOne = function(id){
		return $http.get(url, id);
	}    
    this.save = function(food){
		return $http.post(url, food);
	}
     this.delete = function(id){
		return $http.delete(url+"/"+id);
	}

}]);