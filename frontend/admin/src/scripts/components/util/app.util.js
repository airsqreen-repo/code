
angular.module('airSqreenApp')
    .service('AppUtilsService', function ($translate, LANGUAGE,DATA_STATUS ) {

        this.getLanguages = function () {
            return {
                data: _.keys(LANGUAGE).map(function(key) { return { name: $translate.instant(LANGUAGE[key]), id: key}})
            };
        };
        this.getDataStatus = function () {
            return {
                data: _.keys(DATA_STATUS).map(function(key) { return { name: $translate.instant(DATA_STATUS[key]), id: key}})
            };
        };

    }) .factory('AppUtils', function AppUtils( $log, api, promiseFactory) {
        var _users;
        var _usersApi= api.all('admin/users/list');

    return {
            users: function (force){
                if (force === true) {
                    _users = undefined;
                }
                var dfd = promiseFactory.defer();

                if (angular.isDefined(_users)) {
                    dfd.resolve(_users);
                    return dfd.promise;
                }

                _usersApi.get().then(function(result){
                    _users=result;
                    return dfd.resolve(_users);
                }, function(reason) {
                    
                    _users = null;
                    return dfd.resolve(_users);
                });

                return dfd.promise;
            }
        };
    })