package com.bfurns.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bfurns.R;
import com.bfurns.adapter.SummeryAdapter;
import com.bfurns.application.AppClass;
import com.bfurns.model.AppointementModel;
import com.bfurns.utility.GetData;
import com.bfurns.utility.MyPreferences;
import com.bfurns.utility.URLListner;
import com.bfurns.utility.Utility;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Mahesh on 21/08/16.
 */
public class RevenueActivity extends AppCompatActivity implements View.OnClickListener {


    private Toolbar toolbar;
    EditText editText, to_date, FromDate;
    int year, month, day;
    Calendar c;
    Button button, bargraph, summery,pdf;
    TextView Filter;
    private static final String TAG = RevenueActivity.class.getSimpleName();
    RelativeLayout parent;
    String userId, doctor_id,clinic_id,app_count,walk_in_count,status,clinic_doctor_id,merge_doctor_id;
    TextView revenue, appointment;
    String comman_account_clinic_id="0";


    RelativeLayout relativeLayout;
    String myto,myfrom;

    LinearLayout mainll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_revenue);
        Intent intent = getIntent();

        userId = AppClass.getuserId();
        clinic_id = AppClass.getclinicId();
        doctor_id =AppClass.getuserId();


        clinic_doctor_id=AppClass.getsessionclinic_doctor_id();
        merge_doctor_id= AppClass.getmerge_doctor_id();
        status= AppClass.getcomman_account_status();

        mainll=(LinearLayout)findViewById(R.id.mainll);
        mainll.setVisibility(View.GONE);


        setUpViews();
    }

    private void setUpViews() {


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Revenue");
        to_date = (EditText) findViewById(R.id.to_date);
        FromDate = (EditText) findViewById(R.id.from_date);
        Filter = (TextView) findViewById(R.id.filter);
        revenue = (TextView) findViewById(R.id.revenueee);
        appointment = (TextView) findViewById(R.id.appointment);
        summery = (Button) findViewById(R.id.summery);
        pdf = (Button) findViewById(R.id.pdf);
        FromDate.setOnClickListener(this);
        to_date.setOnClickListener(this);
        Filter.setOnClickListener(this);
        summery.setOnClickListener(this);
        pdf.setOnClickListener(this);



        button = (Button) findViewById(R.id.piechart);
        bargraph = (Button) findViewById(R.id.bargraph);
        bargraph.setClickable(true);
        bargraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RevenueActivity.this, BarGraphActivity.class);
                intent.putExtra(MyPreferences.DOCTOR_ID, doctor_id);
                intent.putExtra(MyPreferences.MYTO, myto);

                intent.putExtra(MyPreferences.MYFROM, myfrom);
                intent.putExtra(MyPreferences.CLINIC_ID, clinic_id);
                startActivity(intent);

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RevenueActivity.this, PieChartActivity.class);
                intent.putExtra(MyPreferences.DOCTOR_ID, doctor_id);
                intent.putExtra(MyPreferences.MYTO, myto);
                intent.putExtra(MyPreferences.MYFROM, myfrom);
                intent.putExtra(MyPreferences.CLINIC_ID, clinic_id);
                startActivity(intent);
            }
        });


        c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);


    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {


            case R.id.pdf:


                if (status.equals("1")) {
                    downloaddpfComman();
                } else {
                    downloaddpf();
                }


              /*  Intent intent1 = new Intent(RevenueActivity.this, PDFActivity.class);
                intent1.putExtra(MyPreferences.MYTO, myto);
                intent1.putExtra(MyPreferences.MYFROM, myfrom);
                startActivity(intent1);
                */




                break;

            case R.id.summery:

                Intent intent = new Intent(RevenueActivity.this, SummeryActivity.class);
                intent.putExtra(MyPreferences.MYTO, myto);
                intent.putExtra(MyPreferences.MYFROM, myfrom);

                startActivity(intent);


                break;

            case R.id.to_date:

                DatePickerDialog dd = new DatePickerDialog(RevenueActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                try {
                                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                                    // String dateInString = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                    String dateInString = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                    Date date = formatter.parse(dateInString);

                                    to_date.setText(formatter.format(date).toString());

                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }, year, month, day);

                dd.getDatePicker().setMaxDate(System.currentTimeMillis());



                dd.show();


                break;
            case R.id.from_date:

                DatePickerDialog dd1 = new DatePickerDialog(RevenueActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                try {
                                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                                    // String dateInString = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                    String dateInString = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                    Date date = formatter.parse(dateInString);

                                    FromDate.setText(formatter.format(date).toString());

                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }, year, month, day);

                dd1.getDatePicker().setMaxDate(System.currentTimeMillis());

                dd1.show();


                break;


            case R.id.filter:



                if (status.equals("1")) {
                    CommanDetails();
                } else {
                    filterData();
                }


                break;


        }
    }

    private void CommanDetails() {
        myto = to_date.getText().toString();
        myfrom = FromDate.getText().toString();

        if(myfrom.length() <= 0){

            FromDate.setError("Select From Date");
            return;
        }else{

            FromDate.setError(null);

        }

        if(myto.length() <= 0){

            to_date.setError("Select To Date");
            return;

        }else{

            to_date.setError(null);


        }


        Utility.showProgressDialog(this, "Please wait..");
        Utility.progressDialog.show();



        List<NameValuePair> params = new ArrayList<>();


        params.add(new BasicNameValuePair("doct_id", merge_doctor_id));
        params.add(new BasicNameValuePair("from_date", myfrom));
        params.add(new BasicNameValuePair("to_date", myto));
        params.add(new BasicNameValuePair("bus_id", comman_account_clinic_id));


        GetData getData = new GetData(params);
        getData.setResultListner(new GetData.ResultListner() {

            int success = 0;
            private String total_amount, total_appointment, title, description, p_from, phone, pto, image, mobileVerify, emailVerify, otp, socialType, socialId;

            @Override
            public void success(JSONObject jsonObject) {

                if (Utility.progressDialog.isShowing()) {
                    Utility.progressDialog.dismiss();
                }

                try {

                    success = jsonObject.getInt("responce");

                    Log.e(TAG, "Status:" + success);

                    if (success == 1) {

                        mainll.setVisibility(View.VISIBLE);
                        revenue.setText("Rs. 0");


                        JSONArray jsonArray3 = jsonObject.getJSONArray("revenue");
                        for (int i = 0; i < jsonArray3.length(); i++) {

                            JSONObject json = jsonArray3.getJSONObject(i);

                            total_amount = json.getString("total_amount");

                            Log.e("total_amount",total_amount);


                            if(total_amount!=null && !total_amount.equalsIgnoreCase("null")){

                                Double  amount = Double.parseDouble(total_amount);
                                long a = (long) Math.floor(amount);
                                revenue.setText("Rs." +a);

                            }else{
                                revenue.setText("Rs. 0");
                            }



                        }

                        JSONArray jsonArray4 = jsonObject.getJSONArray("total_appointment");
                        for (int i = 0; i < jsonArray4.length(); i++) {

                            JSONObject json = jsonArray4.getJSONObject(i);

                            total_appointment = json.getString("total_appointment");
                            appointment.setText(total_appointment);

                        }




                    }
                    if (success != 1) {
                        Utility.showSnackbar(parent, getString(R.string.please_enter_valid_details));
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
                    Utility.showSnackbar(parent, getString(R.string.failed));
                }


            }
        });
        getData.execute(URLListner.BASEURL + URLListner.appointment_summery_report_merge);




    }



    private void filterData() {



        myto = to_date.getText().toString();
        myfrom = FromDate.getText().toString();

        if(myfrom.length() <= 0){

            FromDate.setError("Select From Date");
            return;
        }else{

            FromDate.setError(null);

        }

        if(myto.length() <= 0){

            to_date.setError("Select To Date");
            return;

        }else{

            to_date.setError(null);


        }




        Utility.showProgressDialog(this, "Please wait..");
        Utility.progressDialog.show();

        List<NameValuePair> params = new ArrayList<>();


        params.add(new BasicNameValuePair("doct_id", userId));
        params.add(new BasicNameValuePair("from_date", myfrom));
        params.add(new BasicNameValuePair("to_date", myto));
        params.add(new BasicNameValuePair("bus_id", clinic_id));


        GetData getData = new GetData(params);
        getData.setResultListner(new GetData.ResultListner() {

            int success = 0;
            private String total_amount, total_appointment, title, description, p_from, phone, pto, image, mobileVerify, emailVerify, otp, socialType, socialId;

            @Override
            public void success(JSONObject jsonObject) {

                if (Utility.progressDialog.isShowing()) {
                    Utility.progressDialog.dismiss();
                }

                try {

                    success = jsonObject.getInt("responce");

                    Log.e(TAG, "Status:" + success);

                    if (success == 1) {

                        mainll.setVisibility(View.VISIBLE);


                        JSONArray jsonArray3 = jsonObject.getJSONArray("revenue");
                        for (int i = 0; i < jsonArray3.length(); i++) {

                            JSONObject json = jsonArray3.getJSONObject(i);

                            total_amount = json.getString("total_amount");

                            if(total_amount==null){
                                revenue.setText("Rs." +"00");

                            }else {
                                revenue.setText("Rs." +total_amount);

                            }
                        }

                        JSONArray jsonArray4 = jsonObject.getJSONArray("total_appointment");
                        for (int i = 0; i < jsonArray4.length(); i++) {

                            JSONObject json = jsonArray4.getJSONObject(i);

                            total_appointment = json.getString("total_appointment");
                            appointment.setText(total_appointment);

                        }




                    }
                    if (success != 1) {
                        Utility.showSnackbar(parent, getString(R.string.please_enter_valid_details));
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
                    Utility.showSnackbar(parent, getString(R.string.failed));
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


    //PDF API

    private void downloaddpfComman() {
        Utility.showProgressDialog(this, "Please wait..");
        Utility.progressDialog.show();

        List<NameValuePair> params = new ArrayList<>();


        params.add(new BasicNameValuePair("doct_id", AppClass.getmerge_doctor_id()));
        params.add(new BasicNameValuePair("from_date", myfrom));
        params.add(new BasicNameValuePair("to_date", myto));
        params.add(new BasicNameValuePair("bus_id", "0"));


        GetData getData = new GetData(params);
        getData.setResultListner(new GetData.ResultListner() {

            int success = 0;
            private String total_amount, total_appointment, title, description, p_from, phone, pto, image, mobileVerify, emailVerify, otp, socialType, socialId;

            @Override
            public void success(JSONObject jsonObject) {

                if (Utility.progressDialog.isShowing()) {
                    Utility.progressDialog.dismiss();
                }

                try {

                    success = jsonObject.getInt("response");

                    Log.e(TAG, "Status:" + success);

                    if (success == 1) {
                        // JSONObject jsonObject1=new JSONObject();
                        String pdf = jsonObject.getString("filename");

                        if (pdf.equals("")){
                            Utility.showSnackbar(relativeLayout, "No PDF Available");

                        }else {
                            pdf = pdf.replace("..", "");

                            String PDF = URLListner.PDF_BASEURLL + pdf;

                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(PDF));
                            startActivity(i);


                            // webview.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url=" + PDF);
                        }

                    }
                    if (success != 1) {
                        Utility.showSnackbar(relativeLayout, "No PDF Available");
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
                    Utility.showSnackbar(relativeLayout, getString(R.string.failed));
                }


            }
        });
        getData.execute(URLListner.BASEURL + URLListner.pdf_merge);
    }


    private void downloaddpf() {

        Utility.showProgressDialog(this, "Please wait..");
        Utility.progressDialog.show();

        List<NameValuePair> params = new ArrayList<>();




        params.add(new BasicNameValuePair("doct_id", AppClass.getuserId()));
        params.add(new BasicNameValuePair("from_date", myfrom));
        params.add(new BasicNameValuePair("to_date", myto));
        params.add(new BasicNameValuePair("bus_id", AppClass.getclinicId()));


        GetData getData = new GetData(params);
        getData.setResultListner(new GetData.ResultListner() {

            int success = 0;
            private String total_amount, total_appointment, title, description, p_from, phone, pto, image, mobileVerify, emailVerify, otp, socialType, socialId;

            @Override
            public void success(JSONObject jsonObject) {

                if (Utility.progressDialog.isShowing()) {
                    Utility.progressDialog.dismiss();
                }

                try {

                    success = jsonObject.getInt("response");

                    Log.e(TAG, "Status:" + success);

                    if (success == 1) {
                        // JSONObject jsonObject1=new JSONObject();
                        String pdf = jsonObject.getString("filename");
                        if (pdf.equals("")){
                            Utility.showSnackbar(relativeLayout, "No PDF Available");

                        }else {
                            pdf = pdf.replace("..", "");

                            String PDF = URLListner.PDF_BASEURLL + pdf;

                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(PDF));
                            startActivity(i);


                            // webview.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url=" + PDF);
                        }

                    }
                    if (success != 1) {
                        Utility.showSnackbar(relativeLayout, "No PDF Available");
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
                    Utility.showSnackbar(relativeLayout, getString(R.string.failed));
                }


            }
        });
        getData.execute(URLListner.BASEURL + URLListner.pdf);


    }



}

