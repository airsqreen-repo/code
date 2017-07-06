'use strict';

angular.module('airSqreenApp')
    .factory('Password', function ($resource, SERVICE_URL) {
        return $resource( SERVICE_URL + 'api/account/change_password', {}, {
        });
    });

angular.module('airSqreenApp')
    .factory('PasswordResetInit', function ($resource, SERVICE_URL) {
        return $resource( SERVICE_URL + 'api/account/reset_password/init', {}, {
        })
    });

angular.module('airSqreenApp')
    .factory('PasswordResetFinish', function ($resource, SERVICE_URL) {
        return $resource( SERVICE_URL + 'api/account/reset_password/finish', {}, {
        })
    });
