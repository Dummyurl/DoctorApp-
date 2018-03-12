package com.bfurns.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bfurns.R;
import com.bfurns.adapter.SummeryAdapter;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FilterActivity extends AppCompatActivity implements View.OnClickListener {

    String userId, clinic_id, doctor_id;
    private RecyclerView recyclerView, recyclerView1;
    private ArrayList<AppointementModel> arrayList = new ArrayList<>();
    private SummeryAdapter appointmentAdapter;
    RecyclerView.LayoutManager layoutManager, layoutmanager1;
    RelativeLayout parent;
    EditText editText, to_date, FromDate;
    Button Filter;
    LinearLayout linearLayout, linearlayout1;
    String From, to;
    int year, month, day;
    Calendar c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        Intent intent = getIntent();

        userId = intent.getStringExtra(MyPreferences.USER_ID);
        clinic_id = intent.getStringExtra(MyPreferences.CLINIC_ID);
        doctor_id = intent.getStringExtra(MyPreferences.DOCTOR_ID);

        setUpview();

    }

    private void setUpview() {


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Filter Summery");
        to_date = (EditText) findViewById(R.id.to_date);
        FromDate = (EditText) findViewById(R.id.from_date);
        Filter = (Button) findViewById(R.id.filter);
        linearLayout = (LinearLayout) findViewById(R.id.linearlayout1);
        Filter.setOnClickListener(this);
        to_date.setOnClickListener(this);
        FromDate.setOnClickListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        parent = (RelativeLayout) findViewById(R.id.parent);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        LinearLayoutManager verticalLayoutmanager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);


        recyclerView.setLayoutManager(verticalLayoutmanager);
        recyclerView.setHasFixedSize(true);
        c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.filter:
                to = to_date.getText().toString();
                From = FromDate.getText().toString();

                if (MyUtility.isConnected(this)) {
                    callApi();
                } else {
                    MyUtility.showSnack(parent, MyUtility.INTERNET_ERROR);
                }
                break;


            case R.id.to_date:

                DatePickerDialog dd = new DatePickerDialog(FilterActivity.this,
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



                dd.show();



                break;
            case R.id.from_date:

                DatePickerDialog dd1 = new DatePickerDialog(FilterActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                try {
                                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                                   // String dateInString = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                    String dateInString = year + "-" + (monthOfYear + 1) + "-" +dayOfMonth;
                                    Date date = formatter.parse(dateInString);

                                    FromDate.setText(formatter.format(date).toString());

                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }, year, month, day);



                dd1.show();


                break;

        }
    }

    private void callApi() {
        arrayList.clear();

        Utility.showProgressDialog(FilterActivity.this, "Please wait...");
        Utility.progressDialog.show();


        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("doct_id",doctor_id));
        params.add(new BasicNameValuePair("from_date",From));
        params.add(new BasicNameValuePair("to_date",to));






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
                        JSONArray arr = jsonObject.getJSONArray("appointment");
                        for (int i = 0; i < arr.length(); i++) {

                            JSONObject json = arr.getJSONObject(i);
                            AppointementModel extrasItemModel = new AppointementModel();
                            extrasItemModel.setPatient_name(json.getString("app_name"));
                            extrasItemModel.setMode_of_appointement(json.getString("mode_app"));
                            extrasItemModel.setTime(json.getString("start_time"));
                            extrasItemModel.setDate(json.getString("appointment_date"));
                            extrasItemModel.setId(json.getString("id"));
                            extrasItemModel.setBus_id(json.getString("bus_id"));
                            extrasItemModel.setSub_user_id(json.getString("sub_user_id"));
                            extrasItemModel.setUser_id(json.getString("user_id"));
                            extrasItemModel.setDoct_id(json.getString("doct_id"));
                            extrasItemModel.setApp_staus(json.getString("app_status"));
                            extrasItemModel.setClinic_name(json.getString("bus_title"));
                            extrasItemModel.setEmail(json.getString("app_email"));
                            extrasItemModel.setPayed_amount(json.getString("payed_amount"));
                            extrasItemModel.setRemaining_amount(json.getString("due_amount"));
                            extrasItemModel.setTotal_amount(json.getString("payment_amount"));


                            arrayList.add(extrasItemModel);


                            appointmentAdapter = new SummeryAdapter(arrayList, FilterActivity.this);
                            recyclerView.setAdapter(appointmentAdapter);


                            appointmentAdapter.notifyDataSetChanged();


                        }



                    }


                    if (success != 1) {
                        Utility.showSnackbar(parent, getString(R.string.notavailable));
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
