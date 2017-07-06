/**
 * Created by yaban on 30/7/2015.
 */

angular.module('airSqreenApp')
    .directive('dialog', function() {
        return {
            restrict: 'A',
            link: function(scope, elem, attrs, ctrl) {
                scope[attrs.dialog] = {
                    hide: function() {
                        elem.modal('hide');
                    },
                    show: function() {

                        elem.modal('show');
                    }
                }
            }
        };
    });
