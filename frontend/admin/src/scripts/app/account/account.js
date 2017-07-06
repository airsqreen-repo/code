'use strict';

angular.module('airSqreenApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('account', {
                parent:'app',
                abstract: true,
            });
    });
