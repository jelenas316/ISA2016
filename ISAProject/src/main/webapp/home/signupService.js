app.service('signupService', ['$http', function($http){

	this.signUp = function(user){
		var url=getUrl(user);
		return $http.post(url, user);
	}

	function getUrl(user){
		var url = "/";
		if(user.role == "GUEST")
			url = url + "guests";
		return url;
	}

}]);