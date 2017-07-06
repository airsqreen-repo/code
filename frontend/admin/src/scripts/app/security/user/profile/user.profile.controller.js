'use strict';

angular.module('airSqreenApp')
    .controller('UserProfileController', function ($rootScope, $scope, $state, $stateParams, $translate, $timeout, api, resolvedUserProfile, AppUtilsService, DateUtils) {

        $scope.userProfile = resolvedUserProfile;


        if(!angular.isUndefined(resolvedUserProfile.lastMenstruationTime)){
            $scope.lastMenstruationTime = resolvedUserProfile.lastMenstruationTime;
            $scope.lastMenstruationTime= DateUtils.convertLocaleDateKendoGrid($scope.lastMenstruationTime);
            $scope.dateObject= new Date($scope.lastMenstruationTime);
        }

        $scope.load = function (){
            if($stateParams.mode=="edit"){
                $rootScope.isUpdate = false;
            }
        };

        $scope.load();

    });
