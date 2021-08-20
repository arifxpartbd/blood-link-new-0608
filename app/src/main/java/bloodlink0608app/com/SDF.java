package bloodlink0608app.com;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SDF {

    // return Date
    public static String getDate(long dateInSeconds){

        Date date = new Date(dateInSeconds * 1000);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, yyyy");
        String dateString = dateFormat.format(date);
        Log.e("getDate : ", dateString);
        return dateString;
    }
}
