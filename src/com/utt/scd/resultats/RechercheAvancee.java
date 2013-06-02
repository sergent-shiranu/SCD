package com.utt.scd.resultats;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.utt.scd.R;
import com.utt.scd.SCD;
import com.utt.scd.apropos.Apropos;

public class RechercheAvancee extends SherlockFragmentActivity implements OnClickListener
{
	
	private EditText titre, auteur, support, langue, uv, domaine;
	private Button rechercher;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		setTheme(SCD.THEME);
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		setContentView(R.layout.recherche_avancee);
		
		getSupportActionBar().setHomeButtonEnabled(true);
		
		this.titre = (EditText) findViewById(R.id.titre);
		this.auteur = (EditText) findViewById(R.id.auteur);
		this.support = (EditText) findViewById(R.id.support);
		this.support.setText("Tous");
		this.support.setOnClickListener(this);
		this.langue = (EditText) findViewById(R.id.langue);
		this.langue.setText("Toutes");
		this.langue.setOnClickListener(this);
		this.uv = (EditText) findViewById(R.id.uv);
		this.domaine = (EditText) findViewById(R.id.domaine);
		
		
		this.rechercher = (Button) findViewById(R.id.button1);
		this.rechercher.setOnClickListener(this);
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
 
        MenuItem recherche_simple = menu.add(0,0,0,"A propos");
        {
        	recherche_simple.setIcon(R.drawable.action_about);
        	recherche_simple.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);              
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

	private AlertDialog choice;
	@Override
	public void onClick(View v) 
	{
		if (v.equals(support))
		{
			final CharSequence[] items = {"Tous","Autre","CD","Disquette",
											"DVD" , "En ligne", "K7 audio", "Microfiche",
											"Support", "Text imprimé", "VHS"};
            
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Choissisez votre langue");
            builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int item) 
	            {
	            	support.setText(items[item]);
	                
	            	choice.dismiss();    
	            }
            });
            choice = builder.create();
            choice.setCancelable(true);
            choice.show();
		}
		else if (v.equals(langue))
		{
			final CharSequence[] items = {"Toutes","Français","Anglais","Espagnol","Allemande","Chinois"};

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Choissisez votre support");
			builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int item) 
				{
					langue.setText(items[item]);
					
					choice.dismiss();    
				}
			});
			choice = builder.create();
			choice.setCancelable(true);
			choice.show();
		}
		else if (v.equals(rechercher))
		{
			Intent intent = new Intent(this, Resultats.class);
			
			intent.putExtra("Titre", getText(titre));
			intent.putExtra("Auteur", getText(auteur));
			intent.putExtra("Support", getText(support));
			intent.putExtra("Langue", getText(langue));
			intent.putExtra("UV", getText(uv));
			intent.putExtra("Domaine", getText(domaine));
			
			startActivity(intent);
		}
		
	}
	
	public String getText(TextView textView)
	{
		if (textView.getText() != null)
		{
			if (textView.getText().toString().length() > 0)
			{
				return textView.getText().toString();
			}
			
		}
		return "";
	}
}
