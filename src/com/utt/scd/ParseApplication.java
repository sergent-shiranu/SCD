package com.utt.scd;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.PushService;

public class ParseApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		// Add your initialization code here
		Parse.initialize(this, "UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx", "XqnwGIwr89qMXkPcohKVmny8lYVEyzu58Osh9qW8");
		PushService.setDefaultPushCallback(this, SCD.class);
		PushService.subscribe(this,  "", SCD.class);
		ParseInstallation.getCurrentInstallation().saveInBackground();
		

		ParseUser.enableAutomaticUser();
		ParseACL defaultACL = new ParseACL();
	    
		// If you would like all objects to be private by default, remove this line.
		defaultACL.setPublicReadAccess(true);
		
		ParseACL.setDefaultACL(defaultACL, true);
	}

}
