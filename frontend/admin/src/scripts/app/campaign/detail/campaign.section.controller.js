'use strict';

angular.module('airSqreenApp')
    .controller('CampaignSectionController', function ($rootScope, $scope, $state, $stateParams, $translate, $timeout, api, Principal, KendoUtils, dialogService, AppUtilsService) {

        $scope.isView = true;
        var campaignId=$stateParams.id;
        $scope.load = function (){
            if($stateParams.mode=="edit"){
                $scope.isView = false;
                $scope.isCreate=true;
            }else{
                $scope.isView = false;
                $scope.isView = true;
            }
        };

        $scope.load();

        $scope.right={
            'LIST'  :'ADMIN',
            'VIEW'  :'ADMIN',
            'CREATE':'ADMIN',
            'UPDATE':'ADMIN',
            'SAVE'  :'ADMIN',
            'DELETE':'ADMIN',
            'SEARCH':'ADMIN'
        };


        // Role role  Data Source
        var self = this;
        this.dataSourceName = 'campaignSections';
        $scope.dataSource = $scope.dataSources[self.dataSourceName];

        $scope.gridOptions = {
            columns: [
                {field: "id", title: "{{ 'global.field.id' | translate }}"},
                {field: "section.name", title: "{{ 'campaign.section.label.section' | translate }}"},
                {field: "device.name", title: "{{ 'campaign.section.label.device' | translate }}", template: function (e) {if(e.device!=null)return e.device.name;else return '-'; }},
                {field: "actionId", title: "{{ 'campaign.section.label.actionId' | translate }}"},
                {field: "key", title: "{{ 'campaign.section.label.key' | translate }}"},
                {field: "sspPrice", title: "{{ 'campaign.section.label.sspPrice' | translate }}"},
                {field: "detail", title: "{{ 'campaign.section.label.detail' | translate }}"},
                {
                    command: [
                        { template: KendoUtils.editBtn($scope.right.UPDATE)},
                        { template: KendoUtils.deleteBtn($scope.right.UPDATE)},
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

        $scope.toolbarOptions = {
            items: [
                {template: KendoUtils.removeAllBtn($scope.right.DELETE)},
                {template: KendoUtils.createBtn($scope.right.CREATE)},
                {template: KendoUtils.refreshBtn()}
            ]
        };

        $scope.formConfig = function (){
            $scope.dataStatus.enable(!$scope.isView);
        };

        $scope.view = function (item) {
            $scope.validator.hideMessages();
            $scope.isView=true;
            $scope.campaignSection = angular.copy(item);
            $scope.dialog.show();
            $scope.formConfig();
        };

        $scope.create = function () {
            $scope.validator.hideMessages();
            $scope.isView=false;

            $scope.campaignSection = {};
            $scope.campaignSection.dataStatus='ACTIVE';
            $scope.campaignSection.isCreate=true;
            $scope.campaignSection.isUpdate=false;
            $scope.dialog.show();
            $scope.formConfig();
        };

        $scope.edit = function (item) {
            $scope.validator.hideMessages();
            $scope.isView=false;

            $scope.campaignSection = angular.copy(item);
            $scope.campaignSection.isUpdate=true;
            $scope.campaignSection.isCreate=false;
            $scope.setSection();
            $scope.setDevice();

            $scope.dialog.show();
            $scope.formConfig();
        };
        var goCreated = function (e) {
            $scope.$emit('notifySuccess', sprintf($translate.instant('saved'), $translate.instant('campaign.campaignSection.singular')));
            $scope.refresh();
        };

        $scope.update = function (event) {
            event.preventDefault();
            if ($scope.validator.validate()) {
                $scope.campaignSection.dirty = true;
                $scope.campaignSection.campaignId=campaignId;


                    $scope.campaignSection.device= $scope.campaignSection.device[0];

                    $scope.campaignSection.section= $scope.campaignSection.section[0];

                if (_.isNumber($scope.campaignSection.id)) {
                    var toObject = Object.assign({}, $scope.campaignSection);
                    api.one('admin','campaignSections').patch(toObject).then(success, error);
                } else {
                    var toObject = Object.assign({}, $scope.campaignSection);
                    api.one('admin','campaignSections').post(toObject).then(goCreated, error);
                }
                $scope.dialog.hide();
            } else {
                $scope.validationClass = "invalid";
            }
        };

        $scope.$watch('campaignSection.shortName', function (val) {
            if(!_.isUndefined(val))
                $scope.campaignSection.shortName = $filter('uppercase')(val);
        }, true);


        var goList = function (e) {
            $state.go('campaignSectionManagement');
        };

        $scope.removeDialog = function (uid) {
            var data = $scope.dataSource.getByUid(uid.uid);
            if (data) {
                dialogService.showDialog($translate.instant('deleteConfirmation'),
                    sprintf($translate.instant('deleteMessage'), $translate.instant('campaign.campaignSection.singular'))).then(
                    function () {
                        $scope.delete(data.id);
                        $scope.dataSource.remove(data);
                    },
                    false);
            }
        };

        $scope.removeAll = function () {
            var remove_items = [];
            _.forEach($scope.dataSource.data(), function (values) {
                if (values.deleted == true) {
                    remove_items.push(values);
                }
            });
            if (remove_items.length > 0) {
                dialogService.showDialog($translate.instant('deleteConfirmation'),
                    sprintf($translate.instant('deleteMessage'), $translate.instant('campaign.campaignSection.singular'))).then(
                    function () {
                        _.forEach(remove_items, function (value) {
                            $scope.delete(value.id);
                            $scope.dataSource.remove(value);
                        });
                    },
                    false);
            }
        };

        $scope.onChange = function (data) {
            $scope.selected = data;
        };

        $scope.save = function () {
            $scope.dataSource.sync().then(function () {
                $scope.$emit('notifySuccess', sprintf($translate.instant('saved') , $translate.instant('campaign.campaignSection.singular')));
                $scope.refresh();
            }, function(reason) {
                // delete entry
                $scope.$emit('notifyError', sprintf($translate.instant('notSaved') , reason.message));
                $scope.refresh();
            });

        };


        $scope.refresh = function () {
            $scope.dataSource.read();
        };

        var success = function (data) {
            $scope.campaignSection = data;

            $scope.refresh();
            // tekrar cagir

            $scope.$emit('notifySuccess', sprintf($translate.instant('saved'), $translate.instant('campaign.campaignSection.singular')));
        };

        var error = function (data) {
            $scope.$emit('notifyError', sprintf($translate.instant('notSaved'), data.message));
        };

        $scope.delete = function (id) {
            api.one('admin/campaignSections', id).remove().then(function () {
                $scope.$emit('notifySuccess', sprintf($translate.instant('saved'), $translate.instant('campaign.campaignSection.singular')));
                $scope.refresh();
            }, function (reason) {
                $scope.$emit('notifyError', sprintf($translate.instant('notSaved'), reason.message));
                $scope.refresh();
            });
        };


        $scope.sections = KendoUtils.createKendoDataSourceSearch(api.all('admin/sections'), {
            serverFiltering: true,
            pageSize: 1000,
            sort: [
                { field: "id", dir: "desc" }
            ],
            filterHandler:function(filter) {
                var  autocomplete = $("#section").data("kendoAutoComplete");
                return { url: "search",
                    query: {
                        dataStatus:"ACTIVE",
                        size:100,
                        name: autocomplete.value()
                    }
                };

            }
        });



        $scope.setSection = function(){

            var  autocomplete = $("#section").data("kendoAutoComplete");
            if($scope.campaignSection.section!=null) {
                autocomplete.value($scope.campaignSection.section.name);
                autocomplete.search(0);
                $timeout(function () {
                    autocomplete.select(autocomplete.ul.children().eq(0));
                    autocomplete.close();
                }, 1000);
            }

        };


        $scope.devices = KendoUtils.createKendoDataSourceSearch(api.all('admin/devices'), {
            serverFiltering: true,
            pageSize: 1000,
            sort: [
                { field: "id", dir: "desc" }
            ],
            filterHandler:function(filter) {
                var  autocomplete = $("#device").data("kendoAutoComplete");
                return { url: "search",
                    query: {
                        dataStatus:"ACTIVE",
                        size:100,
                        name: autocomplete.value()
                    }
                };

            }
        });


        $scope.setDevice = function(){
            var  autocomplete = $("#device").data("kendoAutoComplete");
            if($scope.campaignSection.device!=null){
                autocomplete.value($scope.campaignSection.device.name);
                autocomplete.search(0);
                $timeout(function(){
                    autocomplete.select(autocomplete.ul.children().eq(0));
                    autocomplete.close();
                },1000);
            }
        };


        $scope.sectionSelected = function (e) {
            var  autocomplete = $("#section").data("kendoAutoComplete");
            var item=autocomplete.dataSource.data()[e.item.index()];
            $scope.campaignSection.sectionId= item.id;
        }

        $scope.deviceSelected = function (e) {
            var  autocomplete = $("#device").data("kendoAutoComplete");
            var item=autocomplete.dataSource.data()[e.item.index()];
            $scope.campaignSection.deviceId= item.id;
        }

    });
