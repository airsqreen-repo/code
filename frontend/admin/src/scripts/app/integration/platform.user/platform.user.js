'use strict';

angular.module('airSqreenApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('platformUserManagement', {
                parent: 'app',
                url: 'integration/platform-user/management',
                data: {
                    roles: ['ADMIN'],
                    header: 'platformUser.singular',
                    subHeader: 'platformUser.management',
                },
                views: {
                    'content': {
                        templateUrl: 'scripts/app/integration/platform.user/platform.user.html',
                        controller: 'PlatformUserController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('integration/platform.user');
                        return $translate.refresh();
                    }]
                }
            });
    });
