package com.jaydeemanuel.finalproj.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    //The Android's default system path of your application database.
    //Don't forget to put your namespace
    private static String DB_PATH = "/data/data/com.jaydeemanuel.finalproj/databases/";

    private static String DB_NAME = "Vaccination.db";

    private SQLiteDatabase myDatabase;

    private final Context myContext;

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     *
     * @param context
     */
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.myContext = context;
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.disableWriteAheadLogging();
    }


    /**
     * Creates a empty database on the system and rewrites it with your own database.
     */
    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();

        if (dbExist) {
            //do nothing - database already exist
            Log.i("DatabaseHelper", "DB is existing. NOT COPYING");
        } else {
            //By calling this method an empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                Log.e("DatabaseHelper", e.getMessage());
                throw new Error("Error copying database");

            }
        }

    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     *
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase() {

        SQLiteDatabase checkDB = null;

        try {
            String myPath = DB_PATH + DB_NAME;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                File dbFile = myContext.getDatabasePath(DB_NAME);
                myPath = dbFile.getPath();
                Log.i("DatabaseHelper", "here1: " + myPath);

            } else {
                myPath = DB_PATH + DB_NAME;
                Log.i("DatabaseHelper", "here2: " + myPath);
            }
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE | SQLiteDatabase.NO_LOCALIZED_COLLATORS);

        } catch (SQLiteException e) {

            //database does't exist yet.
            Log.e("DatabaseHelper", e.getLocalizedMessage());

        }

        if (checkDB != null) {

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     */
    private void copyDataBase() throws IOException {
        Log.i("DatabaseHelper", "DB is not existing. COPYING");
        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db

        String outFileName = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    @Override
    public synchronized void close() {
        if (myDatabase != null)
            myDatabase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



    public void updateFavorite(int countryID, int value){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("favorite", value);
        try {
            db.update("tblCountries", cv, "countryID = ?", new String[]{"" + countryID});
        }catch (Exception e){
            Log.e("DatabaseHelper", "" + e.getLocalizedMessage());
        }
    }

    public List<NoteModel> getAllNotes(){
        SQLiteDatabase db = this.getWritableDatabase();
        List<NoteModel> data = new ArrayList<>();

        Cursor cursor;

        try{
            cursor = db.query("tblNotes", null, null, null, null, null,null);
            while(cursor.moveToNext()){
                NoteModel note = new NoteModel(cursor.getInt(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3), cursor.getString(4));
                data.add(note);
            }
            Log.i("DatabaseHelper", "" + data);
        }catch(Exception e){
            Log.e("DatabaseHelper", "" + e.getLocalizedMessage());
        }

        return data;
    }

    public NoteModel getNote(int noteID){
        SQLiteDatabase db = this.getWritableDatabase();
        NoteModel note = null;

        Cursor cursor;

        try{
            cursor = db.query("tblNotes", null, "noteID = ?", new String[]{"" + noteID}, null, null,null);
            cursor.moveToFirst();
            note = new NoteModel(cursor.getInt(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3), cursor.getString(4));
            Log.i("DatabaseHelper", "" + note);
        }catch(Exception e){
            Log.e("DatabaseHelper", "" + e.getLocalizedMessage());
        }
        return note;
    }

    public int updateNote(int noteID, String title, String desc, String dates, String doses){
        int result = -1;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("fname", title);
        cv.put("lname", desc);
        cv.put("dates", dates);
        cv.put("doses", doses);
        try {
            result = db.update("tblNotes", cv, "noteID = ?", new String[]{"" + noteID});
        }catch (Exception e){
            Log.e("DatabaseHelper", "" + e.getLocalizedMessage());
        }
        return result;
    }
    public long addNote(String title, String desc, String dates, String doses){
        long result = -1;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("fname", title);
        cv.put("lname", desc);
        cv.put("dates", dates);
        cv.put("doses", doses);
        result = db.insert("tblNotes", null, cv);
        return result;
    }
    public void deleteNote(int noteID){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        db.delete("tblNotes", "noteID = ?", new String[]{"" + noteID});
    }
}
