app.service('cookService', ['$http', function($http){

    var url='/cooks';
	
	this.findAll = function(id){
		return $http.get(url+"/findAll/"+id);
	}
    this.findOne = function(email){
		return $http.get(url + "?email=" + email);
	}
    this.update = function(waiter){
		return $http.put(url, waiter);
	}
    this.save = function(waiter){
		return $http.post(url, waiter);
	}
    this.deleteShift = function(requestParams){
		return $http.post(url+"/deleteShift",requestParams);
	}
    this.addShift = function(shift){
		return $http.post(url+"/addShift",shift);
	}    
    
}]);