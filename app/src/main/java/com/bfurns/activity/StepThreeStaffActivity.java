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
import com.bfurns.adapter.StaffAdapter;
import com.bfurns.model.DoctorStaffModel;
import com.bfurns.utility.GetData;
import com.bfurns.utility.JSONParser;
import com.bfurns.utility.MyPreferences;
import com.bfurns.utility.MyUtility;
import com.bfurns.utility.Preferences;
import com.bfurns.utility.URLListner;
import com.bfurns.utility.Utility;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mahesh on 21/08/16.
 */
public class StepThreeStaffActivity extends AppCompatActivity implements View.OnClickListener {


    private Button forgot,login;
    private Toolbar toolbar;


    String clinic_id,user_id,doctor_id;


    private LinearLayout parent;
    private EditText eemail,epassword;
    private String email,password;
    private RecyclerView recyclerView;
    private ArrayList<DoctorStaffModel> arrayList = new ArrayList<>();
    private StaffAdapter appointmentAdapter;
    RecyclerView.LayoutManager layoutManager;
    RelativeLayout relativeLayout;


    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step_three_staff_activity);
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
        recyclerView = (RecyclerView) findViewById(R.id.staff_recycler);
        relativeLayout = (RelativeLayout) findViewById(R.id.parent);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        LinearLayoutManager verticalLayoutmanager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(verticalLayoutmanager);
        recyclerView.setHasFixedSize(true);

        getApointements();

      /*  forgot=(Button)findViewById(R.id.forgot);
        forgot.setOnClickListener(this);



        parent=(LinearLayout)findViewById(R.id.parent);
        eemail=(EditText)findViewById(R.id.email);
        epassword=(EditText)findViewById(R.id.password);

        epassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {


                    loginValidation();


                }
                return false;
            }
        });

         */

        login=(Button)findViewById(R.id.next);
        login.setOnClickListener(this);

        Button  previous=(Button)findViewById(R.id.previous);
        previous.setOnClickListener(this);


        Button  morestaff =(Button)findViewById(R.id.morestaff);
        morestaff.setOnClickListener(this);


    }


    private void getApointements() {


        arrayList.clear();

        Utility.showProgressDialog(StepThreeStaffActivity.this, "Please wait...");
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
                        JSONArray arr = jsonObject.getJSONArray("staffs");
                        for (int i = 0; i < arr.length(); i++) {

                            JSONObject json = arr.getJSONObject(i);
                            DoctorStaffModel extrasItemModel = new DoctorStaffModel();


                            extrasItemModel.setEmail(json.getString("s_email"));
                            extrasItemModel.setContact(json.getString("s_phone"));
                            extrasItemModel.setName(json.getString("s_name"));


                            arrayList.add(extrasItemModel);


                            appointmentAdapter = new StaffAdapter(arrayList, StepThreeStaffActivity.this);
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
        getData.execute(URLListner.BASEURL + URLListner.get_clinic_staff);

    }







    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.previous:

                finish();

                break;

            case R.id.next:



                Intent i = new Intent(StepThreeStaffActivity.this, AddServiceActivity.class);
                i.putExtra(MyPreferences.CLINIC_ID, clinic_id);
                i.putExtra(MyPreferences.USER_ID, user_id);
                i.putExtra(MyPreferences.DOCTOR_ID, doctor_id);
                startActivity(i);

                break;




            case R.id.morestaff:

                Intent ii = new Intent(StepThreeStaffActivity.this, AddStaffActivity.class);
                ii.putExtra(MyPreferences.CLINIC_ID, clinic_id);
                ii.putExtra(MyPreferences.USER_ID, user_id);
                ii.putExtra(MyPreferences.DOCTOR_ID, doctor_id);
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

    private class Login extends AsyncTask<String, Void, Boolean> {

        ProgressDialog dialog;
        private JSONParser jsonParser=new JSONParser();
        private int success=0;
        private JSONArray vjsonArray;
        private String user_id="",
                user_name,
                user_lastname,
                user_email,
                user_social_id,
                user_social_type,
                user_phone,
                user_image;


        @Override
        protected void onPreExecute() {

            dialog = new ProgressDialog(StepThreeStaffActivity.this);
            dialog.setMessage("Please wait...");
            dialog.setIndeterminate(false);
            dialog.setCancelable(true);
            dialog.show();
            super.onPreExecute();

        }

        @Override
        protected Boolean doInBackground(String... urls) {


            List<NameValuePair> params = new ArrayList<NameValuePair>();


            params.add(new BasicNameValuePair("email", email));
            params.add(new BasicNameValuePair("password", password));

            try {

                // getting JSON string from URL
                JSONObject json = jsonParser.makeHttpRequest(URLListner.MAIN_URL+URLListner.LOGIN_URL, "POST", params);

                Log.e("Login Details: ", json.toString());

                success = json.getInt("status");

                if (success == 1) {

                 /*   vjsonArray = json.getJSONArray("userinfo");

                    for (int i = 0; i < vjsonArray.length(); i++) {

                        JSONObject c = vjsonArray.getJSONObject(i);

                        user_id = c.getString("customer_id");
                        user_name = c.getString("firstname");
                        user_lastname=c.getString("lastname");
                        user_email = c.getString("email_id");
                        user_phone = c.getString("mobile");
                        user_social_id = c.getString("social_id");

                    }

                    */


                }

                return true;

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

        }

        protected void onPostExecute(Boolean result) {


            if (dialog.isShowing()) {

                dialog.dismiss();
            }


            if (success == 1) {

                SharedPreferences.Editor editor = sharedPreferences.edit();
              //  editor.putString(Preferences.Name, user_name);
              //  editor.putString(Preferences.LastName, user_lastname);
              //  editor.putString(Preferences.Email, user_email);
                editor.putString(Preferences.userId, "10");
               // editor.putString(Preferences.userSocial_id,user_social_id);
               // editor.putString(Preferences.userPhone,user_phone);
                editor.putString(Preferences.LOGIN_BY,"app");
                editor.commit();


                if (sharedPreferences.getString(Preferences.userId,"")!="") {

                    finish();
                    Intent i = new Intent(StepThreeStaffActivity.this, HomeActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);


                } else {

                    MyUtility.showSnack(parent, MyUtility.FAILED);

                }

            } else {

                MyUtility.showSnack(parent, MyUtility.FAILED);
            }
        }
    }

}

