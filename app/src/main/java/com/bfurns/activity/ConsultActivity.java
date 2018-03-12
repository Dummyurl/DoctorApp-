package com.bfurns.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.bfurns.R;
import com.bfurns.adapter.ConsultAdapter;
import com.bfurns.application.AppClass;
import com.bfurns.model.ConsultModel;
import com.bfurns.utility.GetData;
import com.bfurns.utility.MyPreferences;
import com.bfurns.utility.MyUtility;
import com.bfurns.utility.URLListner;
import com.bfurns.utility.Utility;
import com.github.clans.fab.FloatingActionButton;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mahesh on 21/08/16.
 */
public class ConsultActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recyclerView;
    private Toolbar toolbar;

    private ArrayList<ConsultModel> arrayList = new ArrayList<>();
    private ConsultAdapter appointmentAdapter;

    RecyclerView.LayoutManager layoutManager;
    RelativeLayout relativeLayout;
    String doctor_id, Doct_name, flag,query_id;
    FloatingActionButton floatingActionButton;
    SwipeRefreshLayout mSwipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_ques_ans);
        Intent intent = getIntent();
        doctor_id = AppClass.getuserId();
        Doct_name = AppClass.getUSER_FULLNAME();
        flag = intent.getStringExtra(MyPreferences.flag);
        query_id=AppClass.getqueryId();


        setUpViews();
    }

    private void setUpViews() {


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Inbox");

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.menu_red);
        relativeLayout = (RelativeLayout) findViewById(R.id.parent);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        LinearLayoutManager verticalLayoutmanager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(verticalLayoutmanager);
        recyclerView.setHasFixedSize(true);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConsultActivity.this, PostMail.class);
                intent.putExtra(MyPreferences.From_doctor, Doct_name);
                intent.putExtra(MyPreferences.DOCTOR_ID, doctor_id);
                startActivity(intent);
            }
        });

        getPatientMessages();


        mSwipeRefreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                getPatientMessages();
                mSwipeRefreshLayout.setRefreshing(false);






            }
        });


    }


    private void getPatientMessages() {


        if(!arrayList.isEmpty()){
            arrayList.clear();
            appointmentAdapter.notifyDataSetChanged();
        }


        if(!MyUtility.isConnected(this)){
            mSwipeRefreshLayout.setRefreshing(false);
            MyUtility.showSnack(relativeLayout,MyUtility.INTERNET_ERROR);
            return;
        }




        Utility.showProgressDialog(ConsultActivity.this, "Please wait...");
        Utility.progressDialog.show();
        Utility.progressDialog.dismiss();



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


                    if (success == 1) {
                        // jsonObject.getString("CharterProduct");
                        JSONArray arr = jsonObject.getJSONArray("doctor_query");
                        if (arr.length() == 0) {
                            Utility.showSnackbar(relativeLayout, "Chats are not available");

                        } else {
                            for (int i = 0; i < arr.length(); i++) {

                                JSONObject json = arr.getJSONObject(i);
                                ConsultModel extrasItemModel = new ConsultModel();

                                extrasItemModel.setName(json.getString("doct_name"));
                                extrasItemModel.setDoctor_name(json.getString("user_fullname"));
                                extrasItemModel.setTitle(json.getString("title"));
                                extrasItemModel.setDescription(json.getString("description"));
                                extrasItemModel.setDate(json.getString("date_time"));
                                extrasItemModel.setQuery_id(json.getString("id"));
                                extrasItemModel.setD_from(json.getString("d_to"));
                                extrasItemModel.setD_to(json.getString("d_from"));
                                extrasItemModel.setReply_id(json.getString("q_reply_id"));
                                extrasItemModel.setChat_type(json.getString("chat_type"));
                                extrasItemModel.setFlag(json.getString("flag"));
                                extrasItemModel.setRead(json.getString("q_read"));

                                arrayList.add(extrasItemModel);

                                appointmentAdapter = new ConsultAdapter(arrayList, ConsultActivity.this, flag);
                                recyclerView.setAdapter(appointmentAdapter);


                                appointmentAdapter.notifyDataSetChanged();

                            }
                        }


                            JSONArray jsonArray = jsonObject.getJSONArray("patient_query");
                        if (jsonArray.length()==0){
                           // Utility.showSnackbar(relativeLayout, "Chats are not available");

                        }else {
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject json = jsonArray.getJSONObject(i);
                                ConsultModel extrasItemModel = new ConsultModel();


                                extrasItemModel.setName(json.getString("user_fullname"));
                                extrasItemModel.setTitle(json.getString("title"));
                                extrasItemModel.setDescription(json.getString("description"));
                                extrasItemModel.setDate(json.getString("date_time"));
                                extrasItemModel.setDoctor_name(json.getString("doct_name"));
                                extrasItemModel.setQuery_id(json.getString("id"));
                                extrasItemModel.setP_from(json.getString("p_to"));
                                extrasItemModel.setP_to(json.getString("p_from"));
                                extrasItemModel.setReply_id(json.getString("q_reply_id"));
                                extrasItemModel.setChat_type(json.getString("chat_type"));
                                extrasItemModel.setRead(json.getString("q_read"));
                                extrasItemModel.setFlag(json.getString("flag"));


                                arrayList.add(extrasItemModel);


                                appointmentAdapter = new ConsultAdapter(arrayList, ConsultActivity.this, flag);
                                recyclerView.setAdapter(appointmentAdapter);


                                appointmentAdapter.notifyDataSetChanged();

                            }

                        }

                    }


                    if (success != 1) {
                        Utility.showSnackbar(relativeLayout, "Chats are not available");
                    }

                } catch (Exception e) {

                    e.printStackTrace();
                }
            }

            @Override
            public void fail() {

                if (Utility.progressDialog.isShowing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    Utility.progressDialog.dismiss();
                }

                if (success != 1) {
                    Utility.showToast(getApplicationContext(), getString(R.string.failed));
                }


            }
        });
        getData.execute(URLListner.BASEURL + URLListner.consult);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {


            case R.id.add:

                finish();

                break;


        }
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

