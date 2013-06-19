package com.utt.scd.MonitorPushNotification;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.utt.scd.R;
import com.utt.scd.SCD;


public class SCDReceiver extends BroadcastReceiver 
{

	public void onReceive(Context context, Intent intent) 
	{
		String action = intent.getAction();
		
		if (action.equals("com.utt.scd.EMPRUNT"))
    	{
			try 
			{
				JSONObject json = new JSONObject(intent.getExtras().getString("com.parse.Data"));
				
				String title = json.getString("titre");
				
				System.out.println("Title : " + title);
				
				//rendre();

			} 
			catch (JSONException e) 
			{
				e.printStackTrace();
			}

    	}
    	else if (action.equals("com.utt.scd.ALERTE"))
    	{
    		try 
			{
				JSONObject json = new JSONObject(intent.getExtras().getString("com.parse.Data"));
				
				String title = json.getString("titre");
				
				String type = json.getString("type");
				
				String message = "Livre " + title + " de type " + type + " est disponible";

				alert(context, intent, message);

			} 
			catch (JSONException e) 
			{
				e.printStackTrace();
			}
    	}
	}


	
	@SuppressWarnings("deprecation")
	public void alert(Context context, Intent intent, String message) 
	{
		//Log.d("PUSH", "Message intent received!");

		// data.message contient le texte de la notification
		String title = "SCD";
		int iconId = R.drawable.action_alert;

		// création de la notification :
		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = new Notification(iconId, message, System.currentTimeMillis()); 

		// création de l'activite à  démarrer lors du clic :
		Intent notificationIntent = new Intent(context, SCD.class);
		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
		notification.setLatestEventInfo(context, title, message, contentIntent);
		notification.defaults |= Notification.DEFAULT_ALL;
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		
		
		notificationManager.notify(30081990, notification);
		
		//context.startActivity(notificationIntent);
		
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("ALERTE");
        broadcastIntent.putExtra("message", message);
        context.sendBroadcast(broadcastIntent);

		
	}
}
