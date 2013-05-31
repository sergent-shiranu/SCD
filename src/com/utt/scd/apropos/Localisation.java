package com.utt.scd.apropos;

import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import it.sephiroth.android.library.imagezoom.ImageViewTouch.OnImageViewTouchDoubleTapListener;
import it.sephiroth.android.library.imagezoom.ImageViewTouch.OnImageViewTouchSingleTapListener;
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase.OnDrawableChangeListener;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.utt.scd.R;
import com.utt.scd.SCD;
import com.utt.scd.dialog.AlertingDialogOneButton;
import com.utt.scd.model.Connection;

public class Localisation extends SherlockFragmentActivity 
{
	private ImageViewTouch localisation;
	private static final String LOG_TAG = "image-test";
	
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
		
		
		this.localisation =  (ImageViewTouch) findViewById(R.id.image);
		

		new RecupererLocalisation().execute(extras.getString("url"));
		
		this.localisation.setSingleTapListener( new OnImageViewTouchSingleTapListener() {

			@Override
			public void onSingleTapConfirmed() 
			{
				Log.d( LOG_TAG, "onSingleTapConfirmed" );
			}
		} );

		this.localisation.setDoubleTapListener( new OnImageViewTouchDoubleTapListener() {

			@Override
			public void onDoubleTap() 
			{
				Log.d( LOG_TAG, "onDoubleTap" );
			}
		} );

		this.localisation.setOnDrawableChangedListener( new OnDrawableChangeListener() {

			@Override
			public void onDrawableChanged( Drawable drawable ) 
			{
				Log.i( LOG_TAG, "onBitmapChanged: " + drawable );
			}
		} );
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

			}
			
		}
	
	}
}
