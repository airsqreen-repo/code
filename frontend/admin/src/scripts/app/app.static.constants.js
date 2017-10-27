"use strict";
// DO NOT EDIT THIS FILE, EDIT THE GRUNT TASK NGCONSTANT SETTINGS INSTEAD WHICH GENERATES THIS FILE
angular.module('airSqreenApp')
    .constant('DATA_STATUS',{
        "ACTIVE":"data.status.active",
        "PASSIVE":"data.status.passive"
    })
    .constant('LANGUAGE',{
        "TURKISH":"languages.turkish",
        "ENGLISH":"languages.english"
    })
    .constant('SERVICE_TYPE',{
        "PLATFORM_161":"serviceType.platform161",
        "SISTEM_9":"serviceType.sistem9"
    })
    .constant('PLATFORM_TYPE',{
    "DSP":"platformType.dsp",
    "SSP":"platformType.ssp"
    }).constant('DEVICE_CONSTRAINT_TYPE',{
        "DYNAMIC_TIME_FILTER":"deviceConstraintType.dynamicTimeFilter"
    }).constant('DEVICE_CONSTRAINT_FILTER',{
        "INCLUDE":"deviceConstraintFilter.include",
        "EXCLUDE":"deviceConstraintFilter.exclude"
    }).constant('CAMPAIGN_CONSTRAINT_FILTER',{
        "BIGGER":"campaignConstraintFilter.bigger",
        "SMALLER":"campaignConstraintFilter.smaller",
        "EQUAL":"campaignConstraintFilter.equal",
        "BIGGER_EQUAL":"campaignConstraintFilter.biggerEqual",
        "SMALLER_EQUAL":"campaignConstraintFilter.smallerEqual"
    }).constant('CAMPAIGN_CONSTRAINT_TYPE',{
        "TEMPERATURE":"campaignConstraintType.temperature"
    });