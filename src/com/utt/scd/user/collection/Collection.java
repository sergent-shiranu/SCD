package com.utt.scd.user.collection;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
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
import com.utt.scd.resultats.LivreDetail;

public class Collection extends SherlockFragmentActivity implements OnItemClickListener 
{
	private ListView list;
	private CollectionAdapter adapter;
	private List<ParseObject> listLivres;
	
	private TextView nombre_resultat;
	
	private ArrayList<String> livresCorbeille;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		setTheme(SCD.THEME);
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS); 
		
		setContentView(R.layout.resultats);
		
		getSupportActionBar().setHomeButtonEnabled(true);
		
		this.list = (ListView) findViewById(R.id.listView1);
		this.list.setOnItemClickListener(this);
		
		this.listLivres = new ArrayList<ParseObject>();
		this.adapter = new CollectionAdapter(this, listLivres);
        this.list.setAdapter(adapter);
        
        this.nombre_resultat = (TextView) findViewById(R.id.textView1);
        
        new RecupererLivresCollection().execute();
	}
	
	
	
	private void populateListView() 
	{
		if (this.listLivres.size() != 0)
		{
			
			this.adapter.setItems(listLivres);
	        this.list.setVisibility(View.VISIBLE);
	        
	        this.nombre_resultat.setText("Vous avez collect� " + this.adapter.getCount() +  " livres");
			this.nombre_resultat.setTypeface(null, Typeface.BOLD);
		}
		else
		{
			this.list.setVisibility(View.GONE);
			
			this.nombre_resultat.setText("Vous n'avez aucun livre");
			this.nombre_resultat.setTypeface(null, Typeface.BOLD);
		}
		
	}
	
	
	public class RecupererLivresCollection extends AsyncTask<String, Integer, String>
	{
		private AlertingDialogOneButton alertingDialogOneButton;
		
		public Connection connection;
		public List<ParseObject> resultats;

		public RecupererLivresCollection()
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
				this.resultats = connection.recupererLivresCollection();
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
				listLivres = resultats;

				populateListView();
				
			}
			
		}

		
		
	}
	
	
	
	
	//private MenuItem mPanierMenuItem;
	
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
				
				if (this.listLivres.size() > 0)
				{
					this.livresCorbeille = new ArrayList<String>();
					
					this.livresCorbeille = this.adapter.getLivresPanier();

					if (this.livresCorbeille.size() == 0)
					{
						this.alertingDialogOneButton = AlertingDialogOneButton.newInstance("Erreur", 
																						"Veuillez choisir au moins un livre",																			
																						R.drawable.action_about);
						this.alertingDialogOneButton.show(getSupportFragmentManager(), "error 1 alerting dialog");
					}
					else
					{
						new retirerCollection().execute();
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
	
	
	
	public class retirerCollection extends AsyncTask<String, Integer, String>
	{
		private AlertingDialogOneButton alertingDialogOneButton;
		
		private Connection connection;
		

		public retirerCollection()
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
				this.connection.retirerLivreCollection(livresCorbeille);
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
				//System.out.println("Ajout Successful");
				Toast.makeText(getBaseContext(), "Retirer avec succ�s", Toast.LENGTH_LONG).show();
			
				new RecupererLivresCollection().execute();
			}
			
		}

	}
	
	
	
	


	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int position, long id) 
	{
		
		Intent intent = new Intent(this, LivreDetail.class);			
		intent.putExtra("objectId", this.adapter.getId(position));
		
		startActivity(intent);
	}
}
