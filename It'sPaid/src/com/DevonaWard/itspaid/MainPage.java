package com.DevonaWard.itspaid;


import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

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
	//Current day
	TextView todayDate;
	
	@SuppressLint("SimpleDateFormat")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_page);
		
		billName = (EditText)findViewById(R.id.billName);
		amountPaid = (EditText)findViewById(R.id.amountPaid);
		totalDue = (EditText)findViewById(R.id.totalDue);	

		yesRadio = (RadioButton)findViewById(R.id.radioYes);
	   	noRadio = (RadioButton)findViewById(R.id.radioNo);
		
		paidInFull = (TextView)findViewById(R.id.paidFullTxt);
		todayDate = (TextView)findViewById(R.id.todayIs);
		
		monthSpinner = (Spinner) findViewById( R.id.monthSpin);
		daySpinner = (Spinner) findViewById( R.id.daySpin);
		
		
		calendar = Calendar.getInstance();
		thisYear = calendar.get(Calendar.YEAR);

		SimpleDateFormat dateFormatter = new SimpleDateFormat("MMMM dd, yyyy");
		String theM = dateFormatter.format(new Date());
		todayDate.setText("Today's date is "+theM);
		
		
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
    	   paidInFull.setText(nameofBill+" was paid in full on "+selectedMonth+" "+selectedDate+", "+selectedYear+".");
    	   
       }else if(noRadio.isChecked()){
    	   paidInFull.setText("$"+decimalFormat.format(thatTotal)+" is due by "+selectedMonth+" "+selectedDate+", "+selectedYear+".");
       }
		
	}
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
		savedIntent.putExtra("BillName", nameofBill);
		savedIntent.putExtra("BillPaid", thatPaid);
		savedIntent.putExtra("BillDue", thatOwed);
		savedIntent.putExtra("BillDate", selectedMonth+" "+selectedDate+", "+selectedYear);
		savedIntent.putExtra("BillCompleted", paidInFull.getText().toString());
	    startActivity(savedIntent);
	}
	
	public void openAbout(){
		Intent aboutIntent = new Intent(this, AboutPage.class);
		startActivity(aboutIntent);
	}

}
