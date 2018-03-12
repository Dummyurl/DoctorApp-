package com.bfurns.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.bfurns.R;

/**
 * Created by Mahesh on 21/08/16.
 */
public class PatientListActivity extends AppCompatActivity implements View.OnClickListener {


    private Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prescription);
        setUpViews();
    }

    private void setUpViews() {


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Patient List");


        LinearLayout linearLayout=(LinearLayout)findViewById(R.id.details);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(PatientListActivity.this,PatientProfileActivity.class));
            }
        });


    }




    @Override
    public void onClick(View v) {

        switch (v.getId()){


            case R.id.add:

                finish();

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

