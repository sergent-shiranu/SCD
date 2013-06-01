package com.utt.scd.periodiques;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.EditText;
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

public class PeriodiqueDetail extends SherlockFragmentActivity 
{
	private Bundle bundle;
	private String objectId;
	private ParseObject periodique;
	
	private TextView titre,description,numero;
	private ImageView localisation;
	private Bitmap bitmap;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		setTheme(SCD.THEME);
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS); 
		
		setContentView(R.layout.periodique_detail);
		
		getSupportActionBar().setHomeButtonEnabled(true);
		
		this.titre = (TextView) findViewById(R.id.textView1);
		this.description = (TextView) findViewById(R.id.description);
		this.numero = (TextView) findViewById(R.id.textView4);
		
		this.bundle = getIntent().getExtras();
		
		this.objectId = this.bundle.getString("objectId");
		
		this.localisation = (ImageView) findViewById(R.id.imageView1);
		
		new RecupererPeriodique().execute(new String[]{this.objectId});
	}
	
	public void populateView()
	{
		if (periodique != null & bitmap != null)
		{
			this.titre.setText(periodique.getString("Titre"));
			this.description.setText(periodique.getString("Description"));
			
			Date da = periodique.getDate("Date");
			Format formatter  = new SimpleDateFormat("dd/MM/yyyy");
			
			this.numero.setText("Numéro " + periodique.getInt("Numero") + " " + formatter.format(da));
			
			this.localisation.setImageBitmap(bitmap);
		}
	}
	
	
	public class RecupererPeriodique extends AsyncTask<String, Integer, String>
	{
		private AlertingDialogOneButton alertingDialogOneButton;
		
		public Connection connection;
		
		private ParseObject ped;
		private Bitmap bit;

		public RecupererPeriodique()
		{
			connection = Connection.getInstance();
			connection.initialize();

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
				this.ped = connection.recupererPeriodique(arg0[0]);
				
				bit = BitmapFactory.decodeStream((InputStream)new URL(this.ped.getParseFile("localisation_image").getUrl()).getContent());
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
				
				periodique = ped;
				bitmap = bit;
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
				
				Intent intent = new Intent(this,Apropos.class);
				startActivity(intent);

				return true;

	
			case android.R.id.home:
	
				this.finish();
	
				return true;
	
		};

		return false;
	}
}
