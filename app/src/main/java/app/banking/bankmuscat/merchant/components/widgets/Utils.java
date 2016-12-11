package app.banking.bankmuscat.merchant.components.widgets;

import android.content.res.Resources;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by bruce on 14-11-6.
 */
public class Utils {
    public static float dp2px(Resources resources, float dp) {
        final float scale = resources.getDisplayMetrics().density;
        return  dp * scale + 0.5f;
    }

    public static float sp2px(Resources resources, float sp){
        final float scale = resources.getDisplayMetrics().scaledDensity;
        return sp * scale;
    }

    public static boolean checkEmailPattern(String email) {

       /* String regEx =
                "^(([w-]+.)+[w-]+|([a-zA-Z]{1}|[w-]{2,}))@"
                        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9]).([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])."
                        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9]).([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        +"([a-zA-Z]+[w-]+.)+[a-zA-Z]{2,4})$";



        Matcher matcher = Pattern.compile(regEx).matcher(email);

        return  matcher.matches();*/

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }

    public static boolean checkDobPattern(String dob) {
        java.util.Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy");
            date = sdf.parse(dob);
            if (!dob.equals(sdf.format(date))) {
                date = null;
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        if (date == null) {
            return true;
        } else {
            return false;
        }
    }
}
