package com.utt.scd.user.alertes;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.parse.ParseObject;
import com.utt.scd.R;
import com.utt.scd.resultats.LivreDetail;

public class LongFragment extends SherlockFragment implements OnItemClickListener 
{
	private ListView list;
	private LongAdapter adapter;
	private List<ParseObject> listLivres;
	
	private Context context;
	
	public LongFragment(Context context, List<ParseObject> listLivres) 
	{
		this.listLivres = listLivres;
		this.adapter = new LongAdapter(context, listLivres);
		
		this.context = context;
	}
	
	
	public void setItems(List<ParseObject> listLivres)
	{
		this.adapter.setItems(listLivres);
	}
	

	public static LongFragment newInstance(Context context, List<ParseObject> listLivres) 
	{
		LongFragment  fragment = new LongFragment(context, listLivres);

		return fragment;
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
	{
		View view = inflater.inflate(R.layout.fragment_long_disponible, null);
		
		
		this.list = (ListView) view.findViewById(R.id.listView1);
		this.list.setAdapter(adapter);
		this.list.setOnItemClickListener(this);
		
		
		return view;
	}

	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int position, long id) 
	{
		ParseObject livre = this.listLivres.get(position);
		
		Intent intent = new Intent(context, LivreDetail.class);
		intent.putExtra("objectId", livre.getObjectId());
		startActivity(intent);

	}

	public LongAdapter getAdapter() 
	{
		return adapter;
	}

	public void setAdapter(LongAdapter adapter) 
	{
		this.adapter = adapter;
	}
	
	
	
}