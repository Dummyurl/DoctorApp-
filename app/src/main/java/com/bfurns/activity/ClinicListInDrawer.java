package com.bfurns.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bfurns.R;
import com.bfurns.adapter.MergeAccountAdapter;
import com.bfurns.application.AppClass;
import com.bfurns.model.UserModel;
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

public class ClinicListInDrawer extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<UserModel> arrayList = new ArrayList<>();
    private MergeAccountAdapter appointmentAdapter;
    RecyclerView.LayoutManager layoutManager,layoutmanager1;
    RelativeLayout relativeLayout;
    String email,doct_id,merge_doctor_id;
    TextView textView;
    private static final String TAG = MAccountClinicList.class.getSimpleName();
    String comman_account_clinic_id="0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic_list_in_drawer);


        SharedPreferences sharedPreferences = getSharedPreferences(MyPreferences.My_PREFRENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        email = sharedPreferences.getString(MyPreferences.USER_EMAIL, "");
        Intent intent=getIntent();
        doct_id= AppClass.getuserId();
        merge_doctor_id=AppClass.getmerge_doctor_id();


        setUpviews();
    }

    private void setUpviews() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Merge Account Clinic List");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        relativeLayout = (RelativeLayout) findViewById(R.id.parent);
        textView = (TextView) findViewById(R.id.comman);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        LinearLayoutManager verticalLayoutmanager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(verticalLayoutmanager);
        recyclerView.setHasFixedSize(true);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getstatus();

            }
        });

        getclinic();
    }

    private void getstatus() {


        Utility.showProgressDialog(ClinicListInDrawer.this, "Please wait...");
        Utility.progressDialog.show();


        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("doct_id",doct_id));



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
                        JSONArray arr = jsonObject.getJSONArray("merge_account");
                        for (int i = 0; i < arr.length(); i++) {

                            JSONObject json = arr.getJSONObject(i);
                            String status=json.getString("status");
                            String doctor_id=json.getString("doct_id");
                            AppClass.setcomman_account_status(status);
                         //   AppClass.setuserid(doctor_id);
                            Intent intent=new Intent(ClinicListInDrawer.this,HomeActivity.class);
                            intent.putExtra(MyPreferences.COMMAN_ACCOUNT_STAUS,status);
                            startActivity(intent);


                        }



                    }


                    if (success != 1) {
                        Utility.showSnackbar(relativeLayout, "No Comman Account");
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
        getData.execute(URLListner.BASEURL + URLListner.merge_account_response);



    }

    private void getclinic() {


        arrayList.clear();

        Utility.showProgressDialog(ClinicListInDrawer.this, "Please wait...");
        Utility.progressDialog.show();


        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("doct_id",doct_id));





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
                        JSONArray arr = jsonObject.getJSONArray("data");
                        for (int i = 0; i < arr.length(); i++) {

                            JSONObject json = arr.getJSONObject(i);
                            UserModel extrasItemModel = new UserModel();
                            extrasItemModel.setBus_id(json.getString("bus_id"));
                            extrasItemModel.setBus_title(json.getString("bus_title"));
                            extrasItemModel.setDoct_id(json.getString("doct_id"));


                            arrayList.add(extrasItemModel);
                            appointmentAdapter = new MergeAccountAdapter(arrayList, ClinicListInDrawer.this);
                            recyclerView.setAdapter(appointmentAdapter);

                            appointmentAdapter.notifyDataSetChanged();


                        }



                    }


                    if (success != 1) {
                        Utility.showSnackbar(relativeLayout, "Clinic's are not available");
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
        getData.execute(URLListner.BASEURL + URLListner.clinic_list_for_doctor);


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
