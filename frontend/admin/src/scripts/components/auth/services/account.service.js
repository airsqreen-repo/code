'use strict';

angular.module('airSqreenApp')
    .factory('Account', function Account($resource, SERVICE_URL) {
        return $resource( SERVICE_URL + 'api/admin/accounts', {}, {
            'get': { method: 'GET', params: {}, isArray: false,
                interceptor: {
                    response: function(response) {
                        // expose response
                        return response;
                    }
                }
            }
        });
    });
