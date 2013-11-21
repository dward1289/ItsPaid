package com.DevonaWard.itspaid;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;


import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainPage extends Activity {
	//User inputs
	EditText billName;
	EditText amountPaid;
	EditText totalDue;
	//Get user input to string
	String nameofBill;
	String paidAmount;
	String dueAmount;
	//Calendar info
	Calendar calendar;
	//Date Value
	String selectedDate;
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
	TextView dateSel;
	//Save Data
	String fileName;
	String content;
	String content2;
	String content3;
	//Font
	Typeface font;
	int savedCount;
	
	
	
	@SuppressLint("SimpleDateFormat")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_page);

		font = Typeface.createFromAsset(getAssets(),"robotothin.ttf");
		
		ENB = (TextView)findViewById(R.id.enterBN);
		ENB.setTypeface(font);
		
		dateSel = (TextView)findViewById(R.id.dateIt);
		dateSel.setTypeface(font);
		
		AP = (TextView)findViewById(R.id.enterAP);
		AP.setTypeface(font);
		
		TAD = (TextView)findViewById(R.id.enterTAD);
		TAD.setTypeface(font);
		
		SAD = (TextView)findViewById(R.id.txtInstruct);
		SAD.setTypeface(font);
		
		billName = (EditText)findViewById(R.id.billName);
		billName.setTypeface(font);
		
		amountPaid = (EditText)findViewById(R.id.amountPaid);
		amountPaid.setTypeface(font);
		
		totalDue = (EditText)findViewById(R.id.totalDue);
		totalDue.setTypeface(font);
		
		paidInFull = (TextView)findViewById(R.id.paidFullTxt);
		paidInFull.setTypeface(font);		
	}

	//Date picker created
	 public void selectDate(View view) {
   	  	DialogFragment newFragment = new SelectDateFragment();
   	  	newFragment.show(getFragmentManager(), "DatePicker");
   	  }
   	  
	 //Display Date in text view
	 public void populateSetDate(int year, int month, int day) {
   	  	dateSel.setText(month+"-"+day+"-"+year);
   	  }
	 
	 //Get date from date picker
   	  @SuppressLint("ValidFragment")
	  public class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
   		  @Override
   		  public Dialog onCreateDialog(Bundle savedInstanceState) {
   			  final Calendar calendar = Calendar.getInstance();
   			  int yy = calendar.get(Calendar.YEAR);
   			  int mm = calendar.get(Calendar.MONTH);
   			  int dd = calendar.get(Calendar.DAY_OF_MONTH);
   			  return new DatePickerDialog(getActivity(), this, yy, mm, dd);
   	  }
   	   
   		  public void onDateSet(DatePicker view, int yy, int mm, int dd) {
   			  populateSetDate(yy, mm+1, dd);
   		  }
   	  }
   	  
	public void saveIt(View v){
	    
		getTheData();
      };

      public void getTheData(){
    	decimalFormat = new DecimalFormat("###.##");

         if(billName.getText().toString().trim().equals("")){
        	 new AlertDialog.Builder(this)
             .setTitle("It's Paid")
             .setMessage("Please enter bill name.")
             .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                 public void onClick(DialogInterface dialog, int which) { 
                 }
         	     })
         	     .show();
      	   
         }else{
        	 nameofBill = (String) billName.getText().toString();
         }
      	   
         if(amountPaid.getText().toString().trim().equals("")){
        	 new AlertDialog.Builder(this)
             .setTitle("It's Paid")
             .setMessage("Please enter amount paid.")
             .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                 public void onClick(DialogInterface dialog, int which) { 
                 }
         	     })
         	     .show();
      		
      	   }else{
      		 paidAmount = (String) amountPaid.getText().toString();     		 
             }
      		   
         if(totalDue.getText().toString().trim().equals("")){
        	 new AlertDialog.Builder(this)
	           .setTitle("It's Paid")
	           .setMessage("Please enter total amount due.")
	           .setPositiveButton("OK", new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int which) { 
	               }
	       	     })
	       	     .show();
      	   
      		   }else{
      			 dueAmount = (String)totalDue.getText().toString();
      			 
      	       }   			  
      			
         if(dateSel.getText().toString().trim().equals("")){
        	 new AlertDialog.Builder(this)
             .setTitle("It's Paid")
             .setMessage("Please select a date.")
             .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                 public void onClick(DialogInterface dialog, int which) { 
                 }
         	     })
         	     .show();
      	   
         }else{
  			 selectedDate = (String)dateSel.getText().toString();
	       }
         
         
        	thatPaid = Double.parseDouble(paidAmount);
     		thatOwed = Double.parseDouble(dueAmount);
     		thatTotal = thatOwed - thatPaid;
     		if(thatTotal == 0){
      	   paidInFull.setText("Paid in full on "+selectedDate);   
        		savedtheData(); 
        		theNotification();
     		}else{ 			
          			paidInFull.setText("$"+decimalFormat.format(thatTotal)+" is due by "+selectedDate);   
             		savedtheData();
             		theNotification();
          		}     		       
      }
      
      
      public void savedtheData(){
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
      }
      
    //Notifies user of the number of bills pending.
	public void theNotification(){
  		    //prepare intent which is triggered if the notification is selected
    	    Intent intent = new Intent(this, SavedBills.class);    	    
    		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

    		//Notification sound
    		Uri theSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    		
    		savedCount = getApplicationContext().fileList().length;
    		//Vibration pattern
    		long[] vibration = { 0, 500, 250, 500 };
    		
    		// build notification
    		Notification notification  = new Notification.Builder(this)
    		
    		        .setContentTitle("It's Paid")
    		        .setContentText("You now have a total of " +savedCount + " bills saved.")
    		        .setSmallIcon(R.drawable.ic_launcher)
    		        .setContentIntent(pendingIntent)
    		        .setSound(theSound)
    		        .setVibrate(vibration)
    		        .setAutoCancel(true)
    		        .build();
    		    
    		  
    		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

    		//Display notification
    		notificationManager.notify(0, notification);
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
	
	public void openSaved(){
		Intent savedIntent = new Intent(this, SavedBills.class);
	    startActivity(savedIntent);
	}
	
	public void openAbout(){
		Intent aboutIntent = new Intent(this, AboutPage.class);
		startActivity(aboutIntent);
	}

}
