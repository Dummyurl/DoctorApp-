package com.bfurns.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bfurns.R;
import com.bfurns.adapter.AppointmentAdapter;
import com.bfurns.adapter.WeeklyAppointmentAdapter;
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

/**
 * Created by USer on 24-08-2017.
 */

public class OneFragment extends android.support.v4.app.Fragment {
    String user_id, doctor_id, clinic_id, email, clinic_doctor_id, merge_doctor_id, status;
    private View rootView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView recyclerView;
    private ArrayList<AppointementModel> arrayList = new ArrayList<>();
    private WeeklyAppointmentAdapter appointmentAdapter;
    RecyclerView.LayoutManager layoutManager;
    RelativeLayout relativeLayout;
    TextView textView, textView1;
    String comman_account_clinic_id = "0";


    public OneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_one, container, false);
        user_id = AppClass.getuserId();
        clinic_id = AppClass.getclinicId();
        doctor_id = AppClass.getdoctor_id();
        email = AppClass.getemail();
        clinic_doctor_id = AppClass.getsessionclinic_doctor_id();
        merge_doctor_id = AppClass.getmerge_doctor_id();
        status = AppClass.getcomman_account_status();
        setUpViews();

        return rootView;

    }

    private void setUpViews() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeToRefresh);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        relativeLayout = (RelativeLayout) rootView.findViewById(R.id.parent);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        LinearLayoutManager verticalLayoutmanager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        verticalLayoutmanager.setReverseLayout(false);
        verticalLayoutmanager.setStackFromEnd(false);
        recyclerView.setLayoutManager(verticalLayoutmanager);
        recyclerView.setHasFixedSize(true);


        if (MyUtility.isConnected(getContext())){


            if (status.equals("1")) {
                CommanDetails();
            }else {
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
                }else {
                    getApointements();

                }



                mSwipeRefreshLayout.setRefreshing(false);


            }
        });


    }

    private void CommanDetails() {


        arrayList.clear();

        Utility.showProgressDialog(getContext(), "Please wait...");
        Utility.progressDialog.show();


        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("doct_id", merge_doctor_id));
        params.add(new BasicNameValuePair("bus_id", comman_account_clinic_id));


        final GetData getData = new GetData(params);
        getData.setResultListner(new GetData.ResultListner() {

            int success = 0;
            int total = 0;
            int checked = 0;

            @Override
            public void success(JSONObject jsonObject) {
                if (Utility.progressDialog.isShowing()) {
                    Utility.progressDialog.dismiss();
                }


                try {

                    success = jsonObject.getInt("responce");
                    //textView.setText(checked + "/" +total);


                    if (success == 1) {

                        JSONArray arr = jsonObject.getJSONArray("weeks");
                        if (arr.length() == 0) {
                            Utility.showSnackbar(relativeLayout, getString(R.string.notavailable));
                        } else {
                            for (int i = 0; i < arr.length(); i++) {

                                JSONObject json = arr.getJSONObject(i);
                                AppointementModel extrasItemModel = new AppointementModel();


                                extrasItemModel.setPatient_name(json.getString("app_name"));
                                extrasItemModel.setMode_of_appointement(json.getString("mode_app"));
                                extrasItemModel.setPatent_category(json.getString("app_type"));
                                extrasItemModel.setTime(json.getString("start_time"));
                                // extrasItemModel.setCount((json.getInt("app_count")));
                                // total=json.getInt("app_count");

                                extrasItemModel.setId(json.getString("id"));
                                extrasItemModel.setBus_id(json.getString("bus_id"));
                                extrasItemModel.setSub_user_id(json.getString("sub_user_id"));
                                extrasItemModel.setUser_id(json.getString("user_id"));
                                extrasItemModel.setDoct_id(json.getString("doct_id"));
                                extrasItemModel.setApp_staus(json.getString("app_status"));
                                extrasItemModel.setDate(json.getString("appointment_date"));
                                extrasItemModel.setContact(json.getString("app_phone"));
                                extrasItemModel.setImage(json.getString("patient_img"));

                                arrayList.add(extrasItemModel);


                                appointmentAdapter = new WeeklyAppointmentAdapter(arrayList, getContext(), total);
                                recyclerView.setAdapter(appointmentAdapter);


                                appointmentAdapter.notifyDataSetChanged();


                            }

                            JSONArray jsonArray = jsonObject.getJSONArray("week_count");
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                AppointementModel extrasItemModel = new AppointementModel();
                                extrasItemModel.setCount(jsonObject1.getInt("app_count"));

                                total = jsonObject1.getInt("app_count");

                                appointmentAdapter = new WeeklyAppointmentAdapter(arrayList, getContext(), total);
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
                    Utility.showToast(getContext(), getString(R.string.failed));
                }


            }
        });
        getData.execute(URLListner.BASEURL + URLListner.doctor_week_appointment_merge);

    }


    private void getApointements() {


        arrayList.clear();
/*

        Utility.showProgressDialog(getContext(), "Please wait...");
       Utility.progressDialog.show();
*/


        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("doct_id", user_id));
        params.add(new BasicNameValuePair("bus_id", clinic_id));



        final GetData getData = new GetData(params);
        getData.setResultListner(new GetData.ResultListner() {

            int success = 0;
            int total = 0;
            int checked = 0;

            @Override
            public void success(JSONObject jsonObject) {

              /*  if (Utility.progressDialog.isShowing()) {
                    Utility.progressDialog.dismiss();
                }*/


                try {

                    success = jsonObject.getInt("responce");
                    //textView.setText(checked + "/" +total);


                    if (success == 1) {
                        Utility.progressDialog.dismiss();

                        JSONArray arr = jsonObject.getJSONArray("weeks");
                        for (int i = 0; i < arr.length(); i++) {

                            JSONObject json = arr.getJSONObject(i);
                            AppointementModel extrasItemModel = new AppointementModel();


                            extrasItemModel.setPatient_name(json.getString("app_name"));
                            extrasItemModel.setMode_of_appointement(json.getString("mode_app"));
                            extrasItemModel.setPatent_category(json.getString("app_type"));
                            extrasItemModel.setTime(json.getString("start_time"));
                            // extrasItemModel.setCount((json.getInt("app_count")));
                            // total=json.getInt("app_count");

                            extrasItemModel.setId(json.getString("id"));
                            extrasItemModel.setBus_id(json.getString("bus_id"));
                            extrasItemModel.setSub_user_id(json.getString("sub_user_id"));
                            extrasItemModel.setUser_id(json.getString("user_id"));
                            extrasItemModel.setDoct_id(json.getString("doct_id"));
                            extrasItemModel.setApp_staus(json.getString("app_status"));
                            extrasItemModel.setDate(json.getString("appointment_date"));
                            extrasItemModel.setContact(json.getString("app_phone"));
                            extrasItemModel.setImage(json.getString("patient_img"));



                            arrayList.add(extrasItemModel);


                            appointmentAdapter = new WeeklyAppointmentAdapter(arrayList, getContext(), total);
                            recyclerView.setAdapter(appointmentAdapter);


                            appointmentAdapter.notifyDataSetChanged();


                        }

                        JSONArray jsonArray = jsonObject.getJSONArray("week_count");
                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            AppointementModel extrasItemModel = new AppointementModel();
                            extrasItemModel.setCount(jsonObject1.getInt("app_count"));

                            total = jsonObject1.getInt("app_count");

                            appointmentAdapter = new WeeklyAppointmentAdapter(arrayList, getContext(), total);
                            recyclerView.setAdapter(appointmentAdapter);


                            appointmentAdapter.notifyDataSetChanged();


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
/*
                if (Utility.progressDialog.isShowing()) {
                    Utility.progressDialog.dismiss();
                }*/

                if (success != 1) {
                    Utility.showToast(getContext(), getString(R.string.failed));
                }


            }
        });
        getData.execute(URLListner.BASEURL + URLListner.doctor_week_appointment);

    }

}
