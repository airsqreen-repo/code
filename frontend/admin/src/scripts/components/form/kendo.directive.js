'use strict';


 angular.module('airSqreenApp')
.directive('kendoGridCheckbox', ['$compile', function ($compile) {
        return {
            restrict: "A",
            scope: true,
            controller: ['$scope', function(s) {
                window.crap = s;
                s.toggleSelectAll = function(ev) {
                    var grid = $(ev.target).closest("[kendo-grid]").data("kendoGrid");
                    var items = grid.dataSource.data();
                    items.forEach(function(item){
                        item.deleted = ev.target.checked;
                    });
                };
            }],
            link:_link
        }

         function _link(scope, element, attrs){
             var options = angular.extend({}, scope.$eval(attrs.kOptions));
             options.columns.unshift({
                 template: "<input type='checkbox' ng-model='dataItem.deleted' />",
                 title: "<input type='checkbox' title='Select all' ng-click='toggleSelectAll($event)' />",
                 width: 30
             });
         }
         _link.$inject = ['$scope', '$element', '$attrs'];
}]);
