'use strict';

    angular.module('airSqreenApp')

        .directive('treeview', function () {
        return {
            restrict: 'A',
            link: function(scope, elem, attrs, ctrl) {
                var btn = elem.children('a').first();
                var menu = elem.children('.treeview-menu').first();
                var isActive = elem.hasClass('active');
                if (isActive) {
                    menu.show();
                    btn.children(".fa-angle-left").first().removeClass("fa-angle-left").addClass("fa-angle-down");
                }

                //Slide open or close the menu on link click
                btn.click(function(e) {
                    e.preventDefault();
                    if (isActive) {
                        //Slide up to close menu
                        menu.slideUp();
                        isActive = false;
                        btn.children(".fa-angle-down").first().removeClass("fa-angle-down").addClass("fa-angle-left");
                        btn.parent("li").removeClass("active");
                    } else {
                        //Slide down to open menu
                        menu.slideDown();
                        isActive = true;
                        btn.children(".fa-angle-left").first().removeClass("fa-angle-left").addClass("fa-angle-down");
                        btn.parent("li").addClass("active");
                    }
                });

                /* Add margins to submenu elements to give it a tree look */
                menu.find("li > a").each(function() {
                    var pad = parseInt($(this).css("margin-left")) + 10;

                    $(this).css({"margin-left": pad + "px"});
                });

            }
        }
    });
