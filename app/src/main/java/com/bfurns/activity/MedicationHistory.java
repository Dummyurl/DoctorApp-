package com.bfurns.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bfurns.R;
import com.bfurns.adapter.MedicationAdapter;
import com.bfurns.adapter.SummeryAdapter;
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

public class MedicationHistory extends AppCompatActivity {

    private RecyclerView recyclerView,recyclerView1;
    private ArrayList<AppointementModel> arrayList = new ArrayList<>();
    private MedicationAdapter appointmentAdapter;
    RecyclerView.LayoutManager layoutManager,layoutmanager1;
    RelativeLayout relativeLayout;
    String user_id,sub_id,app_date,id;
    TextView app;
    SwipeRefreshLayout mSwipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication_history);
        Intent intent=getIntent();
        user_id= AppClass.getUUserId();
        sub_id=AppClass.getSubId();
        app_date=intent.getStringExtra(MyPreferences.DATE);
        id=AppClass.getAppointmentId();

        setUpview();
    }

    private void setUpview() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Medication History");

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeToRefresh);

      /*  app = (TextView) findViewById(R.id.date);
        app.setText(app_date);*/
        relativeLayout = (RelativeLayout) findViewById(R.id.parent);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        LinearLayoutManager verticalLayoutmanager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);


        recyclerView.setLayoutManager(verticalLayoutmanager);
        recyclerView.setHasFixedSize(true);
        getMedicationDetails();


        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                getMedicationDetails();
                mSwipeRefreshLayout.setRefreshing(false);






            }
        });



    }

    private void getMedicationDetails() {

        arrayList.clear();

        Utility.showProgressDialog(MedicationHistory.this, "Please wait...");
        Utility.progressDialog.show();


        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("user_id",user_id));
        params.add(new BasicNameValuePair("sub_user_id",sub_id));
        params.add(new BasicNameValuePair("app_id",id));






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
                        Utility.progressDialog.dismiss();

                        // jsonObject.getString("CharterProduct");
                        JSONArray arr = jsonObject.getJSONArray("medication_histroy");
                        if (arr.length()==0){
                            MyUtility.showSnack(relativeLayout,"History not Found");
                        }else {
                            for (int i = 0; i < arr.length(); i++) {

                                JSONObject json = arr.getJSONObject(i);
                                AppointementModel extrasItemModel = new AppointementModel();
                                extrasItemModel.setPatient_name(json.getString("app_id"));
                                extrasItemModel.setId(json.getString("id"));
                                extrasItemModel.setBus_id(json.getString("bus_id"));
                                extrasItemModel.setSub_user_id(json.getString("sub_user_id"));
                                extrasItemModel.setUser_id(json.getString("user_id"));
                                extrasItemModel.setDoct_id(json.getString("doct_id"));
                                extrasItemModel.setDrug_name(json.getString("drug_name"));
                                extrasItemModel.setStrength(json.getString("strength"));
                                extrasItemModel.setTake(json.getString("take"));
                                extrasItemModel.setDuration(json.getString("duration"));
                                extrasItemModel.setSlot(json.getString("slot"));
                                extrasItemModel.setQuantity(json.getString("quantity"));
                                extrasItemModel.setDate(json.getString("created"));


                                arrayList.add(extrasItemModel);
                                appointmentAdapter = new MedicationAdapter(arrayList, MedicationHistory.this,app_date,user_id,sub_id);
                                recyclerView.setAdapter(appointmentAdapter);

                                appointmentAdapter.notifyDataSetChanged();


                            }

                        }



                    }


                    if (success != 1) {
                        Utility.showSnackbar(relativeLayout, "Data Not Found");
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
        getData.execute(URLListner.BASEURL + URLListner.medication_history);
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
