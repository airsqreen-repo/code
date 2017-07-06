'use strict';

angular.module('airSqreenApp')
    .factory('AuditsService', function ($http, SERVICE_URL) {
        return {
            findAll: function () {
                return $http.get( SERVICE_URL + 'api/audits/all').then(function (response) {
                    return response.data;
                });
            },
            findByDates: function (fromDate, toDate) {

                var formatDate =  function (dateToFormat) {
                    if (dateToFormat !== undefined && !angular.isString(dateToFormat)) {
                        return dateToFormat.getYear() + '-' + dateToFormat.getMonth() + '-' + dateToFormat.getDay();
                    }
                    return dateToFormat;
                };

                return $http.get( SERVICE_URL + 'api/audits/byDates', {params: {fromDate: formatDate(fromDate), toDate: formatDate(toDate)}}).then(function (response) {
                    return response.data;
                });
            }
        };
    });
