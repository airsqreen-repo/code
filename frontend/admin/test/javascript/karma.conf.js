// Karma configuration
// http://karma-runner.github.io/0.10/config/configuration-file.html

module.exports = function (config) {
    config.set({
        // base path, that will be used to resolve files and exclude
        basePath: '../../',

        // testing framework to use (jasmine/mocha/qunit/...)
        frameworks: ['jasmine'],

        // list of files / patterns to load in the browser
        files: [
            // bower:js
            'src/bower_components/modernizr/modernizr.js',
            'src/bower_components/jquery/dist/jquery.js',
            'src/bower_components/bootstrap/dist/js/bootstrap.js',
            'src/bower_components/angular/angular.js',
            'src/bower_components/angular-bootstrap/ui-bootstrap-tpls.js',
            'src/bower_components/stomp-websocket/lib/stomp.min.js',
            'src/bower_components/sockjs-client/dist/sockjs.js',
            'src/bower_components/json3/lib/json3.js',
            'src/bower_components/angular-ui-router/release/angular-ui-router.js',
            'src/bower_components/angular-animate/angular-animate.js',
            'src/bower_components/angular-resource/angular-resource.js',
            'src/bower_components/angular-cookies/angular-cookies.js',
            'src/bower_components/angular-sanitize/angular-sanitize.js',
            'src/bower_components/angular-translate/angular-translate.js',
            'src/bower_components/angular-translate-storage-cookie/angular-translate-storage-cookie.js',
            'src/bower_components/angular-translate-loader-partial/angular-translate-loader-partial.js',
            'src/bower_components/angular-dynamic-locale/src/tmhDynamicLocale.js',
            'src/bower_components/angular-local-storage/dist/angular-local-storage.js',
            'src/bower_components/angular-cache-buster/angular-cache-buster.js',
            'src/bower_components/ngInfiniteScroll/build/ng-infinite-scroll.js',
            'src/bower_components/lodash/lodash.js',
            'src/bower_components/ng-sortable/dist/ng-sortable.js',
            'src/bower_components/angular-bcrypt/dist/dtrw.bcrypt-df34c8d1.js',
            'src/bower_components/angular-ui-mask/dist/mask.js',
            'src/bower_components/ng-file-upload/ng-file-upload.js',
            'src/bower_components/angular-mocks/angular-mocks.js',
            // endbower
            'main/webapp/scripts/app/app.js',
            'main/webapp/scripts/app/**/*.js',
            'main/webapp/scripts/components/**/*.{js,html}',
            'test/javascript/**/!(karma.conf).js'
        ],


        // list of files / patterns to exclude
        exclude: [],

        // web server port
        port: 9876,

        // level of logging
        // possible values: LOG_DISABLE || LOG_ERROR || LOG_WARN || LOG_INFO || LOG_DEBUG
        logLevel: config.LOG_INFO,

        // enable / disable watching file and executing tests whenever any file changes
        autoWatch: false,

        // Start these browsers, currently available:
        // - Chrome
        // - ChromeCanary
        // - Firefox
        // - Opera
        // - Safari (only Mac)
        // - PhantomJS
        // - IE (only Windows)
        browsers: ['PhantomJS'],

        // Continuous Integration mode
        // if true, it capture browsers, run tests and exit
        singleRun: false
    });
};
