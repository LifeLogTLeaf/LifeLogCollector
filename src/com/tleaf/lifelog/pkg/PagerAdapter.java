package com.tleaf.lifelog.pkg;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;

import com.tleaf.lifelog.fragment.BookMarkFragment;
import com.tleaf.lifelog.fragment.CallFragment;
import com.tleaf.lifelog.fragment.PhotoFragment;
import com.tleaf.lifelog.fragment.PositionFragment;
import com.tleaf.lifelog.fragment.SmsFragment;


public class PagerAdapter extends FragmentPagerAdapter {

	public PagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int i) {
		switch (i) {
		case 0:
			return new SmsFragment();
		case 1:
			return new CallFragment();
		case 2:
			return new BookMarkFragment();
		case 3:
			return new PhotoFragment();
		case 4:
			
			return new PositionFragment();
		}
		return null;
	}

	@Override
	public int getCount() {
		return 5;
	}

	@Override
	public String getPageTitle(int position) {
		Log.e("position", ""+position);
		String title = null;
		switch (position) {
		case 0:
			title = "SMS";
			break;
		case 1:
			title = "Call";
			break;
		case 2:
			title = "BookMark";
			break;
		case 3:
			title = "Picture";
			break;
		case 4:
			title = "Position";
			break;

		}
		return title;
	}
}