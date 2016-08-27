package queenskitchen.in.farali.database;

public class DBUtils {

    /**
     * Integer Constants
     */
    public static final int DATABASE_VERSION = 3;

    /**
     * Database Name
     */
    public static final String DATABASE_NAME = "faraliapp.db";

    /**
     * Tables Constant Names
     */
    public static final String RECEIPE_TABLE = "recipes";

//


    /**
     * Columns Names
     */
    // SMS
    public static final String COLUMN_RECEIPE_ID = "recipes_id";
    public static final String COLUMN_RECEIPE_FAV = "recipes_fav";
    public static final String COLUMN_RECEIPE_TITLE = "recipes_title";
    public static final String COLUMN_RECEIPE_IMAGE = "recipes_image";
    public static final String COLUMN_RECEIPE_COOKINGTIME = "recipes_cookingtime";
    public static final String COLUMN_RECEIPE_PREPARATION = "recipes_preproperation";
    public static final String COLUMN_RECEIPE_SERVING = "recipes_servings";
    public static final String COLUMN_RECEIPE_LEVEL = "recipes_level";
    public static final String COLUMN_RECEIPE_INGREDIENTS = "recipes_ingredients";
    public static final String COLUMN_RECEIPE_CONTENT = "recipes_content";
    public static final String COLUMN_RECEIPE_DATE = "recipes_date";
    public static final String COLUMN_RECEIPE_LINK = "recipes_link";
    public static final String COLUMN_RECEIPE_KEYWORD = "recipes_keyword";
    public static final String COLUMN_RECEIPE_LANGUAGE = "recipes_language";
    public static final String COLUMN_RECEIPE_UPDATEDDATE = "recipes_updateddate";



    static final String DB_CREATE_AUDIO_RECORD_TABLE = "CREATE TABLE IF NOT EXISTS " + RECEIPE_TABLE + " (" + COLUMN_RECEIPE_ID + " INTEGER," + COLUMN_RECEIPE_FAV + " INTEGER ," + COLUMN_RECEIPE_TITLE
            + " TEXT," + COLUMN_RECEIPE_IMAGE + " TEXT," + COLUMN_RECEIPE_COOKINGTIME + " TEXT," + COLUMN_RECEIPE_PREPARATION + " TEXT," + COLUMN_RECEIPE_SERVING + " TEXT," + COLUMN_RECEIPE_LEVEL + " TEXT," + COLUMN_RECEIPE_INGREDIENTS + " TEXT," + COLUMN_RECEIPE_CONTENT + " TEXT," + COLUMN_RECEIPE_DATE + " TEXT," + COLUMN_RECEIPE_LINK + " TEXT," + COLUMN_RECEIPE_KEYWORD + " TEXT," + COLUMN_RECEIPE_LANGUAGE + " TEXT," + COLUMN_RECEIPE_UPDATEDDATE + " TEXT,PRIMARY KEY (" + COLUMN_RECEIPE_ID + "))";


}