'use strict';

angular.module('airSqreenApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('deviceManagement', {
                parent: 'app',
                url: '/device/management',
                data: {
                    roles: ['ADMIN'],
                    header: 'device.singular',
                    subHeader: 'device.management'
                },
                views: {
                    'content': {
                        templateUrl: 'scripts/app/device/device.html',
                        controller: 'DeviceController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('device');
                        return $translate.refresh();
                    }]
                }
            }).state('device', {
                abstract: true,
                url: 'device/:mode/:id',
                parent: 'app',
                data: {
                    roles: ['ADMIN']
                },
                views: {
                    'content': {
                        templateUrl: 'scripts/app/device/device.detail.html',
                        controller: 'DeviceDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('device');
                        return $translate.refresh();
                    }],
                    deviceApi: function ($stateParams, api) {
                        return api.one('admin/devices', $stateParams.id);
                    },
                    resolvedDevice: function ($stateParams, api) {
                        return api.one('admin/devices', $stateParams.id).get();

                    }
                }
            }).state('device.detail', {
                url: '',
                parent: 'device',
                data: {
                    roles: ['ADMIN'],
                    header: 'device.singular',
                    subHeader: 'detail'
                },
                views: {
                    'tabContent': {
                        templateUrl: 'scripts/app/device/detail/detail.html',
                        controller: 'DeviceDetailController as ctrl'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('device');
                        return $translate.refresh();
                    }]
                }
            }).state('device.deviceConstraintList', {
                url: '',
                parent: 'device',
                data: {
                    roles: ['ADMIN'],
                    header: 'device.singular',
                    subHeader: 'detail'
                },
                views: {
                    'tabContent': {
                        templateUrl: 'scripts/app/device/detail/device.constraint.html',
                        controller: 'UserRoleController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('device');
                        return $translate.refresh();
                    }]
                }
            })
    });
