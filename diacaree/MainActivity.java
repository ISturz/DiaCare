package com.example.diacaree;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    Button dateButton, addButton, timeButton;
    int hour, minute;
    View historyIcon, settingsIcon;
    EditText record;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDatePicker();

        dateButton = findViewById(R.id.dateBtn);
        dateButton.setText(getTodaysDate());
        timeButton = findViewById(R.id.timeBtn);
        timeButton.setText(getTodaysTime());
        historyIcon = findViewById(R.id.ic_history);
        settingsIcon = findViewById(R.id.ic_settings);
        addButton = findViewById(R.id.addBtn);
        record = findViewById(R.id.readingid);
        iconPicker();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String r = record.getText().toString();
                String d = dateButton.getText().toString();
                String t = timeButton.getText().toString();

                DbHandler db = new DbHandler(MainActivity.this);
                db.insetDetails(r, d, t);

                Toast.makeText(getApplicationContext(), "Reading Added", Toast.LENGTH_SHORT).show();
            }
        });

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initTimePicker();
            }
        });
        
    }

    private void iconPicker() {

        historyIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), HistoryActivity.class);
                startActivity(intent);
            }
        });

        settingsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), SettingsActivity.class);
                startActivity(intent);
            }
        });
    }

    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
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
    }

    private void initTimePicker()
    {
        TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, android.R.style.Theme_Holo_Dialog_MinWidth, new TimePickerDialog.OnTimeSetListener() {
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