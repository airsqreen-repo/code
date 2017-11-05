'use strict';

angular.module('airSqreenApp')
    .config(function ($stateProvider) {
        $stateProvider
           .state('sspViewReportManagement', {
            parent: 'app',
            url: '/report/ssp-view-report-management',
            data: {
                roles: ['ADMIN'],
                header: 'sspViewReport.singular',
                subHeader: 'sspViewReport.management',
            },
            views: {
                'content': {
                    templateUrl: 'scripts/app/report/ssp.view.report/ssp.view.report.html',
                    controller: 'SspViewReportController'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('report/ssp-view-report');
                    return $translate.refresh();
                }]
            }
        })
    });

