package com.utt.scd.periodiques;

import java.util.LinkedList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.parse.ParseObject;
import com.utt.scd.R;



public class PeriodiquesAdapter extends BaseExpandableListAdapter 
{

	private Context context;
	private LinkedList<TypePeriodiques> groupes;
	private LayoutInflater inflater;

	public PeriodiquesAdapter(Context context, LinkedList<TypePeriodiques> groupes) 
	{
		this.context = context;
		this.groupes = groupes;
		this.inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public boolean areAllItemsEnabled() 
	{
		return true;
	}

	public Object getChild(int gPosition, int cPosition) 
	{
		return groupes.get(gPosition).getList().get(cPosition);
	}

	public long getChildId(int gPosition, int cPosition) 
	{
		return cPosition;
	}

	public String getId(int gPosition, int cPosition)
	{
		return groupes.get(gPosition).getList().get(cPosition).getObjectId();
	}



	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) 
	{
		final ParseObject objet = (ParseObject) getChild(groupPosition, childPosition);

		ChildViewHolder childViewHolder;

		if (convertView == null) 
		{
			childViewHolder = new ChildViewHolder();

			convertView = inflater.inflate(R.layout.group_child, null);

			childViewHolder.textViewChild = (TextView) convertView.findViewById(R.id.textView1);


			convertView.setTag(childViewHolder);
		} 
		else 
		{
			childViewHolder = (ChildViewHolder) convertView.getTag();
		}

		childViewHolder.textViewChild.setText((String)objet.get("Titre"));

		return convertView;
	}

	public int getChildrenCount(int gPosition) 
	{
		return groupes.get(gPosition).getList().size();
	}









	public Object getGroup(int gPosition) 
	{
		return groupes.get(gPosition);
	}

	public int getGroupCount() 
	{
		return groupes.size();
	}

	public long getGroupId(int gPosition)
	{
		return gPosition;
	}

	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) 
	{
		GroupViewHolder gholder;

		TypePeriodiques group = (TypePeriodiques) getGroup(groupPosition);

		if (convertView == null) 
		{
			gholder = new GroupViewHolder();

			convertView = inflater.inflate(R.layout.group_parent, null);

			gholder.textViewGroup = (TextView) convertView.findViewById(R.id.textView1);

			convertView.setTag(gholder);
		} 
		else 
		{
			gholder = (GroupViewHolder) convertView.getTag();
		}

		gholder.textViewGroup.setText(group.getNom());

		return convertView;
	}

	public boolean hasStableIds() 
	{
		return true;
	}

	public boolean isChildSelectable(int arg0, int arg1) 
	{
		return true;
	}

	class GroupViewHolder 
	{
		public TextView textViewGroup;
	}

	class ChildViewHolder 
	{
		public TextView textViewChild;
	}

}







/*public class PeriodiquesAdapter extends FragmentPagerAdapter 
{
	private LinkedList<TypePeriodiques> listPeriodiques;
	private Context context;
	

	public PeriodiquesAdapter(Context context, FragmentManager fm, LinkedList<TypePeriodiques> listPeriodiques)
	{
		super(fm);
		this.listPeriodiques = listPeriodiques;
		this.context = context;
	}
	

	@Override
	public Fragment getItem(int position) 
	{
		return MagazineFragment.newInstance(this.context, this.listPeriodiques.get(position));
	}


	@Override
	public int getCount() 
	{
		return this.listPeriodiques.size();
	}
	
	@Override
	public int getItemPosition(Object object) 
	{
		return POSITION_NONE;
	}
	
	@Override
    public CharSequence getPageTitle(int position) 
	{
		return this.listPeriodiques.get(position).getNom();
    }
}*/
