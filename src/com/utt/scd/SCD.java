package com.utt.scd;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.actionbarsherlock.widget.SearchView;
import com.actionbarsherlock.widget.SearchView.OnQueryTextListener;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.PushService;
import com.slidinglayer.SlidingLayer;
import com.utt.scd.apropos.Apropos;
import com.utt.scd.dialog.AlertingDialogOneButton;
import com.utt.scd.evenements.EvenementsAdapter;
import com.utt.scd.model.Connection;
import com.utt.scd.model.ConnectionNotInitializedException;
import com.utt.scd.periodiques.Periodiques;
import com.utt.scd.resultats.RechercheAvancee;
import com.utt.scd.resultats.Resultats;
import com.utt.scd.user.CompteLecteur;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.PageIndicator;

public class SCD extends SherlockFragmentActivity implements OnQueryTextListener, OnItemClickListener 
{

	public static int THEME = R.style.Theme_Dark_purple;
	
    private SlidingLayer slidingLayer;
    
    private GridView gridView;
    private SCDAdapter adapter;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		setTheme(THEME);
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS); 
		
		setContentView(R.layout.activity_scd);
		
		getSupportActionBar().setHomeButtonEnabled(true);
		
		setSupportProgressBarIndeterminateVisibility(false); 
		
		
		this.gridView = (GridView) findViewById(R.id.gridview);
		
		ArrayList<FonctionSCD> items = new ArrayList<FonctionSCD>();
		items.add(new FonctionSCD("Recherche Avancée", R.drawable.search));
		items.add(new FonctionSCD("Compte Lecteur", R.drawable.user));
		items.add(new FonctionSCD("Périodiques", R.drawable.book));
		items.add(new FonctionSCD("Paramètres", R.drawable.setting));
		
		this.adapter = new SCDAdapter(this, items);
		
		this.gridView.setAdapter(adapter);
		this.gridView.setOnItemClickListener(this);
		

		PushService.setDefaultPushCallback(this, SCD.class);
		PushService.subscribe(this,  "", SCD.class);
		ParseInstallation.getCurrentInstallation().saveInBackground();

	}
	
	
	
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) 
	{
        switch (keyCode) 
        {
        case KeyEvent.KEYCODE_BACK:
            if (slidingLayer.isOpened()) 
            {
            	slidingLayer.closeLayer(true);
                return true;
            }

        default:
            return super.onKeyDown(keyCode, event);
        }
    }
	
	
	private AlertDialog choice ;
	private LayoutInflater inflater;
	

	
	public class login extends AsyncTask<String, Integer, String>
	{
		private AlertingDialogOneButton alertingDialogOneButton;
		
		private Connection connection;
		

		public login()
		{
			connection = Connection.getInstance();
			connection.initialize();
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
				System.out.println(arg0[0] + arg0[1]);
				this.connection.login(arg0[0], arg0[1]);
			} 
			catch (ParseException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "fail";
			} 
			catch (ConnectionNotInitializedException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "no internet";
			}
			
			return "successful";
    	    
		}
		
		@Override
		protected void onPostExecute(String result) 
		{
			super.onPostExecute(result);

			setSupportProgressBarIndeterminateVisibility(false); 
			
			if(result.equals("fail"))
			{
				
				alertingDialogOneButton = AlertingDialogOneButton.newInstance("Erreur", 
																			result,																			
																			R.drawable.action_about);
				alertingDialogOneButton.show(getSupportFragmentManager(), "error 1 alerting dialog");
			}
			else if(result.equals("no internet"))
			{
				alertingDialogOneButton = AlertingDialogOneButton.newInstance("Erreur", 
																			result,																			
																			R.drawable.action_search);
				alertingDialogOneButton.show(getSupportFragmentManager(), "error 1 alerting dialog");
				
			}
			else if (result.equals("successful"))
			{	
				System.out.println("Identification successful");
				
				Intent intent = new Intent(getBaseContext(), CompteLecteur.class);
				startActivity(intent);
			}
			
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

	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int position, long id) 
	{
		if (position == 0)
		{
			Intent intent = new Intent(this, RechercheAvancee.class);
			startActivity(intent);
		}
		else if (position == 1)
		{
			if (ParseUser.getCurrentUser().getObjectId() == null)
			{
				final AlertDialog.Builder builder = new AlertDialog.Builder(this);
	            builder.setTitle("Veuillez vous identifier");
	            
	            inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            View login = inflater.inflate(R.layout.login, null);
	            builder.setView(login);
	            
	            final EditText edt_pseudo = (EditText) login.findViewById(R.id.editText1);
	            final EditText edt_mot_de_passe = (EditText) login.findViewById(R.id.editText2);
	            
	            builder.setPositiveButton("S'identifier", new DialogInterface.OnClickListener() {
	    			public void onClick(DialogInterface dialog, int whichButton) 
	    			{
	    				
	    			}
	    		});
	            builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
	    			public void onClick(DialogInterface dialog, int whichButton) 
	    			{
	    				dialog.dismiss();
	    			}
	    		});
	            
	            choice = builder.create();
	            choice.setCancelable(true);
	            choice.setOnShowListener(new DialogInterface.OnShowListener() {

	                @Override
	                public void onShow(final DialogInterface dialog) 
	                {

	                    Button b = choice.getButton(AlertDialog.BUTTON_POSITIVE);
	                    b.setOnClickListener(new View.OnClickListener() {

	                        @Override
	                        public void onClick(View view) 
	                        {
	                        	if (edt_pseudo.getText() == null || edt_pseudo.getText().toString().length() == 0)
	    	    				{
	    	    					edt_pseudo.setError("Veuillez remplir ce champ");
	    	    				}
	    	    				else if (edt_mot_de_passe.getText() == null || edt_mot_de_passe.getText().toString().length() == 0)
	    	    				{
	    	    					edt_mot_de_passe.setError("Veuillez remplir ce champ");
	    	    				}
	    	    				else
	    	    				{
	    	    					String[] data = {edt_pseudo.getText().toString(),edt_mot_de_passe.getText().toString()};
	    	    					new login().execute(data);
	    	    					choice.dismiss();
	    	    				}
	                        }
	                    });
	                }
	            });
	            
	            
	            choice.show();

			}
			else
			{
				Intent intent = new Intent(this, CompteLecteur.class);
				startActivity(intent);
			}
		}
		else if (position == 2)
		{
			Intent intent = new Intent(this, Periodiques.class);
			startActivity(intent);
		}
		else if (position == 3)
		{
			Intent intent = new Intent(this, Parametre.class);
			startActivity(intent);
			
			/*if (!this.slidingLayer.isOpened()) 
			{
				this.slidingLayer.openLayer(true);
            }
			else if (this.slidingLayer.isOpened())
			{
				this.slidingLayer.closeLayer(true);
            }*/
		}
		
	}

	

}
