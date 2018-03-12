package com.bfurns.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bfurns.R;
import com.bfurns.utility.GetData;
import com.bfurns.utility.JSONParser;
import com.bfurns.utility.MyPreferences;
import com.bfurns.utility.MyUtility;
import com.bfurns.utility.Preferences;
import com.bfurns.utility.URLListner;
import com.bfurns.utility.Utility;
import com.bfurns.utility.Validations;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mahesh on 21/08/16.
 */
public class StepFourActivity extends AppCompatActivity implements View.OnClickListener {


    private Button login;
    private Toolbar toolbar;

    private RelativeLayout parent;
    private EditText eemail,phone,sms,emergency_contact,name;
    private String email,myphone,mysms,myemer,myname;

    private SharedPreferences sharedPreferences;
    private static final String TAG = StepFourActivity.class.getSimpleName();
    String clinic_id,user_id,doctor_id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_communiction_details);
        Intent intent = getIntent();
        clinic_id = intent.getStringExtra(MyPreferences.CLINIC_ID);
        user_id = intent.getStringExtra(MyPreferences.USER_ID);
        doctor_id = intent.getStringExtra(MyPreferences.DOCTOR_ID);
        setUpViews();
    }

    private void setUpViews() {

        sharedPreferences=getSharedPreferences(Preferences.MyPREFERENCES, Context.MODE_PRIVATE);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Step 4/4");
        parent=(RelativeLayout)findViewById(R.id.rl_loginActivity);
        name=(EditText) findViewById(R.id.name);
        eemail=(EditText) findViewById(R.id.email);
        phone=(EditText) findViewById(R.id.phone);
        sms=(EditText) findViewById(R.id.sms);
        emergency_contact=(EditText) findViewById(R.id.emergency);

        login=(Button)findViewById(R.id.btn_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginValidation();

            }
        });



    }



    private void loginValidation() {

      MyUtility.hideKeyboard(emergency_contact,StepFourActivity.this);

        email = eemail.getText().toString().trim();
        myphone =phone .getText().toString().trim();
        myemer =emergency_contact .getText().toString().trim();
        mysms =sms.getText().toString().trim();
        myname =name.getText().toString().trim();

        if (myname.equalsIgnoreCase("")) {


            MyUtility.showSnack(parent, Validations.ENTER_NAME);

        }else  if (myphone.equalsIgnoreCase("")) {


            MyUtility.showSnack(parent, Validations.ENTER_MOBILE);

        }else if (myphone.length() < 10) {


            MyUtility.showSnack(parent, Validations.ENTER_VALID_Mobile);


        } else if (myphone.length() > 10) {


            MyUtility.showSnack(parent, Validations.ENTER_VALID_Mobile);
        }

         else    if (email.equalsIgnoreCase("")) {


            MyUtility.showSnack(parent, Validations.ENTER_EMAIL);

        } else if (!email.matches(MyUtility.emailPattern) ) {


            MyUtility.showSnack(parent, Validations.ENTER_VALID_EMAIL);

        } else if (mysms.equalsIgnoreCase("") ) {

            MyUtility.showSnack(parent, Validations.ENTER_SMS_DETAILS);

        } else if (myemer.equalsIgnoreCase("") ) {

            MyUtility.showSnack(parent, Validations.ENTER_EMERGENCY_CONTACT);

        }  else {


            if(MyUtility.isConnected(this)){

                /*finish();
                Intent i = new Intent(StepFourActivity.this, HomeActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);*/
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

        params.add(new BasicNameValuePair("bus_title", myname));
        params.add(new BasicNameValuePair("bus_contact", myphone));
        params.add(new BasicNameValuePair("bus_email", email));
        params.add(new BasicNameValuePair("sms_details", mysms));
        params.add(new BasicNameValuePair("emergency_details", myemer));
        params.add(new BasicNameValuePair("bus_id", clinic_id));
        params.add(new BasicNameValuePair("user_id", user_id));

        GetData getData = new GetData(params);
        getData.setResultListner(new GetData.ResultListner() {

            int success = 0;
            private String id, clinic_id, username,doctor_id, phone, email1, image, mobileVerify, emailVerify, otp, socialType, socialId;

            @Override
            public void success(JSONObject jsonObject) {

                if (Utility.progressDialog.isShowing()) {
                    Utility.progressDialog.dismiss();
                }

                try {

                    success = jsonObject.getInt("responce");

                    Log.e(TAG, "Status:" + success);

                    if (success == 1) {

                        JSONArray arr = jsonObject.getJSONArray("data");
                        for (int i = 0; i < arr.length(); i++) {

                            JSONObject json = arr.getJSONObject(i);

                            id = json.getString("user_id");
                            username = json.getString("bus_title");
                            email1 = json.getString("bus_email");
                            clinic_id = json.getString("bus_id");

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
                            Intent intent = new Intent(StepFourActivity.this, HomeActivity.class);
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
        getData.execute(URLListner.BASEURL + URLListner.update_clinic_details);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){


            case R.id.btn_login:

                finish();
                Intent i = new Intent(StepFourActivity.this, HomeActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);

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

