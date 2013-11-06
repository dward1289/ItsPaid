package com.DevonaWard.itspaid;
import java.util.ArrayList;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;



public class SavedBills extends Activity {

	String NameofBill;
	Double theBillPaid;
	Double WhatDue;
	String theStatus;
	
	ListView savedList;
	
	ArrayList<String> infoArray;
	String info;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.saved_bills);
		
		Bundle extras = getIntent().getExtras();
		
		NameofBill = extras.getString("BillName");
		theBillPaid = extras.getDouble("BillPaid");	
		WhatDue = extras.getDouble("BillDue");
		theStatus = extras.getString("BillCompleted");
		
		info = NameofBill+"\n"+
				"Amount Paid: "+theBillPaid+"\n"+
				"Amount Due: "+WhatDue+"\n"+
				theStatus;
		
		
		
		Log.i("DATA HERE", info);
		
		

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_page, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
}
