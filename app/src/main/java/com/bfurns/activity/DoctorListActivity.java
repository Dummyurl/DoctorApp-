package com.bfurns.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.bfurns.R;
import com.bfurns.adapter.AppointmentAdapter;
import com.bfurns.adapter.DoctorListAdapter;
import com.bfurns.model.AppointementModel;
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

public class DoctorListActivity extends AppCompatActivity {


    String userId, clinic_id, doctor_id,doct_name;
    private RecyclerView recyclerView;
    private ArrayList<AppointementModel> arrayList = new ArrayList<>();
    private DoctorListAdapter appointmentAdapter;
    RecyclerView.LayoutManager layoutManager;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);
        Intent intent = getIntent();

        userId = intent.getStringExtra(MyPreferences.USER_ID);
        clinic_id = intent.getStringExtra(MyPreferences.CLINIC_ID);
        doctor_id = intent.getStringExtra(MyPreferences.DOCTOR_ID);
        doct_name = intent.getStringExtra(MyPreferences.From_doctor);
        setUpview();

    }

    private void setUpview() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Doctor List");


        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        relativeLayout = (RelativeLayout) findViewById(R.id.parent);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        LinearLayoutManager verticalLayoutmanager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(verticalLayoutmanager);
        recyclerView.setHasFixedSize(true);

        getApointements();
    }

    private void getApointements() {


        arrayList.clear();

        Utility.showProgressDialog(DoctorListActivity.this, "Please wait...");
        Utility.progressDialog.show();


        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("doct_id", doctor_id));


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


                    if (success==1) {
                        // jsonObject.getString("CharterProduct");
                        JSONArray arr = jsonObject.getJSONArray("data");
                        for (int i = 0; i < arr.length(); i++) {

                            JSONObject json = arr.getJSONObject(i);
                            AppointementModel extrasItemModel = new AppointementModel();


                            extrasItemModel.setBus_id(json.getString("bus_id"));
                            extrasItemModel.setDoct_id(json.getString("doct_id"));
                            extrasItemModel.setDoctor_name(json.getString("doct_name"));
                            extrasItemModel.setEmail(json.getString("doct_email"));
                            extrasItemModel.setPatient_name(json.getString("title"));


                            arrayList.add(extrasItemModel);


                            appointmentAdapter = new DoctorListAdapter(arrayList, DoctorListActivity.this);
                            recyclerView.setAdapter(appointmentAdapter);


                            appointmentAdapter.notifyDataSetChanged();


                        }


                    }


                    if (success!=0) {
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

                if (success!=0) {
                    Utility.showToast(getApplicationContext(), getString(R.string.failed));
                }


            }
        });
        getData.execute(URLListner.BASEURL + URLListner.get_all_chat_doctor_list);

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
