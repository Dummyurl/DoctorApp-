package com.bfurns.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.bfurns.R;
import com.bfurns.application.AppClass;
import com.bfurns.utility.GetData;
import com.bfurns.utility.MyPreferences;
import com.bfurns.utility.URLListner;
import com.bfurns.utility.Utility;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class PieChartActivity extends AppCompatActivity implements OnChartValueSelectedListener {
    String Doct_id;
    RelativeLayout relativeLayout;

    String app_count, walk_in_count, telephonic_count;
    String myto, myfrom, doctor_id, clinic_id, clinic_doctor_id, merge_doctor_id, status;
    //int walk,tele,app,a,b,c;
    Double walk, tele, app;
    long a, b, c;
    PieChart pieChart;
    String comman_account_clinic_id = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        doctor_id = AppClass.getuserId();
        myto = intent.getStringExtra(MyPreferences.MYTO);
        myfrom = intent.getStringExtra(MyPreferences.MYFROM);
        clinic_id = AppClass.getclinicId();
        clinic_doctor_id = AppClass.getsessionclinic_doctor_id();//clinic list contain this id
        merge_doctor_id = AppClass.getmerge_doctor_id();
        status = AppClass.getcomman_account_status();


        setContentView(R.layout.activity_pie_chart);


        setUpview();


    }

    private void setUpview() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Performance Piechart");
        relativeLayout = (RelativeLayout) findViewById(R.id.relative);




        if (status.equals("1")) {
            CommanDetails();
        } else {
            getRevenue();
        }



    }

    private void CommanDetails() {

        Utility.showProgressDialog(PieChartActivity.this, "Please wait...");
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
//                            app = Integer.parseInt(app_count);
//                            b=app;
                            app = Double.parseDouble(app_count);
                            //b = app;
                            b = (long) Math.floor(app);
                        }


                        JSONArray jsonArray1 = jsonObject.getJSONArray("telephonic_count");
                        for (int i = 0; i < jsonArray1.length(); i++) {

                            JSONObject json = jsonArray1.getJSONObject(i);

                            telephonic_count = json.getString("count");
//                            tele=Integer.parseInt(telephonic_count);
//                            c=tele;

                            tele = Double.parseDouble(telephonic_count);
                            //    c = tele;
                            c = (long) Math.floor(tele);


                        }

                        JSONArray jsonArray2 = jsonObject.getJSONArray("walk_in_count");
                        for (int i = 0; i < jsonArray2.length(); i++) {

                            JSONObject json = jsonArray2.getJSONObject(i);

                            walk_in_count = json.getString("count");
//                            walk=Integer.parseInt(walk_in_count);
//                            a=walk;
                            walk = Double.parseDouble(walk_in_count);
                            //   a = walk;
                            a = (long) Math.floor(walk);


                        }

                        if (a==0 && b==0 && c==0){
                            Utility.showSnackbar(relativeLayout, "Performance Chart is not available");
                        }else {

                            ArrayList<Entry> yvalues = new ArrayList<Entry>();
                           // yvalues.add(new Entry(c, 0));
                           // yvalues.add(new Entry(b, 1));
                           // yvalues.add(new Entry(a, 2));


                            if(c!=0){
                                yvalues.add(new Entry(c, 0));
                            }
                            if(b!=0){
                                yvalues.add(new Entry(b, 1));
                            }

                            if(a!=0){
                                yvalues.add(new Entry(a, 2));
                            }

                            PieDataSet dataSet = new PieDataSet(yvalues, "Performance Chart");

                            ArrayList<String> xVals = new ArrayList<String>();

                            if(c!=0){
                                xVals.add("Telephonic");
                            }
                            if(b!=0){
                                xVals.add("Application");
                            }
                            if(a!=0){
                                xVals.add("Walk-in");

                            }


                          //  xVals.add("Telephonic");
                          //  xVals.add("Application");
                          //  xVals.add("Walk-in");


                            PieData data = new PieData(xVals, dataSet);
                            data.setValueFormatter(new PercentFormatter());
                            pieChart = (PieChart) findViewById(R.id.piechart);
                            pieChart.setUsePercentValues(true);
                            pieChart.setData(data);
                            pieChart.setDescription("This is Performance Pie Chart");
                            pieChart.performClick();
                            pieChart.setDrawSliceText(false);


                            pieChart.setDrawHoleEnabled(true);
                            pieChart.setTransparentCircleRadius(25f);
                            pieChart.setHoleRadius(25f);
                            pieChart.performClick();


                            dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
                            data.setValueTextSize(13f);
                            data.setValueTextColor(Color.DKGRAY);
                            pieChart.setOnChartValueSelectedListener(PieChartActivity.this);

                            pieChart.animateXY(1400, 1400);
                            pieChart.performClick();

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
        getData.execute(URLListner.BASEURL + URLListner.appointment_summery_report_merge);


    }


    private void getRevenue() {


        Utility.showProgressDialog(PieChartActivity.this, "Please wait...");
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
                            Utility.showSnackbar(relativeLayout, "PieChart is not available");
                        } else {

                            ArrayList<Entry> yvalues = new ArrayList<Entry>();

                            if(c!=0){
                                yvalues.add(new Entry(c, 0));
                            }
                            if(b!=0){
                                yvalues.add(new Entry(b, 1));
                            }

                            if(a!=0){
                                yvalues.add(new Entry(a, 2));

                            }

                            PieDataSet dataSet = new PieDataSet(yvalues, "Performance Chart");

                            ArrayList<String> xVals = new ArrayList<String>();


                            if(c!=0){
                                xVals.add("Telephonic");
                            }
                            if(b!=0){
                                xVals.add("Application");
                            }
                            if(a!=0){
                                xVals.add("Walk-in");

                            }


                            PieData data = new PieData(xVals, dataSet);
                            data.setValueFormatter(new PercentFormatter());
                            pieChart = (PieChart) findViewById(R.id.piechart);
                            pieChart.setUsePercentValues(true);
                            pieChart.setData(data);
                            pieChart.setDescription("This is Performance Pie Chart");


                            pieChart.setDrawHoleEnabled(true);
                            pieChart.setTransparentCircleRadius(25f);
                            pieChart.setHoleRadius(25f);
                            pieChart.performClick();

                            dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
                            data.setValueTextSize(13f);
                            data.setValueTextColor(Color.DKGRAY);
                            pieChart.setOnChartValueSelectedListener(PieChartActivity.this);

                            pieChart.animateXY(1400, 1400);


                        }
                    }


                    if (success != 1) {
                        Utility.showSnackbar(relativeLayout, "Revenue Chart is not available");
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
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

        if (e == null)
            return;
        Log.i("VAL SELECTED",
                "Value: " + e.getVal() + ", xIndex: " + e.getXIndex()
                        + ", DataSet index: " + dataSetIndex);
    }

    @Override
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");
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
