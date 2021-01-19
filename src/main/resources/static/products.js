angular.module('market', []).controller('productController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/market';
    $scope.login = false;

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

    $scope.getUserId = function() {
        $http({
            url: contextPath + "/user/login",
            method: "GET",
            params: {name: $scope.user.name}
        }).then(function(response) {
            $scope.user.id = response.data.id;
            $scope.login = true;
            $scope.findCart();
        })
    }

    $scope.findCart = function() {
        if ($scope.login){
            $http({
                url: contextPath + '/cart',
                method: 'GET',
                params: {id: $scope.user.id}
            })
            .then(function(response) {
                $scope.cart = response.data
            })
        }
    }

    $scope.addToCart = function(productId) {
        if($scope.login){
            $scope.cartItem = {};
            $scope.cartItem.product = {};
            $scope.cartItem.userId = $scope.user.id;
            $scope.cartItem.product.id = productId;
            $scope.cartItem.quantity = 1;
            $http({
                url: contextPath + "/cart",
                method: "POST",
                data: $scope.cartItem
            }). then (function() {
                $scope.cartItem = null;
                $scope.findCart();
            })
        }
    }

    $scope.deleteCartItemById = function(userId, productId) {
        $http({
            url: contextPath + "/cart",
            method: "DELETE",
            params: {userId: userId, productId: productId}
        }).then(function(){
            $scope.findCart();
        })
    }

    $scope.clearCart = function() {
        $http({
            url:contextPath + "/cart/all",
            method: "DELETE",
            params: {userId: $scope.user.id}
        }).then(function(){
            $scope.findCart();
        })
    }

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

    $scope.addNewProduct = function() {
        $http({
            url: contextPath + "/products/",
            method: "POST",
            data: $scope.newProduct
        })
        .then(function(){
            $scope.newProduct = null;
            $scope.findAllProducts();
        });
    }

    $scope.findAllProducts();
    $scope.findCart();
});
