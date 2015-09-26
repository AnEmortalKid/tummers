angular.module('participants', []).controller('participant-table',
		function($rootScope, $scope, $http, $location, $route) {

			$http.get('/foodPreferences/list').success(function(data) {
				console.log('got some data');
				$scope.foodPreferences = data
			});

		}).controller('register',
		function($rootScope, $scope, $http, $location, $route) {
		});