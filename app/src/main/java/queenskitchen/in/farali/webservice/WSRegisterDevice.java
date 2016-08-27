package queenskitchen.in.farali.webservice;

import android.content.Context;
import android.text.TextUtils;

import org.json.JSONObject;

import java.util.ArrayList;

import queenskitchen.in.farali.common.Const;
import queenskitchen.in.farali.model.RecipesModel;

/**
 * Created by indianic on 26/08/16.
 */
public class WSRegisterDevice {
    private String message = "";
    private int success = 0;

    public WSRegisterDevice(Context context) {
    }

    public String getMessage() {
        return message;
    }

    public int getSuccess() {
        return success;
    }

    public boolean executeWebservice(String devicetoken) {
        final String url = Const.NOTIFICATION_URL + "db=fa&id=" + devicetoken;
        return parseJSONResponse(WebService.getMethod(url));
    }


    public boolean parseJSONResponse(final String response) {
        ArrayList<RecipesModel> arrayList = new ArrayList<>();
        try {
            if (!TextUtils.isEmpty(response)) {

                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getString("result").equalsIgnoreCase("1")) {
                    return true;
                } else {
                    return false;
                }


            } else {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

}
