angular.module('market', []).controller('productController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/market';


    $scope.findAllProducts = function() {
         $http({
                    url: contextPath + '/products',
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

    $scope.previousPage = function() {
        $scope.page--;
        $scope.findAllProducts();
    }

    $scope.nextPage = function() {
        $scope.page++;
        $scope.findAllProducts();
    }
});