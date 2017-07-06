/**
 * Created by idursun on 1/20/2015.
 */

    angular.module('airSqreenApp')
            .service('KendoUtils', function ($log, _, localStorageService) {


        return {
            objecToArray : function (items){
                var array=[];
                angular.forEach(items, function(value, key) {
                    this.push(value);
                }, array);
                return array;
            },
            deleteBtn : function(right){
                return '<button class="k-widget k-button"   ng-click="removeDialog(dataItem)" ng-disabled="!selected" has-right="' + right + '"   kendo-tooltip k-content="\'{{ deleteTooltip | translate }}\'" translate="destroyBtn"></button>';
            },
            editBtn : function(right){
                return '<button class="k-widget k-button"   ng-click="edit(selected)" ng-disabled="!selected" has-right="' + right + '"   kendo-tooltip k-content="\'{{ editTooltip | translate }}\'" translate="showPopupBtn"></button>';
            },
            viewBtn : function(right){
                return '<button class="k-widget k-button"   ng-click="view(dataItem)" ng-disabled="!selected" has-right="' + right + '"   kendo-tooltip k-content="\'{{ viewTooltip | translate }}\'" translate="viewBtn"></button>';
            },
            removeAllBtn : function(right){
                return '<button class="k-button pull-right" ng-click="removeAll()" has-right="' + right + '" translate="removeBtn"></button>';
             },
            createBtn : function(right){
                return '<button class="k-button k-button" ng-click="create()" has-right="' + right + '" translate="newBtn"></button>';
            },
            refreshBtn : function(){
                return '<button class="k-button k-button" ng-click="refresh()"  translate="refreshBtn"></button>';
            },
            checkbox : function(id, uid){
                return '<input ng-model="selectedItem['+ id +'].selected"  type="checkbox" />';
            },
            customMinLengthValidate : function(input){
                if(input[0].minLength){
                    if (input.val() != "" && input.val() && input.attr('minLength')) {
                        input.attr("data-minsize-msg", input.attr('minlengthMsg') + "  " + input.attr('minLength'));
                        return input.val().length  >= input.attr('minLength')  ? true : false ;
                    }
                }
                return true;
            },
            setGridPager: function(e){
                var pageSizeDropDownList = e.sender.wrapper.children(".k-grid-pager").find("select").data("kendoDropDownList");
                var pageSizes = [{ text:'6', value: 6 }, { text: "11", value: 11 }, { text: "26", value: 26 },{ text: "51", value: 51 },{ text: "101", value: 101 }];
                pageSizeDropDownList.setOptions({
                    valueTemplate:'<span class="k-state-default">#= ( data.text - 1 )  #</span>',
                    template: '<span class="k-state-default">#= ( data.text - 1 )  #</span>',
                    dataTextField: "text",
                    dataValueField: "value",
                });
                pageSizeDropDownList.setDataSource({ data: pageSizes });
                pageSizeDropDownList.bind("change", function(e) {
                   // this.setPerPage(e.sender.value());
                    localStorageService.set('perPage', e.sender.value());
                });
            },
            getPerPage: function(){
                var perPage = localStorageService.get('perPage');
                return _.isUndefined(perPage) ? 10 : perPage ;
            },
            setPerPage: function(perPage){
                localStorageService.set('perPage', perPage);
            },
            createKedoData: function (data) {
                return new kendo.data.DataSource({
                    data: data
                });
            },
            createDataSource : function (promise) {
                return new kendo.data.DataSource({
                    transport: {
                        read: function (e) {
                            promise.then(e.success)
                        }
                    }
                });
            },
            createKendoDataSource: function (api, config) {
                return new kendo.data.DataSource({
                    serverPaging: config.serverPaging || false,
                    serverFiltering: config.serverFiltering || false,
                    serverSorting: config.serverSorting || false,
                    pageSize: config.pageSize || 30,
                    pageable: {
                        refresh: true,
                        pageSizes: true,
                        buttonCount: 5
                    },
                    sort:config.sort,
                    transport: {
                        read: function(e) {
                            var queryParams = {
                                page: e.data.page,
                                size: e.data.pageSize
                            };

                            if (e.data.sort && e.data.sort.length > 0) {
                                queryParams.sort = e.data.sort[0].field + ',' + e.data.sort[0].dir;
                            }
                            var endPoint = api;

                            if (e.data.filter) {

                                var handler = config.filterHandler(e.data.filter);
                                if (handler) {
                                    endPoint = api.all(handler.url);
                                    queryParams = angular.extend(queryParams, handler.query);
                                }
                            }

                            endPoint.get(queryParams).then(function (result) {
                                if(result.page.totalElements > 0){
                                    e.success(result);
                                }else{
                                    result=[];
                                    result.page={_links:{},length: 0,
                                        page: {},
                                        number: 0,
                                        size: 20,
                                        totalElements: 0,
                                        totalPages: 0};
                                    e.success(result);
                                }
                            }, function(status) {
                                e.error(status);
                            });
                        },
                        create: function (e) {
                            api.post(e.data).then(e.success, e.error);
                        },
                        update: function (e) {
                            api.self(e.data).patch(e.data).then(e.success, e.error);

                        },
                        destroy: function (e) {
                            api.self(e.data).remove().then(e.success, e.error);
                        }
                    },
                    schema: {
                        total: "page.totalElements",
                        errors: "errors",
                        model: {
                            id: "id",
                            fields: config.fields
                        }
                    },
                    error: config.error || angular.noop,
                    sync: config.sync || angular.noop
                });
            },
            createKendoDataSourcePopup: function (api, config) {
                return new kendo.data.DataSource({
                    serverPaging: config.serverPaging || false,
                    serverFiltering: config.serverFiltering || false,
                    serverSorting: config.serverSorting || false,
                    pageSize: config.pageSize || 30,
                    sort:config.sort,
                    pageable: {
                        refresh: true,
                        pageSizes: true,
                        buttonCount: 5
                    },
                    transport: {
                        read: function(e) {
                            var queryParams = {
                                page: e.data.page,
                                size: e.data.pageSize
                            };

                            if (e.data.sort && e.data.sort.length > 0) {
                                queryParams.sort = e.data.sort[0].field + ',' + e.data.sort[0].dir;
                            }
                            var endPoint = api;

                            if (e.data.filter) {

                                var handler = config.filterHandler(e.data.filter);
                                if (handler) {
                                    endPoint = api.all(handler.url);
                                    queryParams = angular.extend(queryParams, handler.query);
                                }
                            }

                            endPoint.get(queryParams).then(function (result) {
                                e.success(result);
                            }, function(status) {
                                e.error(status);
                            });

                        },
                        create: function (e) {
                            api.popupPost(e.data).then(e.success, e.error);
                        },
                        update: function (e) {
                            api.self(e.data).patch(e.data).then(e.success, e.error);

                        },
                        destroy: function (e) {
                            api.self(e.data).remove().then(e.success, e.error);
                        }
                    },
                    schema: {
                        total: "page.totalElements",
                        errors: "errors",
                        model: {
                            id: "id",
                            fields: config.fields
                        }
                    },
                    error: config.error || angular.noop,
                    sync: config.sync || angular.noop
                });
            },
            createSubCollectionDataSource: function (config) {
                config = config || {};
                var readApi = config.readApi;
                return new kendo.data.DataSource({
                    serverPaging: config.serverPaging || false,
                    serverFiltering: config.serverFiltering || false,
                    serverSorting: config.serverSorting || false,
                    pageSize: config.pageSize || 30,
                    sort:config.sort,
                    transport: {
                        read: function(e) {
                            var queryParams = {
                                page: e.data.page,
                                size: e.data.pageSize
                            };
                            if (e.data.sort && e.data.sort.length > 0) {
                                queryParams.sort = e.data.sort[0].field + ',' + e.data.sort[0].dir;
                            }
                            readApi.get(_.assign(queryParams, config.readQuery)).then(function (result) {
                                if (!result.page) {
                                    result.page = {totalElements: result.length};
                                }
                                if(result.page.totalElements > 0){
                                    e.success(result);
                                }else{
                                    result=[];
                                    result.page={_links:{},length: 0,
                                        page: {},
                                        number: 0,
                                        size: 20,
                                        totalElements: 0,
                                        totalPages: 0};
                                    e.success(result);
                                }


                                // burda yazması lazım ekrana seçilen itemin objelerini

                            }, e.error);
                        },
                        create: function (e) {
                            //TODO(idursun): backreference url alinacak
                            config.writeApi.post(angular.extend(e.data, config.backReference)).then(e.success, e.error);
                        },
                        update: function (e) {
                            //TODO(idursun): backreference url alinacak
                            readApi.self(e.data).patch(angular.extend(e.data, config.backReference)).then(e.success, e.error);
                        },
                        destroy: function (e) {
                            //TODO(idursun): backreference url alinacak
                            readApi.self(e.data).remove().then(e.success, e.error);
                        }
                    },
                    schema: {
                        total: "page.totalElements",
                        errors: "errors",
                        model: {
                            id: "id",
                            fields: config.fields
                        }
                    }
                });
            },
            createKendoDataSourceSearch: function (api, config) {
                return new kendo.data.DataSource({
                    serverPaging: config.serverPaging || false,
                    serverFiltering: config.serverFiltering || false,
                    serverSorting: config.serverSorting || false,
                    pageSize: config.pageSize || 30,
                    sort:config.sort,
                    transport: {
                        read: function(e) {

                            var queryParams = {
                                page: e.data.page,
                                size: e.data.pageSize
                            };

                            if (e.data.sort && e.data.sort.length > 0) {
                                queryParams.sort = e.data.sort[0].field + ',' + e.data.sort[0].dir;
                            }
                            var endPoint = api;

                            var handler = config.filterHandler(e.data.filter);

                            if (handler) {
                                endPoint = api.all(handler.url);
                                queryParams = angular.extend(queryParams, handler.query);
                            }

                            endPoint.get(queryParams).then(function (result) {

                                if(result.page.totalElements > 0){
                                    e.success(result);
                                }else{
                                    result=[];
                                    result.page={_links:{},length: 0,
                                        page: {},
                                        number: 0,
                                        size: 20,
                                        totalElements: 0,
                                        totalPages: 0};
                                    e.success(result);
                                }
                            }, function(status) {
                               e.error(status);
                            });
                        },
                        create: function (e) {
                            api.post(e.data).then(e.success, e.error);
                        },
                        update: function (e) {
                            api.self(e.data).patch(e.data).then(e.success, e.error);
                        },
                        destroy: function (e) {
                            api.self(e.data).remove().then(e.success, e.error);
                        }
                    },
                    schema: {
                        total: "page.totalElements",
                        errors: "errors",
                        model: {
                            id: "id",
                            fields: config.fields
                        }
                    },
                    error: config.error || angular.noop,
                    sync: config.sync || angular.noop
                });
            },
            createKendoDataSourcePopupSearch: function (api, config) {
                return new kendo.data.DataSource({
                    serverPaging: config.serverPaging || false,
                    serverFiltering: config.serverFiltering || false,
                    serverSorting: config.serverSorting || false,
                    pageSize: config.pageSize || 30,
                    sort:config.sort,
                    transport: {
                        read: function(e) {

                            var queryParams = {
                                page: e.data.page,
                                size: e.data.pageSize
                            };

                            if (e.data.sort && e.data.sort.length > 0) {
                                queryParams.sort = e.data.sort[0].field + ',' + e.data.sort[0].dir;
                            }
                            var endPoint = api;

                            var handler = config.filterHandler(e.data.filter);

                            if (handler) {
                                endPoint = api.all(handler.url);
                                queryParams = angular.extend(queryParams, handler.query);
                            }

                            endPoint.get(queryParams).then(function (result) {

                                if(result.page.totalElements > 0){
                                    e.success(result);
                                }else{
                                    result=[];
                                    result.page={_links:{},length: 0,
                                        page: {},
                                        number: 0,
                                        size: 20,
                                        totalElements: 0,
                                        totalPages: 0};
                                    e.success(result);
                                }
                            }, function(status) {
                                e.error(status);
                            });
                        },
                        create: function (e) {
                            api.popupPost(e.data).then(e.success, e.error);
                        },
                        update: function (e) {
                            api.self(e.data).patch(e.data).then(e.success, e.error);
                        },
                        destroy: function (e) {
                            api.self(e.data).remove().then(e.success, e.error);
                        }
                    },
                    schema: {
                        total: "page.totalElements",
                        errors: "errors",
                        model: {
                            id: "id",
                            fields: config.fields
                        }
                    },
                    error: config.error || angular.noop,
                    sync: config.sync || angular.noop
                });
            },
            createKendoDataSourceSearch: function (api, config) {
                return new kendo.data.DataSource({
                    serverPaging: config.serverPaging || false,
                    serverFiltering: config.serverFiltering || false,
                    serverSorting: config.serverSorting || false,
                    pageSize: config.pageSize || 30,
                    sort:config.sort,
                    transport: {
                        read: function(e) {

                            var queryParams = {
                                page: e.data.page,
                                size: e.data.pageSize
                            };

                            if (e.data.sort && e.data.sort.length > 0) {
                                queryParams.sort = e.data.sort[0].field + ',' + e.data.sort[0].dir;
                            }
                            var endPoint = api;

                            var handler = config.filterHandler(e.data.filter);

                            if (handler) {
                                endPoint = api.all(handler.url);
                                queryParams = angular.extend(queryParams, handler.query);
                            }
                            
                            endPoint.get(queryParams).then(function (result) {

                                if(result.page.totalElements > 0){
                                    e.success(result);
                                }else{
                                    result=[];
                                    result.page={_links:{},length: 0,
                                        page: {},
                                        number: 0,
                                        size: 20,
                                        totalElements: 0,
                                        totalPages: 0};
                                    e.success(result);
                                }
                            }, function(status) {
                                e.error(status);
                            });
                        },
                        create: function (e) {
                            api.post(e.data).then(e.success, e.error);
                        },
                        update: function (e) {
                            api.self(e.data).patch(e.data).then(e.success, e.error);
                        },
                        destroy: function (e) {
                            api.self(e.data).remove().then(e.success, e.error);
                        }
                    },
                    schema: {
                        total: "page.totalElements",
                        errors: "errors",
                        model: {
                            id: "id",
                            fields: config.fields
                        }
                    },
                    error: config.error || angular.noop,
                    sync: config.sync || angular.noop
                });
            }
        }
    });
