package com.example.todo;

import java.util.List;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends SwipeListViewActivity {
	
	private TodoDbHelper db;
	private List<TodoMessage> todos;
	private ListView mListView;
	private TodoAdapter<String> mAdapter;
	private Toast toast;
	
	public int nrOfTodos;
	public static int listItemSize;
	public static int SCREEN_WIDTH;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //DB and lists
        db = new TodoDbHelper(this);
        todos = db.getAllTodos();
        nrOfTodos = todos.size();
        
        //For ListView    
        mListView = (ListView) findViewById(R.id.gradientBackground);
		mAdapter = new TodoAdapter<String>(this, R.layout.text_view_item_default);

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
    	Fragment fragment = new TodoFragment();
    	FragmentManager fm = getFragmentManager();
    	FragmentTransaction transaction = fm.beginTransaction();
    	transaction.addToBackStack("newTodoView");
    	transaction.replace(android.R.id.content, fragment, "newTodoView");
    	transaction.commit();
    }
    
    public void addTodoEvent(View view) {
    	EditText titleText = (EditText) findViewById(R.id.addTodoTitle);
    	EditText infoText = (EditText) findViewById(R.id.addTodoInfo);
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
	
    	FragmentManager fm = getFragmentManager();
    	FragmentTransaction transaction = fm.beginTransaction();
    	fm.popBackStack();
    	transaction.commit();
    	
    	InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    	imm.hideSoftInputFromWindow(titleText.getWindowToken(), 0);

    	createTodoItems();
    }
    
    public void createTodoItems() {
    	refresh();
    	mAdapter.clear();
    	for(int i = 0; i < nrOfTodos; i++) {
    		String title = todos.get(i).getTitle();	
    		mAdapter.add(title);   		
    	}
    	
    	mListView.setAdapter(mAdapter);
    	
    	ImageButton b = (ImageButton) findViewById(R.id.addButton);
    	b.bringToFront();
    }
    
    public void refresh() {
    	todos = db.getAllTodos();
    	nrOfTodos = todos.size();
    }
    
    @Override
    public void onBackPressed() {
    	FragmentManager fm = getFragmentManager();
    	if(fm.findFragmentByTag("newTodoView") != null) {
    		FragmentTransaction transaction = fm.beginTransaction();
        	fm.popBackStack();
        	transaction.commit();
    	}
    	
    }
    
    @Override
	public ListView getListView() {
		return mListView;
	}

	@Override
	public void getSwipeItem(boolean isRight, final int position) {
		if (!isRight) {
			AlertDialog ad = new AlertDialog.Builder(this)
		    .setTitle("Delete todo!")
		    .setMessage("Are you sure you want to delete this todo?")
		    .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
		    	public void onClick(DialogInterface dialog, int which) {
		    		db.delete(todos.get(position));
		    		createTodoItems();
		            dialog.cancel();
		        }
		     })
		    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		    	public void onClick(DialogInterface dialog, int which) { 
		            dialog.cancel();
		        }
		     })
		    .setIcon(android.R.drawable.ic_dialog_alert)
		    .show();
		}
	}

	@Override
	public void onItemClickListener(ListAdapter adapter, int position) {
		if (toast != null) {
			toast.cancel();			
		}
		toast = Toast.makeText(this, todos.get(position).getInfo(),Toast.LENGTH_SHORT);
		toast.show();
		Log.d("ACTIVITY", "" + todos.get(position).toString());
	}
    
    
}
