package com.social.post.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.social.post.model.PostModel;

import java.util.ArrayList;

public class DataBaseHandler extends SQLiteOpenHelper {

    //Database version
    private static final int DATABASE_VERSION = 1;
    // DataBase Name
    private static final String DATABASE_NAME = "postDetailsManager";
    // Mileage table name
    private static final String TABLE_POST_DETAILS = "post_Details";

    // Mileage Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";

    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_POST_DETAILS_TABLE = "CREATE TABLE " + TABLE_POST_DETAILS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_IMAGE + " TEXT," + KEY_TITLE + " TEXT," + KEY_DESCRIPTION +
                " TEXT )";
        sqLiteDatabase.execSQL(CREATE_POST_DETAILS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_POST_DETAILS);
        // Create tables again
        onCreate(sqLiteDatabase);
    }

    public void savePost(PostModel postModel) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_IMAGE, postModel.getImageUri());
        values.put(KEY_TITLE, postModel.getTitle());
        values.put(KEY_DESCRIPTION, postModel.getDescription());
        db.insert(TABLE_POST_DETAILS, null, values);
        db.close();
    }

    public void resetTable() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TABLE_POST_DETAILS, null, null);
    }


    public ArrayList<PostModel> getAllPost() {
        ArrayList<PostModel> mileageList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_POST_DETAILS + " ORDER BY " + KEY_ID + " DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                PostModel model = new PostModel();
                model.setId(Integer.parseInt(cursor.getString(0)));
                model.setImageUri(cursor.getString(1));
                model.setTitle(cursor.getString(2));
                model.setDescription(cursor.getString(3));
                mileageList.add(model);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return mileageList;
    }
}