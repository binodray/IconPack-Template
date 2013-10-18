package adapters;

import java.util.List;

import your.icons.name.here.R;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DrawerMenuAdapter extends BaseAdapter
{
	private Context context;
	private List<Integer> listItem;
	private Resources res;
	
	// Flag Constants
	public static final int DYNASTY_OSS = 0;
	public static final int NEWLY_ADDED = 1;
	public static final int RATE = 2;
	public static final int CONTACT = 3;
	public static final int ABOUT_DEVELOPER = 4;
	public static final int COMMUNITY = 5;
	public static final int DONATE = 6;
	
	public DrawerMenuAdapter(Context context, List<Integer> listItem)
	{
		this.context = context;
		this.listItem = listItem;
		this.res = context.getResources();
	}
	
	public int getCount()
	{
		return listItem.size();
	}
	
	public Object getItem(int position)
	{
		return listItem.get(position);
	}
	
	public long getItemId(int position)
	{
		return position;
	}
	
	public View getView(int position, View convertView, ViewGroup viewGroup)
	{
		ViewHolder holder;
		if (convertView == null)
		{
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.drawer_item, null);
			
			holder = new ViewHolder();
			holder.txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
			holder.imgIcon = (ImageView) convertView.findViewById(R.id.imgIcon);
			
			convertView.setTag(holder);
		}
		else
			holder = (ViewHolder) convertView.getTag();
		
		switch(listItem.get(position))
		{
			case DYNASTY_OSS:
				holder.txtTitle.setText(res.getString(R.string.title_app));
				holder.imgIcon.setImageResource(R.drawable.icon_oss);
				break;
			case NEWLY_ADDED:
				holder.txtTitle.setText(res.getString(R.string.title_new_icons));
				holder.imgIcon.setImageResource(R.drawable.icon_new);
				break;
			case RATE:
				holder.txtTitle.setText(res.getString(R.string.rateme));
				holder.imgIcon.setImageResource(R.drawable.icon_rate);
				break;
			case CONTACT:
				holder.txtTitle.setText(res.getString(R.string.email_dev));
				holder.imgIcon.setImageResource(R.drawable.icon_email);
				break;
			case ABOUT_DEVELOPER:
				holder.txtTitle.setText(res.getString(R.string.about_dev));
				holder.imgIcon.setImageResource(R.drawable.icon_dev_logo);
				break;
			case COMMUNITY:
				holder.txtTitle.setText(res.getString(R.string.title_community));
				holder.imgIcon.setImageResource(R.drawable.icon_community);
				break;
			case DONATE:
				holder.txtTitle.setText(res.getString(R.string.donate));
				holder.imgIcon.setImageResource(R.drawable.icon_wallet);
				break;
		}
		
		return convertView;
	}
	
	private class ViewHolder
	{
		public TextView txtTitle;
		public ImageView imgIcon;
	}
}
