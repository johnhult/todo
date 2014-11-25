package com.example.todo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.text.TextUtils.TruncateAt;
import android.view.Display;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends SwipeListViewActivity {
	
	private TodoDbHelper db;
	private List<TodoMessage> todos;
	private List<View> todoViews;
	private ListView mListView;
	private TodoAdapter<String> mAdapter;
	
	public int nrOfTodos;
	public static int listItemSize;
	public static int SCREEN_WIDTH;
	public static int currentLayout;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentLayout = R.layout.activity_main;
        db = new TodoDbHelper(this);
        todos = db.getAllTodos();
        nrOfTodos = todos.size();
        
        //For ListView
        mListView = (ListView) findViewById(R.id.gradientBackground);
		mAdapter = new TodoAdapter<String>(this, R.layout.text_view_item_default);
		mListView.setAdapter(mAdapter);

        //Calculate height of screen
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        listItemSize = height/10;
        SCREEN_WIDTH = width;        
        
        createTodoItems();
        
    }
    
    public void changeViewToNewTodo(View view) {
    	//Calls for a new view to add a todo
    	setContentView(R.layout.activity_add_todo);
    	currentLayout = R.layout.activity_add_todo;
    	EditText titleText = (EditText) findViewById(R.id.addTodoTitle);
    	titleText.requestFocus();
    	InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    	imm.showSoftInput(titleText, InputMethodManager.SHOW_IMPLICIT);
    }
    
    public void addTodoEvent(View view) {
    	EditText titleText = (EditText) findViewById(R.id.addTodoTitle);
    	EditText infoText = (EditText) findViewById(R.id.addTodoInfo);
    	setContentView(R.layout.activity_main);
    	currentLayout = R.layout.activity_main;
    	String title = titleText.getText().toString();
    	String info = infoText.getText().toString();
    	
    	if(!"".equals(title)) {
    		TodoMessage todo;
    		if("".equals(info)) {
    			todo = new TodoMessage(title, "No info.", nrOfTodos+1);
    		} else {    			
    			todo = new TodoMessage(title, info, nrOfTodos+1);
    		}
    		db.addTodo(todo);		
    	}	
	
    	InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    	imm.hideSoftInputFromWindow(titleText.getWindowToken(), 0);

    	createTodoItems();
    }
    
    public void createTodoItems() {
    	refreshDatabase();
    	
    	for(int i = 0; i < nrOfTodos; i++) {
    		String title = todos.get(i).getTitle();
    		
    		mAdapter.add(title);
    		//System.out.println(title);
    		//System.out.println(mAdapter);
    		
    	}
    	mListView.setAdapter(mAdapter);    		
    	ImageButton b = (ImageButton) findViewById(R.id.addButton);
    	b.bringToFront();
    }
    
    public void refreshDatabase() {
    	todos = db.getAllTodos();
    	nrOfTodos = todos.size();
    }
    
    @Override
    public void onBackPressed() {
    	System.out.println("" + this.findViewById(android.R.id.content).getRootView());
    	System.out.println("" + currentLayout);
    	if(R.layout.activity_add_todo == currentLayout) {
    		setContentView(R.layout.activity_main);
    		currentLayout = R.layout.activity_main;
    		
    		createTodoItems();
    	}
    	
    }
    
    @Override
	public ListView getListView() {
		return mListView;
	}

	@Override
	public void getSwipeItem(boolean isRight, int position) {
		Toast.makeText(this,
				"Swipe to " + (isRight ? "right" : "left") + " direction",
				Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onItemClickListener(ListAdapter adapter, int position) {
		Toast.makeText(this, "Single tap on item position " + position + mAdapter.getItem(position),
				Toast.LENGTH_SHORT).show();
	}
    
    
}
