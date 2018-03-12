package com.bfurns.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.bfurns.R;
import com.bfurns.adapter.AppointmentAdapter;
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

public class TodayAppintment extends AppCompatActivity {

    String userId,clinic_id,doctor_id,clinic_doctor_id,merge_doctor_id,status;
    private RecyclerView recyclerView;
    private ArrayList<AppointementModel> arrayList = new ArrayList<>();
    private AppointmentAdapter appointmentAdapter;
    RecyclerView.LayoutManager layoutManager;
    RelativeLayout relativeLayout;
    SwipeRefreshLayout mSwipeRefreshLayout;
    String comman_account_clinic_id="0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_appintment);
        Intent intent = getIntent();

        userId = AppClass.getuserId();
        clinic_id = AppClass.getclinicId();
        doctor_id= AppClass.getuserId();
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
        getSupportActionBar().setTitle("Today's Appointement");

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

        Utility.showProgressDialog(TodayAppintment.this, "Please wait...");
        Utility.progressDialog.show();


        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("doct_id",merge_doctor_id));
        params.add(new BasicNameValuePair("bus_id",comman_account_clinic_id));
        GetData getData = new GetData(params);
        getData.setResultListner(new GetData.ResultListner() {

            int success = 0;
            int total=0;
            int checked=0;

            @Override
            public void success(JSONObject jsonObject) {

                if (Utility.progressDialog.isShowing()) {
                    Utility.progressDialog.dismiss();
                }



                try {

                    success = jsonObject.getInt("responce");
                    total = jsonObject.getInt("total_count");
                    checked = jsonObject.getInt("checked_count");


                    if (success == 1) {
                        // jsonObject.getString("CharterProduct");
                        JSONArray arr = jsonObject.getJSONArray("appointments");
                        if (arr.length() == 0) {
                            Utility.showSnackbar(relativeLayout, getString(R.string.notavailable));

                        }else {
                            for (int i = 0; i < arr.length(); i++) {

                                JSONObject json = arr.getJSONObject(i);
                                AppointementModel extrasItemModel = new AppointementModel();


                                extrasItemModel.setPatient_name(json.getString("app_name"));
                                extrasItemModel.setMode_of_appointement(json.getString("mode_app"));
                                extrasItemModel.setTime(json.getString("start_time"));
                                extrasItemModel.setDate(json.getString("appointment_date"));

                                extrasItemModel.setId(json.getString("id"));
                                extrasItemModel.setBus_id(json.getString("bus_id"));
                                extrasItemModel.setSub_user_id(json.getString("sub_user_id"));
                                extrasItemModel.setUser_id(json.getString("user_id"));
                                extrasItemModel.setDoct_id(json.getString("doct_id"));
                                extrasItemModel.setApp_staus(json.getString("app_status"));
                                extrasItemModel.setApp_type(json.getString("app_type"));
                                extrasItemModel.setContact(json.getString("app_phone"));
                                extrasItemModel.setImage(json.getString("patient_img"));


                                arrayList.add(extrasItemModel);


                                appointmentAdapter = new AppointmentAdapter(arrayList, TodayAppintment.this);
                                recyclerView.setAdapter(appointmentAdapter);


                                appointmentAdapter.notifyDataSetChanged();


                            }

                        }

                    }


                    if (success != 1) {
                        Utility.showSnackbar(relativeLayout, getString(R.string.notavailable));
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
        getData.execute(URLListner.BASEURL + URLListner.doctor_today_appointment_merge);

    }

    private void showapp() {
        arrayList.clear();

      /*  Utility.showProgressDialog(TodayAppintment.this, "Please wait...");
        Utility.progressDialog.show();
*/

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("doct_id",clinic_doctor_id));
        params.add(new BasicNameValuePair("bus_id",clinic_id));
        GetData getData = new GetData(params);
        getData.setResultListner(new GetData.ResultListner() {

            int success = 0;
            int total=0;
            int checked=0;

            @Override
            public void success(JSONObject jsonObject) {

               /* if (Utility.progressDialog.isShowing()) {
                    Utility.progressDialog.dismiss();
                }*/



                try {

                    success = jsonObject.getInt("responce");
                    total = jsonObject.getInt("total_count");
                    checked = jsonObject.getInt("checked_count");


                    if (success == 1) {
                        // jsonObject.getString("CharterProduct");
                        JSONArray arr = jsonObject.getJSONArray("appointments");
                        if (arr.length() == 0) {
                            Utility.showSnackbar(relativeLayout, getString(R.string.notavailable));

                        }else {
                        for (int i = 0; i < arr.length(); i++) {

                            JSONObject json = arr.getJSONObject(i);
                            AppointementModel extrasItemModel = new AppointementModel();


                            extrasItemModel.setPatient_name(json.getString("app_name"));
                            extrasItemModel.setMode_of_appointement(json.getString("mode_app"));
                            extrasItemModel.setTime(json.getString("start_time"));
                            extrasItemModel.setDate(json.getString("appointment_date"));

                            extrasItemModel.setId(json.getString("id"));
                            extrasItemModel.setBus_id(json.getString("bus_id"));
                            extrasItemModel.setSub_user_id(json.getString("sub_user_id"));
                            extrasItemModel.setUser_id(json.getString("user_id"));
                            extrasItemModel.setDoct_id(json.getString("doct_id"));
                            extrasItemModel.setApp_staus(json.getString("app_status"));
                            extrasItemModel.setApp_type(json.getString("app_type"));
                            extrasItemModel.setContact(json.getString("app_phone"));

                            arrayList.add(extrasItemModel);


                            appointmentAdapter = new AppointmentAdapter(arrayList, TodayAppintment.this);
                            recyclerView.setAdapter(appointmentAdapter);


                            appointmentAdapter.notifyDataSetChanged();


                        }

                    }

                    }


                    if (success != 1) {
                        Utility.showSnackbar(relativeLayout, getString(R.string.notavailable));
                    }

                } catch (Exception e) {

                    e.printStackTrace();
                }
            }

            @Override
            public void fail() {

               /* if (Utility.progressDialog.isShowing()) {
                    Utility.progressDialog.dismiss();
                }*/

                if (success != 1) {
                    Utility.showToast(getApplicationContext(), getString(R.string.failed));
                }


            }
        });
        getData.execute(URLListner.BASEURL + URLListner.doctor_today_appointment);
    }

    private void getApointements() {


        arrayList.clear();

        Utility.showProgressDialog(TodayAppintment.this, "Please wait...");
        Utility.progressDialog.show();
        Utility.progressDialog.dismiss();



        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("doct_id",doctor_id));
        params.add(new BasicNameValuePair("bus_id", clinic_id));

        GetData getData = new GetData(params);
        getData.setResultListner(new GetData.ResultListner() {

            int success = 0;
            int total=0;
            int checked=0;

            @Override
            public void success(JSONObject jsonObject) {

                if (Utility.progressDialog.isShowing()) {
                    Utility.progressDialog.dismiss();
                }



                try {

                    success = jsonObject.getInt("responce");
                    total = jsonObject.getInt("total_count");
                    checked = jsonObject.getInt("checked_count");


                    if (success == 1) {
                        // jsonObject.getString("CharterProduct");
                        JSONArray arr = jsonObject.getJSONArray("appointments");

                        if (arr.length()==0){
                            Utility.showSnackbar(relativeLayout, getString(R.string.notavailable));

                        }else {
                            for (int i = 0; i < arr.length(); i++) {

                                JSONObject json = arr.getJSONObject(i);
                                AppointementModel extrasItemModel = new AppointementModel();


                                extrasItemModel.setPatient_name(json.getString("app_name"));
                                extrasItemModel.setMode_of_appointement(json.getString("mode_app"));
                                extrasItemModel.setTime(json.getString("start_time"));
                                extrasItemModel.setDate(json.getString("appointment_date"));

                                extrasItemModel.setId(json.getString("id"));
                                extrasItemModel.setBus_id(json.getString("bus_id"));
                                extrasItemModel.setSub_user_id(json.getString("sub_user_id"));
                                extrasItemModel.setUser_id(json.getString("user_id"));
                                extrasItemModel.setDoct_id(json.getString("doct_id"));
                                extrasItemModel.setApp_staus(json.getString("app_status"));
                                extrasItemModel.setApp_type(json.getString("app_type"));
                                extrasItemModel.setContact(json.getString("app_phone"));
                                extrasItemModel.setImage(json.getString("patient_img"));

                                arrayList.add(extrasItemModel);


                                appointmentAdapter = new AppointmentAdapter(arrayList, TodayAppintment.this);
                                recyclerView.setAdapter(appointmentAdapter);


                                appointmentAdapter.notifyDataSetChanged();


                            }
                        }



                    }


                    if (success != 1) {
                        Utility.showSnackbar(relativeLayout, getString(R.string.notavailable));
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
        getData.execute(URLListner.BASEURL + URLListner.doctor_today_appointment);

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
