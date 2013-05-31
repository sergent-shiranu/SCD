package com.utt.scd;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.actionbarsherlock.widget.SearchView;
import com.actionbarsherlock.widget.SearchView.OnQueryTextListener;
import com.parse.ParseInstallation;
import com.parse.PushService;
import com.utt.scd.apropos.Apropos;
import com.utt.scd.resultats.Resultats;

public class SCD extends SherlockFragmentActivity implements OnClickListener, OnQueryTextListener 
{

	private Button recherche_avancee,
					compte_lecteur,
					periodiques,
					settings;
	
	public static int THEME = R.style.Theme_Dark_blue;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		setTheme(THEME);
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS); 
		
		setContentView(R.layout.activity_scd);
		
		getSupportActionBar().setHomeButtonEnabled(true);
		
		
		this.recherche_avancee = (Button) findViewById(R.id.recherche_avancee);
		this.recherche_avancee.setOnClickListener(this);
		this.compte_lecteur = (Button) findViewById(R.id.compte_lecteur);
		this.compte_lecteur.setOnClickListener(this);
		this.periodiques = (Button) findViewById(R.id.periodiques);
		this.periodiques.setOnClickListener(this);
		this.settings = (Button) findViewById(R.id.settings);
		this.settings.setOnClickListener(this);
		

		PushService.setDefaultPushCallback(this, SCD.class);
		PushService.subscribe(this,  "Giants", SCD.class);
		ParseInstallation.getCurrentInstallation().saveInBackground();
		
	}
	
	
	@Override
	public void onClick(View v) 
	{
		if (v.equals(this.recherche_avancee))
		{
			Intent intent = new Intent(this, RechercheAvancee.class);
			startActivity(intent);
		}
		else if (v.equals(this.compte_lecteur))
		{
			Intent intent = new Intent(this, CompteLecteur.class);
			startActivity(intent);
		}
		else if (v.equals(this.periodiques))
		{
			Intent intent = new Intent(this, Periodiques.class);
			startActivity(intent);
		}
		else if (v.equals(this.settings))
		{
			Intent intent = new Intent(this, Parametre.class);
			startActivity(intent);
		}
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		
		//Create the search view
        SearchView searchView = new SearchView(getSupportActionBar().getThemedContext());
        searchView.setQueryHint("Recherche simple");
        searchView.setOnQueryTextListener(this);
        
       
        MenuItem recherche_simple = menu.add(0,0,0,"Recherche simple");
        {
        	recherche_simple.setIcon(R.drawable.action_search);
        	recherche_simple.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);              
        	recherche_simple.setActionView(searchView);
        }
        
        MenuItem apropos = menu.add(0,1,1,"A propos");
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
	
				
				return true;
	
			case 1:
				
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
	public boolean onQueryTextSubmit(String query) 
	{
		Intent intent = new Intent(this, Resultats.class);
		intent.putExtra("Titre", query);
		startActivity(intent);
		
		return false;
	}


	@Override
	public boolean onQueryTextChange(String newText) 
	{
		return false;
	}

	

}
