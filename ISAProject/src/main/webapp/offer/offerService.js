app.service('offerService', ['$http', function($http){
    
    var url='/offers';
	
	this.findAll = function(){
		return $http.get(url);
	}
    this.findOne = function(id){
		return $http.get(url+"/"+id);
	}    
    this.save = function(offer){
		return $http.post(url, offer);
	}
    this.delete = function(id){
		return $http.delete(url+"/"+id);
	}
    this.getActiveOffer = function(email){
		return $http.get(url+"/getActiveOffer"+"?email="+email);
	}
    this.getOffers = function(id){
		return $http.get(url+"/getOffersForGroceryList/"+id);
	}
    this.getPastOffers = function(email){
		return $http.get(url+"/getPastOffers"+"?email="+email);
	}
    this.acceptOffer = function(id){
		return $http.get(url+"/acceptOffer/"+id);
	}
    this.rejectOffer = function(id){
		return $http.get(url+"/rejectOffer/"+id);
	}
   
}]);