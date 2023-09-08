package com.cultofgames.AllIndiaGovernmentJobs.AppInit;

import java.util.regex.Pattern;

public class Constants {

    public static final String INDIA_TIME_ZONE_ID = "asia/calcutta";
    public static final String NETWORK_INDIA_CODE = "in";

    public static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[@#$%^&+=])" +     // at least 1 special character
                    "(?=\\S+$)" +            // no white spaces
                    ".{4,}" +                // at least 4 characters
                    "$");
}
