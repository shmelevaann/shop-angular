angular.module('market', []).controller('productController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/market';
    $scope.loggedIn = false;

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

    $scope.tryToLogIn = function() {
        $http({
            url: contextPath + "/user/auth",
            method: "POST",
            data: {
                username: $scope.user.name,
                password: $scope.user.password
            }
        }).then(function(response) {
            $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
            $scope.user.password = null;
            $scope.loggedIn = true;
            $scope.findCart();
        })
    }

    $scope.findCart = function() {
            $http({
                url: contextPath + '/cart',
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
                url: contextPath + "/cart",
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
            url: contextPath + "/cart",
            method: "DELETE",
            params: {productId: productId}
        }).then(function(){
            $scope.findCart();
        })
    }

    $scope.clearCart = function() {
        $http({
            url:contextPath + "/cart/all",
            method: "DELETE",
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

//    $scope.addNewProduct = function() {
//        $http({
//            url: contextPath + "/products/",
//            method: "POST",
//            data: $scope.newProduct
//        })
//        .then(function(){
//            $scope.newProduct = null;
//            $scope.findAllProducts();
//        });
//    }

    $scope.findAllProducts();
//    $scope.findCart();
});
