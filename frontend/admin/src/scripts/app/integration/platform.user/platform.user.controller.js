/**
 * Created by ferhatyaban on 8/30/17.
 */
'use strict';

angular.module('airSqreenApp')
    .controller('PlatformUserController', function ($rootScope, $scope, $state, $filter, $translate, api, Principal, KendoUtils, dialogService, AppUtilsService, PLATFORM_TYPE, SERVICE_TYPE  ) {
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

        $scope.platformTypes= AppUtilsService.getPlatformTypes();
        $scope.serviceTypes= AppUtilsService.getServiceTypes();

        $scope.platformType= PLATFORM_TYPE;
        $scope.serviceType= SERVICE_TYPE;




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
                {template: KendoUtils.createBtn($scope.right.CREATE)},
                {template: KendoUtils.refreshBtn()}
            ]
        };

        $scope.search = function (item) {
            $scope.data=null;
            var searchConfig={
                filterHandler:function(filter) {
                    return { url: "search/findByNameAndStatus", query: { name: $scope.PlatformUserSearch.input, dataStatus: $scope.PlatformUserSearch.dataStatus}}
                }
            };
            sourceConfig=angular.extend(sourceConfig, searchConfig);
            $scope.data = KendoUtils.createKendoDataSourceSearch(api.all('admin/platformUsers'), sourceConfig);
        };

        $scope.clearSearch = function (){
            $scope.PlatformUserSearch = {};
            $scope.PlatformUserSearch.dataStatus="ACTIVE";
            $scope.loadGrid();
        }

        $scope.loadGrid = function (){
            $scope.data = KendoUtils.createKendoDataSource(api.all('admin/platformUsers'), sourceConfig );
        }

        $scope.loadGrid();


        $scope.gridOptions = {
            columns: [
                {field: "username", title: "{{ 'platformUser.label.username' | translate }}"},
                {field: "platformType", title: "{{ 'platformUser.label.platformType' |translate }}", template: "{{platformType[dataItem.platformType] | translate}}", width: 100 },
                {field: "serviceType", title: "{{ 'platformUser.label.serviceType' |translate }}", template: "{{serviceType[dataItem.serviceType] | translate}}", width: 100 },
                {field: "dataStatus", title: "{{ 'status' |translate }}", template: "{{dataStatuses[dataItem.dataStatus] }}", width: 100 },
                {
                    command: [
                        { template: KendoUtils.viewBtn($scope.right.VIEW)},
                        { template: KendoUtils.editBtn($scope.right.UPDATE)},
                        { template: KendoUtils.deleteBtn($scope.right.DELETE)},
                    ],
                    title: "&nbsp;",
                    width: "163px"
                }
            ],
            selectable: "row",
            sortable: "true",
            editable: "inline",
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

        $scope.formConfig = function (){
            $scope.dataStatus.enable(!$scope.isView);
        }

        $scope.view = function (item) {
            $scope.validator.hideMessages();
            $scope.isView=true;
            $scope.formConfig();
            $scope.platformUser = angular.copy(item);
            $scope.dialog.show();
        };

        $scope.create = function () {
            $scope.validator.hideMessages();
            $scope.isView=false;
            $scope.formConfig();
            $scope.platformUser = {};
            $scope.platformUser.dataStatus='ACTIVE';
            $scope.platformUser.isCreate=true;
            $scope.platformUser.isUpdate=false;
            $scope.dialog.show();
        };

        $scope.edit = function (item) {
            $scope.validator.hideMessages();
            $scope.isView=false;
            $scope.formConfig();
            $scope.platformUser = angular.copy(item);
            $scope.platformUser.isUpdate=true;
            $scope.platformUser.isCreate=false;
            $scope.dialog.show();
        };
        var goCreated = function (e) {
            $scope.$emit('notifySuccess', sprintf($translate.instant('saved'), $translate.instant('platformUser.singular')));
            $scope.refresh();
        };

        $scope.update = function (event) {
            event.preventDefault();
            if ($scope.validator.validate()) {
                $scope.platformUser.dirty = true;
                if (_.isNumber($scope.platformUser.id)) {
                    var toObject = Object.assign({}, $scope.platformUser);
                    api.one('admin','platformUsers').patch(toObject).then(success, error);
                } else {
                    var toObject = Object.assign({}, $scope.platformUser);
                    api.one('admin','platformUsers').post(toObject).then(goCreated, error);
                }
                $scope.dialog.hide();
            } else {
                $scope.validationClass = "invalid";
            }
        };

        $scope.$watch('platformUser.shortName', function (val) {
            if(!_.isUndefined(val))
                $scope.platformUser.shortName = $filter('uppercase')(val);
        }, true);


        var goList = function (e) {
            $state.go('platformUserManagement');
        };

        $scope.removeDialog = function (uid) {
            var data = $scope.data.getByUid(uid.uid);
            if (data) {
                dialogService.showDialog($translate.instant('deleteConfirmation'),
                    sprintf($translate.instant('deleteMessage'), $translate.instant('platformUser.singular'))).then(
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
                    sprintf($translate.instant('deleteMessage'), $translate.instant('platformUser.singular'))).then(
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
                $scope.$emit('notifySuccess', sprintf($translate.instant('saved') , $translate.instant('platformUser.singular')));
                $scope.refresh();
            }, function(reason) {
                // delete entry
                $scope.$emit('notifyError', sprintf($translate.instant('notSaved') , reason.message));
                $scope.refresh();
            });

        };


        $scope.refresh = function () {
            $scope.data.read();
        };

        var success = function (data) {
            $scope.platformUser = data;

            $scope.refresh();
            // tekrar cagir

            $scope.$emit('notifySuccess', sprintf($translate.instant('saved'), $translate.instant('platformUser.singular')));
        };

        var error = function (data) {
            $scope.$emit('notifyError', sprintf($translate.instant('notSaved'), data.message));
        };

        $scope.delete = function (id) {
            api.one('admin/platformUsers', id).remove().then(function () {
                $scope.$emit('notifySuccess', sprintf($translate.instant('saved'), $translate.instant('platformUser.singular')));
                $scope.refresh();
            }, function (reason) {
                $scope.$emit('notifyError', sprintf($translate.instant('notSaved'), reason.message));
                $scope.refresh();
            });
        };
    });