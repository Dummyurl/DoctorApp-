package com.bfurns.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bfurns.R;
import com.bfurns.adapter.AppointmentAdapter;
import com.bfurns.adapter.SummeryAdapter;
import com.bfurns.application.AppClass;
import com.bfurns.model.AppointementModel;
import com.bfurns.utility.GetData;
import com.bfurns.utility.MyPreferences;
import com.bfurns.utility.URLListner;
import com.bfurns.utility.Utility;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SummeryActivity extends AppCompatActivity{
    String userId,doctor_id,my_to,my_from,clinic_doctor_id,merge_doctor_id,clinic_id,status;
    private RecyclerView recyclerView,recyclerView1;
    private ArrayList<AppointementModel> arrayList = new ArrayList<>();
    private SummeryAdapter appointmentAdapter;
    RecyclerView.LayoutManager layoutManager,layoutmanager1;
    RelativeLayout relativeLayout;
    EditText editText,to_date,FromDate;
    Button Filter;
    LinearLayout linearLayout,linearlayout1;
    String From,to;
    int year, month, day;
    Calendar c;
    String comman_account_clinic_id="0";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summery);
        Intent intent = getIntent();

        clinic_id = AppClass.getclinicId();
        doctor_id= AppClass.getuserId();
        my_to= intent.getStringExtra(MyPreferences.MYTO);
        my_from= intent.getStringExtra(MyPreferences.MYFROM);
        clinic_doctor_id=AppClass.getsessionclinic_doctor_id();
        merge_doctor_id= AppClass.getmerge_doctor_id();
        status= AppClass.getcomman_account_status();



        setUpview();

    }

    private void setUpview() {


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Summary");

        linearLayout=(LinearLayout) findViewById(R.id.linearlayout1);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        relativeLayout = (RelativeLayout) findViewById(R.id.parent);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        LinearLayoutManager verticalLayoutmanager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        verticalLayoutmanager.setReverseLayout(false);
        verticalLayoutmanager.setStackFromEnd(false);

        recyclerView.setLayoutManager(verticalLayoutmanager);
        recyclerView.setHasFixedSize(true);
        c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);





        if (status.equals("1")) {
            CommanDetails();
        } else {
            filterSummery();
        }


    }

    private void CommanDetails() {
        arrayList.clear();

       /* Utility.showProgressDialog(SummeryActivity.this, "Please wait...");
        Utility.progressDialog.show();
*/

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("doct_id",merge_doctor_id));
        params.add(new BasicNameValuePair("from_date",my_from));
        params.add(new BasicNameValuePair("to_date",my_to));
        params.add(new BasicNameValuePair("bus_id",comman_account_clinic_id));






        GetData getData = new GetData(params);
        getData.setResultListner(new GetData.ResultListner() {

            int success = 0;

            @Override
            public void success(JSONObject jsonObject) {

                if (Utility.progressDialog.isShowing()) {
                    Utility.progressDialog.dismiss();
                }



                try {

                    success = jsonObject.getInt("responce");


                    if (success == 1) {
                        // jsonObject.getString("CharterProduct");
                        JSONArray arr = jsonObject.getJSONArray("appointment");
                        if (arr.length()==0){
                            Utility.showSnackbar(relativeLayout,"No Appointment summary available");

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
                                extrasItemModel.setClinic_name(json.getString("bus_title"));
                                extrasItemModel.setEmail(json.getString("app_email"));
                                extrasItemModel.setPayed_amount(json.getString("payed_amount"));
                                extrasItemModel.setRemaining_amount(json.getString("due_amount"));
                                extrasItemModel.setTotal_amount(json.getString("payment_amount"));
                                extrasItemModel.setApp_type(json.getString("app_type"));


                                arrayList.add(extrasItemModel);

                                appointmentAdapter = new SummeryAdapter(arrayList, SummeryActivity.this);
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
        getData.execute(URLListner.BASEURL + URLListner.appointment_summery_report_merge);


    }


    private void filterSummery() {


        arrayList.clear();

        Utility.showProgressDialog(SummeryActivity.this, "Please wait...");
        Utility.progressDialog.show();


        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("doct_id",doctor_id));
        params.add(new BasicNameValuePair("from_date",my_from));
        params.add(new BasicNameValuePair("to_date",my_to));
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

                    success = jsonObject.getInt("responce");


                    if (success == 1) {
                        JSONArray arr = jsonObject.getJSONArray("appointment");
                        if (arr.length()==0){
                            Utility.showSnackbar(relativeLayout, "No Summary Available");


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
                                extrasItemModel.setClinic_name(json.getString("bus_title"));
                                extrasItemModel.setEmail(json.getString("app_email"));
                                extrasItemModel.setPayed_amount(json.getString("payed_amount"));
                                extrasItemModel.setRemaining_amount(json.getString("due_amount"));
                                extrasItemModel.setTotal_amount(json.getString("payment_amount"));
                                extrasItemModel.setApp_type(json.getString("app_type"));


                                arrayList.add(extrasItemModel);


                                appointmentAdapter = new SummeryAdapter(arrayList, SummeryActivity.this);
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
        getData.execute(URLListner.BASEURL + URLListner.appointment_summery_report);



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
