'use strict';

angular.module('airSqreenApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('healthManagement', {
                parent: 'app',
                url: '/super-admin/health-management',
                data: {
                    roles: ['ADMIN'],
                    header: 'health.singular',
                    subHeader: 'health.management'
                },
                views: {
                    'content': {
                        templateUrl: 'scripts/app/super.admin/health/health.html',
                        controller: 'HealthController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('super.admin/health');
                        return $translate.refresh();
                    }]
                }
            });
    });
