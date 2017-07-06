'use strict';

angular.module('airSqreenApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('home', {
                parent: 'app',
                url: '/',
                data: {
                    roles: [],
                    header: 'main.appName'
                },
                views: {
                    'content': {
                        templateUrl: 'scripts/app/main/main.html',
                        controller: 'MainController'
                    }
                },
                resolve: {
                    dashboardTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                       $translatePartialLoader.addPart('dashboard');
                        return $translate.refresh();
                    }]
                }
            });
    });
