// Generated on 2015-07-07 using generator-jhipster 2.17.0
'use strict';
var fs = require('fs');

var parseString = require('xml2js').parseString;
// Returns the second occurence of the version number
var parseVersionFromPomXml = function () {
    var version = 1.1;
    /*var pomXml = fs.readFileSync('../pom.xml', "utf8");
     parseString(pomXml, function (err, result){
     version = result.project.version[0];
     });*/
    return version;
};

// usemin custom step
var useminAutoprefixer = {
    name: 'autoprefixer',
    createConfig: function (context, block) {
        if (block.src.length === 0) {
            return {};
        } else {
            return require('grunt-usemin/lib/config/cssmin').createConfig(context, block) // Reuse cssmins createConfig
        }
    }
};

module.exports = function (grunt) {
    require('load-grunt-tasks')(grunt);
    require('time-grunt')(grunt);

    grunt.initConfig({
        yeoman: {
            // configurable paths
            app: require('./bower.json').appPath || 'app',
            dist: './src/dist',
            src: './src/'
        },
        watch: {
            bower: {
                files: ['bower.json'],
                tasks: ['wiredep']
            },
            ngconstant: {
                files: ['Gruntfile.js', 'pom.xml'],
                tasks: ['ngconstant:dev', 'ngconstant:devamazon']
            },
            styles: {
                files: [
                    './less/**/*.{css,less}'
                ],
                tasks: [
                    'copy:styles',
                    'less:development'
                ],
                options: {
                    spawn: false,
                    livereload: true
                }
            }

        },
        autoprefixer: {
            // src and dest is configured in a subtask called "generated" by usemin
            options: ['last 1 version'],
            dist: {
                files: [{
                    expand: true,
                    cwd: '.tmp/styles/',
                    src: '**/*.css',
                    dest: '.tmp/styles/'
                }]
            }
        },
        wiredep: {
            app: {
                src: ['./src/index.html', './less/main.less'],
                exclude: [
                    /angular-i18n/, /swagger-ui/, // localizations are loaded dynamically
                    'bower_components/bootstrap/' // Exclude Bootstrap LESS as we use bootstrap-sass
                ],
                ignorePath: /\.\.\/src\/bower_components\// // remove ../src/bower_components/ from paths of injected sass files
            },
            test: {
                src: 'test/javascript/karma.conf.js',
                exclude: [/angular-i18n/, /angular-scenario/],
                ignorePath: /\.\.\/\.\.\//, // remove ../../ from paths of injected javascripts
                devDependencies: true,
                fileTypes: {
                    js: {
                        block: /(([\s\t]*)\/\/\s*bower:*(\S*))(\n|\r|.)*?(\/\/\s*endbower)/gi,
                        detect: {
                            js: /'(.*\.js)'/gi
                        },
                        replace: {
                            js: '\'{{filePath}}\','
                        }
                    }
                }
            }
        },
        browserSync: {
            dev: {
                bsFiles: {
                    src: [
                        './src/**/*.html',
                        './src/**/*.json',
                        './src/assets/styles/**/*.css',
                        './src/scripts/**/*.js',
                        './src/assets/images/**/*.{png,jpg,jpeg,gif,webp,svg}',
                        './src/assets/styles/Bootstrap/**/*.{png,jpg,jpeg,gif,webp,svg}',
                        './src/**/*.{css,js}'
                    ]
                },
                options: {
                    host: "localhost",
                    server: {
                        baseDir: "./src"
                    }
                }
            },
            options: {
                baseDir: "app"
            }
        },
        clean: {
            dist: {
                files: [{
                    dot: true,
                    src: [
                        '.tmp',
                        '<%= yeoman.dist %>/*',
                        '!<%= yeoman.dist %>/.git*'
                    ]
                }]
            },
            server: '.tmp'
        },
        jshint: {
            options: {
                jshintrc: '.jshintrc'
            },
            all: [
                './Gruntfile.js',
                './src/scripts/app.js',
                './src/scripts/app/**/*.js',
                './src/scripts/components/**/*.js'
            ]
        },
        concat: {
            // src and dest is configured in a subtask called "generated" by usemin
        },
        uglifyjs: {
            // src and dest is configured in a subtask called "generated" by usemin
        },
        rev: {
            dist: {
                files: {
                    src: [
                        '<%= yeoman.dist %>/scripts/**/*.js',
                        '<%= yeoman.dist %>/assets/styles/**/*.css',
                        '<%= yeoman.dist %>/assets/styles/Bootstrap/**/*.{png,jpg,jpeg,gif,webp,svg}',
                        '<%= yeoman.dist %>/assets/fonts/*'
                    ]
                }
            }
        },
        useminPrepare: {
            html: './src/**/*.html',
            options: {
                dest: '<%= yeoman.dist %>',
                flow: {
                    html: {
                        steps: {
                            js: ['concat', 'uglifyjs'],
                            css: ['cssmin', useminAutoprefixer] // Let cssmin concat files so it corrects relative paths to fonts and images
                        },
                        post: {}
                    }
                }
            }
        },
        usemin: {
            html: ['<%= yeoman.dist %>/**/*.html'],
            css: ['<%= yeoman.dist %>/assets/styles/**/*.css'],
            js: ['<%= yeoman.dist %>/scripts/**/*.js'],
            options: {
                assetsDirs: ['<%= yeoman.dist %>', '<%= yeoman.dist %>/assets/styles', '<%= yeoman.dist %>/assets/images', '<%= yeoman.dist %>/assets/fonts'],
                patterns: {
                    js: [
                        [/(assets\/images\/.*?\.(?:gif|jpeg|jpg|png|webp|svg))/gm, 'Update the JS to reference our revved images']
                    ]
                },
                dirs: ['<%= yeoman.dist %>']
            }
        },
        imagemin: {
            dist: {
                files: [{
                    expand: true,
                    cwd: './src/assets/images',
                    src: '**/*.{jpg,jpeg}', // we don't optimize PNG files as it doesn't work on Linux. If you are not on Linux, feel free to use '**/*.{png,jpg,jpeg}'
                    dest: '<%= yeoman.dist %>/assets/images'
                }]
            }
        },
        svgmin: {
            dist: {
                files: [{
                    expand: true,
                    cwd: './src/assets/images',
                    src: '**/*.svg',
                    dest: '<%= yeoman.dist %>/assets/images'
                }]
            }
        },
        cssmin: {
            // src and dest is configured in a subtask called "generated" by usemin
        },
        htmlmin: {
            dist: {
                options: {
                    removeCommentsFromCDATA: true,
                    // https://github.com/yeoman/grunt-usemin/issues/44
                    collapseWhitespace: true,
                    collapseBooleanAttributes: true,
                    conservativeCollapse: true,
                    removeAttributeQuotes: true,
                    removeRedundantAttributes: true,
                    useShortDoctype: true,
                    removeEmptyAttributes: true,
                    keepClosingSlash: true
                },
                files: [{
                    expand: true,
                    cwd: '<%= yeoman.dist %>',
                    src: ['*.html', 'scripts/**/*.html'],
                    dest: '<%= yeoman.dist %>'
                }]
            }
        },
        // Put files not handled in other tasks here
        copy: {
            dist: {
                files: [
                    {
                        expand: true,
                        dot: true,
                        cwd: './src',
                        dest: '<%= yeoman.dist %>',
                        src: [
                            '*.html',
                            '*.ico',
                            'scripts/**/*.html',
                            'i18n/**/*.json',
                            'assets/images/**/*.{png,gif,webp,jpg,jpeg,svg}',
                            'assets/styles/Bootstrap/*.*',
                            'assets/fonts/*',
                            [
                                'bower_components/angular-i18n/angular-locale_en.js',
                                'bower_components/angular-i18n/angular-locale_tr.js'
                            ]
                        ]
                    }, {
                        expand: true,
                        dot: true,
                        flatten: true,
                        cwd: './src',
                        dest: '<%= yeoman.dist %>/assets/fonts',
                        src: [
                            'bower_components/bootstrap/fonts/*.*',
                            'bower_components/font-awesome-less/fonts/*.*'
                        ]
                    }, {
                        expand: true,
                        cwd: './src/scripts',
                        dest: '<%= yeoman.dist %>/scripts',
                        src: [
                            'kendo/kendo.all.min.js'
                        ]
                    }]
            },
            styles: {
                files: [
                    //{
                    //    expand: true,
                    //    cwd: 'service/src/styles',
                    //    dest: '.tmp/styles',
                    //    src: ['**/*.{css,less}']
                    //},
                    {
                        expand: true,
                        cwd: './src/bower_components/bootstrap/fonts',
                        dest: './src/assets/fonts',
                        src: '*'
                    },
                    {
                        expand: true,
                        cwd: './src/bower_components/font-awesome-less/fonts',
                        dest: './src/assets/fonts',
                        src: '*'
                    }
                ]
            },
            /*generateOpenshiftDirectory: {
             expand: true,
             dest: 'deploy/openshift',
             src: [
             'pom.xml',
             'src/main/**'
             ]
             }*/
        },
        karma: {
            unit: {
                configFile: 'test/javascript/karma.conf.js',
                singleRun: true
            }
        },
        ngAnnotate: {
            dist: {
                files: [{
                    expand: true,
                    cwd: '.tmp/concat/scripts',
                    src: '*.js',
                    dest: '.tmp/concat/scripts'
                }]
            }
        },
        buildcontrol: {
            options: {
                commit: true,
                push: false,
                connectCommits: false,
                message: 'Built %sourceName% from commit %sourceCommit% on branch %sourceBranch%'
            }
            /*,openshift: {
             options: {
             dir: 'deploy/openshift',
             remote: 'openshift',
             branch: 'master'
             }
             }*/
        },
        ngconstant: {
            options: {
                name: 'airSqreenApp',
                deps: false,
                wrap: '"use strict";\n// DO NOT EDIT THIS FILE, EDIT THE GRUNT TASK NGCONSTANT SETTINGS INSTEAD WHICH GENERATES THIS FILE\n{%= __ngModule %}'
            },
            dev: {
                options: {
                    dest: './src/scripts/app/app.constants.js'
                },
                constants: {
                    ENV: 'dev',
                    VERSION: parseVersionFromPomXml(),
                    SERVICE_URL: 'http://localhost:9080/',
                    PUBLIC_URL: 'http://localhost:3000/',
                    CDN_URL: 'http://127.0.0.1:82/projects/cdn'
                }
            },
            devamazon: {
                options: {
                    dest: './src/scripts/app/app.constants.js'
                },
                constants: {
                    ENV: 'devamazon',
                    VERSION: parseVersionFromPomXml(),
                    SERVICE_URL: 'http://testapi.guideofpregnancy.com/',
                    PUBLIC_URL: 'http://guideofpregnancy.com/',
                    CDN_URL: 'http://testcdn.guideofpregnancy.com/'
                }
            },
            prod: {
                options: {
                    dest: './src/scripts/app/app.constants.js'
                },
                constants: {
                    ENV: 'prod',
                    VERSION: parseVersionFromPomXml(),
                    SERVICE_URL: 'http://testapi.airsqreen.com/',
                    PUBLIC_URL: 'http://testapi.airsqreen.com/',
                    CDN_URL: 'http://testapi.airsqreen.com/'
                }
            },
            preprod: {
                options: {
                    dest: './src/scripts/app/app.constants.js'
                },
                constants: {
                    ENV: 'prod',
                    VERSION: parseVersionFromPomXml(),
                    SERVICE_URL: 'http://testapi.guideofpregnancy.com/',
                    PUBLIC_URL: 'http://test.guideofpregnancy.com/',
                    CDN_URL: 'http://testcdn.guideofpregnancy.com/'
                }
            }
        },
        less: {
            development: {
                options: {},
                files: {
                    //".tmp/styles/main.css": "service/src/styles/style.less"
                    "./src/assets/styles/main.css": "./less/style.less"
                }
            },
            production: {
                options: {
                    compress: true,
                    yuicompress: true,
                    optimization: 2
                },
                files: {
                    //"<%= yeoman.dist %>/styles/main.css": "service/src/styles/style.less"
                    "./src/assets/styles/main.css": "./less/style.less"
                }
            }
        }
    });

    grunt.registerTask('serve', [
        'clean:server',
        'wiredep',
        'ngconstant:dev',
        //'compass:server',
        'less',
        'copy:styles',
        'copy:dist',
        'browserSync',
        'watch'
    ]);

    grunt.registerTask('serveamazon', [
        'clean:server',
        'wiredep',
        'ngconstant:devamazon',
        //'compass:server',
        'less',
        'copy:styles',
        'copy:dist',
        'browserSync',
        'watch'
    ]);

    grunt.registerTask('server', function (target) {
        grunt.log.warn('The `server` task has been deprecated. Use `grunt serve` to start a server.');
        grunt.task.run([target ? ('serve:' + target) : 'serve']);
    });

    grunt.registerTask('test', [
        'clean:server',
        'wiredep:test',
        'ngconstant:dev',
        //'compass',
        'less:development',
        //'karma'
    ]);

    grunt.registerTask('build', [
        'clean:dist',
        'wiredep:app',
        'ngconstant:prod',
        'useminPrepare',
        //'compass:dist',
        'less:production',
        'imagemin',
        'svgmin',
        'concat',
        'copy:styles',
        'copy:dist',
        'ngAnnotate',
        'cssmin',
        'autoprefixer',
        'uglify',
        'rev',
        'usemin',
        'htmlmin'
    ]);
    
    grunt.registerTask('prebuild', [
        'clean:dist',
        'wiredep:app',
        'ngconstant:preprod',
        'useminPrepare',
        //'compass:dist',
        'less:production',
        'imagemin',
        'svgmin',
        'concat',
        'copy:styles',
        'copy:dist',
        'ngAnnotate',
        'cssmin',
        'autoprefixer',
        'uglify',
        'rev',
        'usemin',
        'htmlmin'
    ]);

    grunt.registerTask('appendSkipBower', 'Force skip of bower for Gradle', function () {

        if (!grunt.file.exists(filepath)) {
            // Assume this is a maven project
            return true;
        }

        var fileContent = grunt.file.read(filepath);
        var skipBowerIndex = fileContent.indexOf("skipBower=true");

        if (skipBowerIndex != -1) {
            return true;
        }

        grunt.file.write(filepath, fileContent + "\nskipBower=true\n");
    });

    grunt.registerTask('buildOpenshift', [
        'test',
        'build',
        //'copy:generateOpenshiftDirectory',
    ]);

    grunt.registerTask('deployOpenshift', [
        'test',
        'build',
        //'copy:generateOpenshiftDirectory',
        'buildcontrol:openshift'
    ]);

    grunt.registerTask('default', [
        'test',
        'build',
        //'copy:generateOpenshiftDirectory',
        'buildcontrol:openshift'
    ]);
};
