package queenskitchen.in.farali.common;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import queenskitchen.in.farali.R;

/**
 * Created by indianic on 25/08/16.
 */
public class Utils {

    public static String convertMilisToDate(long milis) {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(Const.DATEFORMATE);
        // Create a calendar object that will convert the date and time value in milliseconds to date.
        return formatter.format(milis);
    }

    public static String getCurrentTime() {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(Const.DATEFORMATE);
        // Create a calendar object that will convert the date and time value in milliseconds to date.
        return formatter.format(Calendar.getInstance().getTimeInMillis());
    }

    public static long convertDateStringtoUnixTimestamp(String dateString) {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(Const.DATEFORMATE);
        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Date date = null;
        try {
            date = formatter.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long unixTime = (long) date.getTime() / 1000;
        return unixTime;
    }

    public static String convertUnixTimestamptoDate(long milliSeconds) {
        milliSeconds = milliSeconds * 1000;
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(Const.DATEFORMATE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());

    }

    public static String decodeUnicode(String theString) {
        char aChar;
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len; ) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    // Read the xxxx
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = theString.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed   \\uxxxx   encoding.");
                        }

                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';
                    else if (aChar == 'n')
                        aChar = '\n';
                    else if (aChar == 'f')
                        aChar = '\f';
                    outBuffer.append(aChar);
                }
            } else
                outBuffer.append(aChar);
        }
        return outBuffer.toString();
    }

    public static void shareApplication(Context context) {
        String message = "You must try this Farali Recipe Application ones, Download Farali Recipes now from Playstore : https://play.google.com/store/apps/details?id=com.nazara.chachapuzzle&hl=en";
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,
                message);
        sendIntent.setType("text/plain");
        context.startActivity(sendIntent);
    }



}
