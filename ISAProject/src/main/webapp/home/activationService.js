app.service('activationService', ['$http', function($http){

	var url='/profiles';
	var guestUrl='/guests';
	
	this.findOne = function(id){
		return $http.get(url + '?id=' + id);
	}
	
	this.saveGuest = function(guest){
		return $http.post(guestUrl, guest);
	}
	
	this.deleteProfile = function(id){
		return $http.delete(url + "?id=" + id);
	}

}]);