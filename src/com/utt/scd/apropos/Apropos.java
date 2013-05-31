package com.utt.scd.apropos;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.utt.scd.R;
import com.utt.scd.SCD;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TitlePageIndicator;

public class Apropos extends SherlockFragmentActivity 
{
	
	private AproposAdapter aproposAdapter;
	private ViewPager mPager;
    private PageIndicator mIndicator;
	
	@Override
    protected void onCreate(Bundle arg0) 
    {
    	setTheme(SCD.THEME);
		super.onCreate(arg0);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		setContentView(R.layout.apropos);
		
		getSupportActionBar().setHomeButtonEnabled(true);
		
		
		aproposAdapter = new AproposAdapter(getSupportFragmentManager());

        mPager = (ViewPager)findViewById(R.id.pager);
        mPager.setAdapter(aproposAdapter);

        mIndicator = (TitlePageIndicator)findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		switch (item.getItemId()) 
		{
			case android.R.id.home:
	
				this.finish();
	
				return true;
	
		};

		return false;
	}
}
