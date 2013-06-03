package com.utt.scd.user;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.parse.ParseUser;
import com.utt.scd.R;
import com.utt.scd.SCD;
import com.utt.scd.apropos.Apropos;

public class CompteLecteur extends SherlockFragmentActivity implements OnClickListener 
{
	
	private TextView nom_prenom;
	private Button collection, alertes;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		setTheme(SCD.THEME);
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS); 
		
		setContentView(R.layout.compte_lecteur);
		
		getSupportActionBar().setHomeButtonEnabled(true);
		
		this.nom_prenom = (TextView) findViewById(R.id.nom_prenom);
		this.nom_prenom.setText(ParseUser.getCurrentUser().getString("nom") + " " + ParseUser.getCurrentUser().getString("prenom"));
		
		this.collection = (Button) findViewById(R.id.collection);
		this.collection.setOnClickListener(this);
		this.alertes = (Button) findViewById(R.id.alertes);
		this.alertes.setOnClickListener(this);
		
		
		
		/*ParseUser.logOut();
		ParseUser.logInInBackground("nguyenn2", "12345678", new LogInCallback() {
			  public void done(ParseUser user, ParseException e) {
			    if (user != null) {
			      System.out.println("Okkkkkkkkkkkkkkk");
			    } else {
			      // Signup failed. Look at the ParseException to see what happened.
			    }
			  }
			});
		ParseUser.logOut();
		String uid = ParseUser.getCurrentUser().getObjectId();
		if (uid != null) {
			System.out.println("Koooooooooooo");
			System.out.println(uid + "hehehe");
			} else {
			  // show the signup or login screen
			}
		ParseUser.logOut();*/
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		MenuItem logout = menu.add(0,0,0,"Logout");
        {
        	logout.setIcon(R.drawable.action_logout);
        	logout.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);           
        }
 
        MenuItem apropos = menu.add(0,1,1,"A propos");
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
				
				ParseUser.logOut();
				
				this.finish();
	
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}
