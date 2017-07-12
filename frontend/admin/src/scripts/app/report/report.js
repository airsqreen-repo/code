'use strict';

angular.module('airSqreenApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('reportManagement', {
                parent: 'app',
                url: '/report/management',
                data: {
                    roles: ['ADMIN'],
                    header: 'report.singular',
                    subHeader: 'report.management',
                },
                views: {
                    'content': {
                        templateUrl: 'scripts/app/report/report.html',
                        controller: 'ReportController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('report');
                        return $translate.refresh();
                    }]
                }
            })
    });

