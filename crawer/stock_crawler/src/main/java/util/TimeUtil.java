package util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeUtil {
    public static String TimeStampToDate(String timStampString,String formats){
        Long timStamp = Long.parseLong(timStampString);
        String date = new SimpleDateFormat(formats,Locale.CHINA).format(new Date(timStamp));
        return date;

    }
}
