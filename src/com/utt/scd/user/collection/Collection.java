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

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.utt.scd.R;
import com.utt.scd.SCD;
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
 
        MenuItem panier = menu.add(0,0,0,"Panier");
        {
        	panier.setIcon(R.drawable.action_cart);
        	panier.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);              
        }
        
        MenuItem corbeille = menu.add(0,1,1,"Corbeille");
        {
        	corbeille.setIcon(R.drawable.action_delete);
        	corbeille.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);           
        }
        
        MenuItem apropos = menu.add(0,2,2,"A propos");
        {
            apropos.setIcon(R.drawable.action_about);
            apropos.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);           
        }

        
		return true;
	}
	
	
	
	
	private void populateListView() 
	{
		if (this.listLivres.size() != 0)
		{
			
			this.adapter.setItems(listLivres);
	        this.list.setVisibility(View.VISIBLE);
	        
	        this.nombre_resultat.setText("Vous avez collecté " + this.adapter.getCount() +  " livres");
			this.nombre_resultat.setTypeface(null, Typeface.BOLD);
		}
		else
		{
			this.list.setVisibility(View.GONE);
			
			this.nombre_resultat.setText("Vous n'avez aucun livre");
			this.nombre_resultat.setTypeface(null, Typeface.BOLD);
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
