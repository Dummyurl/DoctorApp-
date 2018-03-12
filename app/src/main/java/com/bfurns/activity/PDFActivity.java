package com.bfurns.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.RelativeLayout;

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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class PDFActivity extends AppCompatActivity {
    private ArrayList<AppointementModel> arrayList = new ArrayList<>();
    private SummeryAdapter appointmentAdapter;
    RecyclerView.LayoutManager layoutManager, layoutmanager1;
    RelativeLayout relativeLayout;
    String userId, clinic_id, doctor_id, my_to, my_from, merge_doctor_id, status, clinic_doctor_id;
    String comman_account_clinic_id = "0";


    List<AppointementModel> ObjectList;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static final String TAG = PDFActivity.class.getSimpleName();
    Uri myUri;
    String uri;
    WebView webview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        Intent intent = getIntent();
        clinic_id = AppClass.getclinicId();
        doctor_id = AppClass.getuserId();
        my_to = intent.getStringExtra(MyPreferences.MYTO);
        my_from = intent.getStringExtra(MyPreferences.MYFROM);
        merge_doctor_id = AppClass.getmerge_doctor_id();
        status = AppClass.getcomman_account_status();
        clinic_doctor_id = AppClass.getsessionclinic_doctor_id();


        setUpview();

    }

    private void setUpview() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Summary");

        webview = (WebView) findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);





        if (status.equals("1")) {
            downloaddpfComman();
        } else {
            downloaddpf();
        }


    }

    private void downloaddpfComman() {
        Utility.showProgressDialog(this, "Please wait..");
        Utility.progressDialog.show();

        List<NameValuePair> params = new ArrayList<>();


        params.add(new BasicNameValuePair("doct_id", merge_doctor_id));
        params.add(new BasicNameValuePair("from_date", my_from));
        params.add(new BasicNameValuePair("to_date", my_to));
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


        params.add(new BasicNameValuePair("doct_id", doctor_id));
        params.add(new BasicNameValuePair("from_date", my_from));
        params.add(new BasicNameValuePair("to_date", my_to));
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
