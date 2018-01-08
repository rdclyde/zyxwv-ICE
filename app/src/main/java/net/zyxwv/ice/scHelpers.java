package net.zyxwv.ice;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by clyde on 1/1/2018.
 */
public class scHelpers {

    public static String GetDateCurrentFormatted(){
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy hh:mm a");
        String formattedDate = df.format(c.getTime());

        return formattedDate;
    }
}
