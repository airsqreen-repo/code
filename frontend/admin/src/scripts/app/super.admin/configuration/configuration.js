'use strict';

angular.module('airSqreenApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('configurationManagement', {
                parent: 'app',
                url: '/super-admin/configuration-management',
                data: {
                    roles: ['ADMIN'],
                    header: 'configuration.singular',
                    subHeader: 'configuration.management',
                },
                views: {
                    'content': {
                        templateUrl: 'scripts/app/super.admin/configuration/configuration.html',
                        controller: 'ConfigurationController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('super.admin/configuration');
                        return $translate.refresh();
                    }]
                }
            });
    });
