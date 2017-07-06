'use strict';

angular.module('airSqreenApp')
    .factory('ConfigurationService', function ($rootScope, $filter, $http,  SERVICE_URL ) {
        return {
            get: function() {
                return $http.get( SERVICE_URL + 'configprops').then(function (response) {
                    var properties = [];
                    angular.forEach(response.data, function (data) {
                        properties.push(data);
                    });
                    var orderBy = $filter('orderBy');
                    return orderBy(properties, 'prefix');
                });
            }
        };
    });
