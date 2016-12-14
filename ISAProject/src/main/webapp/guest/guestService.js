app.service('guestService', ['$http', function($http){

	var url='/guests';
	
	this.findOne = function(email){
		return $http.get(url + "?email=" + email);
	}
	
	this.findAll = function(){
		return $http.get(url);
	}
	
	this.update = function(guest){
		return $http.put(url, guest);
	}
	
}]);