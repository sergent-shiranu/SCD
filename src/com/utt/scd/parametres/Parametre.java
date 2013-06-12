package com.utt.scd.parametres;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.utt.scd.R;
import com.utt.scd.SCD;
import com.utt.scd.apropos.Apropos;
import com.utt.scd.dialog.AlertingDialogOneButton;
import com.utt.scd.model.Connection;
import com.utt.scd.model.ConnectionNotInitializedException;

public class Parametre extends SherlockFragmentActivity implements OnCheckedChangeListener, OnClickListener 
{
	
	private ToggleButton rappel_prets, rappel_alertes;
	
	private TextView frequence, anticipation, theme;
	
	private SharedPreferences prefs;
	private Editor editor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		setTheme(SCD.THEME);
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS); 
		
		setContentView(R.layout.parametres);
		
		setSupportProgressBarIndeterminateVisibility(false);
		
		getSupportActionBar().setHomeButtonEnabled(true);
		
		this.rappel_prets = (ToggleButton) findViewById(R.id.ParamToggleButton);
		this.rappel_prets.setOnCheckedChangeListener(this);
		
		this.rappel_alertes = (ToggleButton) findViewById(R.id.ParamToggleButton2);
		this.rappel_alertes.setOnCheckedChangeListener(this);
		
		this.frequence = (TextView) findViewById(R.id.textView4);
		this.frequence.setBackgroundColor(SCD.color());
		this.frequence.setOnClickListener(this);
		this.anticipation = (TextView) findViewById(R.id.textView6);
		this.anticipation.setBackgroundColor(SCD.color());
		this.anticipation.setOnClickListener(this);
		this.theme = (TextView) findViewById(R.id.textView10);
		this.theme.setBackgroundColor(SCD.color());
		if (SCD.THEME == R.style.Theme_Dark_blue)
		{
			this.theme.setText("Bleu");
		}
		else if (SCD.THEME == R.style.Theme_Dark_green)
		{
			this.theme.setText("Vert");
		}
		else if (SCD.THEME == R.style.Theme_Dark_purple)
		{
			this.theme.setText("Violet");
		}
		else if (SCD.THEME == R.style.Theme_Dark_red)
		{
			this.theme.setText("Rouge");
		}
		else
		{
			this.theme.setText("Jaune");
		}
		this.theme.setOnClickListener(this);
		
		
		this.prefs = getSharedPreferences("XXX", MODE_PRIVATE);
		this.editor = prefs.edit();
		
		ParseUser user = ParseUser.getCurrentUser();
		
		
		if (prefs.getString("rappel_pret", "").equals("1"))
		{
			this.rappel_prets.setChecked(true);
		}
		else if (prefs.getString("frequence_rappel", "").equals(""))
		{
			this.rappel_prets.setChecked(false);
		}
		
		
		
		
		
		if (prefs.getString("frequence_rappel", "").equals("Non défini"))
		{
			this.frequence.setText("Non défini");
		}
		else if (prefs.getString("frequence_rappel", "").equals("1 fois par jour"))
		{
			this.frequence.setText("1 fois par jour");
		}
		else if (prefs.getString("frequence_rappel", "").equals("2 fois par jour"))
		{
			this.frequence.setText("2 fois par jour");
		}
		else
		{
			this.frequence.setText("Non défini");
		}
		
		
		if (prefs.getString("anticipation", "").equals("Non défini"))
		{
			this.anticipation.setText("Non défini");
		}
		else if (prefs.getString("frequence_rappel", "").equals("2 jours avant"))
		{
			this.anticipation.setText("2 jours avant");
		}
		else if (prefs.getString("frequence_rappel", "").equals("Un seul jour avant"))
		{
			this.anticipation.setText("Un seul jour avant");
		}
		else
		{
			this.anticipation.setText("Non défini");
		}
		
		
		if (user.getObjectId() != null)
		{
			if (user.getBoolean("is_alerted"))
			{
				this.rappel_alertes.setChecked(true);
			}
			else
			{
				this.rappel_alertes.setChecked(false);
			}
		}
		else
		{
			this.rappel_alertes.setChecked(true);
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
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) 
	{
		if (buttonView.equals(rappel_prets))
		{
			if (isChecked)
			{
				
			}
			else
			{
				frequence.setText("Non défini");
				editor.putString("frequence_rappel", "Non défini");
				
				anticipation.setText("Non défini");
				editor.putString("anticipation", "Non défini");
			}
		}
		else
		{
			if (isChecked)
			{
				data[0] = "1";
				new modifierAlerte().execute(data);
			}
			else
			{
				data[0] = "0";
				new modifierAlerte().execute(data);
			}
		}
		
	}
	private String[] data = {""};
	
	
	private AlertDialog choice_frequence, choice_anticipation, choice_theme;
	private AlertingDialogOneButton alertingDialogOneButton;
	@Override
	public void onClick(View v) 
	{
		if (v.equals(frequence))
		{
			if (!rappel_prets.isChecked())
			{
				alertingDialogOneButton = AlertingDialogOneButton.newInstance("Erreur", 
																			"Veuillez activer le Rappel de prêts",																			
																			R.drawable.action_alert);
				alertingDialogOneButton.show(getSupportFragmentManager(), "error 1 alerting dialog");
			}
			else
			{
				final CharSequence[] items = {"Non défini","1 fois par jour","2 fois par jour"};
				
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle("Choix d'anticipation");
				builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) 
					{
						frequence.setText(items[item]);
						choice_frequence.dismiss();    
					}
				});
				choice_frequence = builder.create();
				choice_frequence.setCancelable(true);
				choice_frequence.show();
			}
			
		}
		else if (v.equals(anticipation))
		{
			if (!rappel_prets.isChecked())
			{
				alertingDialogOneButton = AlertingDialogOneButton.newInstance("Erreur", 
																			"Veuillez activer le Rappel de prêts",																			
																			R.drawable.action_alert);
				alertingDialogOneButton.show(getSupportFragmentManager(), "error 1 alerting dialog");
			}
			else
			{
				final CharSequence[] items = {"Non défini","2 jours avant","Un seul jour avant"};
				
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle("Choissisez votre thème");
				builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) 
					{
						anticipation.setText(items[item]);
						choice_anticipation.dismiss();    
					}
				});
				choice_anticipation = builder.create();
				choice_anticipation.setCancelable(true);
				choice_anticipation.show();
			}
			
		}
		else
		{
			final CharSequence[] items = {"Bleu","Vert","Rouge","Violet","Jaune"};
			
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Choissisez la fréquence de rappel");
			builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int item) 
				{
					theme.setText(items[item]);
					choice_theme.dismiss(); 
					
					if (item == 0)
					{
						SCD.THEME = R.style.Theme_Dark_blue;
					}
					else if (item == 1)
					{
						SCD.THEME = R.style.Theme_Dark_green;
					}
					else if (item == 2)
					{
						SCD.THEME = R.style.Theme_Dark_red;
					}
					else if (item == 3)
					{
						SCD.THEME = R.style.Theme_Dark_purple;
					}
					else if (item == 4)
					{
						SCD.THEME = R.style.Theme_Dark_yellow;
					}
					
					Intent intent = new Intent(getApplicationContext(),SCD.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					
					startActivity(intent);
					
				}
			});
			choice_theme = builder.create();
			choice_theme.setCancelable(true);
			choice_theme.show();
		}
		
	}

	
	
	/**
	 * Tâche asynchrône : Activier ou Déactiver les alertes (arg0[0] = "1" ou arg0[0] = "0")
	 * @author mr.giua
	 *
	 */
	public class modifierAlerte extends AsyncTask<String, Integer, String>
	{
		private AlertingDialogOneButton alertingDialogOneButton;
		
		public Connection connection;

		public modifierAlerte()
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
				this.connection.switchAlertingSystem(arg0[0]);
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
																			"Problème inconnu, veuillez vous identifier",																			
																			R.drawable.action_alert);
				alertingDialogOneButton.show(getSupportFragmentManager(), "error 1 alerting dialog");
			}
			else if(result.equals("no internet"))
			{
				alertingDialogOneButton = AlertingDialogOneButton.newInstance("Erreur", 
																			"Problème de connexion, veuillez vérifier le réglage de connexion de votre téléphone",																			
																			R.drawable.action_search);
				alertingDialogOneButton.show(getSupportFragmentManager(), "error 1 alerting dialog");
				
			}
			else if (result.equals("successful"))
			{	

				//Toast.makeText(getBaseContext(), "Votre paramètre est enregistré", Toast.LENGTH_LONG).show();
				
			}
			
		}

		
		
	}
	
}
