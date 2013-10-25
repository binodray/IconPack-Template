package fragments;

import java.util.ArrayList;
import java.util.List;

import your.icons.name.here.R;
import adapters.ApplyLauncherAdapter;
import adapters.ApplyLauncherAdapter.LauncherItem;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

public class ApplyLauncherFragment extends Fragment {

	GridView gridView;
	final List<LauncherItem> applyLauncher = new ArrayList<LauncherItem>();

	// Flag Constants
	public static final int APEX_LAUNCHER = 0;
	public static final int NOVA_LAUNCHER = 1;
	public static final int HOLO_LAUNCHER = 2;
	public static final int ADW_LAUNCHER = 3;
	public static final int ACTION_LAUNCHER = 4;
	public static final int NEXT_LAUNCHER = 5;
	public static final int CANCEL = 6;

	// This is the background layout that gets inflated behind the list view
	public View onCreateView(LayoutInflater inflater, ViewGroup container_launcher,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.launcher_behind, null);
		gridView = (GridView) view.findViewById(R.id.grid);
		return view;
	}

	// Starts when the MainFragment is launched
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		applyLauncher.add(new LauncherItem("Apex", 0));
		applyLauncher.add(new LauncherItem("Nova", 1));
		applyLauncher.add(new LauncherItem("Holo", 2));
		applyLauncher.add(new LauncherItem("ADW", 3));
		applyLauncher.add(new LauncherItem("Action", 4));
		applyLauncher.add(new LauncherItem("Go", 5));
		applyLauncher.add(new LauncherItem("Next", 6));
		applyLauncher.add(new LauncherItem("Cancel", 7));

		ApplyLauncherAdapter adapter = new ApplyLauncherAdapter(getActivity(),
				applyLauncher);

		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {

				final String ACTION_APPLY_ICON_THEME = "com.teslacoilsw.launcher.APPLY_ICON_THEME";
				final String NOVA_PACKAGE = "com.teslacoilsw.launcher";
				final String EXTRA_ICON_THEME_PACKAGE = "com.teslacoilsw.launcher.extra.ICON_THEME_PACKAGE";
				final String EXTRA_ICON_THEME_TYPE = "com.teslacoilsw.launcher.extra.ICON_THEME_TYPE";
				final String ACTION_SET_THEME = "com.anddoes.launcher.SET_THEME";
				final String EXTRA_PACKAGE_NAME = "com.anddoes.launcher.THEME_PACKAGE_NAME";

				@SuppressWarnings("unused")
				MainFragment gridContentT = null;

				switch (position) {
				case APEX_LAUNCHER:
					Intent apex = new Intent(ACTION_SET_THEME);
					apex.putExtra(EXTRA_PACKAGE_NAME, getActivity().getPackageName());
					apex.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					try {
						startActivity(apex);

						Toast applied = Toast.makeText(getActivity().getBaseContext(), 
								getResources().getString (R.string.finish_apply),
								Toast.LENGTH_LONG);
						applied.show();
					} catch (ActivityNotFoundException e) {
						Intent apexMarket = new Intent(Intent.ACTION_VIEW);
						apexMarket.setData(Uri
								.parse("market://details?id=com.anddoes.launcher"));
						startActivity(apexMarket);
						Toast failedApex = Toast
								.makeText(
										getActivity().getBaseContext(),
										getResources().getString (R.string.apex_market),
										Toast.LENGTH_SHORT);
						failedApex.show();
					}

					break;
				case NOVA_LAUNCHER:
					Intent nova = new Intent(ACTION_APPLY_ICON_THEME);
					nova.setPackage(NOVA_PACKAGE);
					nova.putExtra(EXTRA_ICON_THEME_TYPE, 
							getResources().getString (R.string.go));
					nova.putExtra(EXTRA_ICON_THEME_PACKAGE,
							getResources().getString (R.string.package_name));
					try {
						startActivity(nova);
					} catch (ActivityNotFoundException e) {
						Intent novaMarket = new Intent(Intent.ACTION_VIEW);
						novaMarket.setData(Uri
								.parse("market://details?id=com.teslacoilsw.launcher"));
						startActivity(novaMarket);
						Toast failedNova = Toast
								.makeText(
										getActivity().getBaseContext(),
										getResources().getString (R.string.nova_market),
										Toast.LENGTH_SHORT);
						failedNova.show();
					}
					break;
				case HOLO_LAUNCHER:
					Toast failedHolo = Toast.makeText(getActivity().getBaseContext(),
							getResources().getString (R.string.not_supported),
							Toast.LENGTH_LONG);
					failedHolo.show();
					break;
				case ADW_LAUNCHER:
					Intent adw = new Intent("org.adw.launcher.SET_THEME");
					adw.putExtra("org.adw.launcher.theme.NAME",
							getResources().getString (R.string.package_name));
					try {
						startActivity(Intent.createChooser(adw,
								"activating theme..."));
					} catch (ActivityNotFoundException e) {						
						Intent adwMarket = new Intent(Intent.ACTION_VIEW);
						adwMarket.setData(Uri
								.parse("market://details?id=org.adw.launcher"));
						startActivity(adwMarket);
						Toast failedADW = Toast
								.makeText(
										getActivity().getBaseContext(),
										getResources().getString (R.string.adw_market),
										Toast.LENGTH_SHORT);
						failedADW.show();
					} 
					((Activity) getActivity()).finish();
					break;
				case ACTION_LAUNCHER:
					Intent al = getActivity().getPackageManager().getLaunchIntentForPackage(
							"com.chrislacy.actionlauncher.pro");
					if (al != null) {

						String packageName = getResources().getString (R.string.package_name);
						al.putExtra("apply_icon_pack", packageName);
						startActivity(al);
					} else {
						Intent alMarket = new Intent(Intent.ACTION_VIEW);
						alMarket.setData(Uri
								.parse("market://details?id=com.chrislacy.actionlauncher.pro"));
						startActivity(alMarket);
						Toast failedAL = Toast
								.makeText(
										getActivity().getBaseContext(), 
										getResources().getString (R.string.al_market),
										Toast.LENGTH_SHORT);
						failedAL.show();
					}
					break;
				case NEXT_LAUNCHER:
					Toast failedNext = Toast.makeText(getActivity().getBaseContext(),
							getResources().getString (R.string.not_supported),
							Toast.LENGTH_LONG);
					failedNext.show();
					break;
				/* This is your cancel button
				 * Always leave this as the last item
				 */
				case CANCEL:
					((Activity) getActivity()).finish();
					break;
				}
			}
		});
	}
}