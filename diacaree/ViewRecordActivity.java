package com.example.diacaree;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

public class ViewRecordActivity extends AppCompatActivity {

    Bundle intentBundle;
    int recordID;

    TextView editReadingTitle;
    EditText recordInt;
    Button btnUpdate, btnDelete, timeButton, dateButton;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    int hour, minute;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_record);



        intentBundle = getIntent().getExtras();
        recordID = intentBundle.getInt("RID");

        DbHandler db = new DbHandler(this);
        HashMap<String, String> record = db.getSingleRecord(recordID);

        recordInt = findViewById(R.id.readingid);
        dateButton = findViewById(R.id.dateBtn);
        timeButton = findViewById(R.id.timeBtn);

        btnUpdate = findViewById(R.id.updateBtn);
        btnDelete = findViewById(R.id.deleteBtn);

        recordInt.setText(record.get("record"));
        timeButton.setText(record.get("time"));
        dateButton.setText(record.get("date"));


        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initTimePicker();
            }
        });

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initDatePicker();
            }
        });




    }

    public void updateRecord(View view){
        DbHandler db = new DbHandler(ViewRecordActivity.this);
        String rec = recordInt.getText().toString();
        String dat = dateButton.getText().toString();
        String tim = timeButton.getText().toString();

        db.updateRecordDetails(recordID, rec, dat, tim);
        Toast.makeText(this, "Reading Updated", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, HistoryActivity.class));
    }

    public void deleteRecord(View view){
        DbHandler db = new DbHandler(ViewRecordActivity.this);
        db.deleteRecord(recordID);
        Toast.makeText(this, "Reading Deleted", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, HistoryActivity.class));
    }




    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1; //This sets january to 1 instead of 0
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialog.show();
    }

    private void initTimePicker()
    {
        TimePickerDialog timePickerDialog = new TimePickerDialog(ViewRecordActivity.this, android.R.style.Theme_Holo_Dialog_MinWidth, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minuteOfDay) {

                hour = hourOfDay;
                minute = minuteOfDay;

                String time = hour + ":" + minute;
                SimpleDateFormat f24Hours = new SimpleDateFormat(
                        "HH:mm"
                );
                try{
                    Date date = f24Hours.parse(time);
                    SimpleDateFormat f12Hours = new SimpleDateFormat(
                            "hh:mm aa"
                    );
                    timeButton.setText(f12Hours.format(date));
                } catch (ParseException e){
                    e.printStackTrace();
                }
            }
        }, 12, 0, false
        );
        timePickerDialog.show();

    };

    private String getTodaysTime()
    {
        Calendar time = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        Date currentLocalTime = time.getTime();
        DateFormat date = new SimpleDateFormat("HH:mm a");
// you can get seconds by adding  "...:ss" to it
        time.setTimeZone(TimeZone.getTimeZone("GMT"));

        String localTime = date.format(currentLocalTime);
        return localTime;
    }

    private String makeDateString(int day, int month, int year)
    {
        return getMonthFormat(month) + " " + day + " " + year;
    }


    //Changes month from number to month name
    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JLY";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        //DEFAULT
        return "JAN";
    }

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }





}