angular.module('app').controller('ordersController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/market';

    $scope.findOrders = function() {
        $http({
            url: contextPath + "/api/v1/user/orders",
            method: "GET"
        }).then(function(response) {
            $scope.orders = response.data;
            $scope.orders.forEach(item => $scope.countOrderTotal(item));
        })
    }

    $scope.countOrderTotal = function(order) {
        order.price = 0;
        order.items.forEach(item => {
            order.price += item.price * item.quantity;
            });
    }

    $scope.findOrders();
});