package com.utt.scd.apropos;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.actionbarsherlock.app.SherlockFragment;
import com.utt.scd.R;

public class PretsFragment extends SherlockFragment  implements OnClickListener
{
	private Button questions_reponses;
	
	public static PretsFragment newInstance() 
	{
		PretsFragment fragment = new PretsFragment();
		
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
	{
		View view = inflater.inflate(R.layout.fragment_prets, null);
		
		this.questions_reponses = (Button) view.findViewById(R.id.button1);
		this.questions_reponses.setOnClickListener(this);
		
		return view;
	}
	
	@Override
	public void onClick(View v) 
	{
		if (v.equals(questions_reponses))
		{
			Intent intent = new Intent(getSherlockActivity(),QuestionsReponses.class);
			startActivity(intent);
		}
		
	}
}
