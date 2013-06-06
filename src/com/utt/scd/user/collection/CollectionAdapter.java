package com.utt.scd.user.collection;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.parse.ParseObject;
import com.utt.scd.R;

public class CollectionAdapter extends BaseAdapter 
{
	protected Context context;
	protected List<ParseObject> listLivres;
	protected List<ItemLivre> listeLivres;
	protected LayoutInflater inflater;
	
	private ArrayList<String> livresCorbeille;
	
	public CollectionAdapter(Context context, List<ParseObject> listLivres)
	{
		this.context = context;
		this.listLivres = listLivres;
		
		this.listeLivres = new ArrayList<ItemLivre>();
		
		for (int i = 0 ; i < listLivres.size() ; i++)
		{
			this.listeLivres.add(new ItemLivre(listLivres.get(i)));
		}
		
		System.out.println("size : "+this.listeLivres.size());
		
		this.inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	
		this.livresCorbeille = new ArrayList<String>();
	}

	@Override
	public int getCount() 
	{
		return this.listeLivres.size();
	}

	@Override
	public Object getItem(int position) 
	{
		return this.listeLivres.get(position);
	}

	@Override
	public long getItemId(int position) 
	{
		return position;
	}
	
	public String getId(int position)
	{
		return this.listeLivres.get(position).getLivre().getObjectId();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) 
	{
		View view = convertView;
		
		ViewHolder viewHolder = null;
		
		final ParseObject livre = this.listeLivres.get(position).getLivre();
		
		if (view == null)
		{
			view = this.inflater.inflate(R.layout.livre_item, null);
			
			viewHolder = new ViewHolder();
			
			viewHolder.titre = (TextView) view.findViewById(R.id.titre);;
			viewHolder.auteur = (TextView) view.findViewById(R.id.auteur);
			
			
			//Checkbox
			viewHolder.chb = (CheckBox) view.findViewById(R.id.checkBox1);
			viewHolder.chb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) 
				{
					
					if (isChecked)
					{
						listeLivres.get(position).setSelected(true);
						livresCorbeille.add(livre.getObjectId());
					}
					else
					{
						listeLivres.get(position).setSelected(false);
						livresCorbeille.remove(livre.getObjectId());
					}
					
				}
			});
			
			view.setTag(viewHolder);
			viewHolder.chb.setTag(listeLivres.get(position));
		}
		else
		{
			view = convertView;
		    ((ViewHolder) view.getTag()).chb.setTag(listeLivres.get(position));
		}
		
		ViewHolder holder = (ViewHolder) view.getTag();
		
		
		
		String titre_str = (String)livre.get("Titre");
		if (titre_str.length() > 35)
		{
			holder.titre.setText(titre_str.substring(0, 34)+ "...");
		}
		else
		{
			holder.titre.setText(titre_str);
		}
		
		
		
		@SuppressWarnings("unchecked")
		ArrayList<String> auteur_str = (ArrayList<String>)livre.get("Auteur");
		String auteurs_str="";
		
		for(String at : auteur_str)
		{
			auteurs_str += at;
		}
		
		if (auteurs_str.length() > 35)
		{
			holder.auteur.setText(auteurs_str.substring(0, 34)+ "...");
		}
		else
		{
			holder.auteur.setText(auteurs_str);
		}
		
	    holder.chb.setChecked(listeLivres.get(position).isSelected());
		
		
		return view;
	}

	public void setItems(List<ParseObject> listLivres) 
	{
		this.listLivres = listLivres;
		
		for (int i = 0 ; i < listLivres.size() ; i++)
		{
			this.listeLivres.add(new ItemLivre(listLivres.get(i)));
		}
		
		
		notifyDataSetChanged();
		
	}

	public ArrayList<String> getLivresPanier() 
	{
		return this.livresCorbeille;
	}

	public void setLivresPanier(ArrayList<String> livresCorbeille) 
	{
		this.livresCorbeille = livresCorbeille;
	}
	
	
	static class ViewHolder 
	{
        protected TextView titre, auteur;
        protected CheckBox chb;
    }
	
	
	public class ItemLivre
	{
		private boolean selected;
		private ParseObject livre;
		
		
		public ItemLivre(ParseObject livre) 
		{
			super();
			this.livre = livre;
			this.selected = false;
		}
		
		public boolean isSelected() 
		{
			return selected;
		}
		public void setSelected(boolean selected) 
		{
			this.selected = selected;
		}
		public ParseObject getLivre() 
		{
			return livre;
		}
		public void setLivre(ParseObject livre) 
		{
			this.livre = livre;
		}
	}

}
