package com.tleaf.lifelog.listAdapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tleaf.lifelog.R;
import com.tleaf.lifelog.model.Call;
import com.tleaf.lifelog.util.Util;


public class CallListAdapter extends BaseAdapter {
	private Context mContext;
	private LayoutInflater inflater;
	private ArrayList<Call> mArr;
	private int mLayout;
	
	private TextView txt_call_name;
	private TextView txt_call_number;
	private TextView txt_call_type;
	private TextView txt_call_date;
	private TextView txt_call_duration;

	public CallListAdapter(Context context, int layout, ArrayList<Call> arr) {
		mContext = context;
		inflater = (LayoutInflater)context.getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		mArr = arr;
		mLayout = layout;
	}

	public int getCount() {
		return mArr.size();
	}

	public Call getItem(int position) {
		return mArr.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(mLayout, parent, false);
		}

		txt_call_name = (TextView)convertView.findViewById(R.id.txt_call_name);
		txt_call_number = (TextView)convertView.findViewById(R.id.txt_call_number);
		txt_call_type = (TextView)convertView.findViewById(R.id.txt_call_type);
		txt_call_date = (TextView)convertView.findViewById(R.id.txt_call_date);
		txt_call_duration = (TextView)convertView.findViewById(R.id.txt_call_duration);

		//		Log.e("salebookno", ""+mArr.get(position).getSaleBookNo());
		txt_call_name.setText(mArr.get(position).getName());
		txt_call_number.setText(mArr.get(position).getNumber());
		txt_call_type.setText(mArr.get(position).getType());
		txt_call_date.setText(Util.formatLongTime(mArr.get(position).getDate()));
		txt_call_duration.setText(""+mArr.get(position).getDuration());
		

		return convertView;
	}

}
