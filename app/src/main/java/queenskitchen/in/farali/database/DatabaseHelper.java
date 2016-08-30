package queenskitchen.in.farali.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import queenskitchen.in.farali.common.Const;
import queenskitchen.in.farali.common.Utils;
import queenskitchen.in.farali.model.RecipesModel;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = DatabaseHelper.class.getSimpleName();
    private Context context;
    private SQLiteDatabase database;

    //
//    /**
//     * Constructor
//     * *
//     */
    public DatabaseHelper(Context context) {
        super(context, DBUtils.DATABASE_NAME, null, DBUtils.DATABASE_VERSION);
        this.context = context;
    }

    //
//    /**
//     * Create database tables if it does not exists.
//     * *
//     */
//    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL(DBUtils.DB_CREATE_AUDIO_RECORD_TABLE);
        this.database = db;
    }

    //
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //
//    /**
//     * Open Databases
//     * *
//     */
    public void openDataBase() throws SQLException {
        database = this.getWritableDatabase();
    }

    //
    @Override
    public synchronized void close() {
        // if (database != null && database.isOpen())
        // database.close();
        // super.close();
    }

    //--------------------------------SMS START------------------------------------


    public void insertRecipeBulk(ArrayList<RecipesModel> recipelist) {
        if (!database.isOpen()) {
            openDataBase();
        }
        try {
            database.beginTransaction();

            for (int i = 0; i < recipelist.size(); i++) {
                ContentValues values = new ContentValues();
                RecipesModel recipesModel = recipelist.get(i);
                values.put(DBUtils.COLUMN_RECEIPE_TITLE, recipesModel.getTitle());
                values.put(DBUtils.COLUMN_RECEIPE_IMAGE, recipesModel.getImage());
                values.put(DBUtils.COLUMN_RECEIPE_FAV, Const.UNFAV);
                values.put(DBUtils.COLUMN_RECEIPE_COOKINGTIME, recipesModel.getCookingtime());
                values.put(DBUtils.COLUMN_RECEIPE_PREPARATION, recipesModel.getPreproperation());
                values.put(DBUtils.COLUMN_RECEIPE_LEVEL, recipesModel.getLevel());
                values.put(DBUtils.COLUMN_RECEIPE_INGREDIENTS, recipesModel.getIngredients());
                values.put(DBUtils.COLUMN_RECEIPE_CONTENT, recipesModel.getContent());
                values.put(DBUtils.COLUMN_RECEIPE_DATE, recipesModel.getDate());
                values.put(DBUtils.COLUMN_RECEIPE_LINK, recipesModel.getLink());
                values.put(DBUtils.COLUMN_RECEIPE_KEYWORD, recipesModel.getKeyword());
                values.put(DBUtils.COLUMN_RECEIPE_SERVING, recipesModel.getServings());
                values.put(DBUtils.COLUMN_RECEIPE_LANGUAGE, recipesModel.getLanguage());
                values.put(DBUtils.COLUMN_RECEIPE_UPDATEDDATE, Utils.getCurrentTime());
                database.insert(DBUtils.RECEIPE_TABLE, null, values);
            }
            database.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            database.endTransaction();
            close();
            SQLiteDatabase.releaseMemory();
        }
    }


    /**
     * get Maximum date of SMS
     *
     * @return
     */
    public String getMaxDateofRecipe() {
        String maxdate = "";
        if (!database.isOpen()) {
            openDataBase();
        }
        Cursor cursor = null;
        try {
            String query = "select " + DBUtils.COLUMN_RECEIPE_UPDATEDDATE + " from " + DBUtils.RECEIPE_TABLE + " order by " + DBUtils.COLUMN_RECEIPE_UPDATEDDATE + " desc limit 1";
            cursor = database.rawQuery(query, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                maxdate = cursor.getString(cursor.getColumnIndex(DBUtils.COLUMN_RECEIPE_UPDATEDDATE));
            }
        } catch (Exception e) {
            e.printStackTrace();
            maxdate = "";
        } finally {

            close();
            if (cursor != null) {
                cursor.close();
                SQLiteDatabase.releaseMemory();
            }
        }
        return maxdate;

    }


    public ArrayList<RecipesModel> getRecipeList(String language) {
        final ArrayList<RecipesModel> list = new ArrayList<RecipesModel>();
        if (!database.isOpen()) {
            openDataBase();
        }
        Cursor cursor = null;
        try {
            String query = "Select * from " + DBUtils.RECEIPE_TABLE + " where " + DBUtils.COLUMN_RECEIPE_LANGUAGE + " = '" + language + "'";
            cursor = database.rawQuery(query, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                RecipesModel model = null;
                for (int i = 0; i < cursor.getCount(); i++) {
                    model = new RecipesModel();
                    model.setId(cursor.getString(cursor.getColumnIndex(DBUtils.COLUMN_RECEIPE_ID)));
                    model.setTitle(cursor.getString(cursor.getColumnIndex(DBUtils.COLUMN_RECEIPE_TITLE)));
                    model.setImage(cursor.getString(cursor.getColumnIndex(DBUtils.COLUMN_RECEIPE_IMAGE)));
                    model.setServings(cursor.getString(cursor.getColumnIndex(DBUtils.COLUMN_RECEIPE_SERVING)));
                    model.setCookingtime(cursor.getString(cursor.getColumnIndex(DBUtils.COLUMN_RECEIPE_COOKINGTIME)));
                    model.setPreproperation(cursor.getString(cursor.getColumnIndex(DBUtils.COLUMN_RECEIPE_PREPARATION)));
                    model.setLevel(cursor.getString(cursor.getColumnIndex(DBUtils.COLUMN_RECEIPE_LEVEL)));
                    model.setIngredients(cursor.getString(cursor.getColumnIndex(DBUtils.COLUMN_RECEIPE_INGREDIENTS)));
                    model.setContent(cursor.getString(cursor.getColumnIndex(DBUtils.COLUMN_RECEIPE_CONTENT)));
                    model.setDate(cursor.getString(cursor.getColumnIndex(DBUtils.COLUMN_RECEIPE_DATE)));
                    model.setLink(cursor.getString(cursor.getColumnIndex(DBUtils.COLUMN_RECEIPE_LINK)));
                    model.setKeyword(cursor.getString(cursor.getColumnIndex(DBUtils.COLUMN_RECEIPE_KEYWORD)));
                    model.setIsfav(cursor.getInt(cursor.getColumnIndex(DBUtils.COLUMN_RECEIPE_FAV)));
                    model.setLanguage(cursor.getString(cursor.getColumnIndex(DBUtils.COLUMN_RECEIPE_LANGUAGE)));

                    list.add(model);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            close();
            if (cursor != null) {
                cursor.close();
                SQLiteDatabase.releaseMemory();
            }
        }
        return list;
    }


    public ArrayList<RecipesModel> getFavRecipeList() {
        final ArrayList<RecipesModel> list = new ArrayList<RecipesModel>();
        if (!database.isOpen()) {
            openDataBase();
        }
        Cursor cursor = null;
        try {
            String query = "Select * from " + DBUtils.RECEIPE_TABLE + " where " + DBUtils.COLUMN_RECEIPE_FAV + " = " + Const.FAV;
            cursor = database.rawQuery(query, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                RecipesModel model = null;
                for (int i = 0; i < cursor.getCount(); i++) {
                    model = new RecipesModel();
                    model.setId(cursor.getString(cursor.getColumnIndex(DBUtils.COLUMN_RECEIPE_ID)));
                    model.setTitle(cursor.getString(cursor.getColumnIndex(DBUtils.COLUMN_RECEIPE_TITLE)));
                    model.setImage(cursor.getString(cursor.getColumnIndex(DBUtils.COLUMN_RECEIPE_IMAGE)));
                    model.setServings(cursor.getString(cursor.getColumnIndex(DBUtils.COLUMN_RECEIPE_SERVING)));
                    model.setCookingtime(cursor.getString(cursor.getColumnIndex(DBUtils.COLUMN_RECEIPE_COOKINGTIME)));
                    model.setPreproperation(cursor.getString(cursor.getColumnIndex(DBUtils.COLUMN_RECEIPE_PREPARATION)));
                    model.setLevel(cursor.getString(cursor.getColumnIndex(DBUtils.COLUMN_RECEIPE_LEVEL)));
                    model.setIngredients(cursor.getString(cursor.getColumnIndex(DBUtils.COLUMN_RECEIPE_INGREDIENTS)));
                    model.setContent(cursor.getString(cursor.getColumnIndex(DBUtils.COLUMN_RECEIPE_CONTENT)));
                    model.setDate(cursor.getString(cursor.getColumnIndex(DBUtils.COLUMN_RECEIPE_DATE)));
                    model.setLink(cursor.getString(cursor.getColumnIndex(DBUtils.COLUMN_RECEIPE_LINK)));
                    model.setKeyword(cursor.getString(cursor.getColumnIndex(DBUtils.COLUMN_RECEIPE_KEYWORD)));
                    model.setIsfav(cursor.getInt(cursor.getColumnIndex(DBUtils.COLUMN_RECEIPE_FAV)));
                    model.setLanguage(cursor.getString(cursor.getColumnIndex(DBUtils.COLUMN_RECEIPE_LANGUAGE)));

                    list.add(model);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            close();
            if (cursor != null) {
                cursor.close();
                SQLiteDatabase.releaseMemory();
            }
        }
        return list;
    }

    public ArrayList<RecipesModel> getRecipeListUsingSearch(String language, String keyword) {
        final ArrayList<RecipesModel> list = new ArrayList<RecipesModel>();
        if (!database.isOpen()) {
            openDataBase();
        }
        Cursor cursor = null;
        try {
            String query = "SELECT * FROM recipes where recipes_keyword  LIKE '%" + keyword + "%' and " + DBUtils.COLUMN_RECEIPE_LANGUAGE + " = '" + language + "'";
            cursor = database.rawQuery(query, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                RecipesModel model = null;
                for (int i = 0; i < cursor.getCount(); i++) {
                    model = new RecipesModel();
                    model.setId(cursor.getString(cursor.getColumnIndex(DBUtils.COLUMN_RECEIPE_ID)));
                    model.setTitle(cursor.getString(cursor.getColumnIndex(DBUtils.COLUMN_RECEIPE_TITLE)));
                    model.setImage(cursor.getString(cursor.getColumnIndex(DBUtils.COLUMN_RECEIPE_IMAGE)));
                    model.setServings(cursor.getString(cursor.getColumnIndex(DBUtils.COLUMN_RECEIPE_SERVING)));
                    model.setCookingtime(cursor.getString(cursor.getColumnIndex(DBUtils.COLUMN_RECEIPE_COOKINGTIME)));
                    model.setPreproperation(cursor.getString(cursor.getColumnIndex(DBUtils.COLUMN_RECEIPE_PREPARATION)));
                    model.setLevel(cursor.getString(cursor.getColumnIndex(DBUtils.COLUMN_RECEIPE_LEVEL)));
                    model.setIngredients(cursor.getString(cursor.getColumnIndex(DBUtils.COLUMN_RECEIPE_INGREDIENTS)));
                    model.setContent(cursor.getString(cursor.getColumnIndex(DBUtils.COLUMN_RECEIPE_CONTENT)));
                    model.setDate(cursor.getString(cursor.getColumnIndex(DBUtils.COLUMN_RECEIPE_DATE)));
                    model.setLink(cursor.getString(cursor.getColumnIndex(DBUtils.COLUMN_RECEIPE_LINK)));
                    model.setKeyword(cursor.getString(cursor.getColumnIndex(DBUtils.COLUMN_RECEIPE_KEYWORD)));
                    model.setIsfav(cursor.getInt(cursor.getColumnIndex(DBUtils.COLUMN_RECEIPE_FAV)));
                    model.setLanguage(cursor.getString(cursor.getColumnIndex(DBUtils.COLUMN_RECEIPE_LANGUAGE)));

                    list.add(model);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            close();
            if (cursor != null) {
                cursor.close();
                SQLiteDatabase.releaseMemory();
            }
        }
        return list;
    }

    public ArrayList<RecipesModel> getFavRecipeListUsingSearch(String keyword) {
        final ArrayList<RecipesModel> list = new ArrayList<RecipesModel>();
        if (!database.isOpen()) {
            openDataBase();
        }
        Cursor cursor = null;
        try {
            String query = "SELECT * FROM recipes where recipes_keyword  LIKE '%" + keyword + "%' and " + DBUtils.COLUMN_RECEIPE_FAV + " = " + Const.FAV;
            cursor = database.rawQuery(query, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                RecipesModel model = null;
                for (int i = 0; i < cursor.getCount(); i++) {
                    model = new RecipesModel();
                    model.setId(cursor.getString(cursor.getColumnIndex(DBUtils.COLUMN_RECEIPE_ID)));
                    model.setTitle(cursor.getString(cursor.getColumnIndex(DBUtils.COLUMN_RECEIPE_TITLE)));
                    model.setImage(cursor.getString(cursor.getColumnIndex(DBUtils.COLUMN_RECEIPE_IMAGE)));
                    model.setServings(cursor.getString(cursor.getColumnIndex(DBUtils.COLUMN_RECEIPE_SERVING)));
                    model.setCookingtime(cursor.getString(cursor.getColumnIndex(DBUtils.COLUMN_RECEIPE_COOKINGTIME)));
                    model.setPreproperation(cursor.getString(cursor.getColumnIndex(DBUtils.COLUMN_RECEIPE_PREPARATION)));
                    model.setLevel(cursor.getString(cursor.getColumnIndex(DBUtils.COLUMN_RECEIPE_LEVEL)));
                    model.setIngredients(cursor.getString(cursor.getColumnIndex(DBUtils.COLUMN_RECEIPE_INGREDIENTS)));
                    model.setContent(cursor.getString(cursor.getColumnIndex(DBUtils.COLUMN_RECEIPE_CONTENT)));
                    model.setDate(cursor.getString(cursor.getColumnIndex(DBUtils.COLUMN_RECEIPE_DATE)));
                    model.setLink(cursor.getString(cursor.getColumnIndex(DBUtils.COLUMN_RECEIPE_LINK)));
                    model.setKeyword(cursor.getString(cursor.getColumnIndex(DBUtils.COLUMN_RECEIPE_KEYWORD)));
                    model.setIsfav(cursor.getInt(cursor.getColumnIndex(DBUtils.COLUMN_RECEIPE_FAV)));
                    model.setLanguage(cursor.getString(cursor.getColumnIndex(DBUtils.COLUMN_RECEIPE_LANGUAGE)));

                    list.add(model);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            close();
            if (cursor != null) {
                cursor.close();
                SQLiteDatabase.releaseMemory();
            }
        }
        return list;
    }

    public long updateContact(String userid, int isfav) {
        long i = 0;
        if (!database.isOpen()) {
            openDataBase();
        }
        try {
            // database.beginTransaction();
            ContentValues values = new ContentValues();
            values.put(DBUtils.COLUMN_RECEIPE_FAV, isfav);
            i = database.update(DBUtils.RECEIPE_TABLE, values, DBUtils.COLUMN_RECEIPE_ID + "=?", new String[]{userid});

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            close();
            SQLiteDatabase.releaseMemory();
        }
        return i;
    }

    public RecipesModel getRecipeDetail(String id) {
        RecipesModel recipesModel = null;
        if (!database.isOpen()) {
            openDataBase();
        }
        Cursor cursor = null;
        try {
            cursor = database.query(DBUtils.RECEIPE_TABLE, new String[]{"*"}, DBUtils.COLUMN_RECEIPE_ID + "=?", new String[]{id}, null, null, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                recipesModel = new RecipesModel();
                recipesModel.setId(cursor.getString(cursor.getColumnIndex(DBUtils.COLUMN_RECEIPE_ID)));
                recipesModel.setTitle(cursor.getString(cursor.getColumnIndex(DBUtils.COLUMN_RECEIPE_TITLE)));
                recipesModel.setServings(cursor.getString(cursor.getColumnIndex(DBUtils.COLUMN_RECEIPE_SERVING)));
                recipesModel.setImage(cursor.getString(cursor.getColumnIndex(DBUtils.COLUMN_RECEIPE_IMAGE)));
                recipesModel.setCookingtime(cursor.getString(cursor.getColumnIndex(DBUtils.COLUMN_RECEIPE_COOKINGTIME)));
                recipesModel.setPreproperation(cursor.getString(cursor.getColumnIndex(DBUtils.COLUMN_RECEIPE_PREPARATION)));
                recipesModel.setLevel(cursor.getString(cursor.getColumnIndex(DBUtils.COLUMN_RECEIPE_LEVEL)));
                recipesModel.setIngredients(cursor.getString(cursor.getColumnIndex(DBUtils.COLUMN_RECEIPE_INGREDIENTS)));
                recipesModel.setContent(cursor.getString(cursor.getColumnIndex(DBUtils.COLUMN_RECEIPE_CONTENT)));
                recipesModel.setDate(cursor.getString(cursor.getColumnIndex(DBUtils.COLUMN_RECEIPE_DATE)));
                recipesModel.setLink(cursor.getString(cursor.getColumnIndex(DBUtils.COLUMN_RECEIPE_LINK)));
                recipesModel.setKeyword(cursor.getString(cursor.getColumnIndex(DBUtils.COLUMN_RECEIPE_KEYWORD)));
                recipesModel.setIsfav(cursor.getInt(cursor.getColumnIndex(DBUtils.COLUMN_RECEIPE_FAV)));
                recipesModel.setLanguage(cursor.getString(cursor.getColumnIndex(DBUtils.COLUMN_RECEIPE_LANGUAGE)));
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {

            close();
            if (cursor != null) {
                cursor.close();
                SQLiteDatabase.releaseMemory();
            }
        }
        return recipesModel;

    }

}