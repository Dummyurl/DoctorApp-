package com.bfurns.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.bfurns.R;
import com.bfurns.utility.GetData;
import com.bfurns.utility.MyPreferences;
import com.bfurns.utility.MyUtility;
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
public class AddStaffActivity extends AppCompatActivity implements View.OnClickListener {


    private Toolbar toolbar;
    EditText name,email,contact;
    String ename,eemail,econtact;
    Button button;
    String clinic_id;
    RelativeLayout relativeLayout;
    private static final String TAG = AddStaffActivity.class.getSimpleName();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staff_create_profile);
        Intent intent = getIntent();
        clinic_id = intent.getStringExtra(MyPreferences.CLINIC_ID);
        setUpViews();
    }

    private void setUpViews() {


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Create Staff Profile");


        button=(Button) findViewById(R.id.create);
        name=(EditText) findViewById(R.id.name);
        email=(EditText) findViewById(R.id.edt_tax);
        contact=(EditText) findViewById(R.id.edt_cost);
        relativeLayout=(RelativeLayout) findViewById(R.id.rl_loginActivity);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateStaff();

            }
        });





    }

    private void validateStaff() {

        MyUtility.hideKeyboard(contact,AddStaffActivity.this);

        eemail = email.getText().toString().trim();
        econtact = contact.getText().toString().trim();
        ename = name.getText().toString().trim();

        if (ename.equalsIgnoreCase("")) {


            MyUtility.showSnack(relativeLayout, Validations.ENTER_NAME);

        }

        if (eemail.equalsIgnoreCase("")) {


            MyUtility.showSnack(relativeLayout, Validations.ENTER_EMAIL);

        } else if (!eemail.matches(MyUtility.emailPattern) ) {


            MyUtility.showSnack(relativeLayout, Validations.ENTER_VALID_EMAIL);

        } else if (econtact.equalsIgnoreCase("") ) {

            MyUtility.showSnack(relativeLayout, Validations.ENTER_MOBILE);

        }
        else if (econtact.length() < 10) {


            MyUtility.showSnack(relativeLayout, Validations.ENTER_VALID_Mobile);


        } else if (econtact.length() > 10) {


            MyUtility.showSnack(relativeLayout, Validations.ENTER_VALID_Mobile);
        }


        else {


            if(MyUtility.isConnected(this)){
                CallApi();

               /* finish();
                Intent i = new Intent(AddStaffActivity.this, StepTwoActivity.class);
               *//* i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);*//*
                startActivity(i);
*/


            }else{

                MyUtility.showSnack(relativeLayout,MyUtility.INTERNET_ERROR);
            }

        }
    }


    private void CallApi() {

        Utility.showProgressDialog(this, "Please wait..");
        Utility.progressDialog.show();

        List<NameValuePair> params = new ArrayList<>();


        params.add(new BasicNameValuePair("s_name", ename));
        params.add(new BasicNameValuePair("s_phone", econtact));
        params.add(new BasicNameValuePair("s_email", eemail));
        params.add(new BasicNameValuePair("bus_id", clinic_id));

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

                        JSONArray arr = jsonObject.getJSONArray("staff");
                        for (int i = 0; i < arr.length(); i++) {

                            JSONObject json = arr.getJSONObject(i);

                            id = json.getString("staff_id");
                            username = json.getString("s_name");
                            email1 = json.getString("s_email");
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
                            Utility.showSnackbar(relativeLayout, getString(R.string.success));
                            // clearFields();//.....call clear field...
                            Intent intent = new Intent(AddStaffActivity.this, StepThreeStaffActivity.class);
                            intent.putExtra(MyPreferences.USER_ID, id);
                            intent.putExtra(MyPreferences.CLINIC_ID, clinic_id);
                            intent.putExtra(MyPreferences.DOCTOR_ID, doctor_id);
                            startActivity(intent);

                        } else {
                            Utility.showSnackbar(relativeLayout, "Failed to store this session");
                        }


                    }
                    if (success != 1) {
                        Utility.showSnackbar(relativeLayout, getString(R.string.please_enter_valid_details));
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
                    Utility.showSnackbar(relativeLayout, getString(R.string.failed));
                }


            }
        });
        getData.execute(URLListner.BASEURL + URLListner.add_staff);

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

