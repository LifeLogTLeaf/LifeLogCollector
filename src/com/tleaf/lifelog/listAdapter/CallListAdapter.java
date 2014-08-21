package com.tleaf.lifelog.listAdapter;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tleaf.lifelog.R;
import com.tleaf.lifelog.model.Sms;


public class CallListAdapter extends BaseAdapter {
	private Context mContext;
	private LayoutInflater inflater;
	private ArrayList<Sms> mArr;
	private int mLayout;

	private TextView txt_sms_type;
	private TextView txt_sms_target;
	private TextView txt_sms_content;

	public CallListAdapter(Context context, int layout, ArrayList<Sms> arr) {
		mContext = context;
		inflater = (LayoutInflater)context.getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		mArr = arr;
		mLayout = layout;
	}

	public int getCount() {
		return mArr.size();
	}

	public Sms getItem(int position) {
		return mArr.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(mLayout, parent, false);
		}
/*
		txt_sms_type = (TextView)convertView.findViewById(R.id.txt_sms_type);
		txt_sms_target = (TextView)convertView.findViewById(R.id.txt_sms_target);
		txt_sms_content = (TextView)convertView.findViewById(R.id.txt_sms_content);

//		Log.e("salebookno", ""+mArr.get(position).getSaleBookNo());
		txt_sms_type.setText(mArr.get(position).getType());
		txt_sms_target.setText(mArr.get(position).getTarget());
		txt_sms_content.setText(mArr.get(position).getContent());
*/				return convertView;
	}

}
