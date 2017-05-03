package pollub.edu.pl.kolokwium.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Dell on 2017-05-02.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public final static int DB_VERSION = 1;
    public final static String DB_NAME = "games_Db";
    public final static String TABLE_NAME = "games";
    public final static String ID = "_id";
    public final static String TITLE_COLUMN_NAME  = "title";
    public final static String KIND_COLUMN_NAME = "kind";
    public final static String YEAR_COLUMN_NAME = "year";
    public final static String AGE_COLUMN_NAME = "age";

    public final static String TABLE_CREATING_SCRIPT = "CREATE TABLE " + TABLE_NAME +
            "("+ ID +" integer primary key autoincrement, " +
            TITLE_COLUMN_NAME+" text not null,"+
            KIND_COLUMN_NAME+" text not null,"+
            YEAR_COLUMN_NAME+" text not null,"+
            AGE_COLUMN_NAME+" text not null);";
    private static final String TABLE_DELETING_SCRIPT = "DROP TABLE IF EXISTS "+TABLE_NAME;

    public DatabaseHelper(Context context)  {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATING_SCRIPT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
