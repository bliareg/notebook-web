/**
 * Created by bliareg on 13.12.15.
 */
'use strict';
var introApp = angular.module('introApp', []);

var introAppController = function ($scope, $http, $window) {

    var user = {id: 0, login: '', password: '', name: ''};
    $scope.error = {isError: false, message: '', status: 0};

    var registerUser = function () {
        $http.post('http://localhost:8080/user', user).then(
            function (response) {

                if (response.status == 200) {
                    $window.location.href = '/home';
                }
            },

            function (responseError) {
                if (responseError.status == 417) {
                    $scope.error = {isError: true, message: 'alreadyExists', status: 417};
                }
                else {
                    $scope.error = {isError: true, message: '', status: responseError.status};
                }
                //$scope.apply();
            });
    }

    $scope.user = user;
    $scope.registerUser = registerUser;
}

introApp.controller('introAppController', ['$scope', '$http', '$window', introAppController])


