package queenskitchen.in.farali;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import queenskitchen.in.farali.database.DatabaseHelper;


/**
 * Created by indianic on 25/08/16.
 */
public class FaraliApp extends Application {
    private SharedPreferences sharedPreferences;
    private DatabaseHelper databaseHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        sharedPreferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        databaseHelper = new DatabaseHelper(getApplicationContext());
        databaseHelper.openDataBase();

    }


    public DatabaseHelper getDatabaseHelper() {
        return databaseHelper;
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

}
