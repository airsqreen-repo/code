'use strict';

angular.module('airSqreenApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('campaignManagement', {
                parent: 'app',
                url: '/campaign/management',
                data: {
                    roles: ['ADMIN'],
                    header: 'campaign.singular',
                    subHeader: 'campaign.management'
                },
                views: {
                    'content': {
                        templateUrl: 'scripts/app/campaign/campaign.html',
                        controller: 'CampaignController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('campaign');
                        return $translate.refresh();
                    }]
                }
            }).state('campaign', {
                abstract: true,
                url: 'campaign/:mode/:id',
                parent: 'app',
                data: {
                    roles: ['ADMIN']
                },
                views: {
                    'content': {
                        templateUrl: 'scripts/app/campaign/campaign.detail.html',
                        controller: 'CampaignDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('campaign');
                        return $translate.refresh();
                    }],
                    campaignApi: function ($stateParams, api) {
                        return api.one('admin/campaigns', $stateParams.id);
                    },
                    resolvedCampaign: function ($stateParams, api) {
                        return api.one('admin/campaigns', $stateParams.id).get();

                    }
                }
            }).state('campaign.detail', {
                url: '',
                parent: 'campaign',
                data: {
                    roles: ['ADMIN'],
                    header: 'campaign.singular',
                    subHeader: 'detail'
                },
                views: {
                    'tabContent': {
                        templateUrl: 'scripts/app/campaign/detail/detail.html',
                        controller: 'CampaignDetailController as ctrl'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('campaign');
                        return $translate.refresh();
                    }]
                }
            }).state('campaign.campaignSections', {
                url: '',
                parent: 'campaign',
                data: {
                    roles: ['ADMIN'],
                    header: 'campaign.singular',
                    subHeader: 'detail'
                },
                views: {
                    'tabContent': {
                        templateUrl: 'scripts/app/campaign/detail/campaign.section.html',
                        controller: 'CampaignSectionController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('campaign');
                        return $translate.refresh();
                    }]
                }
            }).state('campaign.campaignConstraints', {
                url: '',
                parent: 'campaign',
                data: {
                    roles: ['ADMIN'],
                    header: 'campaign.singular',
                    subHeader: 'detail'
                },
                views: {
                    'tabContent': {
                        templateUrl: 'scripts/app/campaign/detail/campaign.constraint.html',
                        controller: 'CampaignConstraintController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('campaign');
                        return $translate.refresh();
                    }]
                }
            })
    });
