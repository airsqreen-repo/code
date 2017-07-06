'use strict';

angular.module('airSqreenApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('docsManagement', {
                parent: 'app',
                url: '/super-admin/docs-management',
                data: {
                    roles: ['ADMIN'],
                    header: 'docs.singular',
                    subHeader: 'docs.management'
                },
                views: {
                    'content': {
                        templateUrl: 'scripts/app/super.admin/docs/docs.html',
                        controller:function($scope, $sce){
                            $scope.iframe = $sce.trustAsResourceUrl('http://apidoc.guideofpregnancy.com/');
                        }
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('super.admin/docs');
                        return $translate.refresh();
                    }]
                }
            });
    });
