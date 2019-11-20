package com.example.hotelbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    TextView tvCheckIn,tvCheckout,tvOutput,tvOutput1,tvOutput2,tvOutput3,tvOutput4,tvOutput5,tvOutput6,tvOutput7,tvOutput8,tvOutput9;
    Spinner spinRoom,spinLocation;
    EditText etAdult,etChildren,etRoom;
    Button btnCalculate,btnCheckin,btnCheckout;
    Boolean CheckIn=false;
    Boolean Checkout=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        tvOutput=findViewById(R.id.tvOutput);
        tvCheckIn=findViewById(R.id.tvCheckin);
        tvCheckout=findViewById(R.id.tvCheckout);
        btnCalculate=findViewById(R.id.btnCalculate);
        etAdult=findViewById(R.id.etAdult);
        etChildren=findViewById(R.id.etChildren);
        etRoom=findViewById(R.id.etRoom);
        spinRoom=findViewById(R.id.spinRoom);
        spinLocation=findViewById(R.id.spinLocation);
        btnCheckin=findViewById(R.id.btnCheckin);
        btnCheckout=findViewById(R.id.btnCheckout);
        tvOutput1=findViewById(R.id.tvOutput1);
        tvOutput2=findViewById(R.id.tvOutput2);
        tvOutput3=findViewById(R.id.tvOutput3);
        tvOutput4=findViewById(R.id.tvOutput4);
        tvOutput5=findViewById(R.id.tvOutput5);
        tvOutput6=findViewById(R.id.tvOutput6);
        tvOutput7=findViewById(R.id.tvOutput7);
        tvOutput8=findViewById(R.id.tvOutput8);
        tvOutput9=findViewById(R.id.tvOutput9);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Variable Declaration
                String location=spinLocation.getSelectedItem().toString();
                String Room=spinRoom.getSelectedItem().toString();
                int Adult=Integer.parseInt(etAdult.getText().toString());
                int Children=Integer.parseInt(etChildren.getText().toString());
                int numberofroom=Integer.parseInt(etRoom.getText().toString());
                int rate=0;
                String CheckIn=tvCheckIn.getText().toString();
                String CheckOut=tvCheckout.getText().toString();

                //Validation
                if(TextUtils.isEmpty(etAdult.getText()))
                {
                    etAdult.setError("Enter the number of adults");
                    return;
                }
                if(TextUtils.isEmpty(etChildren.getText()))
                {
                    etChildren.setError("Enter the number of children");
                    return;
                }
                if(TextUtils.isEmpty(etRoom.getText()))
                {
                    etRoom.setError("Enter the number of rooms");
                    return;
                }
                if(TextUtils.isEmpty(tvCheckIn.getText()))
                {
                    tvCheckIn.setError("Enter Check in Date");
                    return;
                }
                if(TextUtils.isEmpty(tvCheckout.getText()))
                {
                    tvCheckout.setError("Enter Check Out Date");
                    return;
                }
                if(Room=="Select Room Type")
                {
                    Toast.makeText(MainActivity.this, "Please Select Room Type", Toast.LENGTH_SHORT).show();
                }
                if(location=="Select Location")
                {
                    Toast.makeText(MainActivity.this, "Please Select Location", Toast.LENGTH_SHORT).show();
                }


                SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");
                try{
                    Date date1=sdf.parse(CheckIn);
                    Date date2=sdf.parse(CheckOut);
                    long diff=date2.getTime()-date1.getTime();
                    long diffDays=diff/(24*60*60*1000);
                    int days=(int) diffDays;

                //Room rate fixing
                if(Room=="Deluxe")
                {
                    rate=2000;
                }
                else if(Room=="AC")
                {
                    rate=3000;
                }
                else if(Room=="Platinum")
                {
                    rate=4000;
                }
                else
                {
                    Toast.makeText(MainActivity.this, "You Still need to Select A room type", Toast.LENGTH_SHORT).show();
                }

                //Calculation
                long calculate;
                Double calculate1,calculate2,total;
                double VAT=0.13;
                double SC=0.10;
                calculate=numberofroom*rate*days;
                calculate1=VAT*calculate;
                calculate2=SC*calculate;
                total=calculate+calculate1+calculate2;
                tvOutput.setText("Location: "+ location);
                tvOutput1.setText("Room Type:" +Room);
                tvOutput2.setText("Check In Date:"+CheckIn);
                tvOutput3.setText("Check Out Date:"+CheckOut);
                tvOutput4.setText("No. of Adults:"+Adult);
                tvOutput5.setText("No. of Children:"+Children);
                tvOutput6.setText("No. of Rooms:"+Room);
                tvOutput7.setText("VAT: "+ calculate1);
                tvOutput8.setText("Service Charge:"+calculate2);
                tvOutput9.setText("Total:"+total);
            }
                catch (ParseException e){
                    e.printStackTrace();
                }
            }
        });


        //Location Spinner
        String Location[]={"Select Location","Bhaktapur","Pokhara","Kathmandu","Chitwan"};
        ArrayAdapter adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,Location);
        spinLocation.setAdapter(adapter);

        //Room Spinner
        String Room[]={"Select Room Type","Deluxe","AC","Platinum"};
        ArrayAdapter adapter1=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,Room);
        spinRoom.setAdapter(adapter1);

        //Check in Button setOnClickListener
        btnCheckin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDatePicker();
                CheckIn=true;
            }
        });

        //Check Out Button setOnClickListener
        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDatePicker();
                Checkout=true;
            }
        });
    }

    //Datepicker Method
    public void loadDatePicker(){
        final Calendar c=Calendar.getInstance();
        int year=c.get(c.YEAR);
        int month=c.get(c.MONTH);
        int day=c.get(c.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog=new DatePickerDialog(this,this,year,month,day);
        datePickerDialog.show();
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            month = month + 1;
            String date= month+"/"+ dayOfMonth+"/"+year;
            if(CheckIn==true) {
                tvCheckIn.setText(date);
                CheckIn = false;
            }
            if(Checkout==true)
            {
                tvCheckout.setText(date);
                Checkout=false;
            }
        }
    }
