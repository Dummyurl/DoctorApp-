package com.bfurns.ziffylink;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bfurns.R;
import com.bfurns.utility.GetData;
import com.bfurns.utility.JSONParser;
import com.bfurns.utility.MyUtility;
import com.bfurns.utility.URLListner;
import com.bfurns.utility.Utility;
import com.bfurns.utility.Validations;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mahesh on 21/08/16.
 */
public class ForgotActivity extends AppCompatActivity {
    private Toolbar toolbar;

    private Button forgot;
    private EditText etext;
    private LinearLayout parent;
    private String email;
    private ProgressDialog pDialog;
    private static final String TAG = ForgotActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        setUpViews();
    }

    private void setUpViews() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Forgot Password");

        forgot = (Button) findViewById(R.id.forgot);
        parent = (LinearLayout) findViewById(R.id.parent);
        etext = (EditText) findViewById(R.id.email);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);


        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyUtility.hideKeyboard(etext, ForgotActivity.this);

                forgotValidation();


            }
        });


        etext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    forgotValidation();


                }
                return false;
            }
        });
    }

    private void forgotValidation() {

        MyUtility.hideKeyboard(etext, this);

        email = etext.getText().toString().trim();

        if (email.equalsIgnoreCase("")) {

            MyUtility.showSnack(parent, "Enter email");

        } else if (!email.matches(MyUtility.emailPattern)) {


            MyUtility.showSnack(parent, Validations.ENTER_VALID_EMAIL);
        } else {

            if (MyUtility.isConnected(ForgotActivity.this)) {

                setForgotPassword();

                //   new SetForgot().execute();

            } else {

                MyUtility.showToast(MyUtility.INTERNET_ERROR, ForgotActivity.this);

            }

        }
    }

    //.........Send password on users email.......
    private void setForgotPassword() {

        Utility.showProgressDialog(this, "Please wait..");
        Utility.progressDialog.show();

        List<NameValuePair> params = new ArrayList<>();

        params.add(new BasicNameValuePair("email", email));

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
                    Log.e(TAG, "Error:" + success);

                    if (success == 1) {
                        Utility.showSnackbar(parent, getString(R.string.reset_password_link_send_on_email));

                        Snackbar snackbar = Snackbar
                                .make(parent, R.string.reset_password_link_send_on_email, Snackbar.LENGTH_INDEFINITE)
                                .setAction("OK", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Utility.getIntent(ForgotActivity.this, LoginActivity.class);
                                    }
                                });

                        snackbar.show();
                    } else{
                        Utility.showAlert(ForgotActivity.this, jsonObject.getString("error"));
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
        getData.execute(URLListner.BASEURL + URLListner.forgot_password_mail);

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
