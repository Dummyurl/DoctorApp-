package com.bfurns.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bfurns.R;
import com.bfurns.application.AppClass;
import com.bfurns.utility.GetData;
import com.bfurns.utility.MyPreferences;
import com.bfurns.utility.MyUtility;
import com.bfurns.utility.URLListner;
import com.bfurns.utility.Utility;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mahesh on 21/08/16.
 */
public class PatientProfileActivity extends AppCompatActivity {
    TextView name, gender, height, weight, age, eallergies, ec_medicine, ep_medicine, ediseases, einjuries,
            esurgeries, eoccupation, esmoking, ealcohol, eactivity_level, efood;
    TextView bp, temp, plue, resp, o2, pain, problem, duration, medicin, since;
    TextView button;
    String id, bus_id, sub_id, user_id, doct_id, app_status, mystatus,app_date;
    String mbp, mtemp, mplue, mresp, mo2, mpain, mproblem, mduration, mmedicin, msince, mgender, mheight, mweight,
    mallergies, mc_medicine, mp_medicine, mdiseases, minjuries,
    msurgeries, moccupation, msmoking, malcohol, mactivity_level, mfood;
    RelativeLayout relativeLayout;
    private static final String TAG = PatientProfileActivity.class.getSimpleName();
    CheckBox checkBox;
    int status = 1;
    int mstatus;
    CardView c;


    FloatingActionMenu floatingActionMenu;
    FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3, floatingActionButton4, floatingActionButton5;


    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_patient_details);
        Intent intent = getIntent();
        id = AppClass.getAppointmentId();
        bus_id = AppClass.getclinicId();
        sub_id = AppClass.getSubId();
        user_id = AppClass.getUUserId();
        doct_id = AppClass.getuserId();
        app_status = AppClass.getAppStatus();
        app_date = intent.getStringExtra(MyPreferences.DATE);
        mystatus = Integer.toString(status);
     //   mstatus=Integer.parseInt(app_status);


        setUpViews();
    }

    private void setUpViews() {


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Patient Details");

        name = (TextView) findViewById(R.id.name);
        age = (TextView) findViewById(R.id.age);
        gender = (TextView) findViewById(R.id.P_gender);
        height = (TextView) findViewById(R.id.height);
        weight = (TextView) findViewById(R.id.weight);
        temp = (TextView) findViewById(R.id.Temp);
        bp = (TextView) findViewById(R.id.bp);
        plue = (TextView) findViewById(R.id.PLUSE);
        resp = (TextView) findViewById(R.id.RESP);
        o2 = (TextView) findViewById(R.id.O2SAT);
        pain = (TextView) findViewById(R.id.Pain);
        problem = (TextView) findViewById(R.id.problem);
        medicin = (TextView) findViewById(R.id.medicin);
        duration = (TextView) findViewById(R.id.duration);
        since = (TextView) findViewById(R.id.since);
        eallergies = (TextView) findViewById(R.id.allergies);
        ec_medicine = (TextView) findViewById(R.id.c_medicine);
        ep_medicine = (TextView) findViewById(R.id.p_medicine);
        ediseases = (TextView) findViewById(R.id.diseases);
        einjuries = (TextView) findViewById(R.id.injuries);
        esurgeries = (TextView) findViewById(R.id.surgeries);
        eoccupation = (TextView) findViewById(R.id.occupation);
        esmoking = (TextView) findViewById(R.id.smoking);
        ealcohol = (TextView) findViewById(R.id.alcohol);
        eactivity_level = (TextView) findViewById(R.id.activity_level);
        efood = (TextView) findViewById(R.id.food);










        c = (CardView) findViewById(R.id.c);

        checkBox = (CheckBox) findViewById(R.id.checkbox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                button.setVisibility(View.VISIBLE);
            }
        });


        if (app_status.equals("0")){
            c.setVisibility(View.VISIBLE);
        }else {
            c.setVisibility(View.GONE);
        }
        button = (TextView) findViewById(R.id.save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savepatient();
            }
        });


        relativeLayout = (RelativeLayout) findViewById(R.id.relative);
        floatingActionMenu = (FloatingActionMenu) findViewById(R.id.menu_red);

        floatingActionButton2 = (FloatingActionButton) findViewById(R.id.fab2);
        floatingActionButton3 = (FloatingActionButton) findViewById(R.id.fab3);
        floatingActionButton4 = (FloatingActionButton) findViewById(R.id.fab4);
        floatingActionButton5 = (FloatingActionButton) findViewById(R.id.fab5);


        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientProfileActivity.this, RecommendationTest.class);
                startActivity(intent);
            }
        });

        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientProfileActivity.this, PrescribeActivity.class);

                intent.putExtra(MyPreferences.USER_ID, user_id);
                intent.putExtra(MyPreferences.ID, id);
                intent.putExtra(MyPreferences.sub_id, sub_id);
                intent.putExtra(MyPreferences.DOCTOR_ID, doct_id);
                intent.putExtra(MyPreferences.CLINIC_ID, bus_id);
                intent.putExtra(MyPreferences.APP_STATUS, app_status);
                startActivity(intent);
            }
        });
        floatingActionButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientProfileActivity.this, MedicationHistory.class);
                intent.putExtra(MyPreferences.USER_ID, user_id);
                intent.putExtra(MyPreferences.ID, id);
                intent.putExtra(MyPreferences.sub_id, sub_id);
                intent.putExtra(MyPreferences.DOCTOR_ID, doct_id);
                intent.putExtra(MyPreferences.CLINIC_ID, bus_id);
                intent.putExtra(MyPreferences.APP_STATUS, app_status);
                intent.putExtra(MyPreferences.DATE, app_date);
                startActivity(intent);
            }
        });
        floatingActionButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientProfileActivity.this, PatientHistory.class);

                intent.putExtra(MyPreferences.USER_ID, user_id);
                intent.putExtra(MyPreferences.ID, id);
                intent.putExtra(MyPreferences.sub_id, sub_id);
                intent.putExtra(MyPreferences.DOCTOR_ID, doct_id);
                intent.putExtra(MyPreferences.CLINIC_ID, bus_id);
                intent.putExtra(MyPreferences.APP_STATUS, app_status);
                startActivity(intent);
            }
        });
        if (MyUtility.isConnected(this)) {
            PatientProfile();

        } else {
            MyUtility.showSnack(relativeLayout, MyUtility.INTERNET_ERROR);
        }


    }

    private void savepatient() {
        if (checkBox.isChecked()) {

            callApi();

        } else {
            MyUtility.showSnack(relativeLayout, MyUtility.INTERNET_ERROR);
        }

    }

    private void callApi() {

        Utility.showProgressDialog(this, "Please wait..");
        Utility.progressDialog.show();

        List<NameValuePair> params = new ArrayList<>();


        params.add(new BasicNameValuePair("app_id", id));
        params.add(new BasicNameValuePair("status", mystatus));


        GetData getData = new GetData(params);
        getData.setResultListner(new GetData.ResultListner() {

            int success = 0;
            private String id, doctor1, clinic_id, mage, username, phone, email1, image, mobileVerify, emailVerify, otp, socialType, socialId;

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


                            doctor1 = json.getString("doct_id");

                        }


                     c.setVisibility(View.GONE);

                    }
                    if (success != 1) {
                        Utility.showSnackbar(relativeLayout, "No data found");
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
        getData.execute(URLListner.BASEURL + URLListner.check_patient);


    }


    private void PatientProfile() {

        Utility.showProgressDialog(this, "Please wait..");
        Utility.progressDialog.show();

        List<NameValuePair> params = new ArrayList<>();


        params.add(new BasicNameValuePair("id", id));


        GetData getData = new GetData(params);
        getData.setResultListner(new GetData.ResultListner() {

            int success = 0;
            private String id, clinic_id, mage, username, phone, email1, image, mobileVerify, emailVerify, otp, socialType, socialId;

            @Override
            public void success(JSONObject jsonObject) {

                if (Utility.progressDialog.isShowing()) {
                    Utility.progressDialog.dismiss();
                }

                try {

                    success = jsonObject.getInt("responce");

                    Log.e(TAG, "Status:" + success);

                    if (success == 1) {

                        JSONArray arr = jsonObject.getJSONArray("patient");
                        for (int i = 0; i < arr.length(); i++) {

                            JSONObject json = arr.getJSONObject(i);

                            id = json.getString("id");
                            username = json.getString("app_name");
                            email1 = json.getString("app_email");
                            clinic_id = json.getString("bus_id");
                            mbp = json.getString("bp");
                            mtemp = json.getString("temp");
                            mplue = json.getString("pluse");
                            mresp = json.getString("resp");
                            mo2 = json.getString("o2sat");
                            mpain = json.getString("pain");
                            mproblem = json.getString("problem");
                            mduration = json.getString("duration");
                            mmedicin = json.getString("medicine");
                            msince = json.getString("since");
                            mheight = json.getString("height");
                            mweight = json.getString("weight");
                            mgender = json.getString("gender");
                            mage = json.getString("user_age");
                           /* mallergies = json.getString("allergies");
                            mc_medicine = json.getString("c_medicine");
                            mp_medicine = json.getString("p_medicine");
                            mdiseases = json.getString("diseases");
                            minjuries = json.getString("injuries");
                            msurgeries = json.getString("surgeries");
                            moccupation = json.getString("occupation");
                            msmoking = json.getString("smoking");
                            malcohol = json.getString("alcohol");
                            mactivity_level = json.getString("activity_level");
                            mfood = json.getString("food");
*/



/*

                            if (mallergies.equals("null")){
                                eallergies.setText("");
                            }else {
                                eallergies.setText(mallergies);

                            }



                            if (mc_medicine.equals("null")){
                                ec_medicine.setText("");
                            }else {
                                ec_medicine.setText(mc_medicine);

                            }

                            if (mp_medicine.equals("null")){
                                ep_medicine.setText("");
                            }else {
                                ep_medicine.setText(mp_medicine);

                            }

                            if (mdiseases.equals("null")){
                                ediseases.setText("");
                            }else {
                                ediseases.setText(mdiseases);

                            }


                            if (minjuries.equals("null")){
                                einjuries.setText("");
                            }else {
                                einjuries.setText(minjuries);

                            }

                            if (msurgeries.equals("null")){
                                esurgeries.setText("");
                            }else {
                                esurgeries.setText(msurgeries);

                            }

                            if (moccupation.equals("null")){
                                eoccupation.setText("");
                            }else {
                                eoccupation.setText(moccupation);

                            }

                            if (msmoking.equals("null")){
                                esmoking.setText("");
                            }else {
                                esmoking.setText(msmoking);

                            }

                            if (malcohol.equals("null")){
                                ealcohol.setText("");
                            }else {
                                ealcohol.setText(malcohol);

                            }

                            if (mactivity_level.equals("null")){
                                eactivity_level.setText("");
                            }else {
                                eactivity_level.setText(mactivity_level);

                            }

                            if (mfood.equals("null")){
                                efood.setText("");
                            }else {
                                efood.setText(mfood);

                            }


                            if (username.equals("null")){
                                name.setText("");
                            }else {
                                name.setText(username);

                            }
*/

                            if (mgender.equals("null")){
                                gender.setText("");
                            }else {
                                gender.setText(mgender);

                            }


                            if (mheight.equals("null")){
                                height.setText("");
                            }else {
                                height.setText(mheight);

                            }

                            if (mweight.equals("null")){
                                weight.setText("");
                            }else {
                                weight.setText(mweight);

                            }


                            if (mage.equals("null")){
                                age.setText("");
                            }else {
                                age.setText(mage);

                            }
                            name.setText(username);
                            temp.setText(mtemp);
                            bp.setText(mbp);
                            plue.setText(mplue);
                            resp.setText(mresp);
                            o2.setText(mo2);
                            pain.setText(mpain);
                            problem.setText(mproblem);
                            duration.setText(mduration);
                            medicin.setText(mmedicin);
                            since.setText(msince);


                        }


                    }
                    if (success != 1) {
                        Utility.showSnackbar(relativeLayout, "No data found");
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
        getData.execute(URLListner.BASEURL + URLListner.view_patient_details);

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

