package com.utt.scd.resultats.specifiedcomponent;


import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseObject;
import com.utt.scd.R;

public class AdapterListLinearLayout extends BaseAdapter 
{
	protected Context context;
	protected List<ParseObject> listItems;
	protected LayoutInflater inflater;

	
	
	public AdapterListLinearLayout(Context context, List<ParseObject> listItems)
	{
		this.context = context;
		this.listItems = listItems;
		this.inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public void setItems(ArrayList<ParseObject> listItems)
	{
		this.listItems = listItems;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() 
	{
		return this.listItems.size();
	}
	
	@Override
	public Object getItem(int position) 
	{
		return this.listItems.get(position);
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
		
		view = this.inflater.inflate(R.layout.exemplaire_item, null);
		
		TextView pret = (TextView) view.findViewById(R.id.pret);
		pret.setText((String)listItems.get(position).get("duree_pret"));
		
		
		
		TextView etat = (TextView) view.findViewById(R.id.etat);
		String et = (String)listItems.get(position).get("etat");
		if (et.equals("Disponible"))
		{
			et = "Dispo";
		}
		else if (et.equals("Prêté à l'extérieur"))
		{
			et = "Prêté";
		}
		etat.setText(et);
		
		
		TextView localisation = (TextView) view.findViewById(R.id.localisation);
		localisation.setText((String)listItems.get(position).get("localisation"));
		
		
		TextView retour = (TextView) view.findViewById(R.id.retour);
		if (listItems.get(position).has("retour"))
		{
			Date rt = (Date)listItems.get(position).getDate("retour");
			
			Format formatter  = new SimpleDateFormat("dd/MM/yyyy");
			
			retour.setText(formatter.format(rt));

		}
		
		ImageView imageView = (ImageView) view.findViewById(R.id.imageView1);
		if (position == 0)
		{
			imageView.setVisibility(View.INVISIBLE);
			view.setClickable(false);
		}

		//Important ça !!!!
		view.setTag(position);
		
		return view;
	}

}