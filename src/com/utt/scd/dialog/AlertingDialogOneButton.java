package com.utt.scd.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockDialogFragment;


public class AlertingDialogOneButton extends SherlockDialogFragment
{
	public static AlertingDialogOneButton newInstance(String title, String message, int icon)
	{
		AlertingDialogOneButton frag = new AlertingDialogOneButton();
		Bundle args = new Bundle();
		args.putString("title", title);
		args.putString("message", message);
		args.putInt("icon", icon);
		frag.setArguments(args);
		
		return frag;
	}
	
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		String title = getArguments().getString("title");
		String message = getArguments().getString("message");
		int icon = getArguments().getInt("icon");
		
		AlertDialog.Builder aldg = new AlertDialog.Builder(getActivity());
		
		aldg.setIcon(getActivity().getResources().getDrawable(icon));
		aldg.setTitle(title);
		aldg.setMessage(message);

		
		aldg.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) 
			{
				if (getSherlockActivity().getClass().getSimpleName().equals("Identifications"))
				{
					getSherlockActivity().finish();
				}
			}
		});
		
		
		return aldg.create();
	}
}
