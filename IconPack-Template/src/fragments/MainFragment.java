package fragments;

import gridview.LauncherMain;
import gridview.RequestIconsDialog;
import helper.ScrollGridView;

import java.util.ArrayList;
import java.util.List;

import your.icons.name.here.AboutThemeActivity;
import your.icons.name.here.R;
import your.icons.name.here.Wallpaper;
import adapters.MainAdapter;
import adapters.MainAdapter.AdapterItem;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;


/** 
 ** Some lines may be off a few numbers
 ** Just be sure you're in the general area
 **/

public class MainFragment extends Fragment{
	
	ScrollGridView gridView;
	final List<AdapterItem> listOfStuff = new ArrayList<AdapterItem>();
	
	// Flag Constants
	public static final int ABOUT_THEME = 0;
	public static final int APPLY_LAUNCHER = 1;
	public static final int WALLPAPER = 2;
	public static final int REQUEST_ICONS = 3;

	// This is the background layout that gets inflated behind the list view
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		return inflater.inflate(R.layout.gridview_behind, null);
	}
	
	// Starts when the MainFragment is launched
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
	/* 
	 * This part does two things
	 * First - It counts the number of items and displays them
	 * Second - It displays the text in the "" which is a brief description of that item
	 * Removing any of these will remove that item but be sure to edit ALL the cases below or your list
	 * won't line up properly
	 */
		
		/**
		 ** NOTE: in order to have different views on tablet vs phones, I added an if/else statement to this
		 ** section. Be sure to remove BOTH parts to remove it from phones and tablets. Failure to remove both
		 ** parts will result in the app functioning differently on phones and tablets.
		 **/

		/* 
		 * Sets the Title and description text for each GridView item
		 * Check res/values/strings.xml to change text to whatever you want each GridView to say
		 */
		boolean tabletSize = getResources().getBoolean(R.bool.isTablet);
		if (tabletSize) {
			gridView = (ScrollGridView)getView().findViewById(R.id.grid);
			listOfStuff.remove(new AdapterItem(getResources().getString (R.string.title_info), 
					getResources().getString (R.string.desc_info), 0));
			listOfStuff.add(new AdapterItem(getResources().getString (R.string.title_apply), 
					getResources().getString (R.string.desc_apply), 1));
			listOfStuff.add(new AdapterItem(getResources().getString (R.string.title_walls), 
					getResources().getString (R.string.desc_walls), 2));
			listOfStuff.add(new AdapterItem(getResources().getString (R.string.title_request), 
					getResources().getString (R.string.desc_request), 3));
			//listOfStuff.add(new AdapterItem(getResources().getString (R.string.title_app), 
				//getResources().getString (R.string.desc_oss), 0));
			//listOfStuff.add(new AdapterItem(getResources().getString (R.string.title_community), 
					//getResources().getString (R.string.desc_community), 5));

			
		} else {
			gridView = (ScrollGridView)getView().findViewById(R.id.grid);
			listOfStuff.add(new AdapterItem(getResources().getString (R.string.title_info), 
					getResources().getString (R.string.desc_info), 0));
			listOfStuff.add(new AdapterItem(getResources().getString (R.string.title_apply), 
					getResources().getString (R.string.desc_apply), 1));
			listOfStuff.add(new AdapterItem(getResources().getString (R.string.title_walls), 
					getResources().getString (R.string.desc_walls), 2));
			listOfStuff.add(new AdapterItem(getResources().getString (R.string.title_request), 
					getResources().getString (R.string.desc_request), 3));
			//listOfStuff.add(new AdapterItem(getResources().getString (R.string.title_app), 
					//getResources().getString (R.string.desc_oss), 0));
			//listOfStuff.add(new AdapterItem(getResources().getString (R.string.title_community), 
					//getResources().getString (R.string.desc_community), 5));
		}

		/**
		 ** NOTE: in order to have different views on tablet vs phones, I added an if/else statement to this
		 ** section. Be sure to remove both parts to remove it from phones and tablets. Failure to remove both
		 ** parts will result in the app functioning differently on phones and tablets.
		 **/
			MainAdapter adapter = new MainAdapter(getActivity(), listOfStuff);
	
			gridView.setAdapter(adapter);
			gridView.setExpanded(true);
			gridView.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
					
					@SuppressWarnings("unused")
					MainFragment gridContentT = null;
					
					boolean tabletSize = getResources().getBoolean(R.bool.isTablet);
					if (tabletSize) { // for tablets
						
					switch (position) {
					case APPLY_LAUNCHER:
						Intent launcher = new Intent(getActivity(), LauncherMain.class);
						startActivity(launcher);
			        	break;
					case WALLPAPER:
						Intent wall = new Intent(getActivity(), Wallpaper.class);
						startActivity(wall);
			        	break;
			    	case REQUEST_ICONS:
			    		Intent requestIcons = new Intent(getActivity(), RequestIconsDialog.class);
			    		startActivity(requestIcons);
			    		break;
		}	
				} else {	// for phones
					switch (position) {
					case ABOUT_THEME:
						Intent aboutTheme = new Intent(getActivity(), AboutThemeActivity.class);
						startActivity(aboutTheme);
		        		break;
					case APPLY_LAUNCHER:
						Intent launcher = new Intent(getActivity(), LauncherMain.class);
						startActivity(launcher);
		        		break;
					case WALLPAPER:
						Intent wall = new Intent(getActivity(), Wallpaper.class);
						startActivity(wall);
		        		break;
		    		case REQUEST_ICONS:
		    			Intent requestIcons = new Intent(getActivity(), RequestIconsDialog.class);
		    			startActivity(requestIcons);
		    			break;
		        		
					}
				}
				}	
				
	/** 
	 ** This is the code needed to check the package in case 0
	 ** If you remove that check, you can remove this code too
	 ** Leaving it here won't harm anything either
	 **
	public boolean isPackageExists(String targetPackage){
		  List<ApplicationInfo> packages;
		  PackageManager pm;
		  pm = getActivity().getPackageManager();
		  packages = pm.getInstalledApplications(0);
		  for (ApplicationInfo packageInfo : packages) {
		  if(packageInfo.packageName.equals(targetPackage)) return true;
		  }  
		  return false;
		  }**/	
			});
		
	}
}