package com.bfurns.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bfurns.R;
import com.bfurns.application.AppClass;
import com.bfurns.utility.GetData;
import com.bfurns.utility.MyPreferences;
import com.bfurns.utility.MyUtility;
import com.bfurns.utility.URLListner;
import com.bfurns.utility.Utility;
import com.bfurns.utility.Validations;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ConsultReplyActivity extends AppCompatActivity implements View.OnClickListener {

    TextView name, title, message, dateTime, To,label,label1,mme;
    String p_name, p_title, p_time, p_description, d_name, p_to, p_from, reply_id, dateTi, p_n;
    Button reply, send, d_send;
    String id;
    CardView c1, c2;
    LinearLayout relativeLayout, view_message, relatived;
    RelativeLayout parent;
    int myflag;
    EditText to, from, m_title, m_description, Doctor_to, Doctor_from, Doctor_title, Doctor_description;
    String m_to, m_from, mytitle, mydescription, chat_type, d_to, d_from,flag,read;
    private static final String TAG = ConsultReplyActivity.class.getSimpleName();

    TextView dp_name, doctor_title, doctor_discription, d_time, doctor_name, patient_title, patient_discription, patient_time, patient_name, pd_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult_reply);

        Intent intent = getIntent();
        p_name = intent.getStringExtra(MyPreferences.PATIENT_NAME);
        p_title = intent.getStringExtra(MyPreferences.TITLE);
        p_time = intent.getStringExtra(MyPreferences.DATE);
        p_description = intent.getStringExtra(MyPreferences.DESCRIPTION);
        id = AppClass.getqueryId();
        d_name = intent.getStringExtra(MyPreferences.DOCTOR_NAME);
        p_from = intent.getStringExtra(MyPreferences.P_TO);
        p_to = intent.getStringExtra(MyPreferences.P_FROM);
        reply_id = intent.getStringExtra(MyPreferences.REPLY_ID);
        chat_type = intent.getStringExtra(MyPreferences.CCHAT_TYPE);
        d_to = intent.getStringExtra(MyPreferences.D_from);
        d_from = intent.getStringExtra(MyPreferences.D_to);
        flag = intent.getStringExtra(MyPreferences.flag);
        read = intent.getStringExtra(MyPreferences.read);



        setUpview();
    }

    private void setUpview() {


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Inbox");
        relativeLayout = (LinearLayout) findViewById(R.id.relative);
        parent = (RelativeLayout) findViewById(R.id.parent);
        view_message = (LinearLayout) findViewById(R.id.linearlayout);

        //Display MEsaage details
        name = (TextView) findViewById(R.id.name);
        title = (TextView) findViewById(R.id.title);
        message = (TextView) findViewById(R.id.description);
        dateTime = (TextView) findViewById(R.id.dateTime);
        To = (TextView) findViewById(R.id.To);
        mme = (TextView) findViewById(R.id.mmm);


        reply = (Button) findViewById(R.id.reply);
        name.setText("From" +" "+":" +p_name);
        title.setText("Subject"+" "+":"+p_title);
        message.setText(p_description);
        dateTime.setText(p_time);
        To.setText("To" +" "+":"+d_name);
        mme.setText(p_name + " "+"Message");

        //Doctor to patient reply
        c2 = (CardView) findViewById(R.id.doctorreply);
        to = (EditText) findViewById(R.id.namee);
        from = (EditText) findViewById(R.id.from);
        m_title = (EditText) findViewById(R.id.titleee);
        m_description = (EditText) findViewById(R.id.descriptione);
        label1 = (TextView) findViewById(R.id.hhhh);


        send = (Button) findViewById(R.id.send);
        send.setOnClickListener(this);
        to.setText(p_name);
        from.setText(d_name);
        m_title.setText(p_title);


        //doctor privious reply
        c1 = (CardView) findViewById(R.id.patientreply);
        doctor_name = (TextView) findViewById(R.id.ddde);
        dp_name = (TextView) findViewById(R.id.ddd);
        doctor_title = (TextView) findViewById(R.id.ee);
        doctor_discription = (TextView) findViewById(R.id.gg);
        d_time = (TextView) findViewById(R.id.ff);
        label = (TextView) findViewById(R.id.gggg);


        //patient Previous reply
        patient_name = (TextView) findViewById(R.id.aa);
        patient_discription = (TextView) findViewById(R.id.dd);
        patient_title = (TextView) findViewById(R.id.bb);
        patient_time = (TextView) findViewById(R.id.cc);
        pd_name = (TextView) findViewById(R.id.aaa);



        if (flag.equals("1") ){
            reply.setVisibility(View.GONE);
        }else {
            reply.setVisibility(View.VISIBLE);


        }

        reply.setOnClickListener(this);

        if (chat_type.equals("1")) {
            getDoctorToDaoctorPriviousReply();//doctor to doctor
            get_doctor_next_chat();//doctor to doctor

        } else {
            getdoctorPreviousReply();//DoctorTopatient
            getpatientReply();//patient to doctor

        }

        getreadflag();
    }


    private void getreadflag() {


        List<NameValuePair> params = new ArrayList<>();


        params.add(new BasicNameValuePair("id", id));
        params.add(new BasicNameValuePair("chat_type", chat_type));
        GetData getData = new GetData(params);
        getData.setResultListner(new GetData.ResultListner() {

            int success = 0;
            private String reply_id, title, description, p_from, phone, pto, image, mobileVerify, emailVerify, otp, socialType, socialId;

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

                            read = json.getString("q_read");

                        }


                    }
                    if (success != 1) {
                           Utility.showSnackbar(parent, getString(R.string.no_chats));
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
        getData.execute(URLListner.BASEURL + URLListner.update_read_query);



    }

    private void get_doctor_next_chat() {


        Utility.showProgressDialog(this, "Please wait..");
        Utility.progressDialog.show();
        Utility.progressDialog.dismiss();


        List<NameValuePair> params = new ArrayList<>();


        params.add(new BasicNameValuePair("id", id));
        params.add(new BasicNameValuePair("q_reply_id", reply_id));
        params.add(new BasicNameValuePair("d_to", d_from));
        GetData getData = new GetData(params);
        getData.setResultListner(new GetData.ResultListner() {

            int success = 0;
            private String reply_id, title, description, p_from, phone, pto, image, mobileVerify, emailVerify, otp, socialType, socialId;

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
                        if (arr.length()==0){
                            c1.setVisibility(View.GONE);
                            c2.setVisibility(View.GONE);

                        }
                       else {

                           // c1.setVisibility(View.VISIBLE);
                            c2.setVisibility(View.VISIBLE);
                            for (int i = 0; i < arr.length(); i++) {

                                JSONObject json = arr.getJSONObject(i);

                                reply_id = json.getString("id");
                                p_from = json.getString("d_from");
                                pto = json.getString("d_to");
                                title = json.getString("title");
                                description = json.getString("description");
                                dateTi = json.getString("date_time");
                                d_name = json.getString("doct_to");
                                p_n = json.getString("doct_from");

                                doctor_name.setText(d_name);
                                doctor_title.setText(title);
                                doctor_discription.setText(description);
                                d_time.setText(dateTi);
                                dp_name.setText(p_n);
                                label.setText("Previous" + " " + p_n + " " + "chat");
                                label1.setText("Previous my chat");

                                Utility.progressDialog.dismiss();


                            }
                        }


                    }
                    if (success != 1) {
                        Utility.showSnackbar(parent, getString(R.string.no_chats));
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
        getData.execute(URLListner.BASEURL + URLListner.get_doctor_next_chat);


    }

    private void getDoctorToDaoctorPriviousReply() {

        Utility.showProgressDialog(this, "Please wait..");
        Utility.progressDialog.show();
        Utility.progressDialog.dismiss();


        List<NameValuePair> params = new ArrayList<>();


        params.add(new BasicNameValuePair("id", id));
        params.add(new BasicNameValuePair("q_reply_id", reply_id));
        params.add(new BasicNameValuePair("d_to", d_to));
        GetData getData = new GetData(params);
        getData.setResultListner(new GetData.ResultListner() {

            int success = 0;
            private String reply_id, title, description, p_from, doct_to,doct_from, pto, image, mobileVerify, emailVerify, otp, socialType, socialId;

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
                        if (arr.length()==0){

                            c2.setVisibility(View.GONE);
                            c1.setVisibility(View.GONE);


                        }

                      //  c1.setVisibility(View.VISIBLE);
                        c2.setVisibility(View.VISIBLE);
                        for (int i = 0; i < arr.length(); i++) {

                            JSONObject json = arr.getJSONObject(i);

                            reply_id = json.getString("id");
                            p_from = json.getString("d_from");
                            pto = json.getString("d_to");
                            title = json.getString("title");
                            description = json.getString("description");
                            dateTi = json.getString("date_time");
                            d_name = json.getString("doct_to");
                            p_n = json.getString("doct_from");

                            patient_name.setText("From"+" "+":" +d_name);
                            patient_title.setText("Subject"+" "+":"+ title);
                            patient_discription.setText(description);
                            patient_time.setText(dateTi);
                            pd_name.setText("To"+" "+ ":"+p_n);
                            label.setText("Previous" +" "+ p_n+" " +"chat");
                            label1.setText("Previous my chat");

                            Utility.progressDialog.dismiss();


                        }


                    }
                    if (success != 1) {
                        Utility.showSnackbar(parent, getString(R.string.no_chats));
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
        getData.execute(URLListner.BASEURL + URLListner.get_doctor_to_previous_chat);


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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reply:
                relativeLayout.setVisibility(View.VISIBLE);
                view_message.setVisibility(View.GONE);
               // c1.setVisibility(View.VISIBLE);
               // c2.setVisibility(View.VISIBLE);


                break;

            case R.id.send:
                m_to = to.getText().toString();
                m_from = from.getText().toString();
                mytitle = m_title.getText().toString();
                mydescription = m_description.getText().toString();


                if (m_to.equalsIgnoreCase("")) {

                    MyUtility.showSnack(parent, "Enter Patient Name");
                } else if (m_from.equalsIgnoreCase("")) {
                    MyUtility.showSnack(parent, "Enter Doctor Name");


                } else if (mytitle.equalsIgnoreCase("")) {
                    MyUtility.showSnack(parent, "Enter Subject");

                } else if (mydescription.equalsIgnoreCase("")) {
                    MyUtility.showSnack(parent, "Enter Description");

                } else {

                    if (MyUtility.isConnected(this)) {

                        if (chat_type.equals("1")) {
                            callApi();

                        } else {
                            CallPatientApi();
                        }


                    } else {
                        MyUtility.showSnack(parent, MyUtility.INTERNET_ERROR);

                    }
                }


                break;


        }

    }

    private void CallPatientApi() {

        Utility.showProgressDialog(this, "Please wait..");
        Utility.progressDialog.show();
        Utility.progressDialog.dismiss();


        List<NameValuePair> params = new ArrayList<>();


        params.add(new BasicNameValuePair("q_id", id));
        params.add(new BasicNameValuePair("p_to", p_to));

        //patient_name
        params.add(new BasicNameValuePair("p_from", p_from));
        //doctor_name
        params.add(new BasicNameValuePair("title", mytitle));
        params.add(new BasicNameValuePair("description", mydescription));
        params.add(new BasicNameValuePair("chat_type", chat_type));


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
                            p_from = json.getString("p_from");
                            pto = json.getString("p_to");
                            title = json.getString("title");
                            description = json.getString("description");

                        }


                        if (!id.isEmpty()) {
                            finish();
                            Utility.showSnackbar(parent, getString(R.string.success));
                            Intent intent = new Intent(ConsultReplyActivity.this, ConsultActivity.class);
                            intent.putExtra(MyPreferences.DOCTOR_ID, p_from);
                           /* intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);*/
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
        getData.execute(URLListner.BASEURL + URLListner.reply_patient_query);

    }

    private void callApi() {

        Utility.showProgressDialog(this, "Please wait..");
        Utility.progressDialog.show();
        Utility.progressDialog.dismiss();


        List<NameValuePair> params = new ArrayList<>();


        params.add(new BasicNameValuePair("q_id", id));
        params.add(new BasicNameValuePair("p_to", d_to));
        params.add(new BasicNameValuePair("p_from", d_from));
        params.add(new BasicNameValuePair("title", mytitle));
        params.add(new BasicNameValuePair("description", mydescription));
        params.add(new BasicNameValuePair("chat_type", chat_type));


        GetData getData = new GetData(params);
        getData.setResultListner(new GetData.ResultListner() {

            int success = 0;
            private String id, title, description,flag, p_from, phone, pto, image, mobileVerify, emailVerify, otp, socialType, socialId;

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
                            flag = json.getString("flag");


                        }


                        if (!id.isEmpty()) {
                            finish();
                            Utility.showSnackbar(parent, getString(R.string.success));
                            // clearFields();//.....call clear field...
                            Intent intent = new Intent(ConsultReplyActivity.this, ConsultActivity.class);
                            intent.putExtra(MyPreferences.DOCTOR_ID, p_from);
                            intent.putExtra(MyPreferences.flag, flag);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
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
        getData.execute(URLListner.BASEURL + URLListner.reply_patient_query);

    }


    private void getdoctorPreviousReply() {

        Utility.showProgressDialog(this, "Please wait..");
        Utility.progressDialog.show();
        Utility.progressDialog.dismiss();


        List<NameValuePair> params = new ArrayList<>();


        params.add(new BasicNameValuePair("id", id));
        params.add(new BasicNameValuePair("q_reply_id", reply_id));

        //patient_name
        params.add(new BasicNameValuePair("p_to", p_to));
        //doctor_name
        GetData getData = new GetData(params);
        getData.setResultListner(new GetData.ResultListner() {

            int success = 0;
            private String reply_id, title, description, p_from, phone, pto, image, mobileVerify, emailVerify, otp, socialType, socialId;

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
                        if (arr.length()==0){

                            c2.setVisibility(View.GONE);
                            c1.setVisibility(View.GONE);


                        }else {

                            c1.setVisibility(View.VISIBLE);
                            c2.setVisibility(View.VISIBLE);
                            for (int i = 0; i < arr.length(); i++) {

                                JSONObject json = arr.getJSONObject(i);

                                reply_id = json.getString("id");
                                p_from = json.getString("p_from");
                                pto = json.getString("p_to");
                                title = json.getString("title");
                                description = json.getString("description");
                                dateTi = json.getString("date_time");
                                d_name = json.getString("doct_name");
                                p_n = json.getString("user_fullname");

                                doctor_name.setText("From" + " " + ":" + d_name);
                                doctor_title.setText("Subject" + " " + ":" + title);
                                doctor_discription.setText(description);
                                d_time.setText(dateTi);
                                dp_name.setText("To" + " " + ":" + p_n);
                                label.setText("Previous" + " " + d_name + " " + "chat");
                                label1.setText("Previous my chat");


                            }
                        }


                    }
                    if (success != 1) {
                        Utility.showSnackbar(parent, getString(R.string.no_chats));
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
        getData.execute(URLListner.BASEURL + URLListner.get_doctor_reply);


    }

    private void getpatientReply() {


        Utility.showProgressDialog(this, "Please wait..");
        Utility.progressDialog.show();
        Utility.progressDialog.dismiss();


        List<NameValuePair> params = new ArrayList<>();


        params.add(new BasicNameValuePair("id", id));
        params.add(new BasicNameValuePair("q_reply_id", reply_id));

        //patient_name
        params.add(new BasicNameValuePair("p_to", p_from));
        //doctor_name
        GetData getData = new GetData(params);
        getData.setResultListner(new GetData.ResultListner() {

            int success = 0;
            private String reply_id, title, description, p_from, phone, pto, image, mobileVerify, emailVerify, otp, socialType, socialId;

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
                        if (arr.length()==0){

                            c2.setVisibility(View.GONE);
                            c1.setVisibility(View.GONE);


                        }else {

                            c1.setVisibility(View.VISIBLE);
                            c2.setVisibility(View.VISIBLE);
                            for (int i = 0; i < arr.length(); i++) {

                                JSONObject json = arr.getJSONObject(i);

                                reply_id = json.getString("id");
                                p_from = json.getString("p_from");
                                pto = json.getString("p_to");
                                title = json.getString("title");
                                description = json.getString("description");
                                dateTi = json.getString("date_time");
                                d_name = json.getString("doct_name");
                                p_n = json.getString("user_fullname");

                                patient_name.setText(d_name);
                                patient_title.setText(title);
                                patient_discription.setText(description);
                                patient_time.setText(dateTi);
                                pd_name.setText(p_n);
                                label.setText("Previous" + " " + d_name + " " + "chat");
                                label1.setText("Previous my chat");


                            }
                        }

                    }
                    if (success != 1) {
                        Utility.showSnackbar(parent, getString(R.string.no_chats));
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
        getData.execute(URLListner.BASEURL + URLListner.get_patient_previous_reply);


    }
}
