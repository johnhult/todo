package com.example.todo;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import com.sunrise.todo.R;

public class TodoFragment extends Fragment {
	
	private View v;
	private String titleText;
	private String infoText;
	
	public TodoFragment(TodoMessage todo) {
		titleText = todo.getTitle();
		infoText = todo.getInfo();
	}
	
	public TodoFragment() {
		titleText = "";
		infoText = "";
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
		v = inflater.inflate(R.layout.activity_add_todo, container, false);
		
        return v;
    }
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		EditText title = (EditText) v.findViewById(R.id.addTodoTitle);
		EditText info = (EditText) v.findViewById(R.id.addTodoInfo);
		EditText[] editTextArray = {title, info};
		String[] stringArray = {titleText, infoText};
		title.clearFocus();
    	title.requestFocus();
    	imm.showSoftInput(title, 0);
    	if(!"".equals(title.getText())) {
    		setAllTexts(editTextArray, stringArray);
    	}
	}
	
	public void setAllTexts(EditText[] containers, String[] texts) {
		for(int i = 0; i < containers.length; i++) {
			containers[i].setText(texts[i]);
		}
	}
	
}
