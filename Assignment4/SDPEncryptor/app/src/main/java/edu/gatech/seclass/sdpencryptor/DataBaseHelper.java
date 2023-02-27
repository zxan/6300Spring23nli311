package edu.gatech.seclass.sdpencryptor;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String ASSIGNMENT_4_TABLE = "ASSIGNMENT4_TABLE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_ENTRY_TXT = "ENTRY_TXT";
    public static final String COLUMN_ARG_1 = "ARG1";
    public static final String COLUMN_ARG_2 = "ARG2";
    public static final String COLUMN_ENCRYPTED_TXT = "ENCRYPTED_TXT";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "assignment4data.db", null, 1);
    }

    //first time the database is run
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + ASSIGNMENT_4_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_ENTRY_TXT + " TEXT, " + COLUMN_ARG_1 + " INT, " + COLUMN_ARG_2 + " INT, " + COLUMN_ENCRYPTED_TXT + " TEXT)";

        db.execSQL(createTableStatement);
    }

    //whenever the database version changes this is called
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }

    public boolean addOne(SdpModel sdpModel)  {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ENTRY_TXT, sdpModel.getMyString());
        cv.put(COLUMN_ARG_1, sdpModel.getArg1());
        cv.put(COLUMN_ARG_2,sdpModel.getArg2());
        cv.put(COLUMN_ENCRYPTED_TXT, sdpModel.getEncrypted());

        long insert = db.insert(ASSIGNMENT_4_TABLE, null, cv);
        if (insert == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    public List<SdpModel> getEveryone() {
        List<SdpModel> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM " + ASSIGNMENT_4_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()){
            do {
                int entryID = cursor.getInt(0);
                String text = cursor.getString(1);
                int arg1 = cursor.getInt(2);
                int arg2 = cursor.getInt(3);
                String text2 = cursor.getString(4);

                SdpModel newText = new SdpModel(text, arg1, arg2, text2);
                returnList.add(newText);

            }while (cursor.moveToNext());
        }
        else{

        }

        cursor.close();
        db.close();

        return returnList;
    }
}
