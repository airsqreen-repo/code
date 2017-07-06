/**
 * Created by yaban on 30/7/2015.
 */

'use strict';

angular.module('airSqreenApp')
    .factory('dialogService', function dialogService($q,$translate) {
        var service = {
            showDialog: showDialog
        };

        return service;

        function showDialog(title, message) {
            var deferred = $q.defer();
            var lblYes = $translate.instant('yes');
            var lblNo = $translate.instant('no');

            var html =
                '<div id="myDialogWindow"> ' +
                ' <div style="text-align: center; width:100%"> ' +
                '   <div style="margin:10px 0 15px 0">' + message + '</div> ' +
                '   <button class="k-button k-primary" id="yesButton">' + lblYes + '</button> ' +
                '   <button class="k-button" id="noButton">' + lblNo + '</button> ' +
                '   </div> ' +
                '</div> ';

            $('body').append(html);

            var windowDiv = $('#myDialogWindow');
            windowDiv.kendoWindow({
                width: "250px",
                title: title,
                modal: true,
                visible: false
            });

            var dialog = windowDiv.data("kendoWindow");

            $('#yesButton').click(function(e) {
                dialog.close();
                $('#myDialogWindow').remove();
                deferred.resolve();
            });

            $('#noButton').click(function(e) {
                dialog.close();
                $('#myDialogWindow').remove();
                deferred.reject();
            });

            dialog.center();
            dialog.open();

            return deferred.promise;
        }
    });
