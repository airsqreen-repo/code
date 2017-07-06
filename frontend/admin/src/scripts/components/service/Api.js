/**
 * Created by idursun on 2/1/2015.
 */

    angular.module('airSqreenApp').provider('api', function ($httpProvider) {
        var baseUrl='';

        //convert properties with link to reference uris
        $httpProvider.interceptors.push(function () {
            return {
                'request': function (config) {

                    if (config.url.indexOf(baseUrl) == -1 || config.data == undefined )
                        return config;

                    var data = config.data;
                    _.keys(data).forEach(function (key) {
                        var property = data[key];
                        if (angular.isObject(property)){
                            if ('_links' in property) {
                                data[key] = property._links.self.href;
                            }
                        }
                    });

                    //if(_.findKey(data, '_embedded')){
                    if ('_embedded' in data) {
                        _.keys(data._embedded).filter(function (key) {
                            return _.isObject(data[key])
                        }).forEach(function (key) {
                            delete data[key];
                        })
                    }

                    if ('_links' in data) {
                        _.keys(data._links).filter(function (key) {
                            return _.isObject(data[key])
                        }).forEach(function (key) {
                            delete data[key];
                        })
                    }
                    //}

                    delete data._embedded;
                    delete data._links;
                    delete data.page;
                    return config;
                },
                'response' : function(response) {
                    if (response.config.url.indexOf(baseUrl) == -1)
                        return response;

                    if ( (response.data == undefined || _.isEmpty(response.data)) && !response.config.single) {
                        response.data = [];
                    }

                    var data = response.data || {};

                    if ('_embedded' in data) {

                        if(!_.isUndefined(response.config.popupPost)){
                            return  data;
                        }

                        // birden fazla _emmedded lar icin

                       // if(_.size(data._embedded) > 2 && !response.config.single){
                        if(response.config.collection=='attributes'){
                            var result=[];

                            _.forEach(data._embedded, function(values, key) {
                                _.forEach(values, function(value, key) {
                                    result.push(value);
                                });
                            });

                            result.page = data.page;
                            result._links = data._links;
                            response.data = result;

                            return response;
                        }

                        if (response.config.single) {
                            _.keys(data._embedded).forEach(function (key) {
                                data[key] = data._embedded[key];
                            })
                        } else {
                            var result = data._embedded[Object.keys(data._embedded)[0]];
                            result.page = data.page;
                            result._links = data._links;
                            response.data = result;
                        }

                    }
                    return response;
                }
            }
        });

        //$httpProvider.defaults.headers.post = {
        //
        //}

        this.setBaseUrl = function (url) {
            baseUrl = url;
        };

        this.$get = function ($http, $q) {

            function create(url, extra) {
                var baseConfig = {
                    url: url,
                    headers: {
                        'Content-Type': 'application/json'
                    }
                };
                angular.extend(baseConfig, extra);
                return {
                    config: function() {
                        return baseConfig;
                    },
                    getAttribute: function (query) {
                        var deferred = $q.defer();
                        baseConfig.getAttribute=true;
                        $http(angular.extend({method: 'GET', params: query}, baseConfig))
                            .success(deferred.resolve)
                            .error(deferred.reject);
                        return deferred.promise;
                    },
                    get: function (query) {
                        var deferred = $q.defer();
                        $http(angular.extend({method: 'GET', params: query}, baseConfig))
                            .success(deferred.resolve)
                            .error(deferred.reject);
                        return deferred.promise;
                    },
                    post: function (data) {
                        var deferred = $q.defer();
                        $http(angular.extend({method: 'POST', data: data }, baseConfig))
                            .success(deferred.resolve)
                            .error(deferred.reject);

                        return deferred.promise;
                    },
                    popupPost: function (data) {
                        baseConfig.popupPost=true;
                        var deferred = $q.defer();
                        $http(angular.extend({method: 'POST', data: data }, baseConfig))
                            .success(deferred.resolve)
                            .error(deferred.reject);
                        return deferred.promise;
                    },
                    put: function (data) {
                        var deferred = $q.defer();
                        $http(angular.extend({method: 'PUT', data: data}, baseConfig))
                            .success(deferred.resolve)
                            .error(deferred.reject);
                        return deferred.promise;
                    },
                    patch: function (data) {
                        var deferred = $q.defer();
                        $http(angular.extend({method: 'PATCH', data: data}, baseConfig))
                            .success(deferred.resolve)
                            .error(deferred.reject);
                        return deferred.promise;
                    },
                    remove: function () {
                        var deferred = $q.defer();
                        $http(angular.extend({method: 'DELETE'}, baseConfig))
                            .success(deferred.resolve)
                            .error(deferred.reject);
                        return deferred.promise;
                    },
                    all: function(subUrl) {
                        var newUrl = url + subUrl;
                        if (url[url.length-1] != '/')
                            newUrl = url + '/' + subUrl;

                        return create(newUrl, {collection: subUrl});
                    },
                    one: function(subUrl, id) {
                        return create(url + subUrl + '/' + id, {single: true});
                    },
                    self: function(obj) {
                        return create(obj._links.self.href, {single: true});
                    }
                }
            }

            return create(baseUrl);
        };

        return this;
    });
