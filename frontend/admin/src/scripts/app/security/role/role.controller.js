'use strict';

angular.module('airSqreenApp')
    .controller('RoleController', function ($rootScope, $scope, $state, $translate,  api, Principal, KendoUtils, dialogService ) {
        $scope.right={
            'LIST'  :'ADMIN',
            'VIEW'  :'ADMIN',
            'CREATE':'ADMIN',
            'UPDATE':'ADMIN',
            'SAVE'  :'ADMIN',
            'DELETE':'ADMIN',
            'SEARCH':'ADMIN'
        };

        $scope.isView=true;
        $scope.hasSearch =  Principal.isInRole($scope.right.SEARCH);
        var sourceConfig={
            serverPaging: true,
            serverSorting: true,
            serverFiltering: true,
            pageSize: KendoUtils.getPerPage(),
            filter: true,
            sort: [
                { field: "id", dir: "desc" }
            ]
        };

        $scope.toolbarOptions = {
            items: [
                {template: KendoUtils.removeAllBtn($scope.right.DELETE)},
                {template: "<button class='k-widget k-button' ui-sref='role.detail  ({mode: " + '"create"' + "})' has-right='" + $scope.right.CREATE + "' translate='newBtn'></button>"},
                {template: KendoUtils.refreshBtn()}
            ]
        };

        $scope.search = function (item) {
            $scope.data=null;
            var searchConfig={
                filterHandler:function(filter) {
                        return { url: "search/findAllRoles", query: { name: $scope.roleSearch.input , dataStatus: $scope.roleSearch.dataStatus } }
                     }
            };
            sourceConfig=angular.extend(sourceConfig, searchConfig);
            $scope.data = KendoUtils.createKendoDataSourceSearch(api.all('admin/roles'), sourceConfig);
        };

        $scope.clearSearch = function (){
            $scope.roleSearch = {};
            $scope.loadGrid();
        }

        $scope.loadGrid = function (){
            $scope.data = KendoUtils.createKendoDataSource(api.all('admin/roles'), sourceConfig );
        }

        $scope.loadGrid();

        $scope.lblName = $translate.instant('security.role.label.name');
        $scope.lblStatus = $translate.instant('status');
        $scope.lblSearchInput = $translate.instant('security.role.searchPlaceHolder');

        $scope.gridOptions = {
            columns: [
                {field: "name", title: "{{lblName}}"},
                {
                    command: [
                        { template: "<button class='k-widget k-button' ng-disabled='!selected' ui-sref='role.detail  ({id: selected.id, mode: " + '"view"' + "})' has-right='" + $scope.right.VIEW + "'   translate='viewBtn' kendo-tooltip k-content=\"'{{ viewTooltip | translate }}'\"></button>"},
                        { template: "<button class='k-widget k-button' ng-disabled='!selected' ui-sref='role.detail  ({id: selected.id, mode: " + '"edit"' + "})' has-right='" + $scope.right.UPDATE + "' translate='showBtn' kendo-tooltip k-content=\"'{{ editTooltip | translate }}'\"></button>"},
                        { template: KendoUtils.deleteBtn($scope.right.DELETE)},
                    ],
                    title: "&nbsp;",
                    width: "163px"
                }
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

        $scope.dataBinding = function (e){
            KendoUtils.setGridPager(e);
        }

        $scope.removeDialog = function (uid) {
            var data = $scope.data.getByUid(uid.uid);
            if (data) {
                dialogService.showDialog($translate.instant('deleteConfirmation'),
                    sprintf($translate.instant('deleteMessage'), $translate.instant('security.role.singular'))).then(
                    function () {
                        $scope.delete(data.id);
                        $scope.data.remove(data);
                    },
                    false);
            }
        };

        $scope.removeAll = function () {
            var remove_items = [];
            _.forEach($scope.data.data(), function (values) {
                if (values.deleted == true) {
                    remove_items.push(values);
                }
            });
            if (remove_items.length > 0) {
                dialogService.showDialog($translate.instant('deleteConfirmation'),
                    sprintf($translate.instant('deleteMessage'), $translate.instant('security.role.singular'))).then(
                    function () {
                        _.forEach(remove_items, function (value) {
                            $scope.delete(value.id);
                            $scope.data.remove(value);
                        });
                    },
                    false);
            }
        };



        $scope.onChange = function (data) {
            $scope.selected = data;
        };

        $scope.save = function () {
            $scope.data.sync().then(function () {
                $scope.$emit('notifySuccess', sprintf($translate.instant('saved') , $translate.instant('security.role.singular')));
                $scope.refresh();
            }, function(reason) {
                $scope.$emit('notifyError', sprintf($translate.instant('notSaved') , reason.message));
                $scope.refresh();
            });
        };

        $scope.refresh = function (){
            $scope.data.read();
        }

        $scope.delete = function (id) {
            api.one('admin/roles', id).remove().then(function () {
                $scope.$emit('notifySuccess', sprintf($translate.instant('saved'), $translate.instant('security.role.singular')));
                $scope.refresh();
            }, function (reason) {
                $scope.$emit('notifyError', sprintf($translate.instant('notSaved'), reason.message));
                $scope.refresh();
            });
        };
        $scope.rowTemplate = kendo.template($("#comboTemplate").html());


    });
