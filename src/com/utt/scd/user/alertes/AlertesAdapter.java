package com.utt.scd.user.alertes;

import java.util.List;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.parse.ParseObject;

public class AlertesAdapter extends FragmentPagerAdapter 
{
protected static final String[] CONTENT = new String[] {"Long", "Disponible"};
	
	private int mCount = CONTENT.length;
	
	private Context context;
	
	private LongFragment longFragment;
	private DisponibleFragment disponibleFragment;

	public AlertesAdapter(FragmentManager fm, Context context, List<ParseObject> longAlertes, List<ParseObject> disponibleAlertes)
	{
		super(fm);
		
		this.context = context;
		
		this.longFragment = LongFragment.newInstance(this.context, longAlertes);
		this.disponibleFragment = DisponibleFragment.newInstance(this.context, disponibleAlertes);

	}
	
	
	public void refresh (List<ParseObject> longAlertes, List<ParseObject> disponibleAlertes)
	{	
		this.longFragment.setItems(longAlertes);
		this.disponibleFragment.setItems(disponibleAlertes);
		
		notifyDataSetChanged();
	}

	@Override
	public Fragment getItem(int position) 
	{
		//this.longFragment = LongFragment.newInstance(context, this.longAlertes);
		//this.disponibleFragment = DisponibleFragment.newInstance(context, this.disponibleAlertes);
		
		if (position == 0)
		{
			return longFragment;
			//return LongFragment.newInstance(context, this.longAlertes);
		}			
		else
		{
			return disponibleFragment;
			//return DisponibleFragment.newInstance(context, this.disponibleAlertes);
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
	
	
	
	public LongAdapter getLongAdapter()
	{
		return this.longFragment.getAdapter();
	}
	
	
	public DisponibleAdapter getDisponibleAdapter()
	{
		return this.disponibleFragment.getAdapter();
	}
}
