package com.bfurns.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bfurns.R;
import com.bfurns.activity.BarGraphActivity;
import com.bfurns.activity.PerFormanceActivity;
import com.bfurns.activity.RevenueActivity;
import com.bfurns.activity.TabActivity;
import com.bfurns.activity.TodayAppintment;
import com.bfurns.adapter.AppointmentAdapter;
import com.bfurns.adapter.WeeklyAppointmentAdapter;
import com.bfurns.app.Config;
import com.bfurns.application.AppClass;
import com.bfurns.model.AppointementModel;
import com.bfurns.utility.GetData;
import com.bfurns.utility.MyPreferences;
import com.bfurns.utility.MyUtility;
import com.bfurns.utility.NotificationUtils;
import com.bfurns.utility.URLListner;
import com.bfurns.utility.Utility;
import com.github.clans.fab.FloatingActionButton;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Mahesh on 22/08/16.
 */


public class MainFragment extends Fragment {

    private static final String TEXT_FRAGMENT = "FLIGHT_FRAGMENT";
    private View rootView;
    String user_id, doctor_id, email, clinic_id, count, clinic_name, image, merge_doctor_id, clinic_doctor_id, status;
    private RecyclerView recyclerView;
    private ArrayList<AppointementModel> arrayList = new ArrayList<>();
    private AppointmentAdapter appointmentAdapter;
    RecyclerView.LayoutManager layoutManager;
    RelativeLayout relativeLayout;
    TextView textView, textView1, textView2;
    private RelativeLayout loaderll;
    //SwipeRefreshLayout mSwipeRefreshLayout;
    FloatingActionButton floatingActionButton;
    String comman_account_clinic_id = "0";


    private BroadcastReceiver mRegistrationBroadcastReceiver;


    public static MainFragment newInstance(String text) {
        MainFragment mFragment = new MainFragment();
        Bundle mBundle = new Bundle();
        mBundle.putString(TEXT_FRAGMENT, text);
        mFragment.setArguments(mBundle);
        return mFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        rootView = inflater.inflate(R.layout.activity_main_screen, container, false);
        user_id = AppClass.getuserId();
        clinic_id = AppClass.getclinicId();
        email = AppClass.getemail();
        clinic_name = AppClass.getclinic_name();
        image = AppClass.getImage();
        merge_doctor_id = AppClass.getmerge_doctor_id();//After merge getting this id
        clinic_doctor_id = AppClass.getsessionclinic_doctor_id();//clinic list contain this id
        status=AppClass.getcomman_account_status();

        setUpViews();
        return rootView;
    }


    private void setUpViews() {
      //  mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeToRefresh);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
       // status = getActivity().getIntent().getExtras().getString(MyPreferences.COMMAN_ACCOUNT_STAUS);

        relativeLayout = (RelativeLayout) rootView.findViewById(R.id.relative);
        floatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.menu_rede);
        textView2 = (TextView) rootView.findViewById(R.id.no_data);

        layoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(layoutManager);

        LinearLayoutManager verticalLayoutmanager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        verticalLayoutmanager.setReverseLayout(true);
        verticalLayoutmanager.setStackFromEnd(true);
        recyclerView.setLayoutManager(verticalLayoutmanager);
        recyclerView.setHasFixedSize(true);
        textView = (TextView) rootView.findViewById(R.id.total_count);
        textView1 = (TextView) rootView.findViewById(R.id.total_countt);
       // mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (status.equals("1")) {
                    CommanDetails();
                } else {
                    getApointements();
                }



            }
        });

       /* mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                appointmentAdapter = new AppointmentAdapter(arrayList, getContext());
                recyclerView.setAdapter(appointmentAdapter);
                appointmentAdapter.notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(false);


            }
        });
        */


        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Home");


        LinearLayout ll = (LinearLayout) rootView.findViewById(R.id.today_appointment);
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TodayAppintment.class);

                intent.putExtra(MyPreferences.COMMAN_ACCOUNT_STAUS, status);
                startActivity(intent);

            }
        });


        LinearLayout ll1 = (LinearLayout) rootView.findViewById(R.id.weeklyApp);
        ll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), TabActivity.class);
                intent.putExtra(MyPreferences.COMMAN_ACCOUNT_STAUS, status);

                startActivity(intent);
                //startActivity(new Intent(getActivity(), WeeklyMonthlyApooiintment.class));
            }
        });

        RelativeLayout reports = (RelativeLayout) rootView.findViewById(R.id.icu);
        reports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //  startActivity(new Intent(getActivity(), RevenueActivity.class));
            }
        });

        RelativeLayout consult = (RelativeLayout) rootView.findViewById(R.id.revenue);

        consult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // startActivity(new Intent(getActivity(), PerFormanceActivity.class));

                Intent intent = new Intent(getContext(), PerFormanceActivity.class);
                intent.putExtra(MyPreferences.COMMAN_ACCOUNT_STAUS, status);

                startActivity(intent);

            }
        });

        // Log.e(MyPreferences.USER_ID,user_id);
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);
                    displayFirebaseRegId();
                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    String message = intent.getStringExtra("message");
                }
            }
        };

        displayFirebaseRegId();


        if (MyUtility.isConnected(getContext())) {

            if (status.equals("1")) {
                CommanDetails();
            } else {
                getApointements();
            }

        } else {
            // MyUtility.showSnack(relativeLayout, MyUtility.INTERNET_ERROR);
            Toast.makeText(getContext(), "Check Internet Connection", Toast.LENGTH_SHORT).show();

        }


    }


    private void displayFirebaseRegId() {
        SharedPreferences pref = getActivity().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
    }

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));
        NotificationUtils.clearNotifications(getActivity());

    }


    private void CommanDetails() {


        arrayList.clear();


        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("doct_id", merge_doctor_id));
        params.add(new BasicNameValuePair("bus_id", comman_account_clinic_id));


        final GetData getData = new GetData(params);
        getData.setResultListner(new GetData.ResultListner() {

            int success = 0;
            int total = 0;
            int month = 0;
            int week = 0;
            int checked = 0;

            @Override
            public void success(JSONObject jsonObject) {

                /*if (Utility.progressDialog.isShowing()) {
                    Utility.progressDialog.dismiss();
                }*/


                try {

                    success = jsonObject.getInt("responce");
                    total = jsonObject.getInt("total_count");
                    checked = jsonObject.getInt("checked_count");
                    textView.setText(checked + "/" + total);


                    if (success == 1) {
                        // jsonObject.getString("CharterProduct");
                        JSONArray arr = jsonObject.getJSONArray("appointments");

                        if (arr.length() == 0) {
                            Utility.showSnackbar(relativeLayout, getString(R.string.notavailable));

                           /* textView2.setVisibility(View.VISIBLE);
                            textView2.setText("No Today's Appointments are available");
*/
                        }else {
                            for (int i = 0; i < arr.length(); i++) {

                                JSONObject json = arr.getJSONObject(i);
                                AppointementModel extrasItemModel = new AppointementModel();


                                extrasItemModel.setPatient_name(json.getString("app_name"));
                                extrasItemModel.setMode_of_appointement(json.getString("mode_app"));
                                extrasItemModel.setPatent_category(json.getString("app_type"));
                                extrasItemModel.setTime(json.getString("start_time"));
                                extrasItemModel.setId(json.getString("id"));
                                extrasItemModel.setBus_id(json.getString("bus_id"));
                                extrasItemModel.setSub_user_id(json.getString("sub_user_id"));
                                extrasItemModel.setUser_id(json.getString("user_id"));
                                extrasItemModel.setDoct_id(json.getString("doct_id"));
                                extrasItemModel.setApp_staus(json.getString("app_status"));
                                extrasItemModel.setApp_type(json.getString("app_type"));
                                extrasItemModel.setDate(json.getString("appointment_date"));
                                extrasItemModel.setContact(json.getString("app_phone"));
                                extrasItemModel.setImage(json.getString("patient_img"));


                                arrayList.add(extrasItemModel);


                                appointmentAdapter = new AppointmentAdapter(arrayList, getContext());
                                recyclerView.setAdapter(appointmentAdapter);


                                appointmentAdapter.notifyDataSetChanged();


                            }
                        }
                        JSONArray jsonArray1 = jsonObject.getJSONArray("week_count");
                        for (int i = 0; i < jsonArray1.length(); i++) {

                            JSONObject jsonObject1 = jsonArray1.getJSONObject(i);
                            AppointementModel extrasItemModel = new AppointementModel();
                            extrasItemModel.setCount(jsonObject1.getInt("app_count"));

                            week = jsonObject1.getInt("app_count");

                            appointmentAdapter = new AppointmentAdapter(arrayList, getContext());
                            recyclerView.setAdapter(appointmentAdapter);


                            appointmentAdapter.notifyDataSetChanged();


                        }

                        JSONArray jsonArray = jsonObject.getJSONArray("month_count");
                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            AppointementModel extrasItemModel = new AppointementModel();
                            extrasItemModel.setCount(jsonObject1.getInt("app_count"));

                            month = jsonObject1.getInt("app_count");

                            appointmentAdapter = new AppointmentAdapter(arrayList, getContext());
                            recyclerView.setAdapter(appointmentAdapter);


                            appointmentAdapter.notifyDataSetChanged();


                        }

                        textView1.setText(week + "/" + month);


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

             /*   if (Utility.progressDialog.isShowing()) {
                    Utility.progressDialog.dismiss();
                }*/

                if (success != 1) {
                    Utility.showToast(getContext(), getString(R.string.failed));
                }


            }
        });
        getData.execute(URLListner.BASEURL + URLListner.doctor_today_appointment_merge);

    }


    private void getApointements() {


        arrayList.clear();

        Utility.showProgressDialog(getContext(), "Please wait...");
        Utility.progressDialog.show();


        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("doct_id", user_id));
        params.add(new BasicNameValuePair("bus_id", clinic_id));


        final GetData getData = new GetData(params);
        getData.setResultListner(new GetData.ResultListner() {

            int success = 0;
            int total = 0;
            int month = 0;
            int week = 0;
            int checked = 0;

            @Override
            public void success(JSONObject jsonObject) {

                if (Utility.progressDialog.isShowing()) {
                    Utility.progressDialog.dismiss();
                }


                try {

                    success = jsonObject.getInt("responce");
                    total = jsonObject.getInt("total_count");
                    checked = jsonObject.getInt("checked_count");
                    textView.setText(checked + "/" + total);


                    if (success == 1) {
                        // jsonObject.getString("CharterProduct");
                        JSONArray arr = jsonObject.getJSONArray("appointments");
                        if (arr.length() == 0) {
                            Utility.showSnackbar(relativeLayout, getString(R.string.notavailable));

                            /*textView2.setVisibility(View.VISIBLE);
                            textView2.setText("No Today's Appointments are available");*/

                        }else {

                            for (int i = 0; i < arr.length(); i++) {

                                JSONObject json = arr.getJSONObject(i);
                                AppointementModel extrasItemModel = new AppointementModel();


                                extrasItemModel.setPatient_name(json.getString("app_name"));
                                extrasItemModel.setMode_of_appointement(json.getString("mode_app"));
                                extrasItemModel.setPatent_category(json.getString("app_type"));
                                extrasItemModel.setTime(json.getString("start_time"));
                                extrasItemModel.setId(json.getString("id"));
                                extrasItemModel.setBus_id(json.getString("bus_id"));
                                extrasItemModel.setSub_user_id(json.getString("sub_user_id"));
                                extrasItemModel.setUser_id(json.getString("user_id"));
                                extrasItemModel.setDoct_id(json.getString("doct_id"));
                                extrasItemModel.setApp_staus(json.getString("app_status"));
                                extrasItemModel.setDate(json.getString("appointment_date"));
                                extrasItemModel.setApp_type(json.getString("app_type"));
                                extrasItemModel.setContact(json.getString("app_phone"));
                                extrasItemModel.setImage(json.getString("patient_img"));


                                arrayList.add(extrasItemModel);


                                appointmentAdapter = new AppointmentAdapter(arrayList, getContext());
                                recyclerView.setAdapter(appointmentAdapter);


                                appointmentAdapter.notifyDataSetChanged();


                            }
                        }
                        JSONArray jsonArray1 = jsonObject.getJSONArray("week_count");
                        for (int i = 0; i < jsonArray1.length(); i++) {

                            JSONObject jsonObject1 = jsonArray1.getJSONObject(i);
                            AppointementModel extrasItemModel = new AppointementModel();
                            extrasItemModel.setCount(jsonObject1.getInt("app_count"));

                            week = jsonObject1.getInt("app_count");

                            appointmentAdapter = new AppointmentAdapter(arrayList, getContext());
                            recyclerView.setAdapter(appointmentAdapter);


                            appointmentAdapter.notifyDataSetChanged();


                        }

                        JSONArray jsonArray = jsonObject.getJSONArray("month_count");
                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            AppointementModel extrasItemModel = new AppointementModel();
                            extrasItemModel.setCount(jsonObject1.getInt("app_count"));

                            month = jsonObject1.getInt("app_count");

                            appointmentAdapter = new AppointmentAdapter(arrayList, getContext());
                            recyclerView.setAdapter(appointmentAdapter);


                            appointmentAdapter.notifyDataSetChanged();


                        }

                        textView1.setText(week + "/" + month);


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
        getData.execute(URLListner.BASEURL + URLListner.doctor_today_appointment);

    }


    //@Override
    public void onBackPressed() {
        new AlertDialog.Builder(getContext())
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        getActivity().finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }


}
