package com.example.todo;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class TodoDbHelper extends SQLiteOpenHelper {
	
	private static final int DATABASE_VERSION = 1;
    // Database name
    private static final String DATABASE_NAME = "todoManager";
    // Todos table name
    private static final String TABLE_TODOS = "Todos";
    // Todos table columns names
    private static final String KEY_ID = "id ";
    private static final String KEY_TITLE = "Title";
    private static final String KEY_INFO = "Info";
    private static final String KEY_PRIO = "Prio";

	public TodoDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//String for creating table TODOS. id is primary key. We have title, info and status.
		String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_TODOS + " ("
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + KEY_TITLE + " TEXT, "
                + KEY_INFO + " TEXT, "
                + KEY_PRIO + " INTEGER AUTOINCREMENT)";
        //Run string :D
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//Drop old table, if it existed.
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODOS);
		//Create tables again.
		onCreate(db);
	}
	
	public void addTodo(TodoMessage todo) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		//Prepare and save all values for insertion.
		ContentValues values = new ContentValues();
		values.put(KEY_TITLE, todo.getTitle());
		values.put(KEY_INFO, todo.getInfo());
		values.put(KEY_PRIO, todo.getPrio());
		
		db.insert(TABLE_TODOS, null, values);
		db.close();
	}
	
	public void updateTodo(TodoMessage todo) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		
		//Update rows
		values.put(KEY_TITLE, todo.getTitle());
		values.put(KEY_INFO, todo.getInfo());
		values.put(KEY_PRIO, todo.getPrio());
		
		db.update(TABLE_TODOS, values, KEY_ID + " = ?", new String[]{String.valueOf(todo.getId())});
		
	}
	
	public List<TodoMessage> getAllTodos() {
		List<TodoMessage> list = new ArrayList<TodoMessage>();
		SQLiteDatabase db = this.getWritableDatabase();
		
		//Select all query
		String query = "SELECT * FROM " + TABLE_TODOS;
		Cursor cursor = db.rawQuery(query, null);
		
		//Add to list
		if(cursor.moveToFirst()) {
			do {
				TodoMessage todo = new TodoMessage(cursor.getString(1), cursor.getString(2), cursor.getInt(3));
				todo.setId(cursor.getInt(0));
				list.add(todo);
			} while (cursor.moveToNext());
		}
		
		return list;
	}

}
