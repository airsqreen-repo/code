'use strict';

angular.module('airSqreenApp')
    .factory('LogsService', function ($resource, SERVICE_URL) {
        return $resource( SERVICE_URL + 'api/logs', {}, {
            'findAll': { method: 'GET', isArray: true},
            'changeLevel': { method: 'PUT'}
        });
    });
