'use strict';

angular.module('airSqreenApp')
    .config(function ($stateProvider) {

        $stateProvider
            .state('superadmin', {
                abstract: true,
                parent: 'app',
            });
    });
