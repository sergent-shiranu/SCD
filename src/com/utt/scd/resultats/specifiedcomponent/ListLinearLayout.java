package com.utt.scd.resultats.specifiedcomponent;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class ListLinearLayout extends LinearLayout 
{

	private AdapterListLinearLayout adapter;
	private OnClickListener onClickListener = null;
	
	public ListLinearLayout(Context context) 
	{
		super(context);
	}
	
	

	public ListLinearLayout(Context context, AttributeSet attrs, int defStyle) 
	{
		super(context, attrs, defStyle);
	}



	public ListLinearLayout(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
	}



	public AdapterListLinearLayout getAdapter() 
	{
		return adapter;
	}

	public void setAdapter(AdapterListLinearLayout adapter) 
	{
		this.adapter = adapter;
		bindLinearLayout();
	}

	public OnClickListener getOnClickListener() 
	{
		return onClickListener;
	}

	public void setOnClickListener(OnClickListener onClickListener)
	{
		this.onClickListener = onClickListener;
	}
	
	
	public void bindLinearLayout()
	{
		
		this.removeAllViews();
		
		int count = adapter.getCount();
		
		for (int i = 0 ; i < count ; i++)
		{
			View v = adapter.getView(i, null, null);
			
			v.setOnClickListener(this.onClickListener);
			
			addView(v, i);
		}
	}

}
