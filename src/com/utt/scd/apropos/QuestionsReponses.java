package com.utt.scd.apropos;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.utt.scd.R;
import com.utt.scd.SCD;

public class QuestionsReponses extends SherlockFragmentActivity implements OnClickListener 
{
	private Button envoyer;

	@Override
    protected void onCreate(Bundle arg0) 
    {
    	setTheme(SCD.THEME);
		super.onCreate(arg0);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		setContentView(R.layout.questions_reponses);
		
		getSupportActionBar().setHomeButtonEnabled(true);
		
		this.envoyer = (Button) findViewById(R.id.button1);
		this.envoyer.setOnClickListener(this);
    }

	@Override
	public void onClick(View v) 
	{
		if (v.equals(envoyer))
		{
			this.finish();
		}
		
	}

}
