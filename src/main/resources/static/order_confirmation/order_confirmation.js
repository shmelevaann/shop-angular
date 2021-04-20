angular.module('app').controller('orderConfirmationController', function ($scope, $http, $location) {
    const contextPath = 'http://localhost:8189/market';

    $scope.getAddresses = function() {
        $http({
            url: contextPath + "/api/v1/addresses",
            method: "GET"
        }).then(function(response) {
            $scope.user.addresses = response.data;
        })
    }

    $scope.addAddress = function() {
        $http({
            url: contextPath + "/api/v1/addresses",
            method: "POST",
            params: {address: $scope.user.newAddress}
        }).then(function() {
            $scope.getAddresses();
            $scope.user.newAddress = null;
        })
    }

    $scope.checkOut = function(addressId) {
        $http({
            url: contextPath + "/api/v1/cart/checkout",
            method: "POST",
            params: {address: addressId}
        }).then(function(){
            $location.path('/order_result/' + response.data.id);
        })
    }

    $scope.findCart = function() {
        $http({
            url: contextPath + '/api/v1/cart',
            method: 'GET',
        })
        .then(function(response) {
            $scope.cart = response.data;
            $scope.findCartTotal();
        })
    }

    $scope.findCartTotal = function() {
        $scope.totalCartPrice = 0;
        $scope.cart.forEach(item => {
            $scope.totalCartPrice += item.product.price * item.quantity
        })
    }

    $scope.findCart();
});