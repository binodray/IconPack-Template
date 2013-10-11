package gridview;

import android.R;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.view.WindowManager;
import fragments.LauncherFragment;

public class LauncherMain extends FragmentActivity {

	// Starts the Activity for the list view
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.launcher_main);
		
		getSupportFragmentManager().beginTransaction()
		.replace(R.id.container_launcher, new LauncherFragment())
		.commit();
	}

	// Hides the title bar
	public void noTitle() {
		requestWindowFeature((int) Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}
	// This will return the Activity to the Main Screen when the app is in a Paused (not used) state
	@Override
	  public void onPause(){
		  super.onPause();
		  finish();
	  }
}
