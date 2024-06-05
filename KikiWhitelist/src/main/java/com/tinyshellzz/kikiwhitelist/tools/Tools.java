package com.tinyshellzz.kikiwhitelist.tools;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Tools {
    public static String get_current_time() {
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String currentDateTime = dateFormat.format(currentDate);

        return currentDateTime;
    }
}
