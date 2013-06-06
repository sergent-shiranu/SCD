package com.utt.scd.apropos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;
import com.utt.scd.R;

public class PretsFragment extends SherlockFragment 
{
	public static PretsFragment newInstance() 
	{
		PretsFragment fragment = new PretsFragment();
		
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
	{
		View view = inflater.inflate(R.layout.fragment_prets, null);
		
		return view;
	}
}
