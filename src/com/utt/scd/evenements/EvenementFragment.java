package com.utt.scd.evenements;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;
import com.utt.scd.R;

public class EvenementFragment extends SherlockFragment 
{
	
	public static EvenementFragment newInstance() 
	{
		EvenementFragment fragment = new EvenementFragment();
		
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
	{
		View view = inflater.inflate(R.layout.evenement, null);
		
		return view;
	}
	
	
}
