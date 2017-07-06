'use strict';

angular.module('airSqreenApp')
    .controller('RoleRightController', function ($rootScope, $scope, $state, $stateParams, $translate, $timeout, api, Principal, KendoUtils, dialogService) {

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
            'LIST'  :'ROLE_RIGHT_LIST',
            'VIEW'  :'ROLE_RIGHT_VIEW',
            'CREATE':'ROLE_RIGHT_CREATE',
            'UPDATE':'ROLE_RIGHT_UPDATE',
            'SAVE'  :'ROLE_RIGHT_SAVE',
            'DELETE':'ROLE_RIGHT_DELETE',
            'SEARCH':'ROLE_RIGHT_SEARCH'
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
        $scope.rightSize=100;


        // Selected Rights Array
        $scope._selectedRights  = [];

        // Role right  Data Source
        var self = this;
        this.dataSourceName = 'rightList';
        $scope.dataSource = $scope.dataSources[self.dataSourceName];

        $scope.lblName = $translate.instant('security.role.label.right.name');
        $scope.choose = $translate.instant('choose');

        $scope.gridOptions = {
            columns: [
                {field: "id", hidden: true },
                {field: "right", hidden: true },
                {field: "name", title: "{{lblName}}",
                    template: function(e){
                        return e.rightName ? e.rightName : e.right.name;
                    },
                    editor: function(container, options) {
                        return e.rightName ? e.rightName : e.right.name;
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


        // Popup Rights Data Source
        $scope.rightsGridOptions = {
            columns: [
                {field: "id", title: "Id", hidden: true },
                {field: "name", title: "{{lblName}}"},
                {field: "choose", title: "{{choose}}",
                    template: '<input  ng-model="string" type="checkbox"  class="rightId" ng-click="rightOnChange($event)" />',
                    width: 100
                },
            ],
            selectable: false,
            editable: false,
            sortable: false,
            pageable: false,
        };

        $scope.rightOnChange = function ($event){
            var checkbox = $event.target;
            var uid=checkbox.closest("tr").getAttribute('data-uid');
            var data=$scope.rightsDataSource.getByUid(uid);
            if(checkbox.checked){
                // ekleniyor
                $scope._selectedRights.push($scope.rightsDataSource.getByUid(uid));
            }else {
                // silicez
                var length = $scope._selectedRights.length;
                var item, i;
                for(i=length-1; i>=0; i--){
                    item = $scope._selectedRights[i];
                    if(item.uid == uid ) {
                        //delete
                        $scope._selectedRights.splice(i, 1);
                    }
                }
            }
        };

        $scope.rightsDataSource = null;

        // Ekli olan rightlardan eklenenler siliniyor
        function rightsLoadBeforeFilterDeleteSelectedItems(){
            var raw = $scope.dataSource.data();
            var length = raw.length;
            var item, i;
            for(i=length-1; i>=0; i--){
                item = raw[i];
                removeRightItem(_.isObject(item.right) ? item.right.id : item.rightId);
            }
        }

        function removeRightItem(rightId){
            var raw = $scope.rightsDataSource.data();
            var length = raw.length;
            var item, i;
            for(i=length-1; i>=0; i--){
                item = raw[i];
                if (item.id === rightId){
                    $scope.rightsDataSource.remove(item);
                }
            }
        }

        $scope.rightUpdate = function (){

            angular.forEach($scope._selectedRights, function(value, key) {
                var right={
                    id:null,
                    rightId:value.id,
                    roleId:$scope.$parent.role.id?$scope.$parent.role.id:null,
                    right: {id:value.id, name: value.name},
                    rightName:value.name
                };
                $scope.dataSource.add(right);
                $scope.rightRemoveRow(value);

            });

            // write etmesin diye null
            $scope.$parent.role.roleRightList = $scope.dataSource.data();

            $scope.rightsDataSource=null;
            $scope.dialog.hide();
        };

        $scope.rightRemoveRow = function(value) {
            $scope.rightsDataSource.remove(value);
            $scope.$parent.role.roleRightList = $scope.dataSource.data();
        };

        $scope.create = function (){
            // her popup acilisinda kontrol olacagi icin
            $scope._selectedRights  = [];
            $scope.rightsDataSource = KendoUtils.createKendoDataSource(api.all('admin/rights'), {
                serverPaging: true,
                serverSorting: true,
                serverFiltering: false,
                pageSize: $scope.rightSize
            });

            $timeout(function(){
                    $scope.dialog.show();
                    // var olan roller ve butun rolleri karsilastirma
                    rightsLoadBeforeFilterDeleteSelectedItems();
                }
                ,2000);
        };

        $scope.edit = function (item) {
            $scope.roleRight = angular.copy(item);
            $scope.dialog.show();
        };

        $scope.removeDialog = function (dataItem) {
            dialogService.showDialog($translate.instant('deleteConfirmation'),
                sprintf($translate.instant('deleteMessage') , $translate.instant('security.role.right'))).then(
                function () {
                    $scope.dataSource.remove(dataItem);
                    $scope.$parent.role.roleRightList = $scope.dataSource.data();
                },
                false);
        };


        $scope.onChange = function (data) {
            $scope.selected = data;
        };


    });
