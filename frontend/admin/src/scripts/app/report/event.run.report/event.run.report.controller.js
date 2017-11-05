'use strict';

angular.module('airSqreenApp')
    .controller('EventRunReportController', function ($rootScope, $scope, $state, $translate, $timeout, _, api, KendoUtils, $filter, DateUtils, $http, SERVICE_URL  ) {
        $scope.data=[];
        $scope.series=[];
        $scope.categoryAxis = {
            labels: {
                rotation: -90
            }
        };

        $scope.search={};

        $scope.searchForm = function (item) {
            $scope.data=null;
            var from=DateUtils.convertLocaleDateKendoGrid($scope.search.from);
            var to=DateUtils.convertLocaleDateKendoGrid($scope.search.to);
            $http.get( SERVICE_URL + 'api/report/eventViewReportDay', {params: {
                campaignId:$scope.search.campaignId,
                from: from,
                to: to
            }}).then(function (response) {

                $scope.data = new kendo.data.DataSource({
                    data: response.data,
                    group: {
                        field: "campaignName"
                    },
                    sort: {
                        field: "date",
                        dir: "asc"
                    },
                    schema: {
                        model: {
                            fields: {
                                date: {
                                    type: "string"
                                }
                            }
                        }
                    }
                });
            });


            $http.get( SERVICE_URL + 'api/report/eventViewReportHour', {params: {
                campaignId:$scope.search.campaignId,
                from: from,
                to: to
            }}).then(function (response) {
                $scope.dataHour = new kendo.data.DataSource({
                    data: response.data,
                    group: {
                        field: "campaignName"
                    },
                    sort: {
                        field: "hour",
                        dir: "asc"
                    },
                    schema: {
                        model: {
                            fields: {
                                hour: {
                                    type: "string"
                                }
                            }
                        }
                    }
                });
            });

        };


        $scope.campaigns = KendoUtils.createKendoDataSourceSearch(api.all('admin/campaigns'), {
            serverFiltering: true,
            pageSize: 1000,
            sort: [
                { field: "id", dir: "desc" }
            ],
            filterHandler:function(filter) {
                var  autocomplete = $("#campaign").data("kendoAutoComplete");
                return { url: "search",
                    query: {
                        dataStatus:"ACTIVE",
                        size:100,
                        name: autocomplete.value()
                    }
                };
            }
        });

        $scope.campaignSelected = function (e) {
            var  autocomplete = $("#campaign").data("kendoAutoComplete");
            var item=autocomplete.dataSource.data()[e.item.index()];
            $scope.search.campaignId= item.id;
        };

        $scope.onSeriesHover = function(e) {
            // kendoConsole.log(kendo.format("event :: seriesHover ({0} : {1})", e.series.name, e.value));
        };

    });
