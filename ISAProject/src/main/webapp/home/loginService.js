app.service('loginService', ['$http', function($http){

	var url = '/login';
    var user = {};

  this.logIn = function(loginData){
       return $http.post(url, loginData);
  }

  this.setUser = function(newObj) {
       user = newObj;
  }

  this.getUser = function(){
      return user;
  }

}]);