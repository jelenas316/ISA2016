app.service('signupService', ['$http', function($http){

	var url="/profiles";
	
	this.signUp = function(user){
		return $http.post(url, user);
	}
	
}]);