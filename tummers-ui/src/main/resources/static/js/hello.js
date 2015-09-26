angular
		.module('hello', [ 'participants', 'foodevent', 'ngRoute' ])
		.config(function($routeProvider, $httpProvider) {

			$routeProvider.when('/', {
				templateUrl : 'home.html',
				controller : 'home'
			}).when('/login', {
				templateUrl : 'login.html',
				controller : 'navigation'

			}).when('/upcoming', {
				templateUrl : 'upcoming.html',
			}).when('/admin-home', {
				templateUrl : 'admin-home.html',
				controller : 'admin-home'
			}).when('/participants', {
				templateUrl : 'participants.html',
			}).otherwise('/');

			$httpProvider.defaults.headers.common['Y-TOKEN'] = 'NoToken';

		})
		.controller(
				'navigation',

				function($rootScope, $scope, $http, $location, $route) {

					$scope.tab = function(route) {
						return $route.current
								&& route === $route.current.controller;
					};

					var authenticate = function(credentials, callback) {
						$http
								.post('/accounts/login', credentials)
								.success(
										function(data, headers, status, config) {
											console.log('data=' + data);
											console.log('status=' + status);
											console.log('config=' + config);
											console.log('got tokenized!');
											console.log('token='
													+ headers['Y-TOKEN']);
											console.log('token2=' + headers);
											console
													.log('providerHeader='
															+ $http.defaults.headers.common['Y-TOKEN']);

										});

						$http
								.post('/accounts/details', credentials)
								.success(
										function(data) {
											console.log('AccessLevel:'
													+ data.accessLevel);
											if (data.accessLevel === 'ROLE_ADMIN'
													|| data.accessLevel === 'ROLE_SUPER') {
												$rootScope.authenticated = true;
											} else {
												$rootScope.authenticated = false;
											}
											callback
													&& callback($rootScope.authenticated);
										}).error(function() {
									$rootScope.authenticated = false;
									callback && callback(false);
								});

					}

					authenticate();

					$scope.credentials = {};
					$scope.login = function() {
						authenticate($scope.credentials,
								function(authenticated) {
									if (authenticated) {
										console.log("Login succeeded")
										$location.path("/admin-home");
										$scope.error = false;
										$rootScope.authenticated = true;
									} else {
										console.log("Login failed")
										$location.path("/login");
										$scope.error = true;
										$rootScope.authenticated = false;
									}
								})
					};

					$scope.logout = function() {

						$http.post('/accounts/logout', {}).success(function() {
							console.log('called logout');
						}).error(function() {
							console.log('failed');
						});

						$http.post('logout', {}).success(function() {
							$rootScope.authenticated = false;
							$location.path("/");
						}).error(function(data) {
							console.log("Logout failed");
							$rootScope.authenticated = false;
						});
					}

				}).controller('home', function($scope, $http) {

		}).controller('admin-home', function($scope, $http) {

			$http.get('/foodEvents/upcoming/').success(function(data) {
				console.log("in foodevents");
			});

			$http.get('/accounts/user/').success(function(data) {
				console.log('got dat user');
				console.log('username=' + data.username);
				$scope.username = data.username;
			});

			console.log('admin-home-new');
		});
