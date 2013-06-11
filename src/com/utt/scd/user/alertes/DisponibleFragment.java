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

public class DisponibleFragment extends SherlockFragment implements OnItemClickListener
{
	private ListView list;
	private DisponibleAdapter adapter;
	private List<ParseObject> listLivres;
	
	private Context context;
	
	public DisponibleFragment(Context context, List<ParseObject> listLivres) 
	{
		this.listLivres = listLivres;
		this.adapter = new DisponibleAdapter(context, this.listLivres);
		
		this.context = context;
	}
	
	
	public void setItems(List<ParseObject> listLivres)
	{
		this.adapter.setItems(listLivres);
	}
	

	public static DisponibleFragment newInstance(Context context, List<ParseObject> listLivres) 
	{
		DisponibleFragment  fragment = new DisponibleFragment(context, listLivres);

		return fragment;
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
	{
		View view = inflater.inflate(R.layout.fragment_long_disponible, null);
		
		
		this.list = (ListView) view.findViewById(R.id.listView1);
		this.list.setAdapter(adapter);
		this.list.setClickable(true);
		this.list.setOnItemClickListener(this);
		
		return view;
	}

	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int position, long id) 
	{
		ParseObject livre = (ParseObject) this.adapter.getItem(position);
		
		Intent intent = new Intent(context, LivreDetail.class);
		
		System.out.println("livre id : " + livre.getObjectId());
		
		intent.putExtra("objectId", livre.getObjectId());
		startActivity(intent);

	}
	
	public DisponibleAdapter getAdapter() 
	{
		return adapter;
	}

	public void setAdapter(DisponibleAdapter adapter) 
	{
		this.adapter = adapter;
	}
}
