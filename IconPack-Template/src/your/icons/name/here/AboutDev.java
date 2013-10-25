package your.icons.name.here;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class AboutDev extends Activity {

	private ImageButton
    twitter,
    facebook,
    gplus;
	
	  // This creates your About Dev Activity
	@Override
	  public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.about_dev);  
      
	  // These are your fonts
      Typeface fontType = Typeface.createFromAsset(getAssets(), "RobotoSlab-Regular.ttf");
      
      TextView txt1 = (TextView) findViewById(R.id.devFont);
      txt1.setTypeface(fontType); 
      TextView title1 = (TextView) findViewById(R.id.title1);
      title1.setTypeface(fontType); 
      TextView desc1 = (TextView) findViewById(R.id.description1);
      desc1.setTypeface(fontType); 
      TextView title2 = (TextView) findViewById(R.id.title2);
      title2.setTypeface(fontType); 
      TextView desc2 = (TextView) findViewById(R.id.description2);
      desc2.setTypeface(fontType); 
      
      // Your ImageButton links
      gplus = (ImageButton) findViewById(R.id.gplus_button);
      gplus.setOnClickListener(new OnClickListener() {
              public void onClick(View v) {
            		Intent gplusIntent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse
            				(getResources().getString(R.string.link_gplus)));
                  		startActivity(gplusIntent);
              }
      });
      
      twitter = (ImageButton) findViewById(R.id.twitter_button);
      twitter.setOnClickListener(new OnClickListener() {
              public void onClick(View v) {
              		Intent twitterIntent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse
              				(getResources().getString(R.string.link_twitter)));
              		startActivity(twitterIntent);
              }
      });
      
      facebook = (ImageButton) findViewById(R.id.facebook_button);
      facebook.setOnClickListener(new OnClickListener() {
              public void onClick(View v) {
              		Intent facebookIntent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse
              				(getResources().getString(R.string.link_facebook)));
              		startActivity(facebookIntent);
              }
      });
}
	
	  // This will return the Activity to the Main Screen when the app is in a Paused (not used) state
	@Override
	  public void onPause(){
		  super.onPause();
		  finish();
	  }
}
