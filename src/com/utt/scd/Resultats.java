package com.utt.scd;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragmentActivity;

public class Resultats extends SherlockFragmentActivity 
{
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		setTheme(R.style.Theme_Sherlock_Light_DarkActionBar);
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		setContentView(R.layout.resultats);
		
		getSupportActionBar().setHomeButtonEnabled(true);
	}
}
