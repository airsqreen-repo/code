package com.pusulait.airsqreen.config.constants;

import java.text.SimpleDateFormat;

/**
 * Application constants.
 */
public final class Constants {

    private Constants() {
    }

    public static final String SPRING_PROFILE_DEVELOPMENT = "dev";
    public static final String SPRING_PROFILE_PRODUCTION = "prod";
    public static final String SPRING_PROFILE_FAST = "fast";
    // Spring profile used when deploying with Spring Cloud (used when deploying to CloudFoundry)
    public static final String SPRING_PROFILE_CLOUD = "cloud";
    // Spring profile used when deploying to Heroku
    public static final String SPRING_PROFILE_HEROKU = "heroku";

    public static SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

    public static final String SYSTEM_ACCOUNT = "system";
    public static final String ANONYMOUS_ACCOUNT = "anonymous";
    public static final String PREFIX = "AIR_";

    public static final String FREQUENCY_MINUTE = "minutes";
    public static final String FREQUENCY_HOUR = "hours";
    public static final String FREQUENCY_DAY = "days";
    public static final String FREQUENCY_WEEK = "weeks";



    public static final String NAME_REGEX = "[a-zA-Z]";
    public static final String PASSWORD_REGEX = "^[A-Za-z\\s-]+$";

    public static final String TURKCE = "tr";
    public static final String ENGLISH = "en";

    public static final Long TL_ID = 1L;
    public static final String API_ACCESS_KEY = "API_ACCESS_KEY";


    public static final long ONE_DAY   = 1000 * 60 * 60 * 24;
    public static final long ONE_WEEK  = ONE_DAY * 7;
    public static final Integer GESTATION  = 280;

    public static final Integer GESTATION_MAX_WEEK  = 42;

    public static final String CDN_URL = "http://cdn.airsqreen.com/";

    public static final Integer PAGE_SIZE  = 100;

    public static final String URL_ADMIN = "/admin";
    public static final String URL_V1 = "/v1";

    public static final String URL_ADDRESS = "/address";
    public static final String URL_PROVINCE = "/provinces";
    public static final String URL_COUNTRY = "/countries";

    public static final String URL_SYSTEM_CONFIGURATION = "/systemConfigurations";
    public static final String URL_API = "/apis";


    public static final String URL_CITY = "/cities";
    public static final String URL_USER = "/users";
    public static final String URL_ACCOUNT = "/accounts";
    public static final String URL_SEARCH = "/search";

    public static final String URL_VIEW_COUNT = "/viewcounts";
    public static final String URL_CAMPAIGN = "/campaigns";
    public static final String URL_EVENT = "/events";



    public static final String URL_ROLE = "/roles";
    public static final String URL_RIGHT = "/rights";
    public static final String URL_ROLE_RIGHT = "/roleRights";
    public static final String URL_USER_ROLE = "/user/roles";
    public static final String URL_CARRIER= "/carriers";
    public static final String URL_FLIGHT_DATA_PROVIDER = "/flightDataProviders";
    public static final String URL_FLIGHT_AGENCY = "/flightAgencies";
    public static final String URL_DESTINATION = "/destinations";
    public static final String URL_CREDIT_CART_PROVIDER = "/creditCardProviders";

    public static final String URL_CURRENCY = "/currencies";
    public static final String URL_CURRENCY_RATE = "/currency/rates";




    public static final String API_URL_USER = "/api/admin/users";
    public static final String API_URL_ROLE= "/api/admin/roles";
    public static final String API_URL_ROLE_RIGHT= "/api/admin/roleRights";
    public static final String API_URL_ADMIN_ROLE = "/api/admin/roles";

    public static final String API_URL_CITY = "/api/admin/cities/";
    public static final String API_URL_COUNTRY = "/api/admin/countries/";
    public static final String API_URL_PROVINCE = "/api/admin/provinces";
    public static final String API_URL_USER_ROLE = "/api/admin/user/roles";
    public static final String API_URL_CARRIER= "/api/admin/carriers";
    public static final String API_URL_FLIGHT_DATA_PROVIDER = "/api/admin/flightDataProviders";
    public static final String API_URL_FLIGHT_AGENCY = "/api/admin/flightAgencies";
    public static final String API_URL_DESTINATION = "/api/admin/destinations";
    public static final String API_URL_CREDIT_CART_PROVIDER = "/api/admin/creditCardProviders";
    public static final String API_URL_ACCOUNT = "/api/admin/accounts";

    public static final String API_URL_SYSTEM_CONFIGURATION= "/api/admin/systemConfigurations";
    public static final String API_URL_API= "/api/admin/apis";

    public static final String API_URL_CURRENCY = "/api/currencies";
    public static final String API_URL_CURRENCY_RATE = "/api/currency/rates";
    public static final String URL_LUGGAGE_CANCELLATION = "/luggageCancellations";
    public static final String API_URL_LUGGAGE_CANCELLATION= "/api/admin/luggageCancellations";
    public static final String PLATFORM_161 = "PLT161";
    public static final String SISTEM9_SUCCESS_RESULT = "Gönderildi";
}
