'use strict';

angular.module('airSqreenApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('roleManagement', {
                parent: 'app',
                url: '/security/role/management',
                data: {
                    roles: ['ADMIN'],
                    header: 'security.role.singular',
                    subHeader: 'security.role.management',
                },
                views: {
                    'content': {
                        templateUrl: 'scripts/app/security/role/role.html',
                        controller: 'RoleController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('security/role');
                        return $translate.refresh();
                    }]
                }
            }).state('role', {
                abstract: true,
                url: 'security/role/:mode/:id',
                parent: 'app',
                data: {
                    roles: ['ADMIN']
                },
                views: {
                    'content': {
                        templateUrl: 'scripts/app/security/role/role.detail.html',
                        controller: 'RoleDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('security/role');
                        return $translate.refresh();
                    }],
                    roleApi: function ($stateParams, api) {
                        return api.one('admin/roles', $stateParams.id);
                    },
                    resolvedRole: function ($stateParams, api) {
                        return api.one('admin/roles', $stateParams.id).get();
                    }
                },
            }).state('role.detail', {
                url: '',
                parent: 'role',
                data: {
                    roles: ['ADMIN'],
                    header: 'security.role.singular',
                    subHeader: 'detail',
                },
                views: {
                    'tabContent': {
                        templateUrl: 'scripts/app/security/role/detail/detail.html',
                        controller: 'RoleDetailController as ctrl'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('security/role');
                        return $translate.refresh();
                    }]
                },
            }).state('role.rightList', {
                url: '/right-list',
                parent: 'role',
                data: {
                    roles: ['ADMIN'],
                    header: 'security.role.singular',
                    subHeader: 'detail',
                },
                views: {
                    'tabContent': {
                        templateUrl: 'scripts/app/security/role/detail/role.right.html',
                        controller: 'RoleRightController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('security/role');
                        return $translate.refresh();
                    }]
                },
            });
    });
