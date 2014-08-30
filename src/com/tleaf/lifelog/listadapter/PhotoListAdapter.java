package com.tleaf.lifelog.listadapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tleaf.lifelog.R;
import com.tleaf.lifelog.model.Photo;



//Listview를 설정한다.
public class PhotoListAdapter extends BaseAdapter{
	Context maincon;
	LayoutInflater Inflater;
	ArrayList<Photo> arrFileList;
	int layout;

	public PhotoListAdapter(Context context, int alayout, ArrayList<Photo> aarsrc) {
		maincon = context;
		Inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		arrFileList = aarsrc;
		layout = alayout;
	}

	public int getCount() {
		return arrFileList.size();
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		final int pos = position;
		if (convertView == null) {
			convertView = Inflater.inflate(layout, parent, false);
		}
		ImageView profile = (ImageView) convertView.findViewById(R.id.layoutImg);
		String imgPath=arrFileList.get(pos).getImgPath();
		BitmapFactory.Options opt = new BitmapFactory.Options();
//        opt.inSampleSize = 4;
        Bitmap bm = BitmapFactory.decodeFile(imgPath, opt);
		profile.setImageBitmap(bm);
		
		
		TextView name = (TextView) convertView.findViewById(R.id.layoutTxt);
		name.setText(arrFileList.get(pos).getFileName());
		
		
		return convertView;
	}

	public String getItem(int position) {
		return arrFileList.get(position).getImgPath();
	}
}