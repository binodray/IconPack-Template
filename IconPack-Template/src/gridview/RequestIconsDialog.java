package gridview;

import your.icons.name.here.R;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class RequestIconsDialog extends Activity {
	
	Button 
	btnManual, 
	btnAuto, 
	btnCancel;
	
	@Override
	  public void onCreate(Bundle savedInstanceState) 
	{
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.request_icons_dialog);


      btnManual = (Button) findViewById(R.id.btnManual);
      btnManual.setOnClickListener(new OnClickListener() {
              public void onClick(View v) {
              	Intent manual = new Intent(Intent.ACTION_MAIN);
              	manual.setComponent(new ComponentName
              			("your.icons.name.here", 
              					"gridview.RequestIconsManualMain"));
              	startActivity(manual);
              }	
      });

      btnAuto = (Button) findViewById(R.id.btnAuto);
      btnAuto.setOnClickListener(new OnClickListener() {
              public void onClick(View v) {
              	Intent auto = new Intent(Intent.ACTION_MAIN);
              	auto.setComponent(new ComponentName
              			("your.icons.name.here", 
              					"gridview.RequestIconsAutoMain"));
              	startActivity(auto);
              }	
      });

      btnCancel = (Button) findViewById(R.id.btnCancel);
      btnCancel.setOnClickListener(new OnClickListener() {
              public void onClick(View v) {
                  finish();
              }	
      });
	}
}