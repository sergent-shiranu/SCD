package com.utt.scd.periodiques;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.app.SherlockFragment;
import com.parse.ParseObject;
import com.utt.scd.R;


public class MagazineFragment extends SherlockFragment implements OnItemClickListener 
{

	private ListView list;
	private MagazineAdapter adapter;
	private TypePeriodiques typePeriodiques;
	
	private Context context;
	
	public MagazineFragment(Context context, TypePeriodiques typePeriodiques) 
	{
		this.typePeriodiques = typePeriodiques;
		this.adapter = new MagazineAdapter(context, this.typePeriodiques);
		
		this.context = context;
	}

	public static MagazineFragment newInstance(Context context, TypePeriodiques typePeriodiques) 
	{
		MagazineFragment  fragment = new MagazineFragment(context, typePeriodiques);

		return fragment;
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
	{
		View view = inflater.inflate(R.layout.magazine, null);
		
		
		this.list = (ListView) view.findViewById(R.id.listView1);
		this.list.setAdapter(adapter);
		this.list.setOnItemClickListener(this);
		
		return view;
	}

	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int position, long id) 
	{
		ParseObject ped = this.typePeriodiques.getList().get(position);
		
		Intent intent = new Intent(context, PeriodiqueDetail.class);
		intent.putExtra("objectId", ped.getObjectId());
		startActivity(intent);

	}
	
}