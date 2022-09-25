package util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeUtil {
    public static String GetNowDate(String formate){
        String temp_str = "";
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(formate);
        temp_str = sdf.format(dt);
        return temp_str;
    }
}
