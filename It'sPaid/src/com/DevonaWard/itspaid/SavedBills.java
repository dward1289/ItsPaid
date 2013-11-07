package com.DevonaWard.itspaid;
import java.util.ArrayList;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;




public class SavedBills extends Activity {
	//The Bundled data
	String NameofBill;
	Double theBillPaid;
	Double WhatDue;
	String theStatus;
	//Holds bundle string
	ArrayList<String> infoArray;
	//Bundle added to string
	String info;
	//ListView
	ListView listView;
	 String message;
	 Intent savedIntent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.saved_bills);
		 savedIntent = new Intent(this, MainPage.class);
		listView = (ListView) findViewById(R.id.list);
		infoArray = new ArrayList<String>();
		Bundle extras = getIntent().getExtras();
		
		if (extras != null) {
		NameofBill = extras.getString("BillName");
		theBillPaid = extras.getDouble("BillPaid");	
		WhatDue = extras.getDouble("BillDue");
		theStatus = extras.getString("BillCompleted");
		
		info = NameofBill+"\n"+
				"Amount Paid: "+theBillPaid+"\n"+
				"Amount Due: "+WhatDue+"\n"+
				theStatus;
		infoArray.add(info);
		}
		
        // Create the adapter
        ArrayAdapter<String> arrayAdapter =      
        new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, infoArray);
        // Set The Adapter
        listView.setAdapter(arrayAdapter); 
        
        //ListView onClick
        listView.setOnItemClickListener(new OnItemClickListener()
           {
         
                   public void onItemClick(AdapterView<?> arg0, View v,int position, long arg3)
                   {
                	
                        }
           });

}


	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_page, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
}

