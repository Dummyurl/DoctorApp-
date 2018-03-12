package com.bfurns.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bfurns.R;
import com.bfurns.adapter.MedicationAdapter;
import com.bfurns.adapter.MedicationDtailsAdapter;
import com.bfurns.adapter.PatientAdapter;
import com.bfurns.model.AppointementModel;
import com.bfurns.utility.GetData;
import com.bfurns.utility.MyPreferences;
import com.bfurns.utility.MyUtility;
import com.bfurns.utility.URLListner;
import com.bfurns.utility.Utility;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DetailsMedication extends AppCompatActivity {
    private RecyclerView recyclerView,recyclerView1;
    private ArrayList<AppointementModel> arrayList = new ArrayList<>();
    private MedicationDtailsAdapter appointmentAdapter;
    RecyclerView.LayoutManager layoutManager,layoutmanager1;
    LinearLayout relativeLayout;
    String user_id,sub_id,app_date,id,quantity;
    int i;
    TextView name,strength,take,duration,slot,app,quantity1;
    String notes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medication_history_row_item_details);
        Intent intent=getIntent();
        user_id=intent.getStringExtra(MyPreferences.USER_ID);
        sub_id=intent.getStringExtra(MyPreferences.sub_id);
        app_date=intent.getStringExtra(MyPreferences.DATE);
        id=intent.getStringExtra(MyPreferences.ID);
        quantity=intent.getStringExtra(MyPreferences.quantity);
        setUpview();
    }

    private void setUpview() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Medication Details");


        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        relativeLayout = (LinearLayout) findViewById(R.id.parent);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManagaer);
        recyclerView.setHasFixedSize(true);

        getMedicationDetails();






    }


    private void getMedicationDetails() {


        Utility.showProgressDialog(DetailsMedication.this, "Please wait...");
        Utility.progressDialog.show();


        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("id",id));






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

                        JSONArray arr = jsonObject.getJSONArray("medication_histroy");
                        if (arr.length()==0){
                            MyUtility.showSnack(relativeLayout,"History not Found");
                        }else {
                            for (int i = 0; i < arr.length(); i++) {

                                JSONObject json = arr.getJSONObject(i);
                               AppointementModel extrasItemModel = new AppointementModel();
                                extrasItemModel.setId(json.getString("id"));

                                 notes = json.getString("medication");

                            }
                            JSONArray  jsonArraymedication = new JSONArray(notes);
                            for (int cnt = 0; cnt < jsonArraymedication.length(); cnt++) {
                                AppointementModel extrasItemModel = new AppointementModel();

                                JSONObject jsonObject1=jsonArraymedication.getJSONObject(cnt);

                                extrasItemModel.setDrug_name(jsonObject1.getString("drug_name"));
                                extrasItemModel.setStrength(jsonObject1.getString("strength"));
                                extrasItemModel.setTake(jsonObject1.getString("take"));
                                extrasItemModel.setDuration(jsonObject1.getString("duration"));
                                extrasItemModel.setQuantity(jsonObject1.getString("quantity"));
                                extrasItemModel.setSlot(jsonObject1.getString("slot"));
                                arrayList.add(extrasItemModel);

                                appointmentAdapter = new MedicationDtailsAdapter(arrayList, DetailsMedication.this);
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
        getData.execute(URLListner.BASEURL + URLListner.view_medication_history);
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
