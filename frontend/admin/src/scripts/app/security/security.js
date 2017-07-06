'use strict';

angular.module('airSqreenApp')
    .config(function ($stateProvider) {

        $stateProvider
            .state('security', {
                abstract: true,
                parent: 'app',
            });
    });
