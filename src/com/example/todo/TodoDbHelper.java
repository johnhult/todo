package com.example.todo;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class TodoDbHelper extends SQLiteOpenHelper {
	
	private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "todoManager";
    // tasks table name
    private static final String TABLE_TODOS = "todos";
    // tasks Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TODO = "todo";
    private static final String KEY_TITLE = "todoTitle";
    private static final String KEY_INFO = "todoInfo";
    private static final String KEY_STATUS = "status";

	public TodoDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_TODOS + " ("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_TODO + " TODO, "
                + KEY_STATUS + " INTEGER)";
        db.execSQL(sql);		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODOS);
		onCreate(db);
	}
	
	public void addTodo(TodoMessage todo) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_TITLE, todo.getTitle());
	}

}
