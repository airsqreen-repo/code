'use strict';

angular.module('airSqreenApp')
    .controller('UserController', function ($rootScope, $scope, $state, $translate, $timeout, api, Principal, KendoUtils, dialogService, AppUtilsService, DATA_STATUS ) {

        $scope.dataStatuses=DATA_STATUS;
        $scope.right="ADMIN";


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
                {template: KendoUtils.removeAllBtn($scope.right)},
                {template: "<button class='k-widget k-button' ui-sref='user.detail  ({mode: " + '"create"' + "})' has-right='" + $scope.right + "' translate='newBtn'></button>"},
                {template: KendoUtils.refreshBtn()}

            ]
        };

        $scope.search = function (item) {
            var searchConfig={
                filterHandler:function(filter) {
                    if(angular.isValue(item) )
                        return{ url: "search", query: {  email: item.email, firstname: item.firstname, lastname: item.lastname}};
                    return null;
                }
            };
            $scope.data = KendoUtils.createKendoDataSourceSearch(api.all('admin/users'), angular.extend(sourceConfig, searchConfig));
        };



        $scope.clearSearch = function (){
            $scope.userSearch = null;
            $scope.search();
        };


        $scope.search();

        $scope.gridOptions = {
            columns: [
                {field: "email",  title: "{{ 'security.user.label.email' | translate }}"},
                {field: "firstname",  title: "{{ 'security.user.label.name' | translate }}"},
                {field: "lastname",    title: "{{ 'security.user.label.surname' | translate }}"},
                {field: "dataStatus", title: "{{ 'status' | translate }}", template: "{{dataStatuses[dataItem.dataStatus] | translate}}", width: 100},
                {
                    command: [
                        { template: "<button class='k-widget k-button' ng-disabled='!selected' ui-sref='user.detail  ({id: selected.id, mode: " + '"view"' + "})' has-right='" + $scope.right + "' translate='viewBtn' kendo-tooltip k-content=\"'{{ viewTooltip | translate }}'\"></button>"},
                        { template: "<button class='k-widget k-button' ng-disabled='!selected' ui-sref='user.detail  ({id: selected.id, mode: " + '"edit"' + "})' has-right='" + $scope.right + "' kendo-tooltip k-content=\"'{{ editTooltip | translate }}'\"><i class='fa fa-user'></i></button>"},
                        { template: KendoUtils.deleteBtn($scope.right)},
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
        };

        $scope.removeDialog = function (dataItem) {
            dialogService.showDialog($translate.instant('deleteConfirmation'),
                sprintf($translate.instant('deleteMessage') , $translate.instant('security.user.singular'))).then(
                function () {
                    $scope.data.remove(dataItem);
                    $scope.save();
                },
                false);
        };

        $scope.removeAll = function(){
            var remove_items=[];
            _.forEach($scope.data.data(), function(values) {
                if(values.deleted==true){
                    remove_items.push(values);
                }
            });
            if(remove_items.length > 0){
                dialogService.showDialog($translate.instant('deleteConfirmation'),
                    sprintf($translate.instant('deleteMessage') , $translate.instant('security.user.singular'))).then(
                    function () {
                        _.forEach(remove_items, function(values) {
                            $scope.data.remove(values)
                        });
                        $scope.save();
                    },
                    false);
            }
        };

        $scope.onChange = function (data) {
            $scope.selected = data;
        };

        $scope.save = function () {
            $scope.data.sync().then(function () {
                $scope.$emit('notifySuccess', sprintf($translate.instant('saved') , $translate.instant('security.user.singular')));
                $scope.refresh();
            }, function(reason) {
                $scope.$emit('notifyError', sprintf($translate.instant('notSaved') , reason.message));
                $scope.refresh();
            });
        };

        $scope.refresh = function (){
            $scope.data.read();
        };

        $scope.rowTemplate = kendo.template($("#comboTemplate").html());

    });
