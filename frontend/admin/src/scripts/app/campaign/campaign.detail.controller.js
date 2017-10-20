'use strict';

angular.module('airSqreenApp')
    .controller('CampaignDetailController', function ($rootScope, $scope, $state, $stateParams, $translate, _, campaignApi, api, resolvedCampaign,  Principal, KendoUtils, dialogService, $timeout, AppUtilsService, DateUtils ) {
        $scope.campaign = resolvedCampaign;

        $rootScope.isView = true;

        $scope.load = function (){
            if($stateParams.mode=="create"){
                $rootScope.isView = false;
                $rootScope.isCreate=true;
                $rootScope.isUpdate=false;
            }else if($stateParams.mode=="edit"){
                $rootScope.isView = false;
                $rootScope.isCreate=false;
                $rootScope.isUpdate=true;
            }else{
                $rootScope.isView = true;
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


        $scope.dataSources = {

            campaignConstraints: KendoUtils.createSubCollectionDataSource({
                readApi: campaignApi.all('campaignConstraints'),
                writeApi: api.all('campaignConstraints'),
                backReference: {'campaignConstraint': campaignApi.config().url},
                readQuery: {projection: "summary"}
            }),
            campaignSections: KendoUtils.createSubCollectionDataSource({
                readApi: campaignApi.all('campaignSections'),
                writeApi: api.all('campaignSections'),
                backReference: {'campaignSections': campaignApi.config().url},
                readQuery: {projection: "summary"}
            })
        };
/*
        $scope.save = function (event) {
            event.preventDefault();
            if ($scope.validator.validate()) {
                if (_.isNumber($scope.campaign.id)) {
                    var toObject = Object.assign({}, $scope.campaign);
                    api.one('admin','campaigns').patch(toObject).then(success, error);
                } else {
                    var toObject = Object.assign({}, $scope.campaign);
                    api.one('admin','campaigns').post(toObject).then(goCreatedCampaign, error);
                }

            } else {
                $scope.validationClass = "invalid";
            }
        };*/

        $scope.removeDialog = function () {

            dialogService.showDialog($translate.instant('deleteConfirmation'),
                sprintf($translate.instant('deleteMessage') , $translate.instant('campaign.singular'))).then(
                function () {
                    campaignApi.remove($scope.campaign).then(goDevice,error);
                },
                false);
        };


        var goCreatedCampaign = function (e){
            $scope.$emit('notifySuccess', sprintf($translate.instant('saved') , $translate.instant('campaign.singular')));
            $state.go("campaign.detail", {id: e.id, mode: 'edit'});
        };

        var goDevice = function (e){
            $state.go("campaignManagement");
        };

        var success= function(data){
            $scope.load();
            $scope.campaign = data;
            $scope.$emit('notifySuccess', sprintf($translate.instant('saved') , $translate.instant('campaign.singular')));
        };

        var error= function (data) {
            $scope.$emit('notifyError', sprintf($translate.instant('notSaved') , data.message));
        };


    });
