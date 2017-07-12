'use strict';

angular.module('airSqreenApp')
    .controller('ReportController', function ($rootScope, $scope, $state, $translate, $timeout, _, api, KendoUtils, $filter, DateUtils  ) {
        $scope.onSeriesHover = function(e) {
           // kendoConsole.log(kendo.format("event :: seriesHover ({0} : {1})", e.series.name, e.value));
        };

        $scope.electricity = new kendo.data.DataSource({
            transport: {
                read: {
                    url: "scripts/app/report/fake.json",
                    dataType: "json"
                }
            },
            sort: {
                field: "year",
                dir: "asc"
            }
        });

        $scope.categoryAxis = {
            field: "year",
                labels: {
                rotation: -90
            },
            crosshair: {
                visible: true
            }
        };


        $scope.search={};
        
        var sourceConfig={
            serverPaging: true,
            serverSorting: true,
            serverFiltering: true,
            pageSize: KendoUtils.getPerPage(),
            filter: true,
            sort: [
                { width: 150, field: "id", dir: "desc" }
            ]
        };

        $scope.searchForm = function (item) {
            $scope.data=null;
            var searchConfig={
                filterHandler:function(filter) {
                    var from=DateUtils.convertLocaleDateKendoGrid($scope.search.from);
                    var to=DateUtils.convertLocaleDateKendoGrid($scope.search.to);
                    return { url: "search",
                        query: {
                            msisdn:$scope.search.reportType,
                            name: $scope.search.show,
                            from: from,
                            to: to }
                    }

                }
            };
            sourceConfig=angular.extend(sourceConfig, searchConfig);
            $scope.data = KendoUtils.createKendoDataSourceSearch(api.all('v1/vcandidates'), sourceConfig);
        };

        $scope.clearSearch = function (){
            $scope.loadGrid();
        };

        $scope.loadGrid = function (){
            $scope.search = {};
            $scope.data = KendoUtils.createKendoDataSource(api.all('v1/vcandidates'), sourceConfig );
        };


        $scope.loadGrid();


        $scope.gridOptions = {
            excel: {
                fileName: "Report-"+$filter('date')(new Date(), 'yyyy-MM-dd HH')+".xlsx"
            },
            columns: [
                {width: 150, field: "date", title: $translate.instant('report.label.date')},
                {width: 150, field: "screenName", title: $translate.instant('report.label.screenName')},
                {width: 150, field: "cpm", title: $translate.instant('report.label.cpm')},
                {width: 150, field: "impressions", title: $translate.instant('report.label.impressions')},
                {width: 150, field: "revenue", title: $translate.instant('report.label.revenue')},
            ],
            selectable: "row",
            sortable: "true",
            scrollable: true,
            pageable: {
                refresh: true,
                pageSizes: [5,10,25,50,100],
                buttonCount: 5
            },
            excelExport: function(e) {
                var sheet = e.workbook.sheets[0];
                // color
                for (var rowIndex = 0; rowIndex < 1; rowIndex++) {
                    var row = sheet.rows[rowIndex];
                    for (var cellIndex = 33; cellIndex < row.cells.length; cellIndex++) {
                        var cell = row.cells[cellIndex];
                        /*replace field name*/
                        //stat01Value
                        //stat01Type
                        var field = e.sender.columns[cellIndex].field;
                        var type=field.replace('Value', 'Type');

                        var data=$scope.data.at(0);

                        if(data[type]=="APP_STATS") {
                            row.cells[cellIndex].background = "#0000FF";
                        }else if(data[type]=="TEMPO_STATS") {
                            row.cells[cellIndex].background = "#FFA500";
                        }else if(data[type]=="CARGO_STATS") {
                            row.cells[cellIndex].background = "#008000";
                        }else {}
                    }

                }

                //date
                for (var i = 1; i < sheet.rows.length; i++) {
                    var row = sheet.rows[i];
                    var rId=(i-1);

                    data=e.sender._data[rId];
                    for (var cellIndex = 33; cellIndex < row.cells.length; cellIndex++) {
                        var field = e.sender.columns[cellIndex].field;
                        var date=field.replace('Value', 'Date');
                        var dateValue=data[date]
                        var value=data[field];
                        row.cells[cellIndex].value=dateValue!=null?(value+' / '+ dateValue):value;
                    }


                    row.cells[4].value = data["pbm"]+'/'+data["pbmDate"]+'/'+data["pbmIpAddr"];
                }

            },
            dataBound:function(arg) {
                for(var i=33;i<$scope.grid.columns.length;i++){
                    var exp=$scope.grid.columns[i].field.replace("Value", "Title2");
                    var data=$scope.data.at(0);
                    $scope.grid.columns[i].title= data[exp];
                    $("#grid th[data-field="+$scope.gridOptions.columns[i].field+"]").html(data[exp]);
                }
            }
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



        $scope.saveExcel = function () {
            $scope.grid.saveAsExcel();
        };
    });
