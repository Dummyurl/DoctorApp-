package com.bfurns.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.bfurns.R;
import com.bfurns.application.AppClass;
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

public class PostMail extends AppCompatActivity {
    EditText to, from, m_title, m_description, Doctor_to, Doctor_from, Doctor_title, Doctor_description;
    Button send;
    String p_name, p_title, p_time, p_description, d_name, p_to, p_from, reply_id, dateTi, p_n;
    String m_to, m_from, mytitle, mydescription, chat_type, d_to, d_from;
    RelativeLayout parent;
    String toDoct_name,FromDoctor_name,doctor_id,chat_doct_id;
    private static final String TAG = PostMail.class.getSimpleName();






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_mail);

        Intent intent=getIntent();
        FromDoctor_name=AppClass.getUsername();
        doctor_id= AppClass.getuserId();
        toDoct_name=intent.getStringExtra(MyPreferences.DOCTOR_NAME);
        chat_doct_id=intent.getStringExtra(MyPreferences.CHAT_DOCTOR_ID);

        setUpview();
    }

    private void setUpview() {

     Toolbar   toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Compose");

        to = (EditText) findViewById(R.id.namee);
        to.setText(toDoct_name);
        from = (EditText) findViewById(R.id.from);
        from.setText(FromDoctor_name);
        m_title = (EditText) findViewById(R.id.titleee);
        m_description = (EditText) findViewById(R.id.descriptione);
        send = (Button) findViewById(R.id.send);
        parent = (RelativeLayout) findViewById(R.id.relative);
        to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PostMail.this,DoctorListActivity.class);
                intent.putExtra(MyPreferences.DOCTOR_ID, doctor_id);
                intent.putExtra(MyPreferences.From_doctor, FromDoctor_name);
                startActivity(intent);
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendmail();
            }
        });
    }

    private void sendmail() {

        m_to = to.getText().toString();
        m_from = from.getText().toString();
        mytitle = m_title.getText().toString();
        mydescription = m_description.getText().toString();


        if (m_to.equalsIgnoreCase("")) {

            MyUtility.showSnack(parent, "Enter Doctor Name");
        } else if (m_from.equalsIgnoreCase("")) {
            MyUtility.showSnack(parent, "Enter Doctor Name");


        } else if (mytitle.equalsIgnoreCase("")) {
            MyUtility.showSnack(parent, "Enter Subject");

        } else if (mydescription.equalsIgnoreCase("")) {
            MyUtility.showSnack(parent, "Enter Description");

        } else {

            if (MyUtility.isConnected(this)) {

                    callApi();




            } else {
                MyUtility.showSnack(parent, MyUtility.INTERNET_ERROR);

            }
        }


    }

    private void callApi() {


        Utility.showProgressDialog(this, "Please wait..");
        Utility.progressDialog.show();

        List<NameValuePair> params = new ArrayList<>();


        params.add(new BasicNameValuePair("to_doct_name", chat_doct_id));
        params.add(new BasicNameValuePair("from_doct_name", doctor_id));
        params.add(new BasicNameValuePair("title", mytitle));
        params.add(new BasicNameValuePair("description", mydescription));


        GetData getData = new GetData(params);
        getData.setResultListner(new GetData.ResultListner() {

            int success = 0;
            private String id, title, description, p_from, phone, pto, image, mobileVerify, emailVerify, otp, socialType, socialId;

            @Override
            public void success(JSONObject jsonObject) {

                if (Utility.progressDialog.isShowing()) {
                    Utility.progressDialog.dismiss();
                }

                try {

                    success = jsonObject.getInt("responce");

                    Log.e(TAG, "Status:" + success);

                    if (success == 1) {

                        JSONArray arr = jsonObject.getJSONArray("query");
                        for (int i = 0; i < arr.length(); i++) {

                            JSONObject json = arr.getJSONObject(i);

                            id = json.getString("id");
                            p_from = json.getString("d_from");
                            pto = json.getString("d_to");
                            title = json.getString("title");
                            description = json.getString("description");

                        }


                        if (!id.isEmpty()) {
                            finish();
                            Utility.showSnackbar(parent, getString(R.string.success));
                            // clearFields();//.....call clear field...
                            Intent intent = new Intent(PostMail.this, ConsultActivity.class);
                            intent.putExtra(MyPreferences.DOCTOR_ID, p_from);
                            startActivity(intent);

                        } else {
                            Utility.showSnackbar(parent, "Failed to store this session");
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
        getData.execute(URLListner.BASEURL + URLListner.post_doctor_query);



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
