package com.tleaf.lifelog.pkg;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.tleaf.lifelog.fragment.AllFragment;
import com.tleaf.lifelog.fragment.CallFragment;
import com.tleaf.lifelog.fragment.FbPostFragmnet;
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
			return new AllFragment();
		case 1:
			return new SmsFragment();
		case 2:
			return new CallFragment();
		case 3:
			return new FbPostFragmnet();
		case 4:
			return new PhotoFragment();
		case 5:
			return new PositionFragment();
		}
		return null;
	}

	@Override
	public int getCount() {
		return 6;
	}

	@Override
	public String getPageTitle(int position) {
		Log.e("position", ""+position);
		String title = null;
		switch (position) {
		case 0:
			title = "All";
			break;
		case 1:
			title = "SMS";
			break;
		case 2:
			title = "Call";
			break;
		case 3:
			title = "post";
			break;
		case 4:
			title = "Picture";
			break;
		case 5:
			title = "Position";
			break;

		}
		return title;
	}
}