package com.bfurns.ziffylink;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.bfurns.R;
import com.bfurns.activity.MAccountClinicList;
import com.bfurns.utility.MyPreferences;
import com.bfurns.utility.MyUtility;
import com.bfurns.utility.Preferences;
import com.bfurns.utility.Validations;

/**
 * Created by Mahesh on 21/08/16.
 */
public class MergeAccount extends AppCompatActivity implements View.OnClickListener {


    private Button forgot, signin;
    private Toolbar toolbar;

    private LinearLayout parent;
    private EditText eemail, epassword;
    private String email, password,doctor_id;
    private SharedPreferences sharedPreferences;

    private static final String TAG = MergeAccount.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginuser1);
        Intent intent=getIntent();
        doctor_id=intent.getStringExtra(MyPreferences.DOCTOR_ID);
        setUpViews();
    }

    private void setUpViews() {

        sharedPreferences = getSharedPreferences(Preferences.MyPREFERENCES, Context.MODE_PRIVATE);



        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Verify Email");


        parent = (LinearLayout) findViewById(R.id.parent);
        eemail = (EditText) findViewById(R.id.email);

        signin = (Button) findViewById(R.id.login);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginValidation();

            }
        });


/*
         TextView login=(TextView)findViewById(R.id.create_account);
        login.setOnClickListener(this)*/
        ;


    }


    private void loginValidation() {


        email = eemail.getText().toString().trim();


        if (email.equalsIgnoreCase("")) {


            MyUtility.showSnack(parent, Validations.ENTER_EMAIL);

        } else if (!email.matches(MyUtility.emailPattern)) {


            MyUtility.showSnack(parent, Validations.ENTER_VALID_EMAIL);

        } else {


            if (MyUtility.isConnected(this)) {


                Intent intent = new Intent(MergeAccount.this, MAccountClinicList.class);
                intent.putExtra(MyPreferences.USER_EMAIL, email);
                startActivity(intent);


            } else {

                MyUtility.showSnack(parent, MyUtility.INTERNET_ERROR);
            }

        }

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

           /* case R.id.forgot:

                startActivity(new Intent(MergeAccount.this,ForgotActivity.class));

                break;
*/
         /*   case R.id.create_account:

                startActivity(new Intent(LoginActivity.this,StepOneActivity.class));



                break;*/
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:

                onBackPressed();

                break;
        }

        return super.onOptionsItemSelected(item);
    }


}

