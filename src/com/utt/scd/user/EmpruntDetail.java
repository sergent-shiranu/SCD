package com.utt.scd.user;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
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
import com.utt.scd.resultats.LivreDetail;

public class EmpruntDetail extends SherlockFragmentActivity implements OnClickListener 
{
	
	private Bundle extras;
	private ParseObject exemplaire;
	private TextView titre,auteur,cote;
	private ImageView couverture;private Bitmap bit;
	private TextView date_emprunt,date_retour,renouvelable;
	private Button acceder_fiche;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		setTheme(SCD.THEME);
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS); 
		
		setContentView(R.layout.emprunt_detail);
		
		setSupportProgressBarIndeterminateVisibility(false); 
		
		getSupportActionBar().setHomeButtonEnabled(true);
		
		this.extras = getIntent().getExtras();
		
		this.couverture = (ImageView) findViewById(R.id.imageView1);
		
		this.titre = (TextView) findViewById(R.id.textView1);
		this.auteur = (TextView) findViewById(R.id.textView2);
		this.cote = (TextView) findViewById(R.id.textView3);
		
		this.date_emprunt = (TextView) findViewById(R.id.date_emprunt);
		this.date_retour = (TextView) findViewById(R.id.date_retour);
		this.renouvelable = (TextView) findViewById(R.id.renouvelable);
		
		this.acceder_fiche = (Button) findViewById(R.id.fiche);
		this.acceder_fiche.setOnClickListener(this);
		
		new recupererLivresEmprunter().execute();
	}
	
	public void populateView()
	{
		if (exemplaire != null && bit != null)
		{
			couverture.setImageBitmap(bit);
			
			this.titre.setText(extras.getString("Titre"));
			this.auteur.setText(extras.getString("Auteur"));
			this.cote.setText(extras.getString("Cote"));
			
			Date du = (Date)exemplaire.getDate("date_emprunt");
			
			Format formatter  = new SimpleDateFormat("dd/MM/yyyy");
			
			this.date_emprunt.setText("Date d'emprunt : " + formatter.format(du));
			
			
			Date dr = (Date)exemplaire.getDate("retour");
			
			this.date_retour.setText("Date de retour : " + formatter.format(dr));
			
			boolean re = exemplaire.getBoolean("renouvelable");
			if(re)
			{
				this.renouvelable.setText("Renouvelable : Oui" );
			}
			else
			{
				this.renouvelable.setText("Renouvelable : Non" );
			}
		}
	}
	
	
	public class recupererLivresEmprunter extends AsyncTask<String, Integer, String>
	{
		private AlertingDialogOneButton alertingDialogOneButton;
		
		private Connection connection;
		private List<ParseObject> exemplaires;
		

		public recupererLivresEmprunter()
		{
			connection = Connection.getInstance();
			connection.initialize();
			
			this.exemplaires = new LinkedList<ParseObject>();
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
				
				try 
				{
					bit = BitmapFactory.decodeStream((InputStream)new URL(extras.getString("url")).getContent());
					
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
				
				
				System.out.println("data id : " + extras.getString("objectId"));
				this.exemplaires = this.connection.recupererExemplaireEmprunter(extras.getString("objectId"));
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
				System.out.println("Successful");
				
				for (ParseObject obj : exemplaires)
				{
					System.out.println(obj.getObjectId()+"++++++++++++++");
				}
				
				exemplaire = this.exemplaires.get(0);
				populateView();
			}
			
		}

		
		
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
 
        MenuItem recherche_simple = menu.add(0,0,0,"A propos");
        {
        	recherche_simple.setIcon(R.drawable.action_about);
        	recherche_simple.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);              
        }
        
 
        
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		switch (item.getItemId()) 
		{
			case 0:

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
	public void onClick(View v) 
	{
		Intent intent = new Intent(this, LivreDetail.class);
		intent.putExtra("objectId", extras.getString("objectId"));
		
		startActivity(intent);
		
	}
	
}
