package com.DevonaWard.itspaid;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainPage extends Activity {
	//User inputs
	EditText billName;
	EditText amountPaid;
	EditText totalDue;
	//Radio buttons
	RadioButton yesRadio;
   	RadioButton noRadio;
	String radioName;
	//Get user input to string
	String nameofBill;
	String paidAmount;
	String dueAmount;
	//Get radio buttons to string
	RadioGroup radioSelectGroup;
	String selectYes;
	String selectNo;
	//Calendar info
	Calendar calendar;
	int thisYear;
	ArrayList<String> listYear = new ArrayList<String>();
	//Spinners
	Spinner yearSpinner;
	Spinner monthSpinner;
	Spinner daySpinner;
	//Spinner values
	String selectedMonth;
	String selectedDate;
	String selectedYear;
	//Math for money
	double thatPaid;
	double thatOwed;
	double thatTotal;
	DecimalFormat decimalFormat;
	//Paid in full text view
	TextView paidInFull;
	TextView ENB;
	TextView AP;
	TextView TAD;
	TextView SAD;
	TextView PIF;
	//Save Data
	String fileName;
	String content;
	String content2;
	String content3;
	//Font
	Typeface font;
	
	@SuppressLint("SimpleDateFormat")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_page);

		font = Typeface.createFromAsset(getAssets(),"robotothin.ttf");
		
		ENB = (TextView)findViewById(R.id.enterBN);
		ENB.setTypeface(font);
		
		AP = (TextView)findViewById(R.id.enterAP);
		AP.setTypeface(font);
		
		TAD = (TextView)findViewById(R.id.enterTAD);
		TAD.setTypeface(font);
		
		SAD = (TextView)findViewById(R.id.txtInstruct);
		SAD.setTypeface(font);
		
		PIF = (TextView)findViewById(R.id.paidFull);
		PIF.setTypeface(font);
		
		billName = (EditText)findViewById(R.id.billName);
		billName.setTypeface(font);
		
		amountPaid = (EditText)findViewById(R.id.amountPaid);
		amountPaid.setTypeface(font);
		
		totalDue = (EditText)findViewById(R.id.totalDue);
		totalDue.setTypeface(font);

		yesRadio = (RadioButton)findViewById(R.id.radioYes);
		yesRadio.setTypeface(font);
	   	
		noRadio = (RadioButton)findViewById(R.id.radioNo);
	   	noRadio.setTypeface(font);
		
		paidInFull = (TextView)findViewById(R.id.paidFullTxt);
		paidInFull.setTypeface(font);
		
		monthSpinner = (Spinner) findViewById( R.id.monthSpin);
		daySpinner = (Spinner) findViewById( R.id.daySpin);
		
		
		calendar = Calendar.getInstance();
		thisYear = calendar.get(Calendar.YEAR);
	
		//Populates the spinner for years until 2099
		for(int y=0; y<=86; y++){
			
			int years = thisYear + y;
			String totalYears = Integer.toString(years);
			listYear.add(totalYears);			
		}
		
		//Add years to the year spinner
		yearSpinner = new Spinner(this);
	    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
	            this, android.R.layout.simple_spinner_item, listYear);
	    spinnerArrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );

	    yearSpinner = (Spinner) findViewById( R.id.yearSpin);
	    yearSpinner.setAdapter(spinnerArrayAdapter);
	}

	public void saveIt(View v){
		nameofBill = (String) billName.getText().toString();
		paidAmount = (String) amountPaid.getText().toString();
		dueAmount = (String) totalDue.getText().toString();
		
		thatPaid = Double.parseDouble(paidAmount);
		thatOwed = Double.parseDouble(dueAmount);
		thatTotal = thatOwed - thatPaid;
		
		decimalFormat = new DecimalFormat("###.##");
		selectedMonth = String.valueOf(monthSpinner.getSelectedItem());
		selectedDate = String.valueOf(daySpinner.getSelectedItem());
		selectedYear = String.valueOf(yearSpinner.getSelectedItem());

		
       if(yesRadio.isChecked()){
    	   paidInFull.setText("Paid in full on "+selectedMonth+" "+selectedDate+", "+selectedYear+".");
    	   
       }else if(noRadio.isChecked()){
    	   paidInFull.setText("$"+decimalFormat.format(thatTotal)+" is due by "+selectedMonth+" "+selectedDate+", "+selectedYear+".");
       }

       //Save the data
       fileName = nameofBill+"\n"+"Amount Paid: $"+thatPaid+"\n"+paidInFull.getText().toString();			
       content = nameofBill+"\n"+"Amount Paid: $"+thatPaid+"\n"+paidInFull.getText().toString();
       
       FileOutputStream fos;
       try {
        fos = openFileOutput(fileName, Context.MODE_PRIVATE);
        fos.write(content.getBytes());
        fos.close();
       
        //Show that data was saved
        Toast.makeText(
          MainPage.this,
          "Bill Has Been Saved",
          Toast.LENGTH_LONG).show();
       
       } catch (FileNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
       } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
       }
      
      };

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_page, menu);
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
	
	//Data collected from user will be bundled here.
	public void openSaved(){
		Intent savedIntent = new Intent(this, SavedBills.class);
	    startActivity(savedIntent);
	}
	
	public void openAbout(){
		Intent aboutIntent = new Intent(this, AboutPage.class);
		startActivity(aboutIntent);
	}

}
