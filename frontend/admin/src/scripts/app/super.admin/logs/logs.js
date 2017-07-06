'use strict';

angular.module('airSqreenApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('logManagement', {
                parent: 'app',
                url: '/super-admin/logs-management',
                data: {
                    roles: ['ADMIN'],
                    header: 'logs.plural',
                    subHeader: 'logs.management'
                },
                views: {
                    'content': {
                        templateUrl: 'scripts/app/super.admin/logs/logs.html',
                        controller: 'LogsController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('super.admin/logs');
                        return $translate.refresh();
                    }]
                }
            });
    });
