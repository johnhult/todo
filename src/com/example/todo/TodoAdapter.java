package com.example.todo;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils.TruncateAt;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.sunrise.todo.R;

public class TodoAdapter<T> extends ArrayAdapter<String> {

	private Context context;
	private int resource;

	public TodoAdapter(Context context, int resource) {
		super(context, resource);
		this.context = context;
		this.resource = resource;
	}
	
	@Override
    public View getView(int position, View v, ViewGroup parent) {
		View mView = v ;
        if(mView == null){
            LayoutInflater vi = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mView = vi.inflate(this.resource, null);
        }
        
        TextView text = (TextView) mView.findViewById(R.id.list_item);
        text.setBackgroundColor(Color.parseColor(color(position+1, Id.BACKGROUND)));
        text.setText(getItem(position));
        text.setTextColor(Color.parseColor(color(position+1, Id.TEXT)));
        return mView;
    }
	

	public String color(int i, Id id) {
		String clr = "";
		if(id == Id.BACKGROUND) {
			switch(i) {
			case 1:
				clr = "#A50000";
				break;
			case 2:
				clr = "#AC1803";
				break;
			case 3:
				clr = "#B43006";
				break;
			case 4:
				clr = "#BC490A";
				break;
			case 5:
				clr = "#C4610D";
				break;
			case 6:
				clr = "#CB7910";
				break;
			case 7:
				clr = "#D39214";
				break;
			default :
				clr = "#DBAA17";
				break;
			}
		}
		else if (id == Id.TEXT) {
			switch(i) {
			case 1:
				clr = "#FE7171";
				break;
			case 2:
				clr = "#FF8878";
				break;
			case 3:
				clr = "#FF9E7F";
				break;
			case 4:
				clr = "#FFB288";
				break;
			case 5:
				clr = "#FFC38F";
				break;
			case 6:
				clr = "#FFD196";
				break;
			case 7:
				clr = "#FFDE9F";
				break;
			default:
				clr = "#FFE9A6";
				break;
			}
		}
		return clr;
	}
	
	public enum Id {
		BACKGROUND, TEXT
	}
}
