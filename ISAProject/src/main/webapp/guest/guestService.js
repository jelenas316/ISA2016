app.service('guestService', ['$http', function($http){

	var url='/guests';
	var gradeUrl='/grades';
	var reservationUrl='/reservations';
	var restaurantUrl='/restaurants';
	
	
	
	this.findOne = function(email){
		return $http.get(url + "?email=" + email);
	}
	
	this.findAll = function(){
		return $http.get(url);
	}
	
	this.update = function(guest){
		return $http.put(url, guest);
	}
	
	this.addFriend = function(guestEmail, friendEmail){
		return $http.post(url + "?guest=" + guestEmail + "&friend=" +friendEmail);
	}
	
	this.deleteFriend = function(guestEmail, friendEmail){
		return $http.delete(url + "?guest=" + guestEmail + "&friend=" +friendEmail);
	}
	
	this.acceptRequest = function(guestEmail, friendEmail){
		return $http.post(url + "/request?guest=" + guestEmail + "&friend=" +friendEmail);
	}
	
	this.deleteRequest = function(guestEmail, friendEmail){
		return $http.delete(url + "/request?guest=" + guestEmail + "&friend=" +friendEmail);
	}
	
	
	
	
	this.getRestaurantsWithGrades = function(email){
		return $http.get(gradeUrl + "?email=" + email);
	}
	
	
	
	
	this.getVisitedRestaurants = function(email){
		return $http.get(reservationUrl + "?email=" + email);
	}
	
	this.saveReservation = function(reservation){
		return $http.post(reservationUrl, reservation);
	}
	
	this.findFutureReservations = function(email){
		return $http.get(reservationUrl + "/next?email=" + email);
	}
	
	
	
	
	this.findOneRestaurant = function(id){
		return $http.get(restaurantUrl + "?id=" + id);
	}
	
}]);