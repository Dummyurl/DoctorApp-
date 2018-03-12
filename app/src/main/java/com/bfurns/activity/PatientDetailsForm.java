package com.bfurns.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bfurns.R;
import com.bfurns.utility.GetData;
import com.bfurns.utility.MyPreferences;
import com.bfurns.utility.URLListner;
import com.bfurns.utility.Utility;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PatientDetailsForm extends AppCompatActivity {

    TextView ename,eemail,egender,ebirthday,eheight,eweight,econtact,emaritala_staus,eblood_group,eemer_contact,elocation,ealergy,
            ecurrent_m,epast_m,echronic,einjury,esergery,eoccupation,esmoking,ealchohol,eactivity_level,efood;
    RelativeLayout parent;
    private static final String TAG = PatientDetailsForm.class.getSimpleName();
    String user_id,doctor_id,app_id,id;
    Button button;

    String name,email,gender,birthday,height,weight,contact,maritala_staus,blood_group,emer_contact,location,alergy,
            current_m,past_m,chronic,injury,sergery,occupation,smoking,alchohol,activity_level,food,age;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details_form);
        Intent intent = getIntent();
        user_id = intent.getStringExtra(MyPreferences.USER_ID);
        doctor_id = intent.getStringExtra(MyPreferences.DOCTOR_ID);
        app_id = intent.getStringExtra(MyPreferences.APP_ID);
        id = intent.getStringExtra(MyPreferences.ID);

        setUpview();
    }

    private void setUpview() {

     Toolbar   toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Patient Details");
        parent = (RelativeLayout) findViewById(R.id.parent);

        ename=(TextView)findViewById(R.id.name);
        eemail=(TextView)findViewById(R.id.email);
        egender=(TextView)findViewById(R.id.check);
        ebirthday=(TextView)findViewById(R.id.app_date);
        eheight=(TextView)findViewById(R.id.mode);
        econtact=(TextView)findViewById(R.id.Contact);
        eblood_group=(TextView)findViewById(R.id.AppointmentTime);
        eemer_contact=(TextView)findViewById(R.id.clinic_name);
        elocation=(TextView)findViewById(R.id.PaymentAmount);
     //   ealergy=(TextView)findViewById(R.id.RemainingAmount);

        getDetails();
    }

    private void getDetails() {

        Utility.showProgressDialog(this, "Please wait..");
        Utility.progressDialog.show();

        List<NameValuePair> params = new ArrayList<>();


        params.add(new BasicNameValuePair("user_id", id));

        GetData getData = new GetData(params);
        getData.setResultListner(new GetData.ResultListner() {

            int success = 0;
            private String id, clinic_id,payment_amount,clinic_name,payed_amount,due_amount,start_time, mode_app,username, phone, email1,appointment_date,app_status,image, mobileVerify, emailVerify, otp, socialType, socialId;



            @Override
            public void success(JSONObject jsonObject) {

                if (Utility.progressDialog.isShowing()) {
                    Utility.progressDialog.dismiss();
                }

                try {

                    success = jsonObject.getInt("responce");

                    Log.e(TAG, "Status:" + success);

                    if (success == 1) {

                        JSONArray arr = jsonObject.getJSONArray("appointment");
                        for (int i = 0; i < arr.length(); i++) {




                            JSONObject json = arr.getJSONObject(i);

                            id = json.getString("user_id");
                            username = json.getString("app_name");
                            email1 = json.getString("app_email");
                            phone = json.getString("app_phone");
                            mode_app = json.getString("mode_app");
                            app_status = json.getString("app_status");
                            appointment_date = json.getString("appointment_date");
                            payment_amount = json.getString("payment_amount");
                            payed_amount = json.getString("payed_amount");
                            due_amount = json.getString("due_amount");
                            start_time = json.getString("start_time");
                            clinic_name = json.getString("bus_title");
                            econtact.setText(phone);
                            eblood_group.setText(start_time);
                            eemer_contact.setText(clinic_name);
                            elocation.setText(payment_amount);

                            if (mode_app.equals("0")){
                               eheight.setText("App");
                            }else if (mode_app.equals("1")){
                                eheight.setText("walk-in");

                            }else {
                                eheight.setText("Telephonic");
                            }

                            if (app_status.equals("0")){
                                egender.setText("Unchecked");

                            }else {
                                egender.setText("Checked");

                            }


                            ename.setText(username);
                            eemail.setText(email1);


                            ebirthday.setText(appointment_date);







                        }



                    }else {

                        Utility.showSnackbar(parent, getString(R.string.details_not_available));


                    }
                    if (success != 1) {
                        Utility.showSnackbar(parent, getString(R.string.details_not_available));
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
        getData.execute(URLListner.BASEURL + URLListner.patient_personal_details);


    }







    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:

                onBackPressed();
                return  true;

            case R.id.save:


                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                LayoutInflater inflater = getLayoutInflater();
                View view=inflater.inflate(R.layout.save_alert_dailog, null);
                final CheckBox checkBox=(CheckBox)findViewById(R.id.checkbox);
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (checkBox.isChecked()){
//                            SharedPreferences.Editor editor = .edit();
//                            editor.putBoolean("preferences_never_buy_pro", true);
//                            editor.apply();
                        }

                    }
                });
                TextView title=(TextView)view.findViewById(R.id.alertTitle);
                title.setText("Save Patient");
                builder.setCustomTitle(view);

              builder .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Uri uri = Uri.parse("market://details?id=MY_APP_PACKAGE");
                    Intent intent = new Intent (Intent.ACTION_VIEW, uri);
                    startActivity(intent);

                }
            })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();

                        }
                    }).show();

                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
