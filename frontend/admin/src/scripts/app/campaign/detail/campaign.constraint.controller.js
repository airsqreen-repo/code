'use strict';

angular.module('airSqreenApp')
    .controller('CampaignConstraintController', function ($rootScope, $scope, $state, $stateParams, $translate, $timeout, api, Principal, KendoUtils, dialogService, AppUtilsService, CAMPAIGN_CONSTRAINT_FILTER, CAMPAIGN_CONSTRAINT_TYPE) {

        $scope.isView = true;
        var deviceId=$stateParams.id;
        $scope.load = function (){
            if($stateParams.mode=="edit"){
                $scope.isView = false;
                $scope.isCreate=true;
            }else{
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

        $scope.campaignConstraintType = CAMPAIGN_CONSTRAINT_TYPE;
        $scope.campaignConstraintFilter = CAMPAIGN_CONSTRAINT_FILTER;

        // Role role  Data Source
        var self = this;
        this.dataSourceName = 'campaignConstraints';
        $scope.dataSource = $scope.dataSources[self.dataSourceName];

        $scope.gridOptions = {
            columns: [
                {field: "campaignConstraintType", title: "{{ 'campaign.constraint.label.campaignConstraintType' | translate }}", template: "{{campaignConstraintType[dataItem.campaignConstraintType] | translate}}" },
                {field: "campaignConstraintFilter", title: "{{ 'campaign.constraint.label.campaignConstraintFilter' | translate }}", template: "{{campaignConstraintFilter[dataItem.campaignConstraintFilter] | translate}}" },
                {field: "filter_detail", title: "{{ 'campaign.constraint.label.filter_detail' | translate }}"}
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
            $scope.deviceConstraint = angular.copy(item);
            $scope.dialog.show();
            $scope.formConfig();
        };

        $scope.create = function () {
            $scope.validator.hideMessages();
            $scope.isView=false;

            $scope.deviceConstraint = {};
            $scope.deviceConstraint.dataStatus='ACTIVE';
            $scope.deviceConstraint.isCreate=true;
            $scope.deviceConstraint.isUpdate=false;
            $scope.dialog.show();
            $scope.formConfig();
        };

        $scope.edit = function (item) {
            $scope.validator.hideMessages();
            $scope.isView=false;

            $scope.deviceConstraint = angular.copy(item);
            $scope.deviceConstraint.isUpdate=true;
            $scope.deviceConstraint.isCreate=false;
            $scope.dialog.show();
            $scope.formConfig();
        };
        var goCreated = function (e) {
            $scope.$emit('notifySuccess', sprintf($translate.instant('saved'), $translate.instant('deviceConstraint.singular')));
            $scope.refresh();
        };

        $scope.update = function (event) {
            event.preventDefault();
            if ($scope.validator.validate()) {
                $scope.deviceConstraint.dirty = true;
                $scope.deviceConstraint.deviceId=deviceId;
                if (_.isNumber($scope.deviceConstraint.id)) {
                    var toObject = Object.assign({}, $scope.deviceConstraint);
                    api.one('admin','deviceConstraints').patch(toObject).then(success, error);
                } else {
                    var toObject = Object.assign({}, $scope.deviceConstraint);
                    api.one('admin','deviceConstraints').post(toObject).then(goCreated, error);
                }
                $scope.dialog.hide();
            } else {
                $scope.validationClass = "invalid";
            }
        };

        $scope.$watch('deviceConstraint.shortName', function (val) {
            if(!_.isUndefined(val))
                $scope.deviceConstraint.shortName = $filter('uppercase')(val);
        }, true);


        var goList = function (e) {
            $state.go('deviceConstraintManagement');
        };

        $scope.removeDialog = function (uid) {
            var data = $scope.dataSource.getByUid(uid.uid);
            if (data) {
                dialogService.showDialog($translate.instant('deleteConfirmation'),
                    sprintf($translate.instant('deleteMessage'), $translate.instant('deviceConstraint.singular'))).then(
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
                    sprintf($translate.instant('deleteMessage'), $translate.instant('deviceConstraint.singular'))).then(
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
                $scope.$emit('notifySuccess', sprintf($translate.instant('saved') , $translate.instant('deviceConstraint.singular')));
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
            $scope.deviceConstraint = data;

            $scope.refresh();
            // tekrar cagir

            $scope.$emit('notifySuccess', sprintf($translate.instant('saved'), $translate.instant('deviceConstraint.singular')));
        };

        var error = function (data) {
            $scope.$emit('notifyError', sprintf($translate.instant('notSaved'), data.message));
        };

        $scope.delete = function (id) {
            api.one('admin/deviceConstraints', id).remove().then(function () {
                $scope.$emit('notifySuccess', sprintf($translate.instant('saved'), $translate.instant('deviceConstraint.singular')));
                $scope.refresh();
            }, function (reason) {
                $scope.$emit('notifyError', sprintf($translate.instant('notSaved'), reason.message));
                $scope.refresh();
            });
        };


    });
