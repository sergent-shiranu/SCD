package com.utt.scd.periodiques;

import java.util.LinkedList;
import java.util.List;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

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


public class Periodiques extends SherlockFragmentActivity implements OnChildClickListener
{
	/*private PeriodiquesAdapter pediodiquesAdapter;
	
	private ViewPager mPager;
    private PageIndicator mIndicator;
	
	private LinkedList<TypePeriodiques> listPeriodiques;

	
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

		
		this.listPeriodiques = new LinkedList<TypePeriodiques>();
		
		new RecherchePeriodiques().execute();
		
		
        
	}
	
	
	public void populateView()
	{
        
		if (this.listPeriodiques != null && this.listPeriodiques.size() > 0)
		{
			for (TypePeriodiques tp : listPeriodiques)
			{
				for (ParseObject ob : tp.getList())
				{
					System.out.println("Titre : " + ob.getString("Categorie"));
				}
			}

			this.mPager = (ViewPager)findViewById(R.id.pager);
			this.pediodiquesAdapter = new PeriodiquesAdapter(this, getSupportFragmentManager(), this.listPeriodiques);
			this.mPager.setAdapter(this.pediodiquesAdapter);
			
			this.mIndicator = (TitlePageIndicator) findViewById(R.id.indicator);
			this.mIndicator.setViewPager(this.mPager);
	
		}
	}
	
	
	public class RecherchePeriodiques extends AsyncTask<String, Integer, String>
	{
		private AlertingDialogOneButton alertingDialogOneButton;
		
		public Connection connection;
		public List<ParseObject> resultats;

		public RecherchePeriodiques()
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
				
				listPeriodiques.add(informatique);
				listPeriodiques.add(ecologie);
				listPeriodiques.add(automobile);
				listPeriodiques.add(sciences);

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
				populateView();
			}
			
		}

		
		
	}*/
	
	
	private PeriodiquesAdapter adapter;
	private ExpandableListView list;

	private LinkedList<TypePeriodiques> listPeriodiques;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		setTheme(SCD.THEME);
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS); 

		setContentView(R.layout.periodiques);

		getSupportActionBar().setHomeButtonEnabled(true);

		this.list = (ExpandableListView) findViewById(R.id.expandableListView1);
		this.list.setOnChildClickListener(this);
		this.listPeriodiques = new LinkedList<TypePeriodiques>();

		new RecherchePeriodiques().execute();
	}


	public void populateView()
	{
		if (this.listPeriodiques != null && this.listPeriodiques.size() > 0)
		{
			for (TypePeriodiques tp : listPeriodiques)
			{
				for (ParseObject ob : tp.getList())
				{
					System.out.println("Titre : " + ob.getString("Categorie"));
				}
			}



			this.adapter = new PeriodiquesAdapter(this, this.listPeriodiques);
			this.list.setAdapter(adapter);
		}
	}

	public class RecherchePeriodiques extends AsyncTask<String, Integer, String>
	{
		private AlertingDialogOneButton alertingDialogOneButton;

		public Connection connection;
		public List<ParseObject> resultats;

		public RecherchePeriodiques()
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

				listPeriodiques.add(informatique);
				listPeriodiques.add(ecologie);
				listPeriodiques.add(automobile);
				listPeriodiques.add(sciences);

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


				populateView();
			}

		}



	}

	@Override
	public boolean onChildClick(ExpandableListView expandablelistview, View clickedView, int groupPosition, int childPosition, long childId) 
	{
		Intent intent = new Intent(this, PeriodiqueDetail.class);
		intent.putExtra("objectId", this.adapter.getId(groupPosition, childPosition));
		startActivity(intent);

		return true;
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
	
	


	
	
	
	

}
