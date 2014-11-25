package com.example.todo;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils.TruncateAt;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TodoAdapter<T> extends ArrayAdapter<String> {

	private Context context;
	private int resource;

	public TodoAdapter(Context context, int resource) {
		super(context, resource);
		this.context = context;
		this.resource = resource;
	}
	
//	@Override
//	public View	getView(int position, View convertView, ViewGroup parent) {
//		TextView v = (TextView) convertView;
//		v = 
//		return convertView;
//		
//	}
	

}
