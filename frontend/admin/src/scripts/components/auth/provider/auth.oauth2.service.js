'use strict';

angular.module('airSqreenApp')
    .factory('AuthServerProvider', function loginService($http, localStorageService, Base64, SERVICE_URL) {
        return {
            login: function(credentials) {

                var data = "username=" + credentials.username + "&password="
                    + credentials.password + "&grant_type=password&scope=read write&" +
                    "client_secret=airsqreen&client_id=airsqreen";
                return $http.post( SERVICE_URL + 'oauth/token' , data, {
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded",
                        "Accept": "application/json",
                        "Authorization": "Basic " + Base64.encode("airsqreen" + ':' + "airsqreen")
                    }
                }).success(function (response) {
                    var expiredAt = new Date();
                    expiredAt.setSeconds(expiredAt.getSeconds() + response.expires_in);
                    response.expires_at = expiredAt.getTime();
                    localStorageService.set('token', response);
                    return response;
                });

            },
            logout: function() {
                // logout from the server
                $http.post( SERVICE_URL + 'api/logout').then(function() {
                    localStorageService.clearAll();
                });

            },
            getToken: function () {
                return localStorageService.get('token');
            },
            hasValidToken: function () {
                var token = this.getToken();
                return token && token.expires_at && token.expires_at > new Date().getTime();
            }
        };
    });
