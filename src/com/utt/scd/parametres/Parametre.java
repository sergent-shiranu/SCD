package com.utt.scd.parametres;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.utt.scd.R;
import com.utt.scd.SCD;
import com.utt.scd.apropos.Apropos;

public class Parametre extends SherlockFragmentActivity implements OnCheckedChangeListener, OnClickListener 
{
	
	private ToggleButton rappel_prets, rappel_alertes;
	
	private TextView frequence, anticipation, theme;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		setTheme(SCD.THEME);
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS); 
		
		setContentView(R.layout.parametres);
		
		setSupportProgressBarIndeterminateVisibility(false);
		
		getSupportActionBar().setHomeButtonEnabled(true);
		
		this.rappel_prets = (ToggleButton) findViewById(R.id.ParamToggleButton);
		this.rappel_prets.setOnCheckedChangeListener(this);
		
		this.rappel_alertes = (ToggleButton) findViewById(R.id.ParamToggleButton2);
		this.rappel_alertes.setOnCheckedChangeListener(this);
		
		this.frequence = (TextView) findViewById(R.id.textView4);
		this.frequence.setBackgroundColor(SCD.color());
		this.frequence.setOnClickListener(this);
		this.anticipation = (TextView) findViewById(R.id.textView6);
		this.anticipation.setBackgroundColor(SCD.color());
		this.anticipation.setOnClickListener(this);
		this.theme = (TextView) findViewById(R.id.textView10);
		this.theme.setBackgroundColor(SCD.color());
		this.theme.setOnClickListener(this);
		
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
 
        MenuItem apropos = menu.add(0,0,0,"A propos");
        {
            apropos.setIcon(R.drawable.action_about);
            apropos.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);           
        }
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		switch (item.getItemId()) 
		{
		
			case 0:
				
				Intent intent = new Intent(this,Apropos.class);
				startActivity(intent);

				return true;

	
			case android.R.id.home:
	
				this.finish();
	
				return true;
	
		};

		return false;
	}


	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) 
	{
		if (buttonView.equals(rappel_prets))
		{
			if (isChecked)
			{
				
			}
			else
			{
				
			}
		}
		else
		{
			if (isChecked)
			{
				
			}
			else
			{
				
			}
		}
		
	}

	
	
	private AlertDialog choice_frequence, choice_anticipation, choice_theme;
	
	@Override
	public void onClick(View v) 
	{
		if (v.equals(frequence))
		{
			final CharSequence[] items = {"Non défini","1 fois par jour","2 fois par jour"};
			
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Choix d'anticipation");
			builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int item) 
				{
					frequence.setText(items[item]);
					choice_frequence.dismiss();    
				}
			});
			choice_frequence = builder.create();
			choice_frequence.setCancelable(true);
			choice_frequence.show();
		}
		else if (v.equals(anticipation))
		{
			final CharSequence[] items = {"Non défini","2 jours avant","Un seul jour avant"};
			
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Choissisez votre thème");
			builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int item) 
				{
					anticipation.setText(items[item]);
					choice_anticipation.dismiss();    
				}
			});
			choice_anticipation = builder.create();
			choice_anticipation.setCancelable(true);
			choice_anticipation.show();
		}
		else
		{
			final CharSequence[] items = {"Bleu","Vert","Rouge","Violet","Jaune"};
			
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Choissisez la fréquence de rappel");
			builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int item) 
				{
					theme.setText(items[item]);
					choice_theme.dismiss(); 
					
					if (item == 0)
					{
						SCD.THEME = R.style.Theme_Dark_blue;
					}
					else if (item == 1)
					{
						SCD.THEME = R.style.Theme_Dark_green;
					}
					else if (item == 2)
					{
						SCD.THEME = R.style.Theme_Dark_red;
					}
					else if (item == 3)
					{
						SCD.THEME = R.style.Theme_Dark_purple;
					}
					else if (item == 4)
					{
						SCD.THEME = R.style.Theme_Dark_yellow;
					}
					
					Intent intent = new Intent(getApplicationContext(),SCD.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					
					startActivity(intent);
					
				}
			});
			choice_theme = builder.create();
			choice_theme.setCancelable(true);
			choice_theme.show();
		}
		
	}
}
