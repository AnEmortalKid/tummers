angular.module('foodevent', []).controller(
		'upcoming',
		function($scope, $http, $q, $route) {

			$scope.tab = function(route) {
				return $route.current && route === $route.current.controller;
			};

			$http.get('/foodEvents/upcoming/').success(
					function(data) {
						console.log(data);

						var innerSlot = data.slot;
						$scope.eventDate = slotToDateStr(innerSlot);

						var breakfastIds = data.breakfastParticipants;
						var snackIds = data.snackParticipants;
						console.log("bIds" + breakfastIds);
						console.log("sIds" + snackIds);

						$http.get('/associates/' + breakfastIds).success(
								function(data) {
									console.log("breakfast=" + data);
									$scope.breakfastAssociates = data;
									return data;
								});

						$http.get('/associates/' + snackIds).success(
								function(data) {
									console.log("snacks=" + data)
									$scope.snackAssociates = data;
									return data;
								});
					});
		});

function slotToDateStr(slot) {
	var slotDate = slot.slotDate;
	return slotDate.dayOfMonth + "/" + slotDate.monthOfYear + "/"
			+ slotDate.year;
}

function extractNames($http, associateIds, $q) {
	var defer = $q.defer();
	var temp = {};
	console.log("extracting items");
	$http.get('/associates/' + associateIds).success(function(data) {
		console.log("extractNames:" + data);
		temp = data;
		defer.resolve(data);
	});
	console.log("temp=" + temp);
	var promise = defer.promise;
	console.log(promise);
	return promise;
}