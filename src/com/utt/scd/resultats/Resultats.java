package com.utt.scd.resultats;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
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
	
	private ArrayList<String> livresPanier;
	
	
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
	        
	        this.nombre_resultat.setText(this.adapter.getCount() +  " résultat(s)");
			this.nombre_resultat.setTypeface(null, Typeface.BOLD);
		}
		else
		{
			this.list.setVisibility(View.GONE);
			
			this.nombre_resultat.setText("Aucun résultat");
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
        
        MenuItem apropos = menu.add(0,1,1,"Refresh");
        {
            apropos.setIcon(R.drawable.action_about);
            apropos.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);           
        }

        
		return true;
	}
	
	
	
	private AlertDialog choice ;
	private AlertingDialogOneButton alertingDialogOneButton;
	private LayoutInflater inflater;
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		switch (item.getItemId()) 
		{
			case 0:
				
				if (this.listLivres.size() > 0)
				{
					livresPanier = new ArrayList<String>();
					
					livresPanier = this.adapter.getLivresPanier();

					if (livresPanier.size() == 0)
					{
						alertingDialogOneButton = AlertingDialogOneButton.newInstance("Erreur", 
																						"Veuillez choisir au moins un livre",																			
																						R.drawable.action_about);
						alertingDialogOneButton.show(getSupportFragmentManager(), "error 1 alerting dialog");
					}
					else
					{
						/*for (String a : livresPanier)
						{
							System.out.println(a);
						}*/
						
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
							new ajouterCollection().execute();
						}
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
				//System.out.println("Identification successful");
				Toast.makeText(getBaseContext(), "Login avec succès", Toast.LENGTH_LONG).show();
				new ajouterCollection().execute();
			}
			
		}

		
		
	}

	
	
	public class ajouterCollection extends AsyncTask<String, Integer, String>
	{
		private AlertingDialogOneButton alertingDialogOneButton;
		
		private Connection connection;
		

		public ajouterCollection()
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
				this.connection.ajouterLivreCollection(livresPanier);
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
				Toast.makeText(getBaseContext(), "Ajout avec succès", Toast.LENGTH_LONG).show();
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
