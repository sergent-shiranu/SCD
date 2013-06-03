package com.utt.scd.periodiques;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.parse.ParseObject;
import com.utt.scd.R;

public class MagazineAdapter extends BaseAdapter 
{
	protected Context context;
	protected TypePeriodiques typePeriodiques;
	protected LayoutInflater inflater;
	
	public MagazineAdapter(Context context, TypePeriodiques typePeriodiques)
	{
		this.context = context;
		this.typePeriodiques = typePeriodiques;
		this.inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() 
	{
		return this.typePeriodiques.getList().size();
	}

	@Override
	public Object getItem(int position) 
	{
		return this.typePeriodiques.getList().get(position);
	}

	@Override
	public long getItemId(int position) 
	{
		return position;
	}
	
	public String getObjectId(int position)
	{
		return this.typePeriodiques.getList().get(position).getObjectId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		View view = convertView;
		
		ParseObject periodique = this.typePeriodiques.getList().get(position);
		
		view = this.inflater.inflate(R.layout.livre_item, null);
		
		CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkBox1);
		checkBox.setVisibility(View.INVISIBLE);
		
		//Titre
		TextView titre = (TextView) view.findViewById(R.id.titre);
		String titre_str = periodique.getString("Titre");
		if (titre_str.length() > 35)
		{
			titre.setText(titre_str.substring(0, 34)+ "...");
		}
		else
		{
			titre.setText(titre_str);
		}
		
		//Dernier numéro
		TextView numero = (TextView) view.findViewById(R.id.auteur);
		
		Date da = periodique.getDate("Date");
		Format formatter  = new SimpleDateFormat("dd/MM/yyyy");
		
		numero.setText("Numéro " + periodique.getInt("Numero") + "----" + formatter.format(da));

		return view;
	}

	public void setItems(TypePeriodiques typePeriodiques) 
	{
		this.typePeriodiques = typePeriodiques;
		notifyDataSetChanged();
		
	}
}


