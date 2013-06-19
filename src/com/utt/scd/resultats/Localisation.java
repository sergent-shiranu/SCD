package com.utt.scd.resultats;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import uk.co.senab.photoview.PhotoViewAttacher;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.utt.scd.R;
import com.utt.scd.SCD;
import com.utt.scd.apropos.Apropos;
import com.utt.scd.dialog.AlertingDialogOneButton;
import com.utt.scd.model.Connection;

public class Localisation extends SherlockFragmentActivity implements OnClickListener 
{
	private TextView titre, auteur, cote;
	private ImageView couverture;
	private Bitmap bi;
	
	private ImageView localisation;
	private PhotoViewAttacher mAttacher;
	
	private Bitmap bitmap;
	
	private Bundle extras;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		setTheme(SCD.THEME);
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS); 
		
		setContentView(R.layout.localisation);
		
		getSupportActionBar().setHomeButtonEnabled(true);
		
		this.extras = getIntent().getExtras();
		
		this.couverture = (ImageView) findViewById(R.id.imageView1);
		this.couverture.setOnClickListener(this);
		
		this.bi = this.extras.getParcelable("couverture");
		this.couverture.setImageBitmap(bi);
		
		this.titre = (TextView) findViewById(R.id.textView1);
		this.titre.setText("Titre : " + this.extras.getString("titre"));
		this.auteur = (TextView) findViewById(R.id.textView2);
		this.auteur.setText(this.extras.getString("auteur"));
		this.cote = (TextView) findViewById(R.id.textView3);
		this.cote.setText("Cote : " + this.extras.getString("cote"));
		
		
		this.localisation =  (ImageView) findViewById(R.id.image);
		
		this.mAttacher = new PhotoViewAttacher(localisation);
		

		new RecupererLocalisation().execute(extras.getString("url"));
		
		
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
	
	public class RecupererLocalisation extends AsyncTask<String, Integer, String>
	{
		private AlertingDialogOneButton alertingDialogOneButton;
		
		public Connection connection;
		private Bitmap bit;

		public RecupererLocalisation()
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
				bit = BitmapFactory.decodeStream((InputStream)new URL(arg0[0]).getContent());

			} 
			catch (MalformedURLException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "fail";
			} 
			catch (IOException e) 
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

				bitmap = bit;
				localisation.setImageBitmap(bitmap);
				mAttacher.update();

			}
			
		}
	
	}
	
	@Override
	public void onClick(View v) 
	{
		if (v.equals(couverture))
		{
			zoomCouverture();
		}
		
		
	}
	
	@SuppressWarnings("deprecation")
	public void zoomCouverture()
	{
		Dialog dialog = new Dialog(this);
		LayoutInflater factory = LayoutInflater.from(this);
		
        View view = factory.inflate(R.layout.zoom_couverture, null);
		ImageView cvt = (ImageView) view.findViewById(R.id.imageView1);
		//cvt.setImageBitmap(bitmap);
		
		WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		
		int new_width = display.getWidth()*5/6;
		
		int new_height = bi.getHeight()*display.getWidth()*5/6/ (bi.getWidth());


		Bitmap resized = Bitmap.createScaledBitmap(bi, new_width, new_height, true);
		cvt.setImageBitmap(resized);
		
		dialog.requestWindowFeature((int) Window.FEATURE_NO_TITLE);
		dialog.setContentView(view);
		dialog.setCanceledOnTouchOutside(true);
		dialog.show();
	}
}
