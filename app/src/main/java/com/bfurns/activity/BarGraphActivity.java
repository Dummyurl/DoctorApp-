package com.bfurns.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.bfurns.R;
import com.bfurns.adapter.DoctorListAdapter;
import com.bfurns.application.AppClass;
import com.bfurns.model.AppointementModel;
import com.bfurns.utility.GetData;
import com.bfurns.utility.MyPreferences;
import com.bfurns.utility.URLListner;
import com.bfurns.utility.Utility;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BarGraphActivity extends AppCompatActivity {
    String Doct_id;
    int i = 100;
    RelativeLayout relativeLayout;
    String app_count, walk_in_count, telephonic_count;
    String myto, myfrom, doctor_id, clinic_id,clinic_doctor_id,merge_doctor_id,status;
   // int walk, tele, app, /*a, b, c*/result,result1,result2;
    Double walk, tele, app;
    long a,b,c;
    BarChart barChart;
    String comman_account_clinic_id="0";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_graph);

        Intent intent = getIntent();
        doctor_id = AppClass.getuserId();
        myto = intent.getStringExtra(MyPreferences.MYTO);
        myfrom = intent.getStringExtra(MyPreferences.MYFROM);
        clinic_id =AppClass.getclinicId();

        clinic_doctor_id=AppClass.getsessionclinic_doctor_id();//clinic list contain this id
        merge_doctor_id= AppClass.getmerge_doctor_id();
        status=AppClass.getcomman_account_status();


        setUpviews();

    }

    private void setUpviews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Revenue Barchart");

        barChart = (BarChart) findViewById(R.id.barchart);
        relativeLayout = (RelativeLayout) findViewById(R.id.relative);



        if (status.equals("1")) {
            CommanDetails();
        } else {
            getRevenue();
        }




    }

    private void CommanDetails() {

        Utility.showProgressDialog(BarGraphActivity.this, "Please wait...");
        Utility.progressDialog.show();


        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("doct_id", merge_doctor_id));
        params.add(new BasicNameValuePair("from_date", myfrom));
        params.add(new BasicNameValuePair("to_date", myto));
        params.add(new BasicNameValuePair("bus_id", comman_account_clinic_id));


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

                        JSONArray jsonArray = jsonObject.getJSONArray("app_count");
                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject json = jsonArray.getJSONObject(i);

                            app_count = json.getString("count");
                            app = Double.parseDouble(app_count);
                            //b = app;
                            b = (long) Math.floor(app);


                        }


                        JSONArray jsonArray1 = jsonObject.getJSONArray("telephonic_count");
                        for (int i = 0; i < jsonArray1.length(); i++) {

                            JSONObject json = jsonArray1.getJSONObject(i);

                            telephonic_count = json.getString("count");
                            tele = Double.parseDouble(telephonic_count);
                            c = (long) Math.floor(tele);


                        }

                        JSONArray jsonArray2 = jsonObject.getJSONArray("walk_in_count");
                        for (int i = 0; i < jsonArray2.length(); i++) {

                            JSONObject json = jsonArray2.getJSONObject(i);

                            walk_in_count = json.getString("count");
                            // walk = Integer.parseInt(walk_in_count);
                            walk = Double.parseDouble(walk_in_count);
                            //   a = walk;
                            a = (long) Math.floor(walk);


                        }

                        if (a == 0 && b == 0 && c == 0) {
                            Utility.showSnackbar(relativeLayout, "Performance Chart is not available");
                        } else {
                            ArrayList<BarEntry> entries = new ArrayList<>();
                            entries.add(new BarEntry(a, 0));
                            entries.add(new BarEntry(b, 1));
                            entries.add(new BarEntry(c, 2));

                            BarDataSet bardataset = new BarDataSet(entries, "Cells");

                            ArrayList<String> labels = new ArrayList<String>();
                            labels.add("Walk-in");
                            labels.add("Application");
                            labels.add("Telephonic");

                            BarData data = new BarData(labels, bardataset);
                            barChart.setData(data); // set the data and list of lables into chart

                            barChart.setDescription("Set Bar Chart Description");  // set the description

                            bardataset.setColors(ColorTemplate.COLORFUL_COLORS);

                            barChart.animateY(5000);


                        }
                    }


                    if (success != 1) {
                        Utility.showSnackbar(relativeLayout, "Data Not Available");
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
        getData.execute(URLListner.BASEURL + URLListner.appointment_summery_report_merge);

    }


    private void getRevenue() {


        Utility.showProgressDialog(BarGraphActivity.this, "Please wait...");
        Utility.progressDialog.show();


        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("doct_id", doctor_id));
        params.add(new BasicNameValuePair("from_date", myfrom));
        params.add(new BasicNameValuePair("to_date", myto));
        params.add(new BasicNameValuePair("bus_id", clinic_id));


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

                        JSONArray jsonArray = jsonObject.getJSONArray("app_count");
                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject json = jsonArray.getJSONObject(i);

                            app_count = json.getString("count");

                            app = Double.parseDouble(app_count);
                            b = (long) Math.floor(app);


                        }


                        JSONArray jsonArray1 = jsonObject.getJSONArray("telephonic_count");
                        for (int i = 0; i < jsonArray1.length(); i++) {

                            JSONObject json = jsonArray1.getJSONObject(i);

                            telephonic_count = json.getString("count");


                            tele = Double.parseDouble(telephonic_count);
                            c = (long) Math.floor(tele);


                        }

                        JSONArray jsonArray2 = jsonObject.getJSONArray("walk_in_count");
                        for (int i = 0; i < jsonArray2.length(); i++) {

                            JSONObject json = jsonArray2.getJSONObject(i);

                            walk_in_count = json.getString("count");
                            walk = Double.parseDouble(walk_in_count);
                            a = (long) Math.floor(walk);


                        }


                        if (a == 0 && b == 0 && c == 0) {
                            Utility.showSnackbar(relativeLayout, "Performance Chart is not available");
                        } else {
                            ArrayList<BarEntry> entries = new ArrayList<>();
                            entries.add(new BarEntry(a, 0));
                            entries.add(new BarEntry(b, 1));
                            entries.add(new BarEntry(c, 2));

                            BarDataSet bardataset = new BarDataSet(entries, "Cells");

                            ArrayList<String> labels = new ArrayList<String>();
                            labels.add("Walk-in");
                            labels.add("Application");
                            labels.add("Telephonic");

                            BarData data = new BarData(labels, bardataset);
                            barChart.setData(data); // set the data and list of lables into chart

                            barChart.setDescription("Set Bar Chart Description");  // set the description

                            bardataset.setColors(ColorTemplate.COLORFUL_COLORS);

                            barChart.animateY(5000);


                        }
                    }

                    if (success != 1) {
                        Utility.showSnackbar(relativeLayout, "Performance Graph is not available");
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
        getData.execute(URLListner.BASEURL + URLListner.appointment_summery_report);


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
