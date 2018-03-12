package com.bfurns.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bfurns.R;
import com.bfurns.adapter.DoctorAdapter;
import com.bfurns.adapter.ServiceAdapter;
import com.bfurns.model.DoctorStaffModel;
import com.bfurns.utility.GetData;
import com.bfurns.utility.JSONParser;
import com.bfurns.utility.MyPreferences;
import com.bfurns.utility.MyUtility;
import com.bfurns.utility.Preferences;
import com.bfurns.utility.URLListner;
import com.bfurns.utility.Utility;
import com.bfurns.utility.Validations;
import com.bfurns.ziffylink.ForgotActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mahesh on 21/08/16.
 */
public class StepThreeActivity extends AppCompatActivity implements View.OnClickListener {


    private Button previous,login,more;
    private Toolbar toolbar;

    private LinearLayout parent;
    private EditText eemail,epassword;
    private String email,password;

    private SharedPreferences sharedPreferences;
    String clinic_id,user_id,doctor_id;


    private RecyclerView recyclerView;
    private ArrayList<DoctorStaffModel> arrayList = new ArrayList<>();
    private ServiceAdapter appointmentAdapter;
    RecyclerView.LayoutManager layoutManager;
    RelativeLayout relativeLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_three);
        Intent intent = getIntent();
        clinic_id = intent.getStringExtra(MyPreferences.CLINIC_ID);
        user_id = intent.getStringExtra(MyPreferences.USER_ID);
        doctor_id = intent.getStringExtra(MyPreferences.DOCTOR_ID);
        setUpViews();
    }

    private void setUpViews() {

        sharedPreferences=getSharedPreferences(Preferences.MyPREFERENCES, Context.MODE_PRIVATE);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Step 3/4");


        login=(Button)findViewById(R.id.next);
        login.setOnClickListener(this);

        previous=(Button)findViewById(R.id.previous);
        previous.setOnClickListener(this);

        more =(Button)findViewById(R.id.more);
        more.setOnClickListener(this);

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

        Utility.showProgressDialog(StepThreeActivity.this, "Please wait...");
        Utility.progressDialog.show();


        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("bus_id",clinic_id));






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
                        JSONArray arr = jsonObject.getJSONArray("business_service");
                        for (int i = 0; i < arr.length(); i++) {

                            JSONObject json = arr.getJSONObject(i);
                            DoctorStaffModel extrasItemModel = new DoctorStaffModel();


                            extrasItemModel.setService_name(json.getString("service_title"));
                            extrasItemModel.setService_discount(json.getString("service_discount"));
                            extrasItemModel.setSet_price(json.getString("service_price"));
                            extrasItemModel.setService_tax(json.getString("service_tax"));
                            extrasItemModel.setTotal_cost(json.getString("total_cost"));


                            arrayList.add(extrasItemModel);


                            appointmentAdapter = new ServiceAdapter(arrayList, StepThreeActivity.this);
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

                if (Utility.progressDialog.isShowing()) {
                    Utility.progressDialog.dismiss();
                }

                if (success != 1) {
                    Utility.showToast(getApplicationContext(), getString(R.string.failed));
                }


            }
        });
        getData.execute(URLListner.BASEURL + URLListner.get_clinic_service);

    }






    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.more:

                Intent i = new Intent(StepThreeActivity.this, AddServiceActivity.class);
                i.putExtra(MyPreferences.CLINIC_ID, clinic_id);
                i.putExtra(MyPreferences.USER_ID, user_id);
                i.putExtra(MyPreferences.DOCTOR_ID, doctor_id);

                startActivity(i);


                break;

            case R.id.next:


                Intent ii = new Intent(StepThreeActivity.this, StepFourActivity.class);
                ii.putExtra(MyPreferences.CLINIC_ID, clinic_id);
                ii.putExtra(MyPreferences.USER_ID, user_id);
                ii.putExtra(MyPreferences.DOCTOR_ID, doctor_id);
                startActivity(ii);

                break;

            case R.id.previous:

                finish();

                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case android.R.id.home:

                onBackPressed();

                break;
        }

        return super.onOptionsItemSelected(item);
    }


}

