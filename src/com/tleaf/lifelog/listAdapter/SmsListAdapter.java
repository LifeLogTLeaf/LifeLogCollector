package com.tleaf.lifelog.listAdapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tleaf.lifelog.R;
import com.tleaf.lifelog.model.Sms;


public class SmsListAdapter extends BaseAdapter {
	private Context mContext;
	private LayoutInflater inflater;
	private ArrayList<Sms> mArr;
	private int mLayout;

	private TextView txt_sms_address;
	private TextView txt_sms_date;
	private TextView txt_sms_body;

	public SmsListAdapter(Context context, int layout, ArrayList<Sms> arr) {
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

		txt_sms_address = (TextView)convertView.findViewById(R.id.txt_sms_address);
		txt_sms_date = (TextView)convertView.findViewById(R.id.txt_sms_date);
		txt_sms_body = (TextView)convertView.findViewById(R.id.txt_sms_body);

//		Log.e("salebookno", ""+mArr.get(position).getSaleBookNo());
		txt_sms_address.setText(mArr.get(position).getAddress());
		txt_sms_date.setText(""+mArr.get(position).getDate());
		txt_sms_body.setText(mArr.get(position).getBody());
		return convertView;
	}

}
