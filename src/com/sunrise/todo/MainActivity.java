package com.sunrise.todo;

import java.util.List;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.sunrise.todo.R;


public class MainActivity extends SwipeListViewActivity {
	
	private TodoDbHelper db;
	private List<TodoMessage> todos;
	private TodoMessage tmpTodo;
	private ListView mListView;
	private TodoAdapter<String> mAdapter;
	private Toast toast;
	
	public int nrOfTodos;
	public static int listItemSize;
	public static int SCREEN_WIDTH;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
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
    
    public void changeViewToEditTodo(TodoMessage todo) {
    	Fragment fragment = new TodoFragment(todo);
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
    			todo = new TodoMessage(title, "No info.", 1);
    		} else {    			
    			todo = new TodoMessage(title, info, 1);
    		}
    		if(tmpTodo != null) {
    			todo.setPrio(tmpTodo.getPrio());
    			db.delete(tmpTodo);
    			tmpTodo = null;
    		}
    		todos.add(todo.getPrio()-1, todo);
    		db.updateDbPrioAfterList(todos);
    		db.addTodo(todo);
    		updatePrio();
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
    	updatePrio();
    	mAdapter.clear();
    	for(int i = 0; i < nrOfTodos; i++) {
    		String title = todos.get(i).getTitle();	
    		mAdapter.add(title);   		
    		mAdapter.getItem(i);
    	}
    	mListView.setDivider(null);
    	mListView.setAdapter(mAdapter);
    	
    	ImageButton b = (ImageButton) findViewById(R.id.addButton);
    	b.bringToFront();
    }
    
    public void updatePrio() {
    	todos = db.getAllTodos();
    	nrOfTodos = todos.size();
    	for (int i = 0; i < nrOfTodos; i++) {
    		todos.get(i).setPrio(i+1);
    		db.updateTodo(todos.get(i));
    	}
    }
    
    @Override
    public void onBackPressed() {
    	FragmentManager fm = getFragmentManager();
    	if(fm.findFragmentByTag("newTodoView") != null) {
    		tmpTodo = null;
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
		    .setTitle("Edit or delete todo?")
		    .setMessage("What do you want to do?")
		    .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
		    	public void onClick(DialogInterface dialog, int which) {
		    		db.delete(todos.get(position));
		    		todos.remove(todos.get(position));
		    		createTodoItems();
		            dialog.cancel();
		        }
		     })
		     .setNeutralButton("Edit", new DialogInterface.OnClickListener() {
		    	public void onClick(DialogInterface dialog, int which) {
		    		tmpTodo = todos.get(position);
		    		changeViewToEditTodo(todos.get(position));
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
		toast = Toast.makeText(this, todos.get(position).getInfo(), Toast.LENGTH_LONG);
		toast.show();
	}
	
	@Override
	public void onLongClick(ListAdapter adapter, final int position) {		
		CharSequence[] prioList = new CharSequence[nrOfTodos];
		for (int i = 0; i < nrOfTodos; i++) {
			int a = i+1;
			prioList[i] = "" + a;
		}
		
		AlertDialog ad = new AlertDialog.Builder(this)
	    .setTitle("Set new priority.")
	    .setSingleChoiceItems(prioList, position, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				todos.add(which, todos.remove(position));
				db.updateDbPrioAfterList(todos);
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
