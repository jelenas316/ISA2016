app.service('waiterService', ['$http', function($http){

    var url='/waiters';
    var urlOrders = '/orders';
    
    
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
    
    this.findAllOrders = function() {
    	return $http.get(urlOrders);
    }
    
}]);