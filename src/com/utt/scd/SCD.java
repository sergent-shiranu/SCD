package com.utt.scd;

import java.util.LinkedList;
import java.util.List;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.actionbarsherlock.app.SherlockFragmentActivity;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.actionbarsherlock.widget.SearchView;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.PushService;
import com.utt.scd.dialog.AlertingDialogOneButton;
import com.utt.scd.modele.Connection;
import com.utt.scd.modele.ConnectionNotInitializedException;

public class SCD extends SherlockFragmentActivity implements OnClickListener 
{

	private Button recherche_avancee,
					compte_lecteur,
					periodiques,
					settings;
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		setTheme(R.style.Theme_Sherlock_Light_DarkActionBar);
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
		
		Parse.initialize(this, "UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx", "XqnwGIwr89qMXkPcohKVmny8lYVEyzu58Osh9qW8");
		PushService.subscribe(this,  "Giants", SCD.class);
		ParseInstallation.getCurrentInstallation().saveInBackground();
		
		/*ParseQuery query = new ParseQuery("Livre");
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
				
		
		ParseAnalytics.trackAppOpened(getIntent());*/
		
		
		
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
	
	
	private AlertingDialogOneButton alertingDialogOneButton;
	
	public class RechercheSimple extends AsyncTask<String, Integer, String>
	{
		public Connection connection;
		public List<ParseObject> list;

		public RechercheSimple()
		{
			connection = Connection.getInstance();
			connection.initialize();
			
			this.list = new LinkedList<ParseObject>();
		}

		@Override
		protected void onPreExecute() 
		{
			super.onPreExecute();
			setSupportProgressBarIndeterminateVisibility(true); 
		}
		
		@Override
		protected String doInBackground(String... arg0) 
		{
			try 
			{
				this.list = connection.rechercheSimple("bulats");
			} 
			catch (ConnectionNotInitializedException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "no internet";
			} 
			catch (ParseException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "fail";
			}
			
			return "successful";
    	    
		}
		
		@Override
		protected void onPostExecute(String result) 
		{
			super.onPostExecute(result);
			
			//loadingDialog.dismiss();
			setSupportProgressBarIndeterminateVisibility(false);  
			
			if(result.equals("fail"))
			{
				
				alertingDialogOneButton = AlertingDialogOneButton.newInstance("Erreur", 
																			result,																			
																			R.drawable.action_about);
				alertingDialogOneButton.show(getSupportFragmentManager(), "error 1 alerting dialog");
			}
			else if(result.equals("add"))
			{
				alertingDialogOneButton = AlertingDialogOneButton.newInstance("Erreur", 
																			result,																			
																			R.drawable.action_search);
				alertingDialogOneButton.show(getSupportFragmentManager(), "error 1 alerting dialog");
				
			}
			else if (result.equals("successful"))
			{		
				for (int i = 0; i< list.size(); i++)
	            {
	            	System.out.println("titre : " + list.get(i).get("Titre"));
	            }
			}
			
		}
		
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
	
				new RechercheSimple().execute();
				
				return true;
	
			case android.R.id.home:
	
				this.finish();
	
				return true;
	
		};

		return false;
	}

	

}
