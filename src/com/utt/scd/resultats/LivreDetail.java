package com.utt.scd.resultats;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.utt.scd.apropos.Localisation;
import com.utt.scd.dialog.AlertingDialogOneButton;
import com.utt.scd.model.Connection;
import com.utt.scd.model.ConnectionNotInitializedException;
import com.utt.scd.resultats.Resultats.ajouterCollection;
import com.utt.scd.resultats.Resultats.login;
import com.utt.scd.resultats.specifiedcomponent.AdapterListLinearLayout;
import com.utt.scd.resultats.specifiedcomponent.ListLinearLayout;

public class LivreDetail extends SherlockFragmentActivity implements OnClickListener 
{
	
	private Bundle bundle;
	private String objectId;
	private ParseObject livre;
	private List<ParseObject> exemplaires;
	
	private TextView titre, auteur, cote, isbn, editeur, annee, support;
	private ImageView couverture;
	private Bitmap bitmap;
	
	private ImageView imageView;
	private ListLinearLayout list;
	private AdapterListLinearLayout adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		setTheme(SCD.THEME);
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS); 
		
		setContentView(R.layout.livre_detail);
		
		getSupportActionBar().setHomeButtonEnabled(true);
		
		this.couverture = (ImageView) findViewById(R.id.imageView1);
		
		this.titre = (TextView) findViewById(R.id.textView1);
		this.auteur = (TextView) findViewById(R.id.textView2);
		this.cote = (TextView) findViewById(R.id.textView3);
		this.isbn = (TextView) findViewById(R.id.textView4);
		this.editeur = (TextView) findViewById(R.id.textView5);
		this.annee = (TextView) findViewById(R.id.textView6);
		this.support = (TextView) findViewById(R.id.textView7);
		
		this.bundle = getIntent().getExtras();
		
		this.objectId = this.bundle.getString("objectId");
		
		this.imageView = (ImageView) findViewById(R.id.imageView2);
		this.imageView.setVisibility(View.INVISIBLE);
		this.list = (ListLinearLayout) findViewById(R.id.listView1);
		this.list.setOnClickListener(this);
		
		new RecupererExemplaires().execute(new String[]{this.objectId});
	}
	
	
	public void populateView()
	{
		if (livre != null & exemplaires != null)
		{
			this.titre.setText((String)livre.get("Titre"));
			
			String au = "Auteur : ";
			
			@SuppressWarnings("unchecked")
			ArrayList<String> ar = (ArrayList<String>) livre.get("Auteur");
			
			if (ar.size() == 1)
			{
				au = ar.get(0);
			}
			else
			{
				for (int i = 0 ; i < ar.size() -1 ; i ++)
				{
					au += ar.get(i) + "-";
				}
				au += ar.get(ar.size()-1);
			}
			
			
			this.auteur.setText(au);
			this.cote.setText("Cote : " + (String)livre.get("Cote"));
			this.isbn.setText("ISBN : " + (String)livre.get("ISBN"));
			this.editeur.setText("Editeur : " + (String)livre.get("Editeur"));
			this.annee.setText("Année : " + (String)livre.get("Annee"));
			
			String sup = "Support : ";
			
			for(ParseObject ex : exemplaires)
			{
				if (!sup.contains((String)ex.get("support")))
				{
					sup += (String)ex.get("support") + "-";
				}
				
			}
			this.support.setText(sup.substring(0, sup.length()-1));
			
			this.couverture.setImageBitmap(bitmap);
			
			this.adapter = new AdapterListLinearLayout(this, this.exemplaires);
			this.list.setAdapter(adapter);
			
			
		}
	}
	
	
	
	public class RecupererExemplaires extends AsyncTask<String, Integer, String>
	{
		private AlertingDialogOneButton alertingDialogOneButton;
		
		public Connection connection;
		public List<ParseObject> resultats;
		
		private ParseObject liv;
		private Bitmap bit;

		public RecupererExemplaires()
		{
			connection = Connection.getInstance();
			connection.initialize();
			
			this.resultats = new LinkedList<ParseObject>();
			this.bit = null;
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
				this.liv = connection.rechercheCached(arg0[0]);
				
				bit = BitmapFactory.decodeStream((InputStream)new URL(this.liv.getParseFile("couverture").getUrl()).getContent());
				
				this.resultats = connection.recupererExemplaire(this.liv);
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
			catch (MalformedURLException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
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
			else if(result.equals("no internet"))
			{
				alertingDialogOneButton = AlertingDialogOneButton.newInstance("Erreur", 
																			result,																			
																			R.drawable.action_search);
				alertingDialogOneButton.show(getSupportFragmentManager(), "error 1 alerting dialog");
				
			}
			else if (result.equals("successful"))
			{	
				
				
				for (int i = 0 ; i < resultats.size() ; i++)
				{
					System.out.println("xxx : " + resultats.get(i).get("etat"));
				}
				
				exemplaires = resultats;
				livre = liv;
				bitmap = bit;
				populateView();
				
				
			}
			
		}
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		MenuItem panier = menu.add(0,0,0,"Panier");
        {
        	panier.setIcon(R.drawable.action_cart);
        	panier.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);              
        }
        
        MenuItem alerte = menu.add(0,1,1,"Alerte");
        {
        	alerte.setIcon(R.drawable.action_alerte);
        	alerte.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);              
        }
 
        MenuItem apropos = menu.add(0,2,2,"A propos");
        {
            apropos.setIcon(R.drawable.action_about);
            apropos.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);           
        }
        
		return true;
	}
	
	
	
	private AlertDialog choice ;
	private LayoutInflater inflater;
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		switch (item.getItemId()) 
		{
			case 0:
				
				
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
				
				return true;
				
			case 1:
				
				

				return true;
				
			case 2:
				
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
				ArrayList<String> livresPanier = new ArrayList<String>();
				livresPanier.add(livre.getObjectId());
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
	public void onClick(View v) 
	{
		int position = (Integer) v.getTag();
		
		Intent intent = new Intent(this,Localisation.class);
		intent.putExtra("url", exemplaires.get(position).getParseFile("localisation_image").getUrl());
		startActivity(intent);
	}
}
