'use strict';

angular.module('airSqreenApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('logout', {
                url: 'logout',
                data: {
                    roles: []
                },
                views: {
                    'container@': {
                        templateUrl: 'scripts/app/account/logout/logout.html',
                        controller: 'LogoutController'
                    }
                },
                resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('account/logout');
                    return $translate.refresh();
                }]
            }
            });
    });
