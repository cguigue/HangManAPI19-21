package a02.cguigue.heritage.cguigue_b51_a02_hangman;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 2015-11-08.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 7;

    // Database Name
    private static final String DATABASE_NAME = "HangmanDatabase";

    // table names
    private static final String TABLE_WORDS = "words";
    private static final String TABLE_USERS = "users";
    private static final String TABLE_SCORES = "scores";

    //Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "word";
    private static final String KEY_USER = "username";
    private static final String KEY_SCORE = "score";


    public DatabaseHandler(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db)
    {
        // Category table create query
        String CREATE_WORDS_TABLE = "CREATE TABLE " + TABLE_WORDS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT)";
        db.execSQL(CREATE_WORDS_TABLE);

        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_USER + " TEXT)";
        db.execSQL(CREATE_USERS_TABLE);

        String CREATE_SCORES_TABLE = "CREATE TABLE " + TABLE_SCORES + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_USER + " TEXT," + KEY_SCORE + " TEXT)";
        db.execSQL(CREATE_SCORES_TABLE);
    }//onCreate(DataBase)

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORDS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORES);
        // Create tables again
        onCreate(db);
    }//onUpgrade

    public void reCreateWords()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORDS);

        String CREATE_WORDS_TABLE = "CREATE TABLE " + TABLE_WORDS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT)";
        db.execSQL(CREATE_WORDS_TABLE);
    }

    public void InsertWord(String label){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, label);

        // Inserting Row
        db.insert(TABLE_WORDS, null, values);
        db.close(); // Closing database connection
    }

    public void InsertScore(String user, String score)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USER, user);
        values.put(KEY_SCORE, score);

        // Inserting Row
        db.insert(TABLE_SCORES, null, values);
        db.close(); // Closing database connection
    }

    public void InsertUser(String user)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USER, user);

        // Inserting Row
        db.insert(TABLE_USERS, null, values);
        db.close(); // Closing database connection
    }

    public List<String> getWords(){
        List<String> easy = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_WORDS + " order by " + KEY_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst())
        {
            do {
                easy.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning easy words
        return easy;
    }

    public List<String> getScores(){
        List<String> scores = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_SCORES + " order by " + KEY_SCORE + " DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst())
        {
            do {
                scores.add(cursor.getString(1) + " - " + cursor.getString(2));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning easy words
        return scores;
    }

    public List<String> getUsers(){
        List<String> easy = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_USERS + " order by " + KEY_USER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst())
        {
            do {
                easy.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning easy words
        return easy;
    }
}
