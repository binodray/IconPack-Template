package gridview;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipOutputStream;

import your.icons.name.here.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class RequestIconsDialog extends Activity {

	private static final String TAG = "Pkg";
	private static final String SD = Environment.getExternalStorageDirectory().getAbsolutePath();
	private static final String SAVE_LOC = SD + "/activities";
	private static final int BUFFER = 2048;
	private ProgressDialog pbarDialog;
	
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
              					"gridview.RequestIconsMain"));
              	startActivity(manual);
              }	
      });

      btnCancel = (Button) findViewById(R.id.btnCancel);
      btnCancel.setOnClickListener(new OnClickListener() {
              public void onClick(View v) {
                  finish();
              }	
      });

      btnAuto = (Button) findViewById(R.id.btnAuto);
      btnAuto.setOnClickListener(new OnClickListener(){

			@SuppressLint("DefaultLocale")
			@Override
			public void onClick(View v) {

				pbarDialog = ProgressDialog.show(RequestIconsDialog.this,
						"Please wait...", "Building list of installed apps...", true);

				Thread lancelinkspThread = new Thread()
				{
					@Override
					public void run()
					{
						Looper.prepare();

						final File save_loc = new File(SAVE_LOC);
						save_loc.mkdirs();

						final PackageManager packageManager = getPackageManager();
						final StringBuilder sb = new StringBuilder( );
						final Intent intent = new Intent("android.intent.action.MAIN");
						intent.addCategory("android.intent.category.LAUNCHER");
						final List<ResolveInfo> activities = packageManager.queryIntentActivities( intent, 0 );
						final int size = activities.size();

						if (size == 0) {
							finish();
						}
						for (int i = 0; i < size; i++)
						{
							final ResolveInfo info = activities.get(i);
							final String label = info.loadLabel(packageManager).toString();
							final Drawable icon = info.loadIcon(packageManager);
							final String pkgName = info.activityInfo.packageName;
							final String className = info.activityInfo.name;

							sb.append(label);
							sb.append("\n");
							sb.append("<item component=\"ComponentInfo{");
							sb.append(pkgName);
							sb.append("/");
							sb.append(className);
							sb.append("}\" drawable=\"");
							sb.append(label.replace(" ", "_").toLowerCase());
							sb.append("\" />");
							sb.append("\n");

							Bitmap bitmap = ((BitmapDrawable)icon).getBitmap();
							FileOutputStream fOut;

							try {
								fOut = new FileOutputStream(SAVE_LOC + "/" + label + ".png");
								bitmap.compress(Bitmap.CompressFormat.PNG,100,fOut);
								fOut.flush();fOut.close();
							} catch (FileNotFoundException e) {	} 
							catch (IOException e) {	}
						}

						try {
							FileWriter fstream = new FileWriter(SAVE_LOC + "/appfilter.xml");
							BufferedWriter out = new BufferedWriter(fstream);
							out.write(sb.toString());
							out.close();
						} catch (Exception e){}

						createZipFile(SAVE_LOC, true, SD + "/" + android.os.Build.MODEL + ".zip");
						// deleteDirectory(save_loc);
						
						handler.sendEmptyMessage(0);
					}
				};
				lancelinkspThread.start();				
			}

		});
	}

	private Handler handler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			switch(msg.what)
			{
			case 0:
				pbarDialog.dismiss();
				
				final Intent sendIntent = new Intent(android.content.Intent.ACTION_SEND);
				sendIntent.setType("application/zip");
				final Uri uri = Uri.parse("file://" + SD + "/" + android.os.Build.MODEL + ".zip");
				sendIntent.putExtra(Intent.EXTRA_STREAM, uri);
				sendIntent.putExtra(android.content.Intent.EXTRA_EMAIL, 
						new String[]{getString(R.string.email_address)});
				sendIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, 
						getString(R.string.request_auto_desc));
				sendIntent.putExtra(android.content.Intent.EXTRA_TEXT, "I'm using the " 
						+ Build.DEVICE + " with Android version " + Build.VERSION.RELEASE + "");
				startActivity(Intent.createChooser(sendIntent, getString(R.string.submit)));
				
				break;
			}
		}
	};
	
	public static boolean deleteDirectory(File path) {
		if( path.exists() ) {
			File[] files = path.listFiles();
			for(int i=0; i<files.length; i++) {
				if(files[i].isDirectory()) {
					deleteDirectory(files[i]);
				}
				else {
					files[i].delete();
				}
			}
		}
		return( path.delete() );
	}
	
	public static boolean createZipFile(final String path, 
			final boolean keepDirectoryStructure,
			final String out_file) {

		final File f = new File(path);

		if (!f.canRead() || !f.canWrite())
		{
			Log.d(TAG, path + " cannot be compressed due to file permissions");
			return false;
		}

		try {
			ZipOutputStream zip_out = new ZipOutputStream(
					new BufferedOutputStream(
							new FileOutputStream(out_file), BUFFER));

			if (keepDirectoryStructure)
			{
				zipFile(path, zip_out, "");
			}
			else
			{
				final File files[] = f.listFiles();
				for (final File file : files) {
					zip_folder(file, zip_out);
				}
			}

			zip_out.close();

		} catch (FileNotFoundException e) {
			Log.e("File not found", e.getMessage());
			return false;

		} catch (IOException e) {
			Log.e("IOException", e.getMessage());
			return false;
		}
		return true;
	}

	// keeps directory structure
	public static void zipFile(final String path, final ZipOutputStream out, 
			final String relPath) throws IOException 
	{
		final File file = new File(path);

		if (!file.exists())
		{
			Log.d(TAG, file.getName() + " does NOT exist!");
			return;
		}

		final byte[] buf = new byte[1024];
		final String[] files = file.list();

		if (file.isFile())
		{   
			FileInputStream in = new FileInputStream(file.getAbsolutePath()); 

			try
			{
				out.putNextEntry(new ZipEntry(relPath + file.getName()));

				int len; 

				while ((len = in.read(buf)) > 0) 
				{ 
					out.write(buf, 0, len); 
				}

				out.closeEntry(); 
				in.close();
			}
			catch (ZipException zipE)
			{
				Log.d(TAG, zipE.getMessage());
			}
			finally
			{
				if (out != null) out.closeEntry(); 
				if (in != null) in.close();
			}
		}
		else if (files.length > 0) // non-empty folder
		{
			for (int i = 0, length = files.length; i < length; ++i)
			{
				zipFile(path + "/" + files[i], out, relPath + file.getName() + "/");
			}
		}
	}


	/*
	 * 
	 * @param file
	 * @param zout
	 * @throws IOException
	 */
	
	private static void zip_folder(File file, ZipOutputStream zout) throws IOException {
		byte[] data = new byte[BUFFER];
		int read;

		if(file.isFile()){
			ZipEntry entry = new ZipEntry(file.getName());
			zout.putNextEntry(entry);
			BufferedInputStream instream = new BufferedInputStream(
					new FileInputStream(file));

			while((read = instream.read(data, 0, BUFFER)) != -1)
				zout.write(data, 0, read);

			zout.closeEntry();
			instream.close();

		} else if (file.isDirectory()) {
			String[] list = file.list();
			int len = list.length;

			for(int i = 0; i < len; i++)
				zip_folder(new File(file.getPath() +"/"+ list[i]), zout);
        }	
	}

	// This will return the Activity to the Main Screen when the app is in a Paused (not used) state
	@Override
	  public void onPause(){
		  super.onPause();
		  finish();
	  }
}
	
