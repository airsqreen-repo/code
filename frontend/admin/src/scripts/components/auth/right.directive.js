'use strict';


 angular.module('airSqreenApp');

/*
    app.directive('hasRight', function ($compile, Principal) {
        return {
            priority:1001,
            terminal:true,
            compile: function(element, attrs) {
                var right = attrs.hasRight.replace(/\s+/g, '');
                element.removeAttr('has-right');
                return function(scope){
                    if(Principal.isInRole(right)){
                        element.attr('ng-disabled',false);
                    }else{
                        element.attr('ng-disabled',true);
                    }
                    var fn = $compile(element);
                    fn(scope);
                };
            }
        };
    });

    app.directive('hasRights', function ($compile, Principal) {
        return {
            priority:1001,
            terminal:true,
            compile: function(element, attrs) {
                var right = attrs.hasRights.replace(/\s+/g, '');
                element.removeAttr('has-rights');
                return function(scope){

                    if(Principal.isInRole(right)){
                        element.attr('ng-disabled',false);
                    }else{
                        element.attr('ng-disabled',true);
                    }
                    var fn = $compile(element);
                    fn(scope);
                };
            }
        };
    });
*/
