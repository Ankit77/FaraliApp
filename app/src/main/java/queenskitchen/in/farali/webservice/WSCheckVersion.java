package queenskitchen.in.farali.webservice;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import queenskitchen.in.farali.common.Const;
import queenskitchen.in.farali.common.Utils;
import queenskitchen.in.farali.model.RecipesModel;

/**
 * Created by ANKIT on 10/3/2016.
 */
public class WSCheckVersion {
    private String message = "";
    private int success = 0;

    public WSCheckVersion(Context context) {
    }

    public String getMessage() {
        return message;
    }

    public int getSuccess() {
        return success;
    }

    public boolean executeWebservice(String version) {
        final String url = "http://queenskitchen.in/app/verchk.php?app=fa&ver=" + version;
        Log.e("WebserviceUrl", url);
        String str = WebService.getMethod(url);
        if (str.equalsIgnoreCase("\"Updated\"")) {
            return true;
        } else {
            return false;
        }
    }


}
