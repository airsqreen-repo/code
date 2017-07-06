'use strict';

angular.module('airSqreenApp')
    .controller('UserDetailController', function ($rootScope, $scope, $state, $stateParams, $translate, _, bcrypt, userApi, api, resolvedUser,  Principal, KendoUtils, dialogService, AppUtilsService, DateUtils ) {
        $scope.user = resolvedUser;

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

            roleList: KendoUtils.createSubCollectionDataSource({
                readApi: userApi.all('userRoleList'),
                writeApi: api.all('userRoles'),
                backReference: {'user': userApi.config().url},
                readQuery: {projection: "summary"}
            }),
            groupList:KendoUtils.createKedoData($scope.user.groupList),
            groupMemberList:KendoUtils.createKedoData($scope.user.groupMemberList),
        };

        $scope.save = function (event) {
            event.preventDefault();
            if ($scope.validator.validate()) {
                $scope.user.userRoleList = $scope.dataSources['roleList'] && $scope.dataSources['roleList'].data().length>0 ?$scope.dataSources['roleList'].data(): $scope.user.userRoleList;

                if(_.isNumber($scope.user.id)) {

                    if(!_.isEmpty($scope.user.tempPassword) && !_.isEmpty($scope.user.tempPassword_re) && ($scope.user.tempPassword == $scope.user.tempPassword_re) ){
                        $scope.user.password = bcrypt.hashSync($scope.user.tempPassword, 10);
                        delete $scope.user.tempPassword;
                        delete $scope.user.tempPassword_re;
                    }

                    userApi.patch($scope.user).then(success,error);
                }else{
                    // password hash
                    $scope.user.password = bcrypt.hashSync($scope.user.tempPassword, 10);
                    $scope.user.user_status="ACTIVE";
                    //$scope.user.activated=true;
                    delete $scope.user.tempPassword;
                    delete $scope.user.tempPassword_re;
                    userApi.post($scope.user).then(goCreatedUser,error);
                }
            } else {
                $scope.validationClass = "invalid";
            }
        };

        $scope.removeDialog = function () {

            dialogService.showDialog($translate.instant('deleteConfirmation'),
                sprintf($translate.instant('deleteMessage') , $translate.instant('security.user.singular'))).then(
                function () {
                    userApi.remove($scope.user).then(goUser,error);
                },
                false);
        };


        var goCreatedUser = function (e){
            $scope.$emit('notifySuccess', sprintf($translate.instant('saved') , $translate.instant('security.user.singular')));
            $state.go("user.detail", {id: e.id, mode: 'edit'});
        };

        var goUser = function (e){
            $state.go("userManagement");
        };

        var success= function(data){
            $scope.load();
            $scope.user = data;
            $scope.$emit('notifySuccess', sprintf($translate.instant('saved') , $translate.instant('security.user.singular')));

            /*
                _.keys($scope.dataSources).filter(function (key) {
                    return $scope.dataSources[key].hasChanges()
                }).forEach(function (key) {
                    $scope.dataSources[key].sync().then(function () {
                        $scope.$emit('notifySuccess', sprintf($translate.instant('saved') , $translate.instant('security.user.'+ key)));
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
