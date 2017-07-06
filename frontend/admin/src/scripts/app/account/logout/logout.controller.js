'use strict';

angular.module('airSqreenApp')
    .controller('LogoutController', function (Auth) {
        console.log("burda");
        Auth.logout();
    });
