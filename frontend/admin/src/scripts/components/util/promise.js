'use strict';

angular.module('airSqreenApp')
    .factory('promiseFactory', function($q) {
    return {
        decorate: function(promise) {
            promise.success = function(callback) {
                promise.then(callback);

                return promise;
            };

            promise.error = function(callback) {
                promise.then(null, callback);

                return promise;
            };
        },
        defer: function() {
            var deferred = $q.defer();

            this.decorate(deferred.promise);

            return deferred;
        }
    };
})
