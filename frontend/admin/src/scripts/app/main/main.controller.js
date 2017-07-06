'use strict';

angular.module('airSqreenApp')
    .controller('MainController', function ($rootScope, $scope, $location, $state, Auth, Principal, api, $log) {
        Principal.identity().then(function(account){
            $scope.account=account;
        });
    });
