package com.example.todo;

import java.util.LinkedList;
import java.util.List;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends Activity {
	
	public static int nrOfTodos;
	public List<TodoMessage> todos;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        todos = new LinkedList<TodoMessage>();
        setContentView(R.layout.activity_main);
    }
    
    public void changeViewToNewTodo(View view) {
    	//Calls for a new view to add a todo
    	setContentView(R.layout.activity_add_todo);
    	EditText titleText = (EditText) findViewById(R.id.addTodoTitle);
    	titleText.requestFocus();
    	InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    	imm.showSoftInput(titleText, InputMethodManager.SHOW_IMPLICIT);
    }
    
    public void addTodoEvent(View view) {
    	EditText titleText = (EditText) findViewById(R.id.addTodoTitle);
    	EditText infoText = (EditText) findViewById(R.id.addTodoInfo);
    	setContentView(R.layout.activity_main);
    	String title = titleText.getText().toString();
    	String info = infoText.getText().toString();

    	System.out.println("Title: '" + title + "'" + "\nInfo: '" + info + "'");
    	System.out.println("Title: " + titleText + "Info: " + infoText);
    	
    	if(!"".equals(title) && !"".equals(info)) {
    		System.out.println("Why are we here?");
    		TodoMessage msg = new TodoMessage(title, info);
    		todos.add(msg);
    		TextView todo = new TextView(getApplicationContext());
    		todo.setText(title);
    		todo.setBackgroundColor(Color.RED);
    		Window win = this.getWindow();
    		todo.setWidth(win.getDecorView().getWidth());
    		LinearLayout ll = (LinearLayout) findViewById(R.id.linearLayout);
    		ll.addView(todo);    		
    	}	
	
    	InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    	imm.hideSoftInputFromWindow(titleText.getWindowToken(), 0);
    	}
}
