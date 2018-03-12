package com.bfurns.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

public class DoctorReplyActivity extends AppCompatActivity implements View.OnClickListener {

    EditText name,to,from,title,description;
    Button send,back;
    String message,p_title,p_name,d_name,p_description,id;
    RelativeLayout parent;

    private static final String TAG = DoctorReplyActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_reply);

        Intent intent=getIntent();
        p_name=intent.getStringExtra(MyPreferences.PATIENT_NAME);
        p_title=intent.getStringExtra(MyPreferences.TITLE);
        p_description=intent.getStringExtra(MyPreferences.DESCRIPTION);
        id=intent.getStringExtra(MyPreferences.QUERY_ID);

        setUpview();

    }

    private void setUpview() {


    Toolbar    toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Reply");


        to=(EditText)findViewById(R.id.name);
        from=(EditText) findViewById(R.id.from);
        title=(EditText) findViewById(R.id.titlee);
        description=(EditText) findViewById(R.id.description);
        send=(Button) findViewById(R.id.send);
        parent=(RelativeLayout) findViewById(R.id.parent);
        send.setOnClickListener(this);
        name.setText(p_name);
        title.setText(p_title);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.send:
                message=description.getText().toString();
                p_name=to.getText().toString();
                d_name=from.getText().toString();
                p_title=title.getText().toString();

                if (message.equalsIgnoreCase("")){

                    MyUtility.showSnack(parent, Validations.ENTER_MESSAGE);
                }else {

                    if (MyUtility.isConnected(this)){
                        callApi();

                    }else {
                        MyUtility.showSnack(parent,MyUtility.INTERNET_ERROR);

                    }
                }



                break;

        }

    }

    private void callApi() {
        Utility.showProgressDialog(this, "Please wait..");
        Utility.progressDialog.show();

        List<NameValuePair> params = new ArrayList<>();


        params.add(new BasicNameValuePair("q_id",id ));
        params.add(new BasicNameValuePair("p_to", p_name));

        //patient_name
        params.add(new BasicNameValuePair("p_from", d_name));
        //doctor_name
        params.add(new BasicNameValuePair("title", p_title));
        params.add(new BasicNameValuePair("description", message));


        GetData getData = new GetData(params);
        getData.setResultListner(new GetData.ResultListner() {

            int success = 0;
            private String id, clinic_id, username, phone, email1, image, mobileVerify, emailVerify, otp, socialType, socialId;

            @Override
            public void success(JSONObject jsonObject) {

                if (Utility.progressDialog.isShowing()) {
                    Utility.progressDialog.dismiss();
                }

                try {

                    success = jsonObject.getInt("responce");

                    Log.e(TAG, "Status:" + success);

                    if (success == 1) {

                        JSONArray arr = jsonObject.getJSONArray("query");
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
                        editor.putString(MyPreferences.USER_NAME, username);
                        editor.putString(MyPreferences.USER_EMAIL, email1);
                        editor.putString(MyPreferences.USER_PHONE, phone);
                        editor.putString(MyPreferences.USER_IMAGE, image);

                        editor.commit();

                        if (!MyPreferences.CLINIC_ID.isEmpty()) {
                            finish();
                            Utility.showSnackbar(parent, getString(R.string.success));
                            // clearFields();//.....call clear field...
                            Intent intent = new Intent(DoctorReplyActivity.this, StepTwoActivity.class);
                            intent.putExtra(MyPreferences.USER_ID, id);
                            intent.putExtra(MyPreferences.CLINIC_ID, clinic_id);
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
        getData.execute(URLListner.BASEURL + URLListner.reply_patient_query);




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
