/**
 * Created by Notebook on 12.1.2017.
 */
angular.module('airSqreenApp')
    .filter('user',['AppUtils', function( AppUtils ) {
    return function( input, users ) {
        var name="";
        var item = _.find(users, {id: input});
        if(item) {
            name=item.name;
        }
        return  name;
    }
}]);