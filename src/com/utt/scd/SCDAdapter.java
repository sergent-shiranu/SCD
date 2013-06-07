package com.utt.scd;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SCDAdapter extends BaseAdapter 
{
    private List<FonctionSCD> items = new ArrayList<FonctionSCD>();
    private LayoutInflater inflater;
    private Context context;

    
    public SCDAdapter(Context context, ArrayList<FonctionSCD> items) 
    {
    	
    	this.context = context;
    	
        this.inflater = LayoutInflater.from(this.context);

        this.items = items;

    }

    @Override
    public int getCount() 
    {
        return items.size();
    }

    @Override
    public Object getItem(int i) 
    {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) 
    {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) 
    {
        View v = view;
        ImageView picture;
        TextView name;

        if(v == null) 
        {
            v = inflater.inflate(R.layout.grid_item, viewGroup, false);
            v.setTag(R.id.picture, v.findViewById(R.id.picture));
            v.setTag(R.id.text, v.findViewById(R.id.text));
        }

        picture = (ImageView)v.getTag(R.id.picture);

        name = (TextView)v.getTag(R.id.text);

        FonctionSCD item = this.items.get(i);

        picture.setImageResource(item.getImageSource());
        
        
        name.setText(item.getName());

        return v;
    }

}
