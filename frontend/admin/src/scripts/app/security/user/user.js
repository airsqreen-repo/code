'use strict';

angular.module('airSqreenApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('userManagement', {
                parent: 'app',
                url: '/security/user/management',
                data: {
                    roles: ['ADMIN'],
                    header: 'security.user.singular',
                    subHeader: 'security.user.management'
                },
                views: {
                    'content': {
                        templateUrl: 'scripts/app/security/user/user.html',
                        controller: 'UserController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('security/user');
                        return $translate.refresh();
                    }]
                }
            }).state('user', {
                abstract: true,
                url: 'security/user/:mode/:id',
                parent: 'app',
                data: {
                    roles: ['ADMIN']
                },
                views: {
                    'content': {
                        templateUrl: 'scripts/app/security/user/user.detail.html',
                        controller: 'UserDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('security/user');
                        $translatePartialLoader.addPart('security/role');
                        return $translate.refresh();
                    }],
                    userApi: function ($stateParams, api) {
                        return api.one('admin/users', $stateParams.id);
                    },
                    resolvedUser: function ($stateParams, api) {
                        return api.one('admin/users', $stateParams.id).get();

                    }
                }
            }).state('user.detail', {
                url: '',
                parent: 'user',
                data: {
                    roles: ['ADMIN'],
                    header: 'security.user.singular',
                    subHeader: 'detail'
                },
                views: {
                    'tabContent': {
                        templateUrl: 'scripts/app/security/user/detail/detail.html',
                        controller: 'UserDetailController as ctrl'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('security/user');
                        return $translate.refresh();
                    }]
                }
            }).state('user.roleList', {
                url: '',
                parent: 'user',
                data: {
                    roles: ['ADMIN'],
                    header: 'security.user.singular',
                    subHeader: 'detail'
                },
                views: {
                    'tabContent': {
                        templateUrl: 'scripts/app/security/user/detail/user.role.html',
                        controller: 'UserRoleController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('security/user');
                        return $translate.refresh();
                    }]
                }
            }).state('user.profilesList', {
                url: '',
                parent: 'user',
                data: {
                    roles: ['ADMIN'],
                    header: 'security.user.singular',
                    subHeader: 'detail'
                },
                views: {
                    'tabContent': {
                        templateUrl: 'scripts/app/security/user/profile/user.profile.html',
                        controller: 'UserProfileController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('security/user');
                        return $translate.refresh();
                    }],
                    resolvedUserProfile: function ($stateParams, api) {
                        return api.one('admin/userProfiles', $stateParams.id).get();
                    }
                }
            }).state('user.groupList', {
            url: '',
            parent: 'user',
            data: {
                roles: ['ADMIN'],
                header: 'security.user.singular',
                subHeader: 'detail'
            },
            views: {
                'tabContent': {
                    templateUrl: 'scripts/app/security/user/detail/user.group.html',
                    controller: 'UserGroupController'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('security/user');
                    return $translate.refresh();
                }]
            }
        }).state('user.memberGroupList', {
            url: '',
            parent: 'user',
            data: {
                roles: ['ADMIN'],
                header: 'security.user.singular',
                subHeader: 'detail'
            },
            views: {
                'tabContent': {
                    templateUrl: 'scripts/app/security/user/detail/user.group.html',
                    controller: 'UserMemberGroupController'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('security/user');
                    return $translate.refresh();
                }],
                resolvedGroups: function(AppUtils){
                    return AppUtils.groups()
                }
            }
        });
    });
