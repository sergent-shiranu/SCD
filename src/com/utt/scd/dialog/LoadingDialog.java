package com.utt.scd.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockDialogFragment;
import com.utt.scd.R;

public class LoadingDialog extends SherlockDialogFragment 
{
	ProgressDialog progDialog;

	public static LoadingDialog newInstance(String title, String message) 
	{
		LoadingDialog frag = new LoadingDialog();
		Bundle args = new Bundle();
		args.putString("title", title);
		args.putString("message", message);
		frag.setArguments(args);

		return frag;
	}

	public Dialog onCreateDialog(Bundle savedInstanceState) 
	{
		String title = getArguments().getString("title");
		String message = getArguments().getString("message");

		progDialog = new ProgressDialog(getActivity());
		progDialog.setIcon(R.drawable.sablier);
		progDialog.setCancelable(false);
		progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progDialog.setTitle(title);
		progDialog.setMessage(message);

		return progDialog;
	}
}
