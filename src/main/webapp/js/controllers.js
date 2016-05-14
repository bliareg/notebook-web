/**
 * Created by bliareg on 12.12.15.
 */
'use strict';
var organizer = angular.module('organizer', []);

/*----User info controller----*/
var userInfoController = function ($scope, $http, $window) {


    /*------User Controller------*/
    $scope.user;
    var loadUser = function () {

        $http.get('http://localhost:8080/user').success(
            function (data) {
                $scope.user = data;
            },

            function () {
                /*todo розрулити помилки*/
            }
        );
    }

    var logout = function () {
        $http.get('/logout').success(function (response) {
        }, function (errorResponse) {
        });
        $window.location.href = '/';
    }
    /*--------------------------*/

    $scope.loadUser = loadUser;
    $scope.logout = logout;
    loadUser();
}
/*----------------------------*/



//________________________________________________________________________________


/*------App controller------*/
var appController = function ($scope, $http) {

    /*---Тут дістається id користувача---*/
    var userid;
    $scope.connection = true;
    var loadUserId = function () {

        $http.get('http://localhost:8080/user').success(
            function (data) {
                $scope.connection = true;
                userid = data.id;
            },

            function (response) {
                $scope.connection = false;
                /*todo розрулити помилки*/
            }
        );
    }
    loadUserId();
    /*-----------*/

    /*---CRUD---*/
    var noteService = {};
    /*Завантаження записок*/
    noteService.loadAllNotes = function () {
        $http.get('http://localhost:8080/note').success(
            function (response) {

                $scope.connection = true;
                $scope.notes = response;

            },
            function (response) {
                $scope.connection = false;
                //    todo розрулювання помилки
            }
        );

        var noteIt;
        for (noteIt in $scope.notes) {
            var strDate = noteIt.createTime;
            noteIt.createTime = new Date(strDate);
        }
    }

    /*Обновлення записок*/
    noteService.updateNote = function (id, note) {

        return $http.put('http://localhost:8080/note/' + id, note).then(
            function (data) {
                $scope.connection = true;
                return data;
            }, function (response) {
                $scope.connection = false;
                return null;
            }
        )
    }

    /*Створення нотатки*/
    noteService.createNote = function () {

        var note = {note: '', isTodo: false, header: ''};
        var ct = new Date();
        note.userid = userid;
        note.createTime = ct.getFullYear() + '-' + ct.getMonth() + '-' + ct.getDay() + ' ' + ct.getHours() + ':' + ct.getMinutes() + ':' + ct.getSeconds();
        note.remindPattern = ''
        note.header = note.createTime;

        $http.post('http://localhost:8080/note', note).then(
            function (response) {

                console.log(' CREATED new note!!!');
                $scope.connection = true;
                if ($scope.notes.isArray == true || $scope.notes.length > 0) {
                    $scope.notes.push(response.data);
                }
                else {
                    $scope.notes = [];
                    $scope.notes.push(response.data);
                }


            }).catch(function (error) {
                $scope.connection = false;
                return null;
            });

    }

    /*видалення нотатки з бд*/
    noteService.deleteNote = function (noteId) {
        return $http.delete('http://localhost:8080/note/' + noteId).success(
            function (response) {
                return true;
            },

            function (response) {
                return false;
            }
        );
    }
    /*---CRUD---*/

    /*видаляння нотаток*/
    var removeNote = function (note) {

        var issuccess = noteService.deleteNote(note.id);

        if (issuccess == false || issuccess.undefined || issuccess == null) {
            $scope.connection = false;

        } else {

            var id = $scope.notes.indexOf(note);
            $scope.connection = true;
            $scope.notes.splice(id, 1);
        }

    }

    var saveNotes = function () {
        var iter = 0;
        for (iter; iter < $scope.notes.length; iter++) {
            noteService.updateNote($scope.notes[iter].id, $scope.notes[iter]);
        }

    }

    var initialize = function () {
        $scope.orderItems = [
            {id: '-createTime', title: 'Найновіші'},
            {id: 'header', title: 'За алфавітом'}]
        $scope.orderItem = '-createTime';
    }



    initialize();
    $scope.notes = [];
    noteService.loadAllNotes();
    $scope.noteService = noteService;
    $scope.removeNote = removeNote;
    $scope.saveNotes = saveNotes;
    /*-----------*/
}
/*--------------------------*/


organizer.controller('userInfo', ['$scope', '$http', '$window', userInfoController]);
organizer.controller('app', ['$scope', '$http', appController]);


