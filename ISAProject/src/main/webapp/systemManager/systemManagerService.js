app.service('systemManagerService', ['$http', function($http){


	function getUrl(user){
		var url = "/";
		if(user.role == "GUEST")
			url = url + "guests";
		return url;
	}

}]);