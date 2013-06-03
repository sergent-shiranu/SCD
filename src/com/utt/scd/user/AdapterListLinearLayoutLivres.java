package com.utt.scd.user;


import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.parse.ParseObject;
import com.utt.scd.R;

public class AdapterListLinearLayoutLivres extends BaseAdapter 
{
	protected Context context;
	protected List<ParseObject> listLivres;
	protected LayoutInflater inflater;

	
	public AdapterListLinearLayoutLivres(Context context, List<ParseObject> listLivres)
	{
		this.context = context;
		this.listLivres = listLivres;
		this.inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public void setItems(List<ParseObject> listLivres)
	{
		this.listLivres = listLivres;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() 
	{
		return this.listLivres.size();
	}
	
	@Override
	public Object getItem(int position) 
	{
		return this.listLivres.get(position);
	}
	
	@Override
	public long getItemId(int position) 
	{
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{		
		View view = convertView;
		
		view = this.inflater.inflate(R.layout.livre_item, null);

		
		ParseObject livre = this.listLivres.get(position);
		
		view = this.inflater.inflate(R.layout.livre_item, null);
		
		TextView titre = (TextView) view.findViewById(R.id.titre);
		String titre_str = (String)livre.get("Titre");
		if (titre_str.length() > 35)
		{
			titre.setText(titre_str.substring(0, 34)+ "...");
		}
		else
		{
			titre.setText(titre_str);
		}
		

		TextView auteur = (TextView) view.findViewById(R.id.auteur);
		
		@SuppressWarnings("unchecked")
		ArrayList<String> auteur_str = (ArrayList<String>)livre.get("Auteur");
		String auteurs_str="";
		
		for(String at : auteur_str)
		{
			auteurs_str += at;
		}
		
		if (auteurs_str.length() > 35)
		{
			auteur.setText(auteurs_str.substring(0, 34)+ "...");
		}
		else
		{
			auteur.setText(auteurs_str);
		}
		
		CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkBox1);
		checkBox.setVisibility(View.GONE);

		//Important ça !!!!
		view.setTag(position);
		
		return view;
	}

}