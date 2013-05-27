package com.utt.scd;

import java.util.List;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;

import com.actionbarsherlock.app.SherlockFragmentActivity;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.SearchView;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.PushService;

public class SCD extends SherlockFragmentActivity 
{

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		setTheme(R.style.Theme_Sherlock_Light_DarkActionBar);
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		setContentView(R.layout.activity_scd);
		
		getSupportActionBar().setHomeButtonEnabled(true);
		
		
		
		
		Parse.initialize(this, "UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx", "XqnwGIwr89qMXkPcohKVmny8lYVEyzu58Osh9qW8");
		PushService.subscribe(this,  "Giants", SCD.class);
		ParseInstallation.getCurrentInstallation().saveInBackground();
		
		ParseQuery query = new ParseQuery("Livre");
		//query.whereContains("Titre", "BULATS");
		query.whereMatches("Titre", ".*[bB][Uu][Ll][Aa][Tt][Ss].*");
		query.findInBackground(new FindCallback() {
			
			@Override
			public void done(List<ParseObject> objects, ParseException e) 
			{
				// TODO Auto-generated method stub
				 if (e == null) 
				 {
			            Log.d("score", "Retrieved " + objects.size() + " Livre");
			            for (int i = 0; i< objects.size(); i++)
			            {
			            	System.out.println("titre : " + objects.get(i).get("Titre"));
			            }
			            
			     } 
				 else 
				 {
			            Log.d("score", "Error: " + e.getMessage());
			     }
			}
		});
				
		
		ParseAnalytics.trackAppOpened(getIntent());
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		
		//Create the search view
        SearchView searchView = new SearchView(getSupportActionBar().getThemedContext());
        searchView.setQueryHint("Recherche simple");
        
       
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
	
				return true;
	
			case android.R.id.home:
	
				this.finish();
	
				return true;
	
			}
			;

		return false;
	}

}
