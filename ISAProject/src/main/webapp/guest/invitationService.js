app.service('invitationService', ['$http', function($http){

	var url = '/reservations';
	
	this.findOne = function(id){
		return $http.get(url + '?id=' + id);
	}
	
	this.accept = function(id,email){
		return $http.post(url + '?id=' + id + "&email=" + email);
	}
	
	this.reject = function(id, email){
		return $http.delete(url + '?id=' + id + "&email=" + email);
	}
    this.getVisitsForRestaurant = function(id){
		return $http.get(url +"/findByRestaurantId/"+id);
	}
	
}]);