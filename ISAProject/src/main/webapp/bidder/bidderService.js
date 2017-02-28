app.service('bidderService', ['$http', function($http){

    var url='/bidders';
	
	this.findAll = function(restaurant){
		return $http.get(url+"/findAll/"+restaurant);
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
     
}]);