package queenskitchen.in.farali.activities;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.ArrayList;

import queenskitchen.in.farali.FaraliApp;
import queenskitchen.in.farali.R;
import queenskitchen.in.farali.common.Utils;
import queenskitchen.in.farali.gcm.RegistrationIntentService;
import queenskitchen.in.farali.model.RecipesModel;
import queenskitchen.in.farali.webservice.WSGetRecipe;
import queenskitchen.in.farali.webservice.WebService;

/**
 * Created by ANKIT on 8/20/2016.
 */
public class SplashActivity extends AppCompatActivity {
    private FaraliApp faraliApp;
    private AsyncLoadData asyncLoadData;
    private ProgressBar progressBar;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "SplashScreenActivity";
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        faraliApp = (FaraliApp) getApplicationContext();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        if (!faraliApp.getSharedPreferences().getBoolean("ISDEVICEREGISTER", false)) {
            registerToGCM();
        }


        if (WebService.isNetworkAvailable(SplashActivity.this)) {

            asyncLoadData = new AsyncLoadData();
            asyncLoadData.execute();
        }

    }

    private class AsyncLoadData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            String lastUpdateDate = faraliApp.getDatabaseHelper().getMaxDateofRecipe();
            long date = 0000000;
            if (!TextUtils.isEmpty(lastUpdateDate)) {
                date = Utils.convertDateStringtoUnixTimestamp(lastUpdateDate);
            }
            WSGetRecipe wsGetRecipe = new WSGetRecipe(SplashActivity.this);
            ArrayList<RecipesModel> recipeList = wsGetRecipe.executeWebservice("e", String.valueOf(date));
            if (recipeList != null && recipeList.size() > 0) {
                faraliApp.getDatabaseHelper().insertRecipeBulk(recipeList);
            }
            WSGetRecipe wsGetRecipe1 = new WSGetRecipe(SplashActivity.this);
            ArrayList<RecipesModel> recipeList1 = wsGetRecipe1.executeWebservice("g", String.valueOf(date));
            if (recipeList != null && recipeList.size() > 0) {
                faraliApp.getDatabaseHelper().insertRecipeBulk(recipeList1);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar.setVisibility(View.GONE);
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    /**
     * Register to GCM
     */
    public void registerToGCM() {
        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);

        }
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            }
            return false;
        }
        return true;
    }
}
