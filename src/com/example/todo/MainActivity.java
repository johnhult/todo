package com.example.todo;

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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {
	
	private TodoDbHelper db;
	private List<TodoMessage> todos;
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
    	LinearLayout ll = (LinearLayout) findViewById(R.id.gradientBackground);
    	//ll.layout(0, 0, SCREEN_WIDTH, listItemSize*nrOfTodos);
    	for(int i = 0; i < nrOfTodos; i++) {
    		TextView v = new TextView(this);
    		String title = todos.get(i).getTitle();
    		System.out.println(title);
    		v.setText(title);
    		v.setHeight(listItemSize);
    		v.setAllCaps(true);
    		v.setTextColor(Color.parseColor("#FFD9A7"));
    		v.setTextSize(listItemSize/6);
    		v.setHorizontallyScrolling(false);
    		v.setMaxLines(1);
    		v.setEllipsize(TruncateAt.END);
    		v.setPadding(30, 50, 0, 0);
    		v.setId(i);
    		v.setOnTouchListener(new OnSwipeTouchListener(this) {
    		    @Override
    		    public void onSwipeLeft() {
    		    	
    		    	/*
    		    	 * 
    		    	 * 
    		    	 * TODO!!! NEXT PART OF MY PLAN TO WORLD DOMINATION!
    		    	 * 
    		    	 * 
    		    	 */
    		    	//todos.get();
    		    }
    		});
    		if(i%2 == 1) {
    			v.setBackgroundColor(Color.parseColor("#11FFFFFF"));
    		}
    		ll.addView(v, i);    		
    		//v.layout(0, i*listItemSize, SCREEN_WIDTH, ((i*listItemSize) + listItemSize));
    		
    	}
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
    
    
}
