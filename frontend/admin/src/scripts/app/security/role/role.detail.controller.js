'use strict';

angular.module('airSqreenApp')
    .controller('RoleDetailController', function ($rootScope, $scope, $state, $stateParams, $translate, _, roleApi, api, resolvedRole,  Principal, KendoUtils, dialogService) {

        $scope.role = resolvedRole;
        $scope.isView = true;

        $scope.load = function (){
            if($stateParams.mode=="create"){
                $scope.isView = false;
                $scope.isCreate=true;
                $scope.isUpdate=false;

            }else if($stateParams.mode=="edit"){
                $scope.isView = false;
                $scope.isCreate=false;
                $scope.isUpdate=true;
            }else{
                $scope.isView = true;
            }
        }

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
            rightList: KendoUtils.createSubCollectionDataSource({
                readApi: roleApi.all('roleRights'),
                writeApi: api.all('roleRights'),
                backReference: {'role': roleApi.config().url},
                readQuery: {projection: "summary"}
            })
        };
        $scope.save = function (event) {
            event.preventDefault();
            if ($scope.validator.validate()) {
                $scope.role.roleRightList = $scope.dataSources['roleRights'] && $scope.dataSources['roleRights'].data().length>0 ?$scope.dataSources['roleRights'].data(): $scope.role.roleRightList;

                if(_.isNumber($scope.role.id)) {
                    api.one('admin','roles').patch($scope.role).then(success,error);
                }else{
                    api.one('admin','roles').post($scope.role).then(goCreatedRole,error);
                }
            } else {
                $scope.validationClass = "invalid";
            }
        };



        $scope.removeDialog = function () {
            dialogService.showDialog($translate.instant('deleteConfirmation'),
                sprintf($translate.instant('deleteMessage'), $translate.instant('security.role.singular'))).then(
                function () {
                    roleApi.remove($scope.role).then(goRole(), error);
                },
                false);
        };

        var goCreatedRole = function (e){
            $scope.$emit('notifySuccess', sprintf($translate.instant('saved') , $translate.instant('security.role.singular')));
            $state.go("role.detail", {id: e.id, mode: 'edit'});
        };

        var goRole = function (e){
            $state.go("roleManagement");
        };

        var success= function(data){
            $scope.load();
            $scope.role = data;
            $scope.$emit('notifySuccess', sprintf($translate.instant('saved') , $translate.instant('security.role.singular')));

            /*
            _.keys($scope.dataSources).filter(function (key) {
                return $scope.dataSources[key].hasChanges()
            }).forEach(function (key) {
                $scope.dataSources[key].sync().then(function () {
                    $scope.$emit('notifySuccess', sprintf($translate.instant('saved') , $translate.instant('security.role.'+ key)));
                },function(reason) {
                    $scope.$emit('notifyError', sprintf($translate.instant('notSaved') , reason.message));
                });
            });
            */
        };

        var error= function (data) {
            $scope.$emit('notifyError', sprintf($translate.instant('notSaved') , data.message));
        };


    });
