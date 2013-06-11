package com.utt.scd.periodiques;

import java.util.LinkedList;

import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.utt.scd.R;


public class PeriodiquesAdapter extends BaseAdapter
{

	private Context context;
	private LinkedList<TypePeriodiques> groupes;
	private LayoutInflater inflater;

	public PeriodiquesAdapter(Context context, LinkedList<TypePeriodiques> groupes) 
	{
		this.context = context;
		this.groupes = groupes;
		this.inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		for (TypePeriodiques a : this.groupes)
		{
			System.out.println(a.getNom());
		}
	}
	
	
    @Override
    public int getCount() 
    {
        return groupes.size();
    }

    @Override
    public Object getItem(int i) 
    {
        return groupes.get(i);
    }

    @Override
    public long getItemId(int i) 
    {
        return i;
    }

    @SuppressWarnings("deprecation")
	@Override
    public View getView(int position, View convertView, ViewGroup viewGroup) 
    {
    	View view = convertView;
        ViewHolder viewHolder = null;

        if(view == null) 
        {
        	view = inflater.inflate(R.layout.periodique_item, viewGroup, false);
            
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) view.findViewById(R.id.imageView1);
            viewHolder.name = (TextView) view.findViewById(R.id.title);
            
            view.setTag(viewHolder);

        }
        else
        {
        	view = convertView;
        }
        

        ViewHolder holder = (ViewHolder) view.getTag();


        TypePeriodiques item = this.groupes.get(position);
        
        if (item.getNom().equals("Informatique"))
        {
        	holder.imageView.setImageResource(R.drawable.informatique);
        }
        else if (item.getNom().equals("Ecologie"))
        {
        	holder.imageView.setImageResource(R.drawable.ecologie);
        }
        else if (item.getNom().equals("Automobile"))
        {
        	holder.imageView.setImageResource(R.drawable.automobile);
        }
        else if (item.getNom().equals("Sciences"))
        {
        	holder.imageView.setImageResource(R.drawable.sciences);
        }
        
        WindowManager wm = (WindowManager) this.context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		
		holder.imageView.setMaxWidth(display.getWidth());
		holder.imageView.setMinimumWidth(display.getWidth());

        holder.name.setText(item.getNom());


        return view;
    }
    
    static class ViewHolder 
	{
        protected TextView name;
        protected ImageView imageView;
    }

}

