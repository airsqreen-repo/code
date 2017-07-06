'use strict';

angular.module('airSqreenApp')
    .service('DateUtils', function ($filter) {
        this.convertLocaleDateKendoGrid = function (date){
            if (date) {
                return $filter('date')(date,'yyyy-MM-dd');
            } else {
                return null;
            }

        };

        this.convertLocaleDateKendoToServer = function(date) {
            if (date) {
                var newDate= date.split("/");
                return new Date(newDate[2],newDate[1] - 1,newDate[0]).toISOString();
            } else {
                return null;
            }
       };
      this.convertLocaleDateToServer = function(date) {
        if (date) {
          var utcDate = new Date();
          utcDate.setUTCDate(date.getDate());
          utcDate.setUTCMonth(date.getMonth());
          utcDate.setUTCFullYear(date.getFullYear());

          utcDate.setUTCHours(0);
          utcDate.setUTCMinutes(0);
          utcDate.setUTCSeconds(0);
          utcDate.setUTCMilliseconds(0);

          return utcDate;
        } else {
          return null;
        }
      };
      this.convertLocaleDateFromServer = function(date) {
        if (date) {
          var dateString = date.split("-");
          return new Date(dateString[0], dateString[1] - 1, dateString[2]);
        }
        return null;
      };
      this.convertDateTimeFromServer = function(date) {
        if (date) {
          return new Date(date);
        } else {
          return null;
        }
      }
    });
