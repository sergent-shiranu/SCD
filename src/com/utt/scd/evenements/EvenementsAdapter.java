package com.utt.scd.evenements;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.utt.scd.apropos.InfosPratiquesFragment;
import com.utt.scd.apropos.ServicesFragment;

public class EvenementsAdapter extends FragmentPagerAdapter 
{
protected static final String[] CONTENT = new String[] {"Infos pratiques", "Horaires"};
	
	private int mCount = CONTENT.length;

	public EvenementsAdapter(FragmentManager fm)
	{
		super(fm);
	}
	

	@Override
	public Fragment getItem(int position) 
	{
		if (position == 0)
		{
			return InfosPratiquesFragment.newInstance();
		}			
		else
		{
			return ServicesFragment.newInstance();
		}
	}

	@Override
	public int getCount() 
	{
		return mCount;
	}
	
	@Override
	public int getItemPosition(Object object) 
	{
		return POSITION_NONE;
	}
	
	@Override
    public CharSequence getPageTitle(int position) 
	{
		return CONTENT[position % CONTENT.length];
    }
}
