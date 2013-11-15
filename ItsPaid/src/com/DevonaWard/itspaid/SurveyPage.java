package com.DevonaWard.itspaid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SurveyPage extends Activity {
	
	//Buttons
	ImageButton aRefresh;
	ImageButton aCancel;
	//WebView
	WebView surveyView;
	//Progress Bar
	ProgressBar progress;
	Intent aboutIntent;
	//TextView
	TextView theTitle;
	Typeface font;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.thesurvey_page);
		
		font = Typeface.createFromAsset(getAssets(),"robotothin.ttf");
		theTitle = (TextView)findViewById(R.id.theText);
		theTitle.setTypeface(font);
		
		surveyView = (WebView) findViewById(R.id.webview);
		progress = (ProgressBar) findViewById(R.id.progressBar);
		aRefresh = (ImageButton)findViewById(R.id.refreshBtn);
		aCancel = (ImageButton)findViewById(R.id.cancelBtn);
		aboutIntent = new Intent(this, AboutPage.class);
		
		//Create new web view client to handle progress bar.
        surveyView.setWebChromeClient(new theWebViewClient());

        progress.setMax(100);
        progress.setBackgroundColor(Color.rgb(85,107,47));
        
        surveyView.getSettings().setJavaScriptEnabled(true);
		surveyView.loadUrl("https://docs.google.com/forms/d/1DtMAiwbnPL_7Jt2lUnjkoxUdCktflG77zSe3m0G4uqI/viewform");

        SurveyPage.this.progress.setProgress(0);
	
	}
	
	//Shows loading progress of survey.
	private class theWebViewClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {         
            SurveyPage.this.setValue(newProgress);
            super.onProgressChanged(view, newProgress);
        }
    }
	//The value that changes to show progression in the progress bar.
	public void setValue(int progress) {
        this.progress.setProgress(progress);       
    }
	
	public void onRefresh(View v){
		finish();
    	startActivity(getIntent());
	}
	
	public void onCancel(View v){
		new AlertDialog.Builder(this)
	    .setTitle("It's Paid Feedback Survey")
	    .setMessage("Cancel survey?")
	    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	    		startActivity(aboutIntent);
	        }
	     })
	     .setNegativeButton("No", new DialogInterface.OnClickListener() {
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
