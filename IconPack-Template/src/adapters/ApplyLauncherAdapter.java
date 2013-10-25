package adapters;

import java.util.List;

import your.icons.name.here.R;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ApplyLauncherAdapter extends BaseAdapter
{
	private Context context;
	private List<LauncherItem> gridItem;

	// Flag Constants
	public static final int APEX_LAUNCHER = 0;
	public static final int NOVA_LAUNCHER = 1;
	public static final int HOLO_LAUNCHER = 2;
	public static final int ADW_LAUNCHER = 3;
	public static final int ACTION_LAUNCHER = 4;
	public static final int NEXT_LAUNCHER = 5;
	public static final int CANCEL = 6;

	public ApplyLauncherAdapter(Context context, List<LauncherItem> gridItem) {
		this.gridItem = gridItem;
		this.context = context;
	}

	public View getView(int position, View v, ViewGroup parent) {
		ViewHolder holder;
		LauncherItem entry = gridItem.get(position);
		
		if (v == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			v = inflater.inflate(R.layout.launcher_layout, null);
			
			holder = new ViewHolder();
			holder.title = (TextView) v.findViewById(R.id.title);
			holder.launcher_Image = (ImageView) v.findViewById(R.id.list_image);
			
			v.setTag(holder);
		}
		else {
			holder = (ViewHolder) v.getTag();
		}
			holder.title.setText(entry.getTitle());

			/* 
			 * Sets the font type for the title
			 * Make sure the font file is in the projects Asset folder
			 * Default for this template is Roboto-Thin
			 * themefont.ttf is the font the theme grabs also
			 */
			Typeface tfTitle = Typeface.createFromAsset(context.getAssets(),"themefont.ttf");
			holder.title.setTypeface(tfTitle);
			
			switch(entry.getID()){
			case APEX_LAUNCHER:
				/** Use this first option for the newer single column look **/
				holder.title.setTextColor(context.getResources().getColor(R.color.gray_light2));
				holder.launcher_Image.setImageResource(R.mipmap.banner_apex);
				
				/** Use this second option for the traditional Dialog popup **/
				//holder.title.setTextColor(context.getResources().getColor(R.color.black));
				//holder.launcher_Image.setImageResource(R.mipmap.icon_apex);
				break;
			case NOVA_LAUNCHER:
				/** Use this first option for the newer single column look **/
				holder.title.setTextColor(context.getResources().getColor(R.color.gray_light2));
				holder.launcher_Image.setImageResource(R.mipmap.banner_nova);
				
				/** Use this second option for the traditional Dialog popup **/
				//holder.title.setTextColor(context.getResources().getColor(R.color.black));
				//holder.launcher_Image.setImageResource(R.mipmap.icon_nova);
				break;
			case HOLO_LAUNCHER:
				/** Use this first option for the newer single column look **/
				holder.title.setTextColor(context.getResources().getColor(R.color.gray_light2));
				holder.launcher_Image.setImageResource(R.mipmap.banner_holo);
				
				/** Use this second option for the traditional Dialog popup **/
				//holder.title.setTextColor(context.getResources().getColor(R.color.black));
				//holder.launcher_Image.setImageResource(R.mipmap.icon_holo);
				break;
			case ADW_LAUNCHER:
				/** Use this first option for the newer single column look **/
				holder.title.setTextColor(context.getResources().getColor(R.color.gray_light2));
				holder.launcher_Image.setImageResource(R.mipmap.banner_adw);
				
				/** Use this second option for the traditional Dialog popup **/
				//holder.title.setTextColor(context.getResources().getColor(R.color.black));
				//holder.launcher_Image.setImageResource(R.mipmap.icon_adw);
				break;
			case ACTION_LAUNCHER:
				/** Use this first option for the newer single column look **/
				holder.title.setTextColor(context.getResources().getColor(R.color.gray_light2));
				holder.launcher_Image.setImageResource(R.mipmap.banner_al);
				
				/** Use this second option for the traditional Dialog popup **/
				//holder.title.setTextColor(context.getResources().getColor(R.color.black));
				//holder.launcher_Image.setImageResource(R.mipmap.icon_al);
				break;
			case NEXT_LAUNCHER:
				/** Use this first option for the newer single column look **/
				holder.title.setTextColor(context.getResources().getColor(R.color.gray_light2));
				holder.launcher_Image.setImageResource(R.mipmap.banner_next);
				
				/** Use this second option for the traditional Dialog popup **/
				//holder.title.setTextColor(context.getResources().getColor(R.color.black));
				//holder.launcher_Image.setImageResource(R.mipmap.icon_next);
				break;
			case CANCEL:
				/** Use this first option for the newer single column look **/
				holder.title.setTextColor(context.getResources().getColor(R.color.gray_light2));
				holder.launcher_Image.setImageResource(R.mipmap.banner_cancel);
				
				/** Use this second option for the traditional Dialog popup **/
				//holder.title.setTextColor(context.getResources().getColor(R.color.black));
				//holder.launcher_Image.setImageResource(R.mipmap.icon_cancel);
				break;
			}
			holder.title.setText(entry.getTitle());
			
		return v;
	}

	@Override
	public int getCount() {
		return gridItem.size();
	}

	@Override
	public Object getItem(int position) {
		return gridItem.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	// Code added for better efficiency and less redraws
	public static class ViewHolder {
		public TextView title;
		public ImageView launcher_Image;
	}
	
	public static class LauncherItem{
		String Title;
		int ID;
		
		public LauncherItem(String Title, int ID) {
			this.Title = Title;
			this.ID = ID;
		}
		
		public String getTitle() {
			return Title;
		}
		
		public int getID() {
			return ID;
		}
	}
}