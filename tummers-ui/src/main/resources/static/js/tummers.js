angular.module('tummers', [ 'ngRoute' ]).config(
		function($routeProvider, $httpProvider) {

			$routeProvider.when('/', {
				templateUrl : 'index.html',
				controller : 'navigation'
			}).when('/login', {
				templateUrl : 'login.html',
				controller : 'auth'
			}).when('/upcoming', {
				templateUrl : 'upcoming.html',
				controller : 'upcoming'
			}).otherwise('/');
		}).controller('navigation',
		function($rootScope, $scope, $http, $location, $route) {
			console.log('in navigation');

			$scope.tab = function(route) {
				return $route.current && route === $route.current.controller;
			}

			console.log('past route');

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