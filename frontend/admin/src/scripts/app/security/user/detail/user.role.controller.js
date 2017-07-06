'use strict';

angular.module('airSqreenApp')
    .controller('UserRoleController', function ($rootScope, $scope, $state, $stateParams, $translate, $timeout, api, Principal, KendoUtils, dialogService) {

        $scope.isView = true;

        $scope.load = function (){
            if($stateParams.mode=="edit"){
                $scope.isView = false;
                $scope.isCreate=true;
            }else{
                $scope.isView = true;
            }
        }

        $scope.load();
        /*
         $scope.right={
         'LIST'  :'USER_ROLE_LIST',
         'VIEW'  :'USER_ROLE_VIEW',
         'CREATE':'USER_ROLE_CREATE',
         'UPDATE':'USER_ROLE_UPDATE',
         'SAVE'  :'USER_ROLE_SAVE',
         'DELETE':'USER_ROLE_DELETE',
         'SEARCH':'USER_ROLE_SEARCH'
         };
         */

        $scope.right={
            'LIST'  :'ADMIN',
            'VIEW'  :'ADMIN',
            'CREATE':'ADMIN',
            'UPDATE':'ADMIN',
            'SAVE'  :'ADMIN',
            'DELETE':'ADMIN',
            'SEARCH':'ADMIN'
        };

        // role Size
        $scope.roleSize=100;


        // Selected Roles Array
        $scope._selectedRoles  = [];

        // Role role  Data Source
        var self = this;
        this.dataSourceName = 'roleList';
        $scope.dataSource = $scope.dataSources[self.dataSourceName];

        $scope.lblName = $translate.instant('security.user.label.role.name');
        $scope.choose = $translate.instant('choose');

        $scope.gridOptions = {
            columns: [
                {field: "id", hidden: true },
                {field: "role", hidden: true },
                {field: "name", title: "{{lblName}}",
                    template: function(e){
                        return e.name ? e.name : e.role.name;
                    },
                    editor: function(container, options) {
                        return e.name ? e.name : e.role.name;
                    }
                },
                {
                    command: [
                        { template: KendoUtils.deleteBtn($scope.right.DELETE)},
                    ],
                    title: "&nbsp;",
                    width: "64px"
                }
            ],
            selectable: "row",
            editable: false,
            sortable: false,
            scrollable: true,
            pageable: false,
            toolbar: [
                {template: KendoUtils.createBtn($scope.right.CREATE)}
            ]
        };


        // Popup Roles Data Source
        $scope.rolesGridOptions = {
            columns: [
                {field: "id", title: "Id", hidden: true },
                {field: "name", title: "{{lblName}}"},
                {field: "choose", title: "{{choose}}",
                    template: '<input  ng-model="string" type="checkbox"  class="rightId" ng-click="roleOnChange($event)" />',
                    width: 100
                },
            ],
            selectable: false,
            editable: false,
            sortable: false,
            pageable: false,
        };

        $scope.roleOnChange = function ($event){
            var checkbox = $event.target;
            var uid=checkbox.closest("tr").getAttribute('data-uid');
            var data=$scope.rolesDataSource.getByUid(uid);
            if(checkbox.checked){
                // ekleniyor
                $scope._selectedRoles.push($scope.rolesDataSource.getByUid(uid));
            }else {
                // silicez
                var length = $scope._selectedRoles.length;
                var item, i;
                for(i=length-1; i>=0; i--){
                    item = $scope._selectedRoles[i];
                    if(item.uid == uid ) {
                        //delete
                        $scope._selectedRoles.splice(i, 1);
                    }
                }
            }
        };

        $scope.rolesDataSource = null;

        // Ekli olan rollerden eklenenler siliniyor
        function rolesLoadBeforeFilterDeleteSelectedItems(){
            var raw = $scope.dataSource.data();
            var length = raw.length;
            var item, i;
            for(i=length-1; i>=0; i--){
                item = raw[i];
                // item.role.id servisten geliyor eger front tarafinda eklenmis ise roleId
                removeRolesItem(_.isObject(item.role) ? item.role.id : item.roleId);
            }
        }

        function removeRolesItem(roleId){
            var raw = $scope.rolesDataSource.data();
            var length = raw.length;
            var item, i;
            for(i=length-1; i>=0; i--){
                item = raw[i];
                if (item.id === roleId){
                    $scope.rolesDataSource.remove(item);
                }
            }
        }

        $scope.roleUpdate = function (){

            angular.forEach($scope._selectedRoles, function(value, key) {
                var right={
                    id:null,
                    roleId:value.id,
                    userId:$scope.$parent.user.id?$scope.$parent.user.id:null,
                    role:{id:value.id,name:value.name},
                    name:value.name,
                };
                $scope.dataSource.add(right);
                $scope.roleRemoveRow(value);

            });

            // write etmesin diye null
            $scope.rolesDataSource=null;
            $scope.dialog.hide();
            $scope.$parent.user.userRoleList = $scope.dataSource.data();
        };

        $scope.roleRemoveRow = function(value) {
            $scope.rolesDataSource.remove(value);
            $scope.$parent.user.userRoleList = $scope.dataSource.data();
        };

        $scope.create = function (){
            // her popup acilisinda kontrol olacagi icin
            $scope._selectedRoles  = [];
            $scope.rolesDataSource = KendoUtils.createKendoDataSource(api.all('admin/roles'), {
                serverPaging: true,
                serverSorting: true,
                serverFiltering: false,
                pageSize: $scope.roleSize
            });

            $timeout(function(){
                    $scope.dialog.show();
                    // var olan roller ve butun rolleri karsilastirma
                    rolesLoadBeforeFilterDeleteSelectedItems();
                }
                ,2000);
        };

        $scope.removeDialog = function (dataItem) {
            dialogService.showDialog($translate.instant('deleteConfirmation'),
                sprintf($translate.instant('deleteMessage') , $translate.instant('security.role.singular'))).then(
                function () {
                    $scope.dataSource.remove(dataItem);
                    $scope.$parent.user.userRoleList = $scope.dataSource.data();
                },
                false);
        };


        $scope.onChange = function (data) {
            $scope.selected = data;
        };

    });
