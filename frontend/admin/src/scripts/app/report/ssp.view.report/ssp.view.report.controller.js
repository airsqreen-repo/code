'use strict';

angular.module('airSqreenApp')
    .controller('SspViewReportController', function ($rootScope, $scope, $state, $translate, $timeout, _, api, KendoUtils, $filter, DateUtils , $http, SERVICE_URL  ) {

        $scope.data=[];
        $scope.series=[];
        $scope.categoryAxis = {
            labels: {
                rotation: -90
            }
        };
        $http.get( SERVICE_URL + 'api/report/sspViewCountLog/last30day').then(function (response) {

            $scope.dataChart = new kendo.data.DataSource({
                data: response.data,
                group: {
                    field: "deviceName"
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


        $scope.search={};
        
        var sourceConfig={
            serverPaging: true,
            serverSorting: true,
            serverFiltering: true,
            pageSize: KendoUtils.getPerPage(),
            filter: true,
            sort: [
                { width: 150, field: "date", dir: "desc" }
            ]
        };

        $scope.searchForm = function (item) {
            $scope.data=null;
            var searchConfig={
                filterHandler:function(filter) {
                    var from=DateUtils.convertLocaleDateKendoGrid($scope.search.from);
                    var to=DateUtils.convertLocaleDateKendoGrid($scope.search.to);
                    return { url: "",
                        query: {
                            deviceId:$scope.search.deviceId,
                            from: from,
                            to: to
                    }
                    }

                }
            };
            sourceConfig=angular.extend(sourceConfig, searchConfig);
            $scope.data = KendoUtils.createKendoDataSourceSearch(api.all('report/sspViewCountLogs'), sourceConfig);
        };

        $scope.gridOptions = {
            excel: {
                fileName: "Report-"+$filter('date')(new Date(), 'yyyy-MM-dd HH')+".xlsx"
            },
            columns: [
                { field: "date", title: $translate.instant('sspViewReport.label.date')},
                { field: "deviceName", title: $translate.instant('sspViewReport.label.screenName')},
                { field: "sspPrice", title: $translate.instant('sspViewReport.label.cpm')},
            ],
            selectable: "row",
            sortable: "true",
            scrollable: true,
            pageable: {
                refresh: true,
                pageSizes: [5,10,25,50,100],
                buttonCount: 5
            }
        };


        $scope.saveExcel = function () {
            $scope.grid.saveAsExcel();
        };

        /*
         $("#grid thead [data-field=CustomerNumber] .k-link").html("NewTitle")
         * */
        $scope.dataBinding = function (e){
            KendoUtils.setGridPager(e);
        };

        $scope.onChange = function (data) {
            $scope.selected = data;
        };


        $scope.refresh = function (){
            $scope.data.read();
        };


        $scope.devices = KendoUtils.createKendoDataSourceSearch(api.all('admin/devices'), {
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

        $scope.deviceSelected = function (e) {
            var  autocomplete = $("#device").data("kendoAutoComplete");
            var item=autocomplete.dataSource.data()[e.item.index()];
            $scope.search.deviceId= item.id;
        };




    });
