app.controller('reportController', ['$scope', '$window', '$location', 'loginService','restaurantService','foodService','$state','waiterService','gradeService','invitationService',
	function ($scope, $window, $location, loginService, restaurantService, foodService, $state, waiterService, gradeService, invitationService) {
        
	function init(){
        if($window.localStorage.getItem("user") == undefined){
             $state.go('login'); 
             return;
        }
              
        var result = JSON.parse(localStorage.getItem("user"));
        $scope.user = result[0];
        if($scope.user.role != 'RESTAURANT_MANAGER'){
             $state.go('login'); 
             return; 
        }
        $scope.getRestaurantGrade($scope.user.restaurant.id);
        $scope.getWaiters($scope.user.restaurant.id);
        $scope.getVisits($scope.user.restaurant.id);       

    }

    $scope.getRestaurantGrade = function(id){
        gradeService.findByRestaurant(id).then(
            function(response){
                $scope.restaurantGrade = response.data;
            },
            function(response){
                alert("Error while fetching restaurant grade.");
            }
        );
    }   
        
    $scope.getWaiters = function(id){
        waiterService.findAll(id).then(
            function(response){
                $scope.waiters = response.data;
            },
            function(response){
                alert("Error while fetching waiters.");
            }
        );
    }
    $scope.getDate = function(date){
        var year   = parseInt(date.substring(0,4));
        var month  = parseInt(date.substring(5,7));
        var day   = parseInt(date.substring(8,10));
        var date1 = Date.UTC(year,month-1,day);
        //var date1 = day+"/"+(month < 10 ? "0" : "")+month+"/"+year;
        return date1;
    } 
    $scope.getDatesInUTCFormat = function(){
        var data = [];
        var visit = [];
        for(var i = 0; i < $scope.visits.length; i++){
           visit.push($scope.getDate($scope.visits[i].date));
           visit.push($scope.visits[i].guests);
           data.push(visit);
           visit = [];
        }
        return data;
    }
    $scope.getVisits = function(id){
        invitationService.getVisitsForRestaurant(id).then(
            function(response){
                $scope.visits = response.data;
                $scope.data = $scope.getDatesInUTCFormat();
                    $scope.chart = new Highcharts.chart('container', {
                    chart: {
                    zoomType: 'x',
                    spacingRight: 20
                    },
                    title: {
                    text: 'Restaurant visits'
                    },
                    subtitle: {
                    text: document.ontouchstart === undefined ?
                        'Click and drag in the plot area to zoom in' :
                        'Pinch the chart to zoom in'
                    },
                    xAxis: {
                    type: 'datetime',
                    //minRange: 2 * 24 * 3600000,
                    //maxZoom: 5 * 24 * 3600000, // fourteen days
                    dateTimeLabelFormats: {
                        week: '%e. %b'
                    },
                    title: {
                        text: null
                    }
                    },
                    yAxis: {
                    title: {
                        text: 'Number of guests per day'
                    }
                    },
                    tooltip: {
                    shared: true
                    },
                    legend: {
                    enabled: true
                    },
                    plotOptions: {
                    area: {
                        fillColor: {
                        linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1},
                        stops: [
                            [0, Highcharts.getOptions().colors[9]],
                            [1, Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]
                        ]
                        },
                        //lineWidth: 1,
                        marker: {
                        enabled: false
                        },
                        shadow: false,
                        states: {
                        hover: {
                            lineWidth: 1
                        }
                        },
                        threshold: null
                    }
                    },

                    series: [{
                            type: 'area',
                            name: 'Number of guests',
                            pointInterval: 24 * 3600 * 1000,
                            data: $scope.data
                }] 

                });
            },
            function(response){
                alert("Error while fetching restaurant visits.");
            }
        );
    }
                                    
	init();


        
    $scope.getGradeWaiter = function(){
        gradeService.findByWaiter($scope.selectedWaiter).then(
            function(response){
                $scope.waiterGrade = response.data;
            },
            function(response){
                alert("Error while fetching waiter grade.");
            }
        );
    }
    $scope.getGradeFood = function(){
        gradeService.findByFood($scope.selectedFood).then(
            function(response){
                $scope.foodGrade = response.data;
            },
            function(response){
                alert("Error while fetching food grade.");
            }
        );
    }
}]);