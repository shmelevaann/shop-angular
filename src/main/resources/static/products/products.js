angular.module('app').controller('productsController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/market';

    $scope.findAllProducts = function() {
         $http({
                    url: contextPath + '/api/v1/products',
                    method: 'GET',
                    params: {
                        page: $scope.page ? $scope.page : 0,
                        size: $scope.size ? $scope.size : 3
                    }
                })
            .then(function (response) {
                $scope.ProductsList = response.data.products;
                $scope.page = response.data.currentPage;
                $scope.totalPages = response.data.totalPages;
            })
    }

    $scope.previousPage = function() {
        $scope.page--;
        $scope.findAllProducts();
    }

    $scope.nextPage = function() {
        $scope.page++;
        $scope.findAllProducts();
    }

    $scope.updateCart = function(productId, quantity) {
        $http({
            url: contextPath + "/api/v1/cart",
            method: "POST",
            params: {
                product: productId,
                quantity: quantity
            }
        })
    }

    $scope.findAllProducts();
});