package com.bor3i.schreiber;





import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.googlecode.tesseract.android.TessBaseAPI;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends Activity {
	private String letter, recognizedText="none";;
	private boolean nullref=false;
	private String strtab[]={"A","B","C","D","E","F","G","H","I","J","K","K","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","goodjob","tryagain"};
	private int raws[]={R.raw.a,R.raw.b,R.raw.c,R.raw.d,R.raw.e,R.raw.f,R.raw.g,R.raw.h,R.raw.i,R.raw.j,R.raw.k,R.raw.l,R.raw.m,R.raw.n,R.raw.o,R.raw.p,R.raw.q,R.raw.r,R.raw.s,R.raw.t,R.raw.u,R.raw.v,R.raw.w,R.raw.x,R.raw.y,R.raw.z,R.raw.goodjob,R.raw.tryagain};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if(nullref==false){
			letter=rand();
				
			speak(letter);
		}
		nullref=false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	public void save(View view){
		
		final String DATA_PATH = Environment.getExternalStorageDirectory().toString() +
                "/Image2Text/";
		 View u = findViewById(R.id.DrawSpace);
         u.setDrawingCacheEnabled(true);                                                
         View z = (View) findViewById(R.id.DrawSpace);
         int totalHeight = z.getHeight();
         int totalWidth = z.getWidth();
         u.layout(0, 0, totalWidth, totalHeight);    
         u.buildDrawingCache(true);
         Bitmap b = Bitmap.createBitmap(u.getDrawingCache());             
         u.setDrawingCacheEnabled(false);
         File dir = new File(DATA_PATH + "tessdata");
         dir.mkdirs();

         if (!(new File(DATA_PATH + "tessdata/" + "eng" + ".traineddata")).exists()) {
             try {

                 AssetManager assetManager = getAssets();
                 InputStream in = assetManager.open("tessdata/eng.traineddata");
                 OutputStream out = new FileOutputStream(DATA_PATH
                         + "tessdata/eng.traineddata");

                 byte[] buf = new byte[1024];
                 int len;
                 while ((len = in.read(buf)) > 0) {
                     out.write(buf, 0, len);
                 }
                 in.close();
                 out.close();
             } catch (IOException e) {}
         }
         TessBaseAPI baseApi = new TessBaseAPI();
      baseApi.init(DATA_PATH, "eng");
      baseApi.setImage(b);
      recognizedText = baseApi.getUTF8Text();
      try {
    	  verif(recognizedText);
      } catch(Exception e){
    	  e.printStackTrace();
      } finally {
      baseApi.end();
      }
	}
	private void verif(String str) throws Exception{
		if(str == null)
			return;
		if ( str.charAt(0) == letter.charAt(0)){
			speak("goodjob");
			delay();
			reset();
			return;
		}
		speak("tryagain");
	}
	public void reset(){
	
		onCreate(null);
		
	}
	public void reset(View view){
		nullref=true;
		onCreate(null);
				
	}
	private void delay() throws Exception{
		Thread.sleep(1000l);
	}
	public void speak(String str ){
		int i=0;
		while(i<28 ){
			Log.d("speak", str);
			if(strtab[i]==str) 
					break;
			i+=1;
			
		}
		 MediaPlayer mPlayer = MediaPlayer.create(this,raws[i]);
		 mPlayer.start();
	}
	private String rand(){
		return strtab[(int)(Math.random() * 25)];
	}
}
