/**
 * Created by bliareg on 12.12.15.
 */

var app = angular.module("app",[]);

var loadUser = function($scope, $http){
    $http.get('http://localhost:8080/user').success(function(data){
        $scope.user = data;
    });
}

app.controller("userController",['$scope','$http',loadUser]);
