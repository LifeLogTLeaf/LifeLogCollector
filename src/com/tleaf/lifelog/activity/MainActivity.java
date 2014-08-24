package com.tleaf.lifelog.activity;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

import com.tleaf.lifelog.R;
import com.tleaf.lifelog.pkg.FragmentListener;
import com.tleaf.lifelog.pkg.PagerAdapter;
import com.tleaf.lifelog.util.Mylog;
import com.tleaf.lifelog.util.Preference;
import com.tleaf.lifelog.util.Util;


public class MainActivity extends FragmentActivity implements ActionBar.TabListener, FragmentListener {

	public static PagerAdapter mPagerAdapter;
	public static ViewPager mViewPager;
	private int saleBookNo;
	private String isbn;
	private Preference pref;
	private boolean installation;
	private Context context;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = getApplicationContext();
		pref = new Preference(context);
		setInstallTime();

		mPagerAdapter = new PagerAdapter(getSupportFragmentManager());
		final ActionBar actionBar = getActionBar();

		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mPagerAdapter);
		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
			}
		});

		for (int i = 0; i < mPagerAdapter.getCount(); i++) {
			actionBar.addTab(
					actionBar.newTab()
					.setText(mPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
	}

	@Override
	public void onTabSelected(Tab tab, android.app.FragmentTransaction ft) {
		// When the given tab is selected, switch to the corresponding page in the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, android.app.FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabReselected(Tab tab, android.app.FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	/*    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

	 */

	public void onPause() {
		super.onPause();
		setInstallTime();
	}

	private void setInstallTime() {
		if(!pref.getBooleanPref("installation")) { 
			long currentTime = Util.getCurrentTime();
			Mylog.i("installTime", Util.formatLongTime(currentTime));
			pref.setLongPref("installTime", Util.getCurrentTime());
			pref.setBooleanPref("installation", true);
			Mylog.i("installation true", ""+pref.getBooleanPref("installation"));
		}
	}

}
