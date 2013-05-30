package resultats;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.parse.ParseObject;
import com.utt.scd.R;

public class LivreAdapter extends BaseAdapter 
{
	protected Context context;
	protected List<ParseObject> listLivres;
	protected LayoutInflater inflater;
	
	public LivreAdapter(Context context, List<ParseObject> listLivres)
	{
		this.context = context;
		this.listLivres = listLivres;
		this.inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
		String auteur_str = (String)livre.get("Auteur");
		if (auteur_str.length() > 35)
		{
			auteur.setText(auteur_str.substring(0, 34)+ "...");
		}
		else
		{
			auteur.setText(auteur_str);
		}

		
		return view;
	}

	public void setItems(List<ParseObject> listLivres) 
	{
		this.listLivres = listLivres;
		notifyDataSetChanged();
		
	}



}
