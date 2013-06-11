package com.utt.scd.periodiques;

import java.util.LinkedList;
import java.util.List;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

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


public class Periodiques extends SherlockFragmentActivity implements OnItemClickListener
{
	private LinkedList<TypePeriodiques> listPeriodiques;
	private GridView gridView;
	private PeriodiquesAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		setTheme(SCD.THEME);
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS); 

		setContentView(R.layout.periodiques);
		
		setSupportProgressBarIndeterminateVisibility(false); 
		
		getSupportActionBar().setHomeButtonEnabled(true);

		this.gridView = (GridView) findViewById(R.id.gridview);
		this.gridView.setOnItemClickListener(this);
		
		
		this.listPeriodiques = new LinkedList<TypePeriodiques>();

		
		this.adapter = new PeriodiquesAdapter(this, this.listPeriodiques);
		
		this.gridView.setAdapter(adapter);
		this.gridView.setOnItemClickListener(this);
		
		new RecherchePeriodiques().execute();
		
	}

	
	
	
	
	public void populateView()
	{
		if (this.listPeriodiques.size() > 0)
		{
			for (TypePeriodiques a : this.listPeriodiques)
			{
				System.out.println("ייייייייייייייייי");
				System.out.println(a.getNom());
			}
			
			this.adapter = new PeriodiquesAdapter(this, this.listPeriodiques);
			
			this.gridView.setAdapter(adapter);
			this.gridView.setOnItemClickListener(this);
		}
	}
	
	
	
	
	public class RecherchePeriodiques extends AsyncTask<String, Integer, String>
	{
		private AlertingDialogOneButton alertingDialogOneButton;

		private Connection connection;
		private List<ParseObject> resultats;
		private LinkedList<TypePeriodiques> list;

		public RecherchePeriodiques()
		{
			this.connection = Connection.getInstance();
			this.connection.initialize();

			this.resultats = new LinkedList<ParseObject>();
			this.list = new LinkedList<TypePeriodiques>();
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
				this.resultats = connection.recupererToutesPeriodiques();

				TypePeriodiques informatique = new TypePeriodiques("Informatique");
				TypePeriodiques ecologie = new TypePeriodiques("Ecologie");
				TypePeriodiques automobile = new TypePeriodiques("Automobile");
				TypePeriodiques sciences = new TypePeriodiques("Sciences");

				for (ParseObject ob : resultats)
				{
					if (ob.getString("Categorie").equals("Informatique")) 
					{
						informatique.ajouterElement(ob);
					}
					else if (ob.getString("Categorie").equals("Ecologie"))
					{
						ecologie.ajouterElement(ob);
					}
					else if (ob.getString("Categorie").equals("Automobile"))
					{
						automobile.ajouterElement(ob);
					}
					else if (ob.getString("Categorie").equals("Sciences"))
					{
						sciences.ajouterElement(ob);
					}	
				}

				this.list.add(informatique);
				this.list.add(ecologie);
				this.list.add(automobile);
				this.list.add(sciences);

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
				listPeriodiques = this.list;
				populateView();
			}

		}



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

				Intent intent = new Intent(this, Apropos.class);
				startActivity(intent);
			
				
				return true;
	
			case android.R.id.home:
	
				this.finish();
	
				return true;
	
		};

		return false;
	}



	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int position, long id) 
	{
		
		
	}
}
