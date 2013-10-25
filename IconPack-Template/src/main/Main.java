/*
 * Copyright 2013 the1dynasty
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package main;

/** 
 ** Some lines may be off a few numbers
 ** Just be sure you're in the general area
 **/

import fragments.MainFragment;
import helper.GlassActionBarHelper;
import helper.PkDrawerLayout;

import java.util.ArrayList;
import java.util.List;

import your.icons.name.here.AboutDev;
import your.icons.name.here.R;
import adapters.DrawerMenuAdapter;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ShareActionProvider;
import android.widget.Toast;

public class Main extends FragmentActivity {
	
	private ActionBar actionBar;
	private SharedPreferences prefs;
	private GlassActionBarHelper helper;
	private MenuItem shareItem;
	private ShareActionProvider mShareActionProvider;
	
	// Navigation Drawer
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private PkDrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private ListView mDrawerList;
	private List<Integer> drawerMenuList;
	private DrawerMenuAdapter drawerAdapter;

	boolean doubleBackToExitPressedOnce = false;
	
	// Starts the Activity for the gridview
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initActionBar();
		initView();
		checkBuild();
		initDrawer();
		
		getSupportFragmentManager().beginTransaction()
		.replace(R.id.container, 
				new MainFragment())
		.commit();
	}
	
	private void initActionBar()
	{
		actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(true); // Set this to false to hide AB Icon
		actionBar.setDisplayShowTitleEnabled(true); // Set this to false to hide AB Title
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);
		mTitle = mDrawerTitle = getTitle();
	}
	
	private void initView()
	{
		helper = new GlassActionBarHelper().contentLayout(R.layout.gridview_main);
		setContentView(helper.createView(this));
		mDrawerLayout = (PkDrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
	}
	
	private void initDrawer()
	{
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, 
				R.drawable.ic_drawer_indicator, 
				R.string.drawer_open, 
				R.string.drawer_close)
		{
			public void onDrawerClosed(View view)
			{
				actionBar.setTitle(mTitle);
				invalidateOptionsMenu();
			}
			
			public void onDrawerOpened(View drawerView)
			{
				actionBar.setTitle(mDrawerTitle);
				invalidateOptionsMenu();
			}
		};
		
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		
		drawerMenuList = new ArrayList<Integer>();
		drawerMenuList.add(DrawerMenuAdapter.DYNASTY_OSS);
		drawerMenuList.add(DrawerMenuAdapter.NEWLY_ADDED);
		drawerMenuList.add(DrawerMenuAdapter.RATE);
		drawerMenuList.add(DrawerMenuAdapter.CONTACT);
		drawerMenuList.add(DrawerMenuAdapter.ABOUT_DEVELOPER);
		drawerMenuList.add(DrawerMenuAdapter.COMMUNITY);
		drawerMenuList.add(DrawerMenuAdapter.DONATE);
		drawerAdapter = new DrawerMenuAdapter(Main.this, drawerMenuList);
		mDrawerList.setAdapter(drawerAdapter);
		
		mDrawerList.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long index) {
				switch(drawerMenuList.get(position))
				{
				case DrawerMenuAdapter.DYNASTY_OSS:
					/** 
					 ** This checks if MY OSS app is installed. You can remove this case
					 ** statement completely or add your own app to check against or leave
					 ** it and let it check for MY app :D
					 ** If it is installed, the app will open when you press the list item
					 ** If it is NOT installed, it will open up the play store to download it
					 **/
					if(isPackageExists("app.the1dynasty.oss")){
						Intent oss = new Intent("android.intent.action.MAIN");
						oss.setComponent(ComponentName.unflattenFromString
								("app.the1dynasty.oss/app.activities.MainActivity"));
						oss.addCategory("android.intent.category.LAUNCHER");
						startActivity(oss);
					}
					else{
						Intent oss = new Intent(Intent.ACTION_VIEW).setData(Uri.parse
								("market://details?id=app.the1dynasty.oss"));
						startActivity(oss);
				}
	    			break;
					case DrawerMenuAdapter.NEWLY_ADDED:
						Intent newIcons = new Intent(Main.this, NewIconsMain.class);
						startActivity(newIcons);
						break;
					case DrawerMenuAdapter.RATE:
						Intent rate = new Intent(Intent.ACTION_VIEW).setData(Uri.parse
								("market://details?id=your.icons.name.here"));
		            	startActivity(rate);
						break;
					case DrawerMenuAdapter.CONTACT:
						Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
						emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, 
								new String[]{getString(R.string.email_address)});
						emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, 
								getResources().getText(R.string.email_subject));
						emailIntent.setType("plain/text");
						startActivity(Intent.createChooser(emailIntent, "Contact Developer"));
						break;
					case DrawerMenuAdapter.ABOUT_DEVELOPER:
						Intent about = new Intent(Main.this, AboutDev.class);
						startActivity(about);
						break;
					case DrawerMenuAdapter.COMMUNITY:
						/** 
						 ** This launches my community on G+
						 ** Please leave this link in here for others to join. Thank You!
						 **/
						Intent gpCommunity = new Intent(Intent.ACTION_VIEW).setData(Uri.parse
	              				(getResources().getString(R.string.link_gplus_community)));
		          		startActivity(gpCommunity);
		        		break;
					case DrawerMenuAdapter.DONATE:
						Intent donate = new Intent(Intent.ACTION_VIEW).setData(Uri.parse
	              				(getResources().getString(R.string.link_donate)));
		        		startActivity(donate);
						break;
				}
				
				mDrawerLayout.closeDrawer(mDrawerList);
			}
		});
	}
	
	/************************************************************************
	 ******************** This is your Changelog Stuff **********************
	 ************************************************************************/
	public void checkBuild() {
		prefs = getSharedPreferences(getResources().getString(R.string.theme_name), 0);
	  int buildNum = prefs.getInt("Build Number", 1);
	  int currentVersion = 0;
	  
	  try {
	    currentVersion = getPackageManager()
	    		.getPackageInfo(getPackageName(), 0).versionCode;
	  }
	  catch (NameNotFoundException e) {
	    e.printStackTrace();
	  }
	    if(currentVersion > buildNum) {
	    	  getChangelog().show();
	    	  Editor editor = prefs.edit();
	    	  editor.putInt("Build Number", currentVersion);
	    	  editor.commit();
	    	}
	  }
	
	public Dialog getChangelog()
	 {
	 	final Dialog CDialog = new Dialog(Main.this);
	 	CDialog.setTitle(getResources().getString(R.string.changelog_title));
	 	CDialog.setContentView(R.layout.changelog);
	 	CDialog.setCanceledOnTouchOutside(true);
	 	CDialog.setCancelable(true);
	 	 
	 	Button Close = (Button) CDialog.findViewById(R.id.close);
	 	Close.setOnClickListener(new View.OnClickListener()
	 	{
	 	 @Override
	 	 public void onClick(View v)
	 	 {
	 	 CDialog.dismiss();
	 	 }
	 	});
	 	 
	 	return CDialog;
	 }

	/******************************************************************************
	 ** This code checks if MY OSS is installed on first run. If it is installed **
	 ** you get a dialog that says you're awesome and the user hits OK to remove **
	 ** that dialog. If it is NOT installed, the user is prompted to install it. **
	 ** You can remove this section if you're not checking for apps on first run **
	 ******************************************************************************/
	public void onStart() {
		super.onStart();
		boolean installed = isAppInstalled("app.the1dynasty.oss");
		
		// Checking if installed and if its the first run
	    if (installed) {
	    	boolean firstrunOSS = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
	    			.getBoolean("firstrunOSS", true);
		    if (firstrunOSS){
		    	
	    /* 
	     * Installed dialog
	     * Check res/values/strings.xml to change text to whatever you want the Alert to say
	     */
	        AlertDialog.Builder alert = new AlertDialog.Builder(this);
	        alert.setTitle(getResources().getString (R.string.alert_start_title));
	        alert.setMessage(getResources().getString (R.string.alert_start_desc));
	        alert.setIcon(R.drawable.alert_pass);
	        alert.setPositiveButton(getResources().getString (R.string.ok), null).show ();
	        
		    // Save the state so this dialog doesn't run again
		    getSharedPreferences("PREFERENCE", MODE_PRIVATE)
		        .edit()
		        .putBoolean("firstrunOSS", false) /* You can change this to another name */
		        .commit();
	              }
	    }
		
	    /* 
	     * Not Installed dialog
	     * Check res/values/strings.xml to change text to whatever you want the Alert to say
	     */
		else {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(getResources().getString (R.string.error_start_title));
			builder.setMessage(getResources().getString (R.string.error_start_desc));
	        builder.setIcon(R.drawable.alert_fail);
			builder.setNeutralButton(getResources().getString (R.string.later), new OnClickListener(){
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
				}
			});
			
		 // Change line 326 with the URL to YOUR app
			builder.setPositiveButton(getResources().getString (R.string.get), new OnClickListener(){
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					Intent share = new Intent(Intent.ACTION_VIEW).setData(Uri.parse
							("market://details?id=app.the1dynasty.oss"));
		    		startActivity(share);
			}
			});
			builder.show();
	    }
	}

	/************************************************************************
	 *********************** This is your Menu Stuff ************************
	 ************************************************************************/
	@Override
	protected void onPostCreate(Bundle savedInstanceState)
	{
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);
		
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu)
	{
		return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		
		shareItem = menu.findItem(R.id.Share_Label);
		mShareActionProvider = (ShareActionProvider) shareItem.getActionProvider();
	    configureShare();
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		/** This section is commented out. Remove comments to add the overflow menu back to the ActionBar **/
        /*switch(item.getItemId())
        {
        	case R.id.more:
        		return true;
	        case R.id.newIconsButton:
				Intent newIcons = new Intent(Main.this, NewIconsMain.class);
				startActivity(newIcons);
	            break;
            case R.id.shareButton:
        		Intent shareIntent = new Intent(Intent.ACTION_SEND);
        	    shareIntent.setType("text/plain");
        	    shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.app_link));
        	    startActivity(Intent.createChooser(shareIntent, "Share Via"));
                break;
            case R.id.rateButton:
            	Intent rate = new Intent(Intent.ACTION_VIEW).setData(Uri.parse
            			("market://details?id=your.icons.name.here"));
            	startActivity(rate);
                break;
            case R.id.emailButton:
            	Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
				emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { "the1dynasty.android@gmail.com" });
				emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getResources().getText(R.string.email_subject));
				emailIntent.setType("plain/text");
				startActivity(Intent.createChooser(emailIntent, "Contact Developer"));
				
                break;
            case R.id.aboutButton:
				Intent about = new Intent(Main.this, AboutDev.class);
				startActivity(about);
                break;
            case R.id.donateButton:
				Intent donate = new Intent(Intent.ACTION_VIEW).setData(Uri.parse
						("http://bit.ly/YWwhWu"));
        		startActivity(donate);
                break;
        }*/
        
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
	}

	@Override
    public void onBackPressed() {
		if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, (getResources().getString(R.string.back_twice)), 
        		Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
             doubleBackToExitPressedOnce=false;   

            }
        }, 2000);
    }
	
	private void configureShare()
	{
		Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
		shareIntent.setType("text/plain");
		shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, 
				getResources().getString(R.string.link_share));
		if (mShareActionProvider != null)
			mShareActionProvider.setShareIntent(shareIntent);
	}
	

	/************************************************************************
	 ************ This is the end of checking for installed app *************
	 ************************************************************************/
	
	/** 
	 ** This is the code needed to check the package in case 0
	 ** If you remove that check, you can remove this code too
	 ** Leaving it here won't harm anything either
	 **/
	public boolean isPackageExists(String targetPackage){
		  List<ApplicationInfo> packages;
		  PackageManager pm;
		  pm = this.getPackageManager();
		  packages = pm.getInstalledApplications(0);
		  for (ApplicationInfo packageInfo : packages) {
		  if(packageInfo.packageName.equals(targetPackage)) return true;
		  }  
		  return false;
		  }

	private boolean isAppInstalled(String packageName){
		// Tool we need to parse other packages
		PackageManager pm = getPackageManager();
		// True/False variable set to false by default, show "Not Installed"
		boolean app_installed = false;
		// If the app isn't installed, we would get a force close without a try/catch clause
		// If installed, try returns true, if not, we catch the exception and set it to false
		try {
			pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
			app_installed = true;
		} catch (PackageManager.NameNotFoundException e){
			app_installed = false;
		}
		// Must return a value (not overridden method)
		return app_installed;
	}
}
