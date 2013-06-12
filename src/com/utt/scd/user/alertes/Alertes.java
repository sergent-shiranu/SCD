package com.utt.scd.user.alertes;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.utt.scd.R;
import com.utt.scd.SCD;
import com.utt.scd.apropos.Apropos;
import com.utt.scd.dialog.AlertingDialogOneButton;
import com.utt.scd.model.Connection;
import com.utt.scd.model.ConnectionNotInitializedException;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TitlePageIndicator;

public class Alertes extends SherlockFragmentActivity 
{
	private AlertesAdapter alertesAdapter;
	private ViewPager mPager;
    private PageIndicator mIndicator;
	
	private List<ParseObject> listeLivresLong, listeLivresDisponible;
	
	private ArrayList<String> livresLongCorbeille;
	private ArrayList<String> livresDisponibleCorbeille;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		setTheme(SCD.THEME);
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS); 
		
		setContentView(R.layout.alertes);
		
		setSupportProgressBarIndeterminateVisibility(false); 
		
		getSupportActionBar().setHomeButtonEnabled(true);
		
		this.listeLivresLong = new ArrayList<ParseObject>();
		this.listeLivresDisponible = new ArrayList<ParseObject>();
		
		alertesAdapter = new AlertesAdapter(getSupportFragmentManager(),this,this.listeLivresLong, this.listeLivresDisponible );

        mPager = (ViewPager)findViewById(R.id.pager);
        mPager.setAdapter(alertesAdapter);

        mIndicator = (TitlePageIndicator)findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);
		
		new RecupererLivresAlertes().execute();
	}
	
	
	
	public void populateListView()
	{
		this.alertesAdapter.refresh(listeLivresLong, listeLivresDisponible);
	}
	
	
	
	public class RecupererLivresAlertes extends AsyncTask<String, Integer, String>
	{
		private AlertingDialogOneButton alertingDialogOneButton;
		
		public Connection connection;
		public List<ParseObject> resultats;

		public RecupererLivresAlertes()
		{
			connection = Connection.getInstance();
			connection.initialize();
			
			this.resultats = new LinkedList<ParseObject>();
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
				listeLivresLong = this.connection.recupererAlertesLong();
				listeLivresDisponible = this.connection.recupererAlertesDisponibles();
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

			setSupportProgressBarIndeterminateVisibility(false); 
			
			if(result.equals("fail"))
			{
				
				alertingDialogOneButton = AlertingDialogOneButton.newInstance("Erreur", 
																			"Erreur inconnue s'est produite, veuillez réessayer plus tard",																			
																			R.drawable.action_alert);
				alertingDialogOneButton.show(getSupportFragmentManager(), "error 1 alerting dialog");
			}
			else if(result.equals("no internet"))
			{
				alertingDialogOneButton = AlertingDialogOneButton.newInstance("Erreur", 
																			"Problème de connexion, veuillez vérifier le réglage de connexion de votre téléphone",																			
																			R.drawable.action_alert);
				alertingDialogOneButton.show(getSupportFragmentManager(), "error 1 alerting dialog");
				
			}
			else if (result.equals("successful"))
			{	
				populateListView();	
			}
			
		}

		
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
 
        MenuItem corbeille = menu.add(0,0,0,"Corbeille");
        {
        	corbeille.setIcon(R.drawable.action_delete);
        	corbeille.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);           
        }
        
        MenuItem apropos = menu.add(0,1,1,"A propos");
        {
            apropos.setIcon(R.drawable.action_about);
            apropos.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);           
        }

        
		return true;
	}
	
	
	private AlertingDialogOneButton alertingDialogOneButton;
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		switch (item.getItemId()) 
		{
			case 0:
				
				if (this.listeLivresLong.size() > 0 || this.listeLivresDisponible.size() > 0)
				{
					this.livresLongCorbeille = new ArrayList<String>();
					this.livresDisponibleCorbeille = new ArrayList<String>();
					
					this.livresLongCorbeille.addAll(this.alertesAdapter.getLongAdapter().getLivresPanier());
					this.livresDisponibleCorbeille.addAll(this.alertesAdapter.getDisponibleAdapter().getLivresPanier());


					if (this.livresLongCorbeille.size() == 0 && this.livresDisponibleCorbeille.size() == 0)
					{
						this.alertingDialogOneButton = AlertingDialogOneButton.newInstance("Erreur", 
																						"Veuillez choisir au moins un livre",																			
																						R.drawable.action_about);
						this.alertingDialogOneButton.show(getSupportFragmentManager(), "error 1 alerting dialog");
					}
					else
					{
						new retirerAlertes().execute();
					}
				}
				
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
	
	
	public class retirerAlertes extends AsyncTask<String, Integer, String>
	{
		private AlertingDialogOneButton alertingDialogOneButton;
		
		private Connection connection;
		

		public retirerAlertes()
		{
			this.connection = Connection.getInstance();
			this.connection.initialize();
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
				this.connection.retirerAlertes(livresLongCorbeille, "");
				this.connection.retirerAlertes(livresDisponibleCorbeille, "");
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
																			"Erreur inconnue s'est produite, veuillez réessayer plus tard",																			
																			R.drawable.action_alert);
				alertingDialogOneButton.show(getSupportFragmentManager(), "error 1 alerting dialog");
			}
			else if(result.equals("no internet"))
			{
				alertingDialogOneButton = AlertingDialogOneButton.newInstance("Erreur", 
																			"Problème de connexion, veuillez vérifier le réglage de connexion de votre téléphone",																			
																			R.drawable.action_alert);
				alertingDialogOneButton.show(getSupportFragmentManager(), "error 1 alerting dialog");
				
			}
			else if (result.equals("successful"))
			{	
				//System.out.println("Ajout Successful");
				Toast.makeText(getBaseContext(), "Retirer avec succès", Toast.LENGTH_LONG).show();
			
				new RecupererLivresAlertes().execute();
			}
			
		}

	}
	
}
