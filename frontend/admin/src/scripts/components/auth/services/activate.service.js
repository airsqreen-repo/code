'use strict';

angular.module('airSqreenApp')
    .factory('Activate', function ($resource, SERVICE_URL ) {
        return $resource( SERVICE_URL + 'api/activate', {}, {
            'get': { method: 'GET', params: {}, isArray: false}
        });
    });


