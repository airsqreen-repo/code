'use strict';

angular.module('airSqreenApp')
    .factory('MonitoringService', function ($rootScope, $http, SERVICE_URL) {
        return {
            getMetrics: function () {
                return $http.get( SERVICE_URL +  'metrics/metrics').then(function (response) {
                    return response.data;
                });
            },

            checkHealth: function () {
                return $http.get( SERVICE_URL + 'health').then(function (response) {
                    return response.data;
                });
            },

            threadDump: function () {
                return $http.get( SERVICE_URL + 'dump').then(function (response) {
                    return response.data;
                });
            }
        };
    });
