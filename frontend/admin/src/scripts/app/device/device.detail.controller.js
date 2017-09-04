'use strict';

angular.module('airSqreenApp')
    .controller('DeviceDetailController', function ($rootScope, $scope, $state, $stateParams, $translate, _, deviceApi, api, resolvedDevice,  Principal, KendoUtils, dialogService, $timeout, AppUtilsService, DateUtils ) {
        $scope.device = resolvedDevice;

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

            roleList: KendoUtils.createSubCollectionDataSource({
                readApi: deviceApi.all('deviceConstraintList'),
                writeApi: api.all('deviceConstraints'),
                backReference: {'deviceConstraint': deviceApi.config().url},
                readQuery: {projection: "summary"}
            })
        };

        $scope.save = function (event) {
            event.preventDefault();
            if ($scope.validator.validate()) {
                $scope.device.userRoleList = $scope.dataSources['roleList'] && $scope.dataSources['roleList'].data().length>0 ?$scope.dataSources['roleList'].data(): $scope.device.userRoleList;

                if(_.isObject($scope.device.platformUser[0])){
                    $scope.device.platformUser= $scope.device.platformUser[0];
                    $scope.device.platformUserId= $scope.device.platformUser.id;
                }



                if (_.isNumber($scope.device.id)) {
                    var toObject = Object.assign({}, $scope.device);
                    api.one('admin','devices').patch(toObject).then(success, error);
                } else {
                    var toObject = Object.assign({}, $scope.device);
                    api.one('admin','devices').post(toObject).then(goCreatedDevice, error);
                }





            } else {
                $scope.validationClass = "invalid";
            }
        };

        $scope.removeDialog = function () {

            dialogService.showDialog($translate.instant('deleteConfirmation'),
                sprintf($translate.instant('deleteMessage') , $translate.instant('device.singular'))).then(
                function () {
                    deviceApi.remove($scope.device).then(goDevice,error);
                },
                false);
        };


        var goCreatedDevice = function (e){
            $scope.$emit('notifySuccess', sprintf($translate.instant('saved') , $translate.instant('device.singular')));
            $state.go("device.detail", {id: e.id, mode: 'edit'});
        };

        var goDevice = function (e){
            $state.go("deviceManagement");
        };

        var success= function(data){
            $scope.load();
            $scope.device = data;
            $scope.$emit('notifySuccess', sprintf($translate.instant('saved') , $translate.instant('device.singular')));
        };

        var error= function (data) {
            $scope.$emit('notifyError', sprintf($translate.instant('notSaved') , data.message));
        };



        $scope.platformUsers = KendoUtils.createKendoDataSourceSearch(api.all('admin/platformUsers'), {
            serverFiltering: true,
            pageSize: 1000,
            sort: [
                { field: "id", dir: "desc" }
            ],
            filterHandler:function(filter) {
                var  autocomplete = $("#platformUser").data("kendoAutoComplete");
                return { url: "search/findByUsernameAndStatus",
                    query: {
                        dataStatus:"ACTIVE",
                        size:100,
                        username: autocomplete.value(),
                    }
                };

            }
        });


        $scope.setPlatformUser = function(){
            var  autocomplete = $("#platformUser").data("kendoAutoComplete");
            autocomplete.value($scope.device.platformUser.username);
            autocomplete.search(0);
            $timeout(function(){
                autocomplete.select(autocomplete.ul.children().eq(0));
                autocomplete.close();
            },500);
        };


        $scope.$on("kendoWidgetCreated", function(event,widget){
            // set Group Owner
            if (widget === $scope.platformUser) {
                if($scope.platformUser!=null){
                    $scope.setPlatformUser();
                }
            }
        });

    });
