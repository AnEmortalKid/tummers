angular.module('foodevent', []).controller(
		'upcoming',
		function($scope, $http) {
			$http.get('/foodEvents/upcoming/').success(
					function(data) {
						console.log(data);
						$scope.upcoming = data.slot.slotDate.year + "/"
								+ data.slot.slotDate.monthOfYear + "/"
								+ data.slot.slotDate.dayOfMonth;
						var breakfastIds = data.breakfastParticipants;
						console.log(breakfastIds);

						var names = $http.get('/associates/' + breakfastIds)
								.success(function(data) {
									console.log("names=" + data);
									$scope.breakfast = data;
									return data;
								});

						var credentials = {
							username : 'guest',
							password : 'guest'
						}
						
						var details = $http.post('/accounts/details',
								credentials).success(function(data) {
							console.log("details=" + data.accessLevel);
						});
					})
		});