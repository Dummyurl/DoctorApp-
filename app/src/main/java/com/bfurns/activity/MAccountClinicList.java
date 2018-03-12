package com.bfurns.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.bfurns.R;
import com.bfurns.adapter.AppointmentAdapter;
import com.bfurns.adapter.MergeAccountAdapter;
import com.bfurns.adapter.MergeAccountClinicAdapter;
import com.bfurns.adapter.MyAcccountsAdapter;
import com.bfurns.adapter.SummeryAdapter;
import com.bfurns.application.AppClass;
import com.bfurns.model.AppointementModel;
import com.bfurns.model.UserModel;
import com.bfurns.utility.GetData;
import com.bfurns.utility.MyPreferences;
import com.bfurns.utility.MyUtility;
import com.bfurns.utility.URLListner;
import com.bfurns.utility.Utility;
import com.bfurns.utility.Validations;
import com.bfurns.ziffylink.LoginActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MAccountClinicList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<UserModel> arrayList = new ArrayList<>();
    private MergeAccountClinicAdapter appointmentAdapter;
    RecyclerView.LayoutManager layoutManager, layoutmanager1;
    RelativeLayout relativeLayout;
    String email,doctor_id;
    private static final String TAG = MAccountClinicList.class.getSimpleName();


    EditText eemail, pass, c_pass, p_email;
    String peemail, ppass, pc_pass, pp_email;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maccount_clinic_list);
        Intent intent = getIntent();
        email = intent.getStringExtra(MyPreferences.USER_EMAIL);
        doctor_id = AppClass.getuserId();

        setUpviews();
    }

    private void setUpviews() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Clinic List");



        button = (Button) findViewById(R.id.login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Merge();


            }
        });


        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        relativeLayout = (RelativeLayout) findViewById(R.id.parent);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        LinearLayoutManager verticalLayoutmanager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(verticalLayoutmanager);
        recyclerView.setHasFixedSize(true);

        getclinic();
    }

    private void Merge() {


        Utility.showProgressDialog(MAccountClinicList.this, "Please wait...");
        Utility.progressDialog.show();


        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("user_email", email));
        params.add(new BasicNameValuePair("doct_id", doctor_id));


        GetData getData = new GetData(params);
        getData.setResultListner(new GetData.ResultListner() {

            int success = 0;
            String message;

            @Override
            public void success(JSONObject jsonObject) {

                if (Utility.progressDialog.isShowing()) {
                    Utility.progressDialog.dismiss();
                }


                try {

                    success = jsonObject.getInt("responce");


                    if (success == 1) {

                         message=jsonObject.getString("account");
                        MyUtility.showSnack(relativeLayout,message);
                        Intent intent=new Intent(MAccountClinicList.this,LoginActivity.class);
                        startActivity(intent);



                    }


                    if (success != 1) {
                        Utility.showSnackbar(relativeLayout,message );
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
        getData.execute(URLListner.BASEURL + URLListner.merge_account);




    }


    private void getclinic() {


        arrayList.clear();

        Utility.showProgressDialog(MAccountClinicList.this, "Please wait...");
        Utility.progressDialog.show();


        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("doct_email", email));
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


                    if (success == 1) {
                        JSONArray arr = jsonObject.getJSONArray("clinic_name");


                        if (arr.length()==0){
                            Utility.showSnackbar(relativeLayout,"This Email id not registered with clinic");

                        }else {
                            for (int i = 0; i < arr.length(); i++) {

                                JSONObject json = arr.getJSONObject(i);
                                UserModel extrasItemModel = new UserModel();
                                extrasItemModel.setName(json.getString("bus_title"));
                                extrasItemModel.setUser(json.getString("bus_id"));


                                arrayList.add(extrasItemModel);
                                appointmentAdapter = new MergeAccountClinicAdapter(arrayList, MAccountClinicList.this);
                                recyclerView.setAdapter(appointmentAdapter);

                                appointmentAdapter.notifyDataSetChanged();


                            }
                        }



                    }


                    if (success != 1) {
                        Utility.showSnackbar(relativeLayout, jsonObject.getString("error"));
                        button.setVisibility(View.GONE);
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
        getData.execute(URLListner.BASEURL + URLListner.get_clinic_name);


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
