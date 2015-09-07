angular.module('hello', []).controller('home', function($scope, $http) {
	$http.get('/resource/').success(function(data) {
		$scope.greeting = data;
	})
}).controller(
		'upcoming',
		function($scope, $http) {
			$http.get('/foodEvents/upcoming/').success(
					function(data) {
						console.log(data);
						$scope.upcoming = data.slot.slotDate.year + "/"
								+ data.slot.slotDate.monthOfYear + "/"
								+ data.slot.slotDate.dayOfMonth;
					})
		});