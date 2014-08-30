package com.tleaf.lifelog.listAdapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tleaf.lifelog.R;
import com.tleaf.lifelog.model.Call;
import com.tleaf.lifelog.model.Lifelog;
import com.tleaf.lifelog.util.Util;


public class AllListAdapter extends BaseAdapter {
	private Context mContext;
	private LayoutInflater inflater;
	private List<Lifelog> mArr;
	private int mLayout;

	private TextView txt;

	public AllListAdapter(Context context, int layout, List<Lifelog> arr) {
		mContext = context;
		inflater = (LayoutInflater)context.getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		mArr = arr;
		mLayout = layout;
	}

	public int getCount() {
		return mArr.size();
	}

	public Lifelog getItem(int position) {
		return mArr.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(mLayout, parent, false);
		}

		txt = (TextView)convertView.findViewById(R.id.txt);
		txt.setText(mArr.get(position).toString());
		
		return convertView;
	}

}
