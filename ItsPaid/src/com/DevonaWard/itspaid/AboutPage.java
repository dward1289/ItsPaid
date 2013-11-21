package com.DevonaWard.itspaid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageButton;
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
	//Internet stuff
	ConnectivityManager connectivityManager;
	NetworkInfo networkInfo;
	ImageButton playButton;

	
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
			
			playButton = (ImageButton)findViewById(R.id.playBtn);
			
			//YouTube video link
			String SrcPath = "android.resource://" + getPackageName() + "/" + R.raw.tutorial;
			
			vidView.setVideoURI(Uri.parse(SrcPath));
	        vidView.requestFocus(); 	        
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
			checkConnection();
		}
		
		public void playIt(View v){
			playButton.setVisibility(View.GONE);
			vidView.start();
			
		}
		
		private boolean checkConnection() {
			  connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			  networkInfo = connectivityManager.getActiveNetworkInfo();
			  if (networkInfo == null) {
				  new AlertDialog.Builder(this)
				    .setTitle("About")
				    .setMessage("You will need an internet connection. Please connect to a network.")
				    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int which) { 
				    		
				        }
				     })
				     .show();
			   return false;
			  } else{
				Intent surveyIntent = new Intent(this, SurveyPage.class);
				startActivity(surveyIntent);
			    }
			   return true;
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
			new AlertDialog.Builder(this)
		    .setTitle("About")
		    .setMessage("You are currently viewing the application info.")
		    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		    		
		        }
		     })
		     .show();
		}

		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.main_page, menu);
			return super.onCreateOptionsMenu(menu);
		}

}
