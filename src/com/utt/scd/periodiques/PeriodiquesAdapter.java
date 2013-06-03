package com.utt.scd.periodiques;

import java.util.LinkedList;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class PeriodiquesAdapter extends FragmentPagerAdapter 
{
	private LinkedList<TypePeriodiques> listPeriodiques;
	private Context context;
	

	public PeriodiquesAdapter(Context context, FragmentManager fm, LinkedList<TypePeriodiques> listPeriodiques)
	{
		super(fm);
		this.listPeriodiques = listPeriodiques;
		this.context = context;
	}
	

	@Override
	public Fragment getItem(int position) 
	{
		return MagazineFragment.newInstance(this.context, this.listPeriodiques.get(position));
	}


	@Override
	public int getCount() 
	{
		return this.listPeriodiques.size();
	}
	
	@Override
	public int getItemPosition(Object object) 
	{
		return POSITION_NONE;
	}
	
	@Override
    public CharSequence getPageTitle(int position) 
	{
		return this.listPeriodiques.get(position).getNom();
    }
}
