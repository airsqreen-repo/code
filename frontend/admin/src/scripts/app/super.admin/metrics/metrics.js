'use strict';

angular.module('airSqreenApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('metricManagement', {
                parent: 'app',
                url: '/super-admin/metric-management',
                data: {
                    roles: ['ADMIN'],
                    header: 'metrics.singular',
                    subHeader: 'metrics.management',
                },
                views: {
                    'content': {
                        templateUrl: 'scripts/app/super.admin/metrics/metrics.html',
                        controller: 'MetricsController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('super.admin/metrics');
                        return $translate.refresh();
                    }]
                }
            });
    });
