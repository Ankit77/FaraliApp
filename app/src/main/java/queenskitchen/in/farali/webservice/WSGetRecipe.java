package queenskitchen.in.farali.webservice;

import android.content.Context;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import queenskitchen.in.farali.common.Const;
import queenskitchen.in.farali.common.Utils;
import queenskitchen.in.farali.model.RecipesModel;


public class WSGetRecipe {


    private String message = "";
    private int success = 0;

    public WSGetRecipe(Context context) {
    }

    public String getMessage() {
        return message;
    }

    public int getSuccess() {
        return success;
    }

    public ArrayList<RecipesModel> executeWebservice(String language, String time) {
        final String url = Const.BASE_URL + "dt=" + time + "&l=" + language;
        return parseJSONResponse(WebService.getMethod(url), language);
    }


    public ArrayList<RecipesModel> parseJSONResponse( String response, String language) {
        ArrayList<RecipesModel> arrayList = new ArrayList<>();
        try {
            if (!TextUtils.isEmpty(response)) {
                //response = response.replaceAll("\\\\", "");
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.optJSONArray("info");
                if (jsonArray != null && jsonArray.length() > 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        RecipesModel recipesModel = new RecipesModel();
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        recipesModel.setTitle(jsonObject1.getString("title"));
                        recipesModel.setImage(jsonObject1.getString("image"));
                        recipesModel.setCookingtime(jsonObject1.getString("cookingtime"));
                        recipesModel.setPreproperation(jsonObject1.getString("preproperation"));
                        recipesModel.setServings(jsonObject1.getString("servings"));
                        recipesModel.setLevel(jsonObject1.getString("level of cooking"));
                        recipesModel.setIngredients(jsonObject1.getString("ingredients"));
                        recipesModel.setContent(jsonObject1.getString("content"));
                        if (!TextUtils.isEmpty(jsonObject1.getString("date"))) {
                            recipesModel.setDate(Utils.convertUnixTimestamptoDate(Long.parseLong(jsonObject1.getString("date"))));
                        } else {
                            recipesModel.setDate(Utils.convertUnixTimestamptoDate(Calendar.getInstance().getTimeInMillis() / 1000));
                        }

                        recipesModel.setLink(jsonObject1.getString("link"));
                        recipesModel.setKeyword(jsonObject1.getString("keyword"));
                        recipesModel.setLanguage(language);
                        arrayList.add(recipesModel);

                    }
                    return arrayList;
                } else {
                    return null;
                }

            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
    }

}
