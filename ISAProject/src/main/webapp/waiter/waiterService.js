app.service('waiterService', ['$http', function($http){

    var url='/waiters';
    var urlOrders = '/orders';
    var urlFood = '/waiters';
    var urlDrink = '/waiters'
    var urlOrderedFood = '/orderedFood';
    var urlOrderedDrink = '/orderedDrinks';
    var urlRestaurant = '/restaurants';
    var urlShift = '/shifts';
    
    
	this.findAll = function(id){
		return $http.get(url+"/findAll/"+id);
	}
    this.findOne = function(email){
		return $http.get(url + "?email=" + email);
	}
    this.update = function(waiter){
		return $http.put(url, waiter);
	}
    this.save = function(waiter){
		return $http.post(url, waiter);
	}
    this.deleteShift = function(requestParams){
		return $http.post(url+"/deleteShift",requestParams);
	}
    this.addShift = function(shift){
		return $http.post(url+"/addShift",shift);
	}
    
    this.findAllOrders = function() {
    	return $http.get(urlOrders);
    }
    
    this.cancelFood = function(order, orderedFood) {
    	return $http.put(urlFood + "?order=" + order + "&orderedFood=" + orderedFood);
    }
    
    this.cancelDrink = function(order, orderedDrink){
    	return $http.put(urlDrink + "?order=" + order + "&orderedDrink=" + orderedDrink);
    }
    
    this.findOneOrder = function(id) {
    	return $http.get(urlOrders + "?id=" +id);
    }
    
    this.saveFood= function(food){
    	return $http.post(urlOrderedFood, food);
    }
    
    this.saveDrink = function(drink){
    	return $http.post(urlOrderedDrink, drink);
    }
    
    this.saveOrder = function(order){
    	return $http.post(urlOrders, order);
    }
    
    this.findAllRestaurants = function() {
    	return $http.get(urlRestaurant);
    }
    
    this.findOneRestaurant = function(id) {
    	return $http.get(urlRestaurant + "?id=" + id);
    }
    
    this.findAllShifts = function() {
    	return $http.get(urlShift);
    }
    
    this.deleteOrder = function(id) {
    	return $http.delete(urlOrders + "?id=" + id);
    }
    
}]);