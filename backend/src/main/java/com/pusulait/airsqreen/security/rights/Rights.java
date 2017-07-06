package com.pusulait.airsqreen.security.rights;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by benan on 7/30/2015.
 */


public final class Rights {

    public static final String ADMIN = "ADMIN";
    public static final String COUNTRY_CREATE =   "COUNTRY_CREATE";

    public static List<String> getRightList() {

        ArrayList<String> rightList = new ArrayList<String>();

        rightList.add(ADMIN);
        return rightList;
    }


}
