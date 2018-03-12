package com.bfurns.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bfurns.R;
import com.bfurns.utility.GetData;
import com.bfurns.utility.MyPreferences;
import com.bfurns.utility.MyUtility;
import com.bfurns.utility.Preferences;
import com.bfurns.utility.URLListner;
import com.bfurns.utility.Utility;
import com.bfurns.utility.Validations;
import com.bfurns.ziffylink.ForgotActivity;
import com.bfurns.ziffylink.LoginActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mahesh on 22/10/17.
 */

public class AddServiceActivity  extends AppCompatActivity  {


    private Button forgot,login;
    private Toolbar toolbar;

    private RelativeLayout parent;
    private EditText service,cost,tax,amount;
    private String myservice,mycost,mytax,myamoumt;
    String clinic_id,user_id,doctor_id;
    private static final String TAG = AddServiceActivity.class.getSimpleName();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_add_service);
        Intent intent = getIntent();
        clinic_id = intent.getStringExtra(MyPreferences.CLINIC_ID);
        user_id = intent.getStringExtra(MyPreferences.USER_ID);
        doctor_id = intent.getStringExtra(MyPreferences.DOCTOR_ID);
        setUpViews();
    }

    private void setUpViews() {


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Add Practice");
        parent=(RelativeLayout)findViewById(R.id.rl_loginActivity);
     service=(EditText) findViewById(R.id.servicename);
     tax=(EditText) findViewById(R.id.edt_tax);
     cost=(EditText) findViewById(R.id.edt_cost);
     amount=(EditText) findViewById(R.id.edt_total);

        login=(Button)findViewById(R.id.btn_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addservices();

            }
        });


    }

    private void addservices() {

        MyUtility.hideKeyboard(cost,AddServiceActivity.this);

        myamoumt = amount.getText().toString().trim();
        mycost = cost.getText().toString().trim();
        myservice = service.getText().toString().trim();
        mytax = tax.getText().toString().trim();


        if (myservice.equalsIgnoreCase("")) {


            MyUtility.showSnack(parent, Validations.ENTER_SERVICE_NAME);

        } else if ( mycost.equalsIgnoreCase("")) {


            MyUtility.showSnack(parent, Validations.ENTER_COST);

        } else if (mytax.equalsIgnoreCase("") ) {

            MyUtility.showSnack(parent, Validations.ENTER_TAX);

        }
        else if (myamoumt.equalsIgnoreCase("") ) {

            MyUtility.showSnack(parent, Validations.ENTER_AMOUNT);

        }else {


            if(MyUtility.isConnected(this)){

             /*   finish();
                Intent i = new Intent(AddServiceActivity.this, StepThreeActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i)*/;
                callApi();



            }else{

                MyUtility.showSnack(parent,MyUtility.INTERNET_ERROR);
            }

        }
    }

    private void callApi() {

        Utility.showProgressDialog(this, "Please wait..");
        Utility.progressDialog.show();

        List<NameValuePair> params = new ArrayList<>();


        params.add(new BasicNameValuePair("service_title", myservice));
        params.add(new BasicNameValuePair("service_price", mycost));
        params.add(new BasicNameValuePair("service_tax", mytax));
        params.add(new BasicNameValuePair("total_cost", myamoumt));
        params.add(new BasicNameValuePair("bus_id", clinic_id));

        GetData getData = new GetData(params);
        getData.setResultListner(new GetData.ResultListner() {

            int success = 0;
            private String id, clinic_id,service_id, username,doctor_id, phone, email1, image, mobileVerify, emailVerify, otp, socialType, socialId;

            @Override
            public void success(JSONObject jsonObject) {

                if (Utility.progressDialog.isShowing()) {
                    Utility.progressDialog.dismiss();
                }

                try {

                    success = jsonObject.getInt("responce");

                    Log.e(TAG, "Status:" + success);

                    if (success == 1) {

                        JSONArray arr = jsonObject.getJSONArray("service");
                        for (int i = 0; i < arr.length(); i++) {

                            JSONObject json = arr.getJSONObject(i);

                            id = json.getString("doct_id");
                            username = json.getString("service_title");
                            email1 = json.getString("service_title");
                            clinic_id = json.getString("bus_id");
                            service_id = json.getString("id");

                        }

                        SharedPreferences sharedPreferences = getSharedPreferences(MyPreferences.My_PREFRENCES, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putString(MyPreferences.USER_ID, id);
                        editor.putString(MyPreferences.CLINIC_ID, clinic_id);
                        editor.putString(MyPreferences.DOCTOR_ID, doctor_id);
                        editor.putString(MyPreferences.USER_EMAIL, email1);
                        editor.putString(MyPreferences.USER_PHONE, phone);
                        editor.putString(MyPreferences.USER_IMAGE, image);

                        editor.commit();

                        if (!MyPreferences.CLINIC_ID.isEmpty()) {
                            finish();
                            Utility.showSnackbar(parent, getString(R.string.success));
                            // clearFields();//.....call clear field...
                            Intent intent = new Intent(AddServiceActivity.this, StepThreeActivity.class);
                            intent.putExtra(MyPreferences.USER_ID, user_id);
                            intent.putExtra(MyPreferences.CLINIC_ID, clinic_id);
                            intent.putExtra(MyPreferences.DOCTOR_ID, doctor_id);
                            startActivity(intent);

                        } else {
                            Utility.showSnackbar(parent, "Failed to store this session");
                        }


                    }
                    if (success != 1) {
                        Utility.showSnackbar(parent, getString(R.string.please_enter_valid_details));
                    }

                } catch (Exception e) {

                    e.printStackTrace();
                }
            }

            @Override
            public void fail() {

                if (Utility.progressDialog.isShowing()) {
                    Utility.progressDialog.dismiss();
                }

                if (success != 1) {
                    Utility.showSnackbar(parent, getString(R.string.failed));
                }


            }
        });
        getData.execute(URLListner.BASEURL + URLListner.add_clinic_services);

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.service_menu, menu);

        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:

                onBackPressed();
                return  true;

            case R.id.services:
                Intent intent=new Intent(AddServiceActivity.this,StepThreeActivity.class);
                intent.putExtra(MyPreferences.USER_ID, user_id);
                intent.putExtra(MyPreferences.CLINIC_ID, clinic_id);
                intent.putExtra(MyPreferences.DOCTOR_ID, doctor_id);
                startActivity(intent);

                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }



}

