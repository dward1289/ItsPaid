package com.DevonaWard.itspaid;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;


public class AboutPage extends Activity {
	//TextView
	TextView theTitle;
	TextView theVersion;
	TextView theDC;
	TextView theD;
	Typeface font;
	//Button
	Button PFBtn;
	//VideoView
	VideoView vidView;
	
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.theabout_page);
			font = Typeface.createFromAsset(getAssets(),"robotothin.ttf");
			theTitle = (TextView)findViewById(R.id.txtAN);
			theTitle.setTypeface(font);
			
			theVersion = (TextView)findViewById(R.id.txtV);
			theVersion.setTypeface(font);
			
			theDC = (TextView)findViewById(R.id.txtDC);
			theDC.setTypeface(font);
			
			theD =(TextView)findViewById(R.id.txtD);
			theD.setTypeface(font);
			
			PFBtn = (Button)findViewById(R.id.pfBtn);
			
			vidView = (VideoView) findViewById(R.id.videoView);
				
			//YouTube video link
			String SrcPath = "rtsp://r4---sn-p5qlsu7r.c.youtube.com/CiILENy73wIaGQmiY3ToNfBlgBMYDSANFEgGUgZ2aWRlb3MM/0/0/0/video.3gp";
			vidView.setVideoURI(Uri.parse(SrcPath));
	        vidView.requestFocus(); 
	        vidView.start();
	        
	        vidView.setOnTouchListener(new OnTouchListener() {
	            
	            public boolean onTouch(View v, MotionEvent event) {

	                if(vidView.isPlaying())
	                {
	                    vidView.pause();
	                }
	                else
	                {
	                    vidView.start();
	                }
	                return true;
	            }
	        });
		}
			
		//Display survey
		public void onClick(View v){
			Intent surveyIntent = new Intent(this, SurveyPage.class);
			startActivity(surveyIntent);
		}
		
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
		    // Handle presses on the action bar items
		    switch (item.getItemId()) {
		        case R.id.savedBills:
		        	openSaved();
		            return true;
		        case R.id.aboutIt:
		            openAbout();
		            return true;
		        default:
		            return super.onOptionsItemSelected(item);
		    }
		}
		
		public void openSaved(){
			Intent savedIntent = new Intent(this, SavedBills.class);
		    startActivity(savedIntent);
		}
		
		public void openAbout(){
			Intent aboutIntent = new Intent(this, AboutPage.class);
			startActivity(aboutIntent);
		}

		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.main_page, menu);
			return super.onCreateOptionsMenu(menu);
		}

}
