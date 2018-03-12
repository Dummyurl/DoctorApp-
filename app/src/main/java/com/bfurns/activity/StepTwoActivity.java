package com.bfurns.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bfurns.R;
import com.bfurns.adapter.DoctorAdapter;
import com.bfurns.model.DoctorStaffModel;
import com.bfurns.utility.GetData;
import com.bfurns.utility.MyPreferences;
import com.bfurns.utility.MyUtility;
import com.bfurns.utility.Preferences;
import com.bfurns.utility.URLListner;
import com.bfurns.utility.Utility;
import com.bfurns.utility.Validations;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mahesh on 21/08/16.
 */
public class StepTwoActivity extends AppCompatActivity implements View.OnClickListener {


    private Button forgot,login;
    private Toolbar toolbar;

    private LinearLayout parent;
    private EditText eemail,epassword;
    private String email,password;
    String clinic_id,user_id,doctor_id;
    private RecyclerView recyclerView;
    private ArrayList<DoctorStaffModel> arrayList = new ArrayList<>();
    private DoctorAdapter appointmentAdapter;
    RecyclerView.LayoutManager layoutManager;
    RelativeLayout relativeLayout;


    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_two);
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
        getSupportActionBar().setTitle("Step 2/4");


        recyclerView = (RecyclerView) findViewById(R.id.doctor_recycler);
        relativeLayout = (RelativeLayout) findViewById(R.id.parent);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        LinearLayoutManager verticalLayoutmanager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(verticalLayoutmanager);
        recyclerView.setHasFixedSize(true);

        getApointements();


        login=(Button)findViewById(R.id.next);
        login.setOnClickListener(this);

        Button  previous=(Button)findViewById(R.id.previous);
        previous.setOnClickListener(this);

        Button more =(Button)findViewById(R.id.more);
        more.setOnClickListener(this);

    }



    private void getApointements() {


        arrayList.clear();

        Utility.showProgressDialog(StepTwoActivity.this, "Please wait...");
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
                        JSONArray arr = jsonObject.getJSONArray("users");
                        for (int i = 0; i < arr.length(); i++) {

                            JSONObject json = arr.getJSONObject(i);
                            DoctorStaffModel extrasItemModel = new DoctorStaffModel();


                            extrasItemModel.setEmail(json.getString("doct_email"));
                            extrasItemModel.setContact(json.getString("doct_phone"));
                            extrasItemModel.setName(json.getString("doct_name"));


                            arrayList.add(extrasItemModel);


                            appointmentAdapter = new DoctorAdapter(arrayList, StepTwoActivity.this);
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
        getData.execute(URLListner.BASEURL + URLListner.get_clinic_doctor);

    }


    private void loginValidation() {

        MyUtility.hideKeyboard(epassword,StepTwoActivity.this);

        email = eemail.getText().toString().trim();
        password = epassword.getText().toString().trim();


        if (email.equalsIgnoreCase("")) {


            MyUtility.showSnack(parent, Validations.ENTER_EMAIL);

        } else if (!email.matches(MyUtility.emailPattern) ) {


            MyUtility.showSnack(parent, Validations.ENTER_VALID_EMAIL);

        } else if (password.equalsIgnoreCase("") ) {

            MyUtility.showSnack(parent, Validations.ENTER_PASSWORD);

        }  else {


            if(MyUtility.isConnected(this)){

                finish();
                Intent i = new Intent(StepTwoActivity.this, HomeActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);



            }else{

                MyUtility.showSnack(parent,MyUtility.INTERNET_ERROR);
            }

        }

    }




    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.previous:

                finish();

                break;

            case R.id.next:
                Intent i = new Intent(StepTwoActivity.this, StepThreeStaffActivity.class);
                i.putExtra(MyPreferences.CLINIC_ID, clinic_id);
                i.putExtra(MyPreferences.USER_ID, user_id);
                i.putExtra(MyPreferences.DOCTOR_ID, doctor_id);

                startActivity(i);



                break;


            case R.id.more:
                Intent ii = new Intent(StepTwoActivity.this, AddDoctorActivity.class);
                ii.putExtra(MyPreferences.CLINIC_ID, clinic_id);
                ii.putExtra(MyPreferences.USER_ID, user_id);

                startActivity(ii);



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

