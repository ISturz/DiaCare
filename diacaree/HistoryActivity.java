package com.example.diacaree;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;


import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class HistoryActivity extends AppCompatActivity {

    private View recordIcon, settingsIcon;
    ListView listView;
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recordIcon = findViewById(R.id.ic_record);
        settingsIcon = findViewById(R.id.ic_settings);
        iconPicker();

        DbHandler db = new DbHandler(this);
        ArrayList<HashMap<String, String>> recordList = db.getAllRecords();

        listView = findViewById(R.id.recordList);

        ListAdapter adapter = new SimpleAdapter(HistoryActivity.this, recordList, R.layout.list_row, new String[]{"date", "record", "time"},
                new int[]{R.id.date, R.id.record, R.id.time});

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), ViewRecordActivity.class);
                HashMap<String, String> recordObj = (HashMap<String, String>) adapter.getItem(i);

                int tempID = Integer.valueOf(recordObj.get("id"));

                Bundle extra = new Bundle();
                extra.putInt("RID", tempID);
                intent.putExtras(extra);
                startActivity(intent);
            }
        });

        GraphView graphView = (GraphView) findViewById(R.id.lineGraph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
        double doubleRecord;

        graphView.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(){
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if(isValueX){
                    return sdf.format(new Date((long) value));
                }
                else{
                    return super.formatLabel(value, isValueX);
                }
            }
        });

        int idInt = 1;
        double y = 0.0;
        if(db.hasRecords()) {
            for (int i = 0; i < 2; i++) {

                String record = db.getGraphDataRecord(idInt);
                String time = db.getGraphDataTime(idInt);
                idInt++;

                Date timeDate = null;
                try {
                    timeDate = new SimpleDateFormat("HH:mm a").parse(time);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (record != null) {
                    doubleRecord = Double.parseDouble(record);
                    assert timeDate != null;
                    series.appendData(new DataPoint(timeDate, doubleRecord), true, 2);
                }
            }

            graphView.addSeries(series);
        }

        else{
            Toast.makeText(this, "No Records", Toast.LENGTH_SHORT).show();
        }

    }

    private void iconPicker() {
        recordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), MainActivity.class);
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
}