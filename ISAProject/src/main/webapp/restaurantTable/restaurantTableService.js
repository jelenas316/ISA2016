app.service('restaurantTableService', ['$http', function($http){


    var url='/restauranttables';
	
	this.findAll = function(){
		return $http.get(url);
	}
    
    this.findOne = function(id){
		return $http.get(url, id);
	}    
    this.save = function(configuration){
		return $http.post(url, configuration);
	}
     this.delete = function(tables){
		return $http.delete(url+"/"+tables);
	}

}]);