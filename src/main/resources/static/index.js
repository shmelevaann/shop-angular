(function ($localStorage) {
    'use strict';

    angular
        .module('app', ['ngRoute', 'ngStorage'])
        .config(config)
        .run(run);

    function config($routeProvider, $httpProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'home/home.html',
                controller: 'homeController'
            })
            .when('/products', {
                templateUrl: 'products/products.html',
                controller: 'productsController'
            })
            .when('/cart', {
                templateUrl: 'cart/cart.html',
                controller: 'cartController'
            })
            .when('/order_confirmation', {
                templateUrl: 'order_confirmation/order_confirmation.html',
                controller: 'orderConfirmationController'
            })
            .when('/order_result/:orderId', {
                templateUrl: 'order_result/order_result.html',
                controller: 'orderResultController'
            })
            .when('/orders', {
                templateUrl: 'orders/orders.html',
                controller: 'ordersController'
            })
            .otherwise({
                redirectTo: '/'
            });

        // $httpProvider.interceptors.push(function ($q, $location) {
        //     return {
        //         'responseError': function (rejection, $localStorage, $http) {
        //             var defer = $q.defer();
        //             if (rejection.status == 401 || rejection.status == 403) {
        //                 console.log('error: 401-403');
        //                 $location.path('/auth');
        //                 if (!(localStorage.getItem("localUser") === null)) {
        //                     delete $localStorage.currentUser;
        //                     $http.defaults.headers.common.Authorization = '';
        //                 }
        //                 console.log(rejection.data);
        //                 var answer = JSON.parse(rejection.data);
        //                 console.log(answer);
        //                 // window.alert(answer.message);
        //             }
        //             defer.reject(rejection);
        //             return defer.promise;
        //         }
        //     };
        // });
    }

    function run($rootScope, $http, $localStorage) {
        if ($localStorage.currentUser) {
            $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.currentUser.token;
        }
    }
})();

angular.module('app').controller('indexController', function ($scope, $http, $localStorage) {
    const contextPath = 'http://localhost:8189/market';

/*
    $scope.tryToAuth = function () {
        $http.post(contextPath + '/auth', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.currentUser = {username: $scope.user.username, token: response.data.token};

                    $scope.currentUserName = $scope.user.username;

                    $scope.user.username = null;
                    $scope.user.password = null;
                }
            }, function errorCallback(response) {
            });
    };
*/

    $scope.tryToAuth = function() {
        $http({
            url: contextPath + "/api/v1/user/auth",
            method: "POST",
            data: {
                username: $scope.user.name,
                password: $scope.user.password
            }
        }).then(function successCallback(response) {
            if(response.data.token){
                $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                $localStorage.currentUser = {username: $scope.user.name, token: response.data.token};

                $scope.user.password = null;
            }
        }, function errorCallback(response) {
          //todo
        })
    }

    $scope.tryToLogout = function () {
        $scope.clearUser();
        if ($scope.user.username) {
            $scope.user.username = null;
        }
        if ($scope.user.password) {
            $scope.user.password = null;
        }
    };

    $scope.clearUser = function () {
        delete $localStorage.currentUser;
        $http.defaults.headers.common.Authorization = '';
    };

    $scope.isUserLoggedIn = function () {
        return $localStorage.currentUser !== undefined;
    };

/*
    $scope.signUp = function() {
        $http({
            url: contextPath + "/user/signup",
            method: "POST",
            data: {
                username: $scope.user.name,
                password: $scope.user.password
            }
        }).then(function(response){
        //todo
        })
    }
*/

});