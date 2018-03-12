package com.bfurns.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.bfurns.R;
import com.bfurns.adapter.AppointmentAdapter;
import com.bfurns.adapter.PatientAdapter;
import com.bfurns.application.AppClass;
import com.bfurns.model.AppointementModel;
import com.bfurns.utility.GetData;
import com.bfurns.utility.MyPreferences;
import com.bfurns.utility.MyUtility;
import com.bfurns.utility.URLListner;
import com.bfurns.utility.Utility;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PatientDetails extends AppCompatActivity {
    String userId,clinic_id,doctor_id,clinic_doctor_id,merge_doctor_id,status;
    private RecyclerView recyclerView;
    private ArrayList<AppointementModel> arrayList = new ArrayList<>();
    private PatientAdapter appointmentAdapter;
    RecyclerView.LayoutManager layoutManager;
    RelativeLayout relativeLayout;
    SwipeRefreshLayout mSwipeRefreshLayout;
    String comman_account_clinic_id="0";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details);
        Intent intent = getIntent();

        userId = AppClass.getuserId();
        clinic_id = AppClass.getclinicId();
        doctor_id= AppClass.getclinicId();
        clinic_doctor_id= AppClass.getsessionclinic_doctor_id();
        merge_doctor_id= AppClass.getmerge_doctor_id();
        status= AppClass.getcomman_account_status();

        setUpviews();
    }

    private void setUpviews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Patient Management");
        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeToRefresh);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        relativeLayout = (RelativeLayout) findViewById(R.id.parent);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        LinearLayoutManager verticalLayoutmanager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        verticalLayoutmanager.setReverseLayout(false);
        verticalLayoutmanager.setStackFromEnd(false);
        recyclerView.setLayoutManager(verticalLayoutmanager);
        recyclerView.setHasFixedSize(true);




        if (MyUtility.isConnected(this)){

            if (status.equals("1")) {
                CommanDetails();
            } else {
                getApointements();

            }
        }else {
            MyUtility.showSnack(relativeLayout, MyUtility.INTERNET_ERROR);

        }


        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (status.equals("1")) {
                    CommanDetails();
                } else {
                    getApointements();

                }

                mSwipeRefreshLayout.setRefreshing(false);






            }
        });

    }

    private void CommanDetails() {

        arrayList.clear();

    /*    Utility.showProgressDialog(PatientDetails.this, "Please wait...");
        Utility.progressDialog.show();
*/

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("doct_id",merge_doctor_id));
        params.add(new BasicNameValuePair("bus_id", comman_account_clinic_id));







        GetData getData = new GetData(params);
        getData.setResultListner(new GetData.ResultListner() {

            int success = 0;

            @Override
            public void success(JSONObject jsonObject) {
/*

                if (Utility.progressDialog.isShowing()) {
                    Utility.progressDialog.dismiss();
                }
*/



                try {

                    success = jsonObject.getInt("response");


                    if (success == 1) {
                        // jsonObject.getString("CharterProduct");
                        JSONArray arr = jsonObject.getJSONArray("appointment");
                        for (int i = 0; i < arr.length(); i++) {
                            if (arr.length() == 0) {
                                Utility.showSnackbar(relativeLayout, getString(R.string.notavailable));
                            } else {

                                JSONObject json = arr.getJSONObject(i);
                                AppointementModel extrasItemModel = new AppointementModel();


                                extrasItemModel.setPatient_name(json.getString("app_name"));
                                extrasItemModel.setEmail(json.getString("app_email"));
                                extrasItemModel.setContact(json.getString("app_phone"));
                                extrasItemModel.setDate(json.getString("appointment_date"));
                                extrasItemModel.setTime(json.getString("start_time"));
                                extrasItemModel.setUser_id(json.getString("user_id"));
                                extrasItemModel.setId(json.getString("id"));
                                extrasItemModel.setImage(json.getString("patient_img"));


                                arrayList.add(extrasItemModel);


                                appointmentAdapter = new PatientAdapter(arrayList, PatientDetails.this);
                                recyclerView.setAdapter(appointmentAdapter);


                                appointmentAdapter.notifyDataSetChanged();


                            }

                        }

                    }


                    if (success != 1) {
                        Utility.showSnackbar(relativeLayout, "Patient Details Not Available");
                    }

                } catch (Exception e) {

                    e.printStackTrace();
                }
            }

            @Override
            public void fail() {
/*
                if (Utility.progressDialog.isShowing()) {
                    Utility.progressDialog.dismiss();
                }*/

                if (success != 1) {
                    Utility.showToast(getApplicationContext(), getString(R.string.failed));
                }


            }
        });
        getData.execute(URLListner.BASEURL + URLListner.get_patient_list_merge);


    }



    private void getApointements() {


        arrayList.clear();

        Utility.showProgressDialog(PatientDetails.this, "Please wait...");
        Utility.progressDialog.show();


        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("doct_id",userId));
        params.add(new BasicNameValuePair("bus_id", clinic_id));







        GetData getData = new GetData(params);
        getData.setResultListner(new GetData.ResultListner() {

            int success = 0;

            @Override
            public void success(JSONObject jsonObject) {

                if (Utility.progressDialog.isShowing()) {
                    Utility.progressDialog.dismiss();
                }



                try {

                    success = jsonObject.getInt("response");


                    if (success == 1) {
                        // jsonObject.getString("CharterProduct");
                        JSONArray arr = jsonObject.getJSONArray("appointment");
                        for (int i = 0; i < arr.length(); i++) {
                            if (arr.length() == 0) {
                                Utility.showSnackbar(relativeLayout, getString(R.string.notavailable));
                            } else {

                                JSONObject json = arr.getJSONObject(i);
                                AppointementModel extrasItemModel = new AppointementModel();


                                extrasItemModel.setPatient_name(json.getString("app_name"));
                                extrasItemModel.setEmail(json.getString("app_email"));
                                extrasItemModel.setContact(json.getString("app_phone"));
                                extrasItemModel.setDate(json.getString("appointment_date"));
                                extrasItemModel.setTime(json.getString("start_time"));
                                extrasItemModel.setUser_id(json.getString("user_id"));
                                extrasItemModel.setId(json.getString("id"));
                                extrasItemModel.setImage(json.getString("patient_img"));


                                arrayList.add(extrasItemModel);


                                appointmentAdapter = new PatientAdapter(arrayList, PatientDetails.this);
                                recyclerView.setAdapter(appointmentAdapter);


                                appointmentAdapter.notifyDataSetChanged();


                            }

                        }

                    }


                    if (success != 1) {
                        Utility.showSnackbar(relativeLayout, "Patient Details Not Available");
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
                    Utility.showToast(getApplicationContext(), getString(R.string.failed));
                }


            }
        });
        getData.execute(URLListner.BASEURL + URLListner.get_patient_list);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:

                onBackPressed();

                return true;

        }

        return false;
    }


}
