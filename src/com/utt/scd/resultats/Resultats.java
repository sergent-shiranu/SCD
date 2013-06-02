package com.utt.scd.resultats;

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
import com.utt.scd.apropos.Apropos;
import com.utt.scd.dialog.AlertingDialogOneButton;
import com.utt.scd.model.Connection;
import com.utt.scd.model.ConnectionNotInitializedException;

public class Resultats extends SherlockFragmentActivity implements OnItemClickListener 
{
	private ListView list;
	private LivreAdapter adapter;
	private List<ParseObject> listLivres;
	
	private String titre="", auteur="", support="", langue="", uv="", domaine = "";
	private Bundle extras;
	
	private TextView nombre_resultat;
	
	
	
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
		this.adapter = new LivreAdapter(this, listLivres);
        this.list.setAdapter(adapter);
        
        this.extras = getIntent().getExtras();
        
        if (this.extras.containsKey("Titre"))
        {
        	this.titre = getIntent().getExtras().getString("Titre");
        }
        if (this.extras.containsKey("Auteur"))
        {
        	this.auteur = getIntent().getExtras().getString("Auteur");
        }
        if (this.extras.containsKey("Support"))
        {
        	this.support = getIntent().getExtras().getString("Support");
        }
        if (this.extras.containsKey("Langue"))
        {
        	this.langue = getIntent().getExtras().getString("Langue");
        }
        if (this.extras.containsKey("UV"))
        {
        	this.uv = getIntent().getExtras().getString("UV");
        }
        if (this.extras.containsKey("Domaine"))
        {
        	this.domaine = getIntent().getExtras().getString("Domaine");
        }
        
        
        this.nombre_resultat = (TextView) findViewById(R.id.textView1);
		
		String[] data = {titre, auteur, support, langue, uv, domaine};
		new RechercheSimple().execute(data);
	}
	
	
	private void populateListView() 
	{
		if (this.listLivres.size() != 0)
		{
			
			this.adapter.setItems(listLivres);
	        this.list.setVisibility(View.VISIBLE);
	        
	        this.nombre_resultat.setText(this.adapter.getCount() +  " r�sultat(s)");
			this.nombre_resultat.setTypeface(null, Typeface.BOLD);
		}
		else
		{
			this.list.setVisibility(View.GONE);
			
			this.nombre_resultat.setText("Aucun r�sultat");
			this.nombre_resultat.setTypeface(null, Typeface.BOLD);
		}
		
	}
	
	
	public class RechercheSimple extends AsyncTask<String, Integer, String>
	{
		private AlertingDialogOneButton alertingDialogOneButton;
		
		public Connection connection;
		public List<ParseObject> resultats;

		public RechercheSimple()
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
				//connection.doRequest(HttpVerb.GET, API.LIVRES,"");
				//System.out.println("where={\"Titre\":{\"$regex\":\".*[bB][Uu][Ll][Aa][Tt][Ss].*\"}}");
				//connection.doRequest(HttpVerb.GET, API.LIVRES, URLEncoder.encode("where={\"Titre\":{\"$regex\":\".*[bB][Uu][Ll][Aa][Tt][Ss].*\"}}","UTF-8"));
				//connection.doRequest(HttpVerb.GET, API.LIVRES, URLEncoder.encode("where={" + "Titre" + " : { " + "$regex"+ " : " + ".*[bB][Uu][Ll][Aa][Tt][Ss].*"+ "}"+"}" ));
				//this.list = connection.rechercheSimple("bulats");
				
				this.resultats = connection.rechercheAvancee(arg0[0], arg0[1], arg0[2], arg0[3], arg0[4], arg0[5]);
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
			
			System.out.println("hehehehehehe");
			System.out.println(result);
			
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
				
				//livre = listLivres.get(0);
				populateListView();
				
				/*for (int i = 0; i< resultats.size(); i++)
	            {
	            	System.out.println("titre : " + resultats.get(i).get("Titre"));
	            }*/
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
        
        MenuItem apropos = menu.add(0,1,1,"Refresh");
        {
            apropos.setIcon(R.drawable.action_about);
            apropos.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);           
        }
		
		/*getSupportMenuInflater().inflate(R.menu.resultats, menu);
		mPanierMenuItem = menu.findItem(R.id.action_panier);*/
        
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
		
		/*if (item.equals(mPanierMenuItem))
		{
			mPanierMenuItem.setActionView(R.layout.actionbar_indeterminate_progress);
		}*/

		return false;
	}


	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int position, long id) 
	{
		
		Intent intent = new Intent(this, LivreDetail.class);			
		intent.putExtra("objectId", this.adapter.getId(position));
		
		startActivity(intent);
	}
	
	
	
	
}
