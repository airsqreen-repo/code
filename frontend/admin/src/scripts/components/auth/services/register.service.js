'use strict';

angular.module('airSqreenApp')
    .factory('Register', function ($resource,  SERVICE_URL ) {
        return $resource( SERVICE_URL + 'api/register', {}, {
        });
    });


