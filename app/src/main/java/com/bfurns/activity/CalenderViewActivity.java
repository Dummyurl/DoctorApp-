package com.bfurns.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.bfurns.R;
import com.bfurns.utility.MyUtility;
import com.bfurns.utility.Preferences;
import com.bfurns.utility.Validations;

/**
 * Created by Mahesh on 21/08/16.
 */
public class CalenderViewActivity extends AppCompatActivity implements View.OnClickListener {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_calender_main);
        setUpViews();
    }

    private void setUpViews() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Appointment");

        FloatingActionButton add=(FloatingActionButton) findViewById(R.id.add);
        add.setOnClickListener(this);

        CalendarView simpleCalendarView = (CalendarView) findViewById(R.id.calendeview); // get the reference of CalendarView
        simpleCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                startActivity(new Intent(CalenderViewActivity.this,AppointmentListActivity.class));

            }
        });

    }





    @Override
    public void onClick(View v) {

        switch (v.getId()){


            case R.id.add:


                startActivity(new Intent(CalenderViewActivity.this,AddAppointmentActivity.class));

                break;


        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case android.R.id.home:

                onBackPressed();

                break;
        }

        return super.onOptionsItemSelected(item);
    }


}

