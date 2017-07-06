angular.module('airSqreenApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('trackerManagement', {
                parent: 'app',
                url: '/super-admin/tracker-management',
                data: {
                    roles: ['ADMIN'],
                    header: 'tracker.singular',
                    subHeader: 'tracker.management',
                },
                views: {
                    'content': {
                        templateUrl: 'scripts/app/super.admin/tracker/tracker.html',
                        controller: 'TrackerController',
                    }
                },
                resolve: {
                    mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('super.admin/tracker');
                        return $translate.refresh();
                    }]
                },
                onEnter: function(Tracker) {
                    Tracker.subscribe();
                },
                onExit: function(Tracker) {
                    Tracker.unsubscribe();
                },
            });
    });
