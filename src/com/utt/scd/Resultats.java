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
	
	
	public static String regex(String chaine)
	{
		String[] mots = chaine.split(" ");
		String regex="";
		
		regex += ".*";
		for (int i = 0 ; i < mots.length ; i++)
		{
			
			for (int j = 0 ; j < mots[i].length() ; j++)
			{
				regex += "[";
				if (Character.isUpperCase(mots[i].charAt(j)))
				{
					regex += Character.toString(mots[i].charAt(j)) + Character.toString(Character.toLowerCase(mots[i].charAt(j)));
				}
				else if (Character.isLowerCase(mots[i].charAt(j)))
				{
					regex += Character.toString(mots[i].charAt(j)) + Character.toString(Character.toUpperCase(mots[i].charAt(j)));
				}
				else
				{
					regex += Character.toString(mots[i].charAt(j));
				}
				regex += "]";
			}
			if(i != mots.length)
			{
				regex += ".*";
			}
			
		}

		return regex;
	}
}
