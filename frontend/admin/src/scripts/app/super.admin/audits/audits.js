'use strict';

angular.module('airSqreenApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('auditManagement', {
                parent: 'app',
                url: '/super-admin/audit-management',
                data: {
                    roles: ['ADMIN'],
                    header: 'audits.singular',
                    subHeader: 'audits.management',
                },
                views: {
                    'content': {
                        templateUrl: 'scripts/app/super.admin/audits/audits.html',
                        controller: 'AuditsController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('super.admin/audits');
                        return $translate.refresh();
                    }]
                }
            });
    });
