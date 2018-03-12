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
import com.bfurns.adapter.Category;
import com.bfurns.adapter.CategoryListAdapter;
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

public class CategoryListActivity extends AppCompatActivity {
    String userId, clinic_id, doctor_id,doct_name;

    private RecyclerView recyclerView;
    private ArrayList<AppointementModel> arrayList = new ArrayList<>();
    private CategoryListAdapter appointmentAdapter;
    RecyclerView.LayoutManager layoutManager;
    RelativeLayout relativeLayout;


    String email,doctor_name, gender, doct_degree, doct_college, doct_year,doctor_email,
            doct_phone, doct_experience, d_city, d_reg_no, d_reg_con, d_reg_year,
            d_reg_proof, d_qua_proof, d_id_proof, doct_photo, doct_speciality,passwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);
        Intent intent = getIntent();

       /* userId = intent.getStringExtra(MyPreferences.USER_ID);
        clinic_id = intent.getStringExtra(MyPreferences.CLINIC_ID);
        doctor_id = intent.getStringExtra(MyPreferences.DOCTOR_ID);
      //  doct_name = intent.getStringExtra(MyPreferences.From_doctor);

        doct_experience = intent.getStringExtra(MyPreferences.doct_experience);
        gender = intent.getStringExtra(MyPreferences.GENDER);




        doctor_name = intent.getStringExtra(MyPreferences.USER_FULLNAME);
        email = intent.getStringExtra(MyPreferences.USER_EMAIL);
        doctor_email = intent.getStringExtra(MyPreferences.DOCTOR_EMAIL);
        passwd = intent.getStringExtra(MyPreferences.password);
        doct_phone = intent.getStringExtra(MyPreferences.CONTACT);
        d_city = intent.getStringExtra(MyPreferences.CITY);
        doct_speciality = intent.getStringExtra(MyPreferences.doct_speciality);
        doct_degree = intent.getStringExtra(MyPreferences.doct_degree);
        doct_college = intent.getStringExtra(MyPreferences.doct_college);
        doct_year = intent.getStringExtra(MyPreferences.completion_year);
        d_reg_no = intent.getStringExtra(MyPreferences.d_reg_no);
        d_reg_year = intent.getStringExtra(MyPreferences.d_reg_year);
        d_reg_con = intent.getStringExtra(MyPreferences.d_reg_con);
        d_reg_proof = intent.getStringExtra(MyPreferences.r_proof_photo);
        d_qua_proof = intent.getStringExtra(MyPreferences.r_proof_qualification);
        d_id_proof = intent.getStringExtra(MyPreferences.r_proof_id);
        doct_photo = intent.getStringExtra(MyPreferences.doct_photo);

*/


        setUpview();

    }

    private void setUpview() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Speciality");


        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        relativeLayout = (RelativeLayout) findViewById(R.id.parent);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        LinearLayoutManager verticalLayoutmanager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(verticalLayoutmanager);
        recyclerView.setHasFixedSize(true);

        getcategory();
    }

    private void getcategory() {


        arrayList.clear();

        Utility.showProgressDialog(CategoryListActivity.this, "Please wait...");
        Utility.progressDialog.show();


        List<NameValuePair> params = new ArrayList<>();


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


                            extrasItemModel.setBus_id(json.getString("id"));
                            extrasItemModel.setDoct_speciality(json.getString("title"));


                            arrayList.add(extrasItemModel);


                            appointmentAdapter = new CategoryListAdapter(arrayList, CategoryListActivity.this);
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
        getData.execute(URLListner.BASEURL + URLListner.get_categories);


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
