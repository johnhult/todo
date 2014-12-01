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

public class TodoFragment extends Fragment {
	
	private View v;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
		v = inflater.inflate(R.layout.activity_add_todo, container, false);
		
//		EditText titleText = (EditText) v.findViewById(R.id.addTodoTitle);
//    	Log.d("EDIT_TEXT", "" + titleText);
//    	titleText.clearFocus();
//    	titleText.requestFocus();
//    	titleText.
//    	
//    	getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
//    	Log.d("EDIT_TEXT", "" + getActivity().getWindow());
//    	InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//    	imm.showSoftInput(titleText, InputMethodManager.SHOW_IMPLICIT);
        return v;
    }
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		EditText titleText = (EditText) v.findViewById(R.id.addTodoTitle);
		titleText.clearFocus();
    	titleText.requestFocus();
    	imm.showSoftInput(titleText, 0);
	}
}
