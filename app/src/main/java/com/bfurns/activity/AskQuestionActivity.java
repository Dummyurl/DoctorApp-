package com.bfurns.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.bfurns.R;

/**
 * Created by Mahesh on 21/08/16.
 */
public class AskQuestionActivity extends AppCompatActivity implements View.OnClickListener {


    private Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ask_query);
        setUpViews();
    }

    private void setUpViews() {


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Post Query");


        Button linearLayout=(Button) findViewById(R.id.ask);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

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

