/**
 * Created by Notebook on 30.11.2016.
 */
angular.isValue = function(val) {
    return !(val === null || !angular.isDefined(val) || (angular.isNumber(val) && !isFinite(val)));
};