angular.module('market', []).controller('productController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/market';

    $scope.findAllProducts = function() {
        $http.get(contextPath + '/products')
            .then(function (response) {
                $scope.ProductsList = response.data;
            })
    }

    $scope.findAllProducts();

    $scope.deleteProductById = function(id) {
        $http({
            url: contextPath + "/products/" + id,
            method: "DELETE"
        })
            .then(function () {
                $scope.findAllProducts();
            })
    }
});