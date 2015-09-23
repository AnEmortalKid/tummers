angular
		.module('tummers', [ 'authenticate','foodevent', 'ngRoute' ])
		.config(
				function($routeProvider, $httpProvider) {

					$routeProvider.when('/', {
						templateUrl : 'home.html',
					}).when('/login', {
						templateUrl : 'login.html',
						controller : 'auth',
						controllerAs : "vm"
					}).when('/upcoming', {
						templateUrl : 'upcoming.html',
						controller : 'upcoming',
						controllerAs : "vm"
					}).otherwise('/');

					$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';

				}).controller(
				'navigation',
				function($rootScope, $scope, $http, $location, $route) {
					console.log('in navigation');

					$scope.tab = function(route) {
						return $route.current
								&& route === $route.current.controller;
					}

					$scope.logout = function() {
						$http.post('logout', {}).success(function() {
							$rootScope.authenticated = false;
							$location.path("/");
						}).error(function(data) {
							console.log("Logout failed")
							$rootScope.authenticated = false;
						});
					}
				});