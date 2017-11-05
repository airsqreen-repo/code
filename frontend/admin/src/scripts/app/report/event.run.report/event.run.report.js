'use strict';

angular.module('airSqreenApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('eventRunReportManagement', {
                parent: 'app',
                url: '/report/event-run-report-management',
                data: {
                    roles: ['ADMIN'],
                    header: 'eventRunReport.singular',
                    subHeader: 'eventRunReport.management',
                },
                views: {
                    'content': {
                        templateUrl: 'scripts/app/report/event.run.report/event.run.report.html',
                        controller: 'EventRunReportController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('report/event-run-report');
                        return $translate.refresh();
                    }]
                }
            })
    });

