app.service('loginService', ['$http', function($http){

	var url = '/login';

	this.logIn = function(loginData){
		return $http.post(url, loginData);
	}

}]);