'use strict';

angular.module('airSqreenApp', ['LocalStorageModule', 'tmh.dynamicLocale', 'pascalprecht.translate',
    'ngResource', 'ui.router', 'ui.bootstrap', 'ngSanitize', 'ngAnimate', 'ngSanitize', 'kendo.directives', 'as.sortable', 'ui.mask', 'dtrw.bcrypt', 'ngCookies', 'ngCacheBuster', 'ngFileUpload', 'infinite-scroll'])
    .config(function (apiProvider, SERVICE_URL) {
        apiProvider.setBaseUrl(SERVICE_URL+'api/');
    })
    .run(function ($rootScope, $location, $window, $http, $state, $translate, Language, Auth, Principal, ENV, VERSION,CDN_URL) {
        $rootScope.ENV = ENV;
        $rootScope.VERSION = VERSION;
        $rootScope.$on('$stateChangeStart', function (event, toState, toStateParams) {
            $rootScope.toState = toState;
            $rootScope.toStateParams = toStateParams;

            if (Principal.isIdentityResolved()) {
                Auth.authorize();
            }

            // Update the language
            Language.getCurrent().then(function (language) {
                $translate.use(language);
            });

        });

        $rootScope.$on('$stateChangeSuccess',  function(event, toState, toParams, fromState, fromParams) {
            var headerKey = 'global.title' ;
            var subHeader = 'global.subheader' ;

            $rootScope.previousStateName = fromState.name;
            $rootScope.previousStateParams = fromParams;

            // Set the page title key to the one configured in state or use default one
            if (toState.data.header) {
                headerKey = toState.data.header;
                $rootScope.header=headerKey;
            }
            if (toState.data.subHeader) {
                subHeader = toState.data.subHeader;
                $rootScope.subHeader=subHeader;
            }

            $translate(headerKey).then(function (title) {
                // Change window title with translated one
                $window.document.title = title;
                $translate(subHeader).then(function (title) {
                    // Change window title with translated one
                    $window.document.title  = $window.document.title  + ' ' + title;
                });
            });


        });

        $rootScope.back = function() {
            // If previous state is 'activate' or do not exist go to 'home'
            if ($rootScope.previousStateName === 'activate' || $state.get($rootScope.previousStateName) === null) {
                $state.go('home');
            } else {
                $state.go($rootScope.previousStateName, $rootScope.previousStateParams);
            }
        };

        $rootScope.dataStatuses = {
            "ACTIVE": 'Active',
            "PASSIVE": 'Passive'
        };

        $rootScope.dataStatusDataSource={
            data: _.keys($rootScope.dataStatuses).map(function(key) { return { text: $rootScope.dataStatuses[key], value: key}})
        };

        $rootScope.deleteTooltip = $translate.instant('deleteTooltip');
        $rootScope.editTooltip = $translate.instant('editTooltip');
        $rootScope.viewTooltip = $translate.instant('viewTooltip');

        $rootScope.choose = $translate.instant('choose');

        $rootScope.cdnUrl=CDN_URL;

        $rootScope.$on('notifySuccess', function (e, args) {
            $rootScope.notification.show(args, 'success');
        });

        $rootScope.$on('notifyError', function (e, args) {
            $rootScope.notification.show(args, 'error');
        });

        $rootScope.kendoTools= [
            "bold",
            "italic",
            "underline",
            "strikethrough",
            "justifyLeft",
            "justifyCenter",
            "justifyRight",
            "justifyFull",
            "insertUnorderedList",
            "insertOrderedList",
            "indent",
            "outdent",
            "createLink",
            "unlink",
            "insertImage",
            "insertFile",
            "subscript",
            "superscript",
            "createTable",
            "addRowAbove",
            "addRowBelow",
            "addColumnLeft",
            "addColumnRight",
            "deleteRow",
            "deleteColumn",
            "viewHtml",
            "formatting",
            "cleanFormatting",
            "fontName",
            "fontSize",
            "foreColor",
            "backColor",
            "print"
        ];

    })
    .factory('authInterceptor', function ($rootScope, $q, $location, localStorageService) {
        return {
            // Add authorization token to headers
            request: function (config) {
                config.headers = config.headers || {};
                var token = localStorageService.get('token');

                if (token && token.expires_at && token.expires_at > new Date().getTime()) {
                    config.headers.Authorization = 'Bearer ' + token.access_token;
                }

                return config;
            }
        };
    })
    .factory('authExpiredInterceptor', function ($rootScope, $q, $injector, localStorageService) {
        return {
            responseError: function (response) {
                // token has expired
                if (response.status === 401 && (response.data.error == 'invalid_token' || response.data.error == 'Unauthorized')) {
                    localStorageService.remove('token');
                    var Principal = $injector.get('Principal');
                    if (Principal.isAuthenticated()) {
                        var Auth = $injector.get('Auth');
                        Auth.authorize(true);
                    }
                }
                return $q.reject(response);
            }
        };
    })
    .config(function ($stateProvider, $urlRouterProvider, $httpProvider, $locationProvider, $translateProvider, tmhDynamicLocaleProvider, httpRequestInterceptorCacheBusterProvider) {
        //Cache everything except rest api requests
        httpRequestInterceptorCacheBusterProvider.setMatchlist([/.*api.*/, /.*protected.*/], true);

        $urlRouterProvider.otherwise('/');

        $stateProvider.state('app', {
            'abstract': true,
            views: {
                'container@': {
                    templateUrl: 'scripts/app/main/app/app.html',
                    controller: function (ENV, $rootScope, $scope, $location, $state, Auth, Principal, AppUtils){

                        Principal.identity().then(function(account) {
                            $rootScope.account = account;
                            $rootScope.isAuthenticated = Principal.isAuthenticated;

                            if(!$rootScope.isAuthenticated()){
                                $state.go('login');
                            }
                        });

                        $scope.$state = $state;

                        $scope.isAuthenticated = Principal.isAuthenticated;


                        $scope.loadCache = function () {
                            AppUtils.users(true).then();
                            AppUtils.groups(true).then();
                            AppUtils.polls(true).then();
                            AppUtils.chats(true).then();
                            AppUtils.productCategories(true).then();
                            AppUtils.pushMessages(true).then();

                            /*AppUtils.systemMessages();*/
                        };
                        if(ENV == 'prod') {
                            $scope.loadCache();
                        }

                    }
                }
            },
            resolve: {
                authorize: ['Auth',
                    function (Auth) {
                        return Auth.authorize();
                    }
                ],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('global');
                }]
            }
        });


        $httpProvider.interceptors.push('authInterceptor');
        $httpProvider.interceptors.push('authExpiredInterceptor');

        // Initialize angular-translate
        $translateProvider.useLoader('$translatePartialLoader', {
            urlTemplate: 'i18n/{lang}/{part}.json'
        });

        $translateProvider.preferredLanguage('tr');
        $translateProvider.useCookieStorage();
        $translateProvider.useSanitizeValueStrategy('escaped');

        tmhDynamicLocaleProvider.localeLocationPattern('bower_components/angular-i18n/angular-locale_{{locale}}.js');
        tmhDynamicLocaleProvider.useCookieStorage();
        tmhDynamicLocaleProvider.storageKey('NG_TRANSLATE_LANG_KEY');

    });
