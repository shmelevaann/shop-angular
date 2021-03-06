angular.module('app').controller('cartController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:8189/market';

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

    $scope.updateCart = function(productId, quantity) {
        $http({
            url: contextPath + "/api/v1/cart",
            method: "POST",
            params: {
                product: productId,
                quantity: quantity
            }
        }). then (function() {
            $scope.findCart();
        })
    }

    $scope.deleteCartItem = function(productId) {
        $http({
            url: contextPath + "/api/v1/cart",
            method: "DELETE",
            params: {productId: productId}
        }).then(function(){
            $scope.findCart();
        })
    }

    $scope.clearCart = function() {
        $http({
            url:contextPath + "/api/v1/cart/all",
            method: "DELETE",
        }).then(function(){
            $scope.findCart();
        })
    }

    $scope.startCheckOut = function() {
         $location.path('/order_confirmation');
    }

    $scope.isUserLoggedIn = function () {
        return $localStorage.currentUser !== undefined;
    };

    $scope.findCart();
});