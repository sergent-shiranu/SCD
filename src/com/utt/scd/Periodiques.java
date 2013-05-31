package com.utt.scd;

import com.actionbarsherlock.app.SherlockFragmentActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

public class Periodiques extends SherlockFragmentActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		setTheme(SCD.THEME);
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		setContentView(R.layout.recherche_avancee);
		
		getSupportActionBar().setHomeButtonEnabled(true);
	}

}
