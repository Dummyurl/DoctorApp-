package com.bfurns.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.bfurns.R;
import com.bfurns.utility.GetData;
import com.bfurns.utility.JSONParser;
import com.bfurns.utility.MultiSelectionSpinner;
import com.bfurns.utility.MyPreferences;
import com.bfurns.utility.MyUtility;
import com.bfurns.utility.Preferences;
import com.bfurns.utility.URLListner;
import com.bfurns.utility.Utility;
import com.bfurns.utility.Validations;
import com.bfurns.ziffylink.ForgotActivity;
import com.bfurns.ziffylink.LoginActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mahesh on 21/08/16.
 */
public class StepOneActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, MultiSelectionSpinner.OnMultipleItemsSelectedListener {


    private Button forgot, login;
    private Toolbar toolbar;
    private static final String TAG = StepOneActivity.class.getSimpleName();


    Spinner duration, days, totime, from_time;
    private RelativeLayout parent;
    private EditText eemail, epassword, c_password;
    private String email, password, confirm, totimes, fromtimes, myduration, mydays;

    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_one);
        setUpViews();
    }

    private void setUpViews() {

        sharedPreferences = getSharedPreferences(Preferences.MyPREFERENCES, Context.MODE_PRIVATE);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Step 1/4");
        parent = (RelativeLayout) findViewById(R.id.parent);

        eemail = (EditText) findViewById(R.id.email);
        epassword = (EditText) findViewById(R.id.password);
        c_password = (EditText) findViewById(R.id.c_pass);
        duration = (Spinner) findViewById(R.id.duration);
        totime = (Spinner) findViewById(R.id.tospiner);
        from_time = (Spinner) findViewById(R.id.fromspinner);
        login = (Button) findViewById(R.id.next);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginValidation();

            }
        });
        duration.setOnItemSelectedListener(this);
        totime.setOnItemSelectedListener(this);
        from_time.setOnItemSelectedListener(this);

        String[] array = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        MultiSelectionSpinner multiSelectionSpinner = (MultiSelectionSpinner) findViewById(R.id.mySpinner);
        multiSelectionSpinner.setItems(array);
        multiSelectionSpinner.setSelection(new int[]{1, 3});
        multiSelectionSpinner.setListener(this);


        List<String> categories = new ArrayList<String>();
        categories.add("5");
        categories.add("10");
        categories.add("15");
        categories.add("20");
        categories.add("25");
        categories.add("30");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        duration.setAdapter(dataAdapter);


        List<String> TotimeCate = new ArrayList<String>();
        TotimeCate.add("2:00 PM");
        TotimeCate.add("2:30 PM");
        TotimeCate.add("3:00 PM");
        TotimeCate.add("3:30 PM");
        TotimeCate.add("4:00 PM");
        TotimeCate.add("4:30 PM");
        TotimeCate.add("5:00 PM");
        TotimeCate.add("5:30 PM");
        TotimeCate.add("6:00 PM");
        TotimeCate.add("6:30 PM");
        TotimeCate.add("7:00 PM");
        TotimeCate.add("7:30 PM");
        TotimeCate.add("8:00 PM");
        TotimeCate.add("8:30 PM");
        TotimeCate.add("9:00 PM");
        TotimeCate.add("9:30 PM");
        TotimeCate.add("10:00 PM");


        ArrayAdapter<String> TotimeCateAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, TotimeCate);
        TotimeCateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        totime.setAdapter(TotimeCateAdapter);

        List<String> FromtimeCate = new ArrayList<String>();
        FromtimeCate.add("6:00 AM");
        FromtimeCate.add("6:30 AM");
        FromtimeCate.add("7:00 AM");
        FromtimeCate.add("7:30 AM");
        FromtimeCate.add("8:00 AM");
        FromtimeCate.add("8:30 AM");
        FromtimeCate.add("9:00 AM");
        FromtimeCate.add("9:30 AM");
        FromtimeCate.add("10:00 AM");
        FromtimeCate.add("10:30 AM");
        FromtimeCate.add("11:00 AM");
        FromtimeCate.add("11:30 AM");
        FromtimeCate.add("12:00 PM");


        ArrayAdapter<String> FromtimeCateAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, FromtimeCate);
        FromtimeCateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        from_time.setAdapter(FromtimeCateAdapter);

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.duration:


                myduration = duration.getSelectedItem().toString();

                // do stuffs with you spinner 1
                break;
            case R.id.tospiner:

                totimes = totime.getSelectedItem().toString();

                // do stuffs with you spinner 2
                break;
            case R.id.fromspinner:

                fromtimes = from_time.getSelectedItem().toString();

                break;
            default:
                break;


        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void loginValidation() {

        MyUtility.hideKeyboard(c_password, StepOneActivity.this);

        email = eemail.getText().toString().trim();
        password = epassword.getText().toString().trim();
        confirm = c_password.getText().toString().trim();


        if (email.equalsIgnoreCase("")) {


            MyUtility.showSnack(parent, Validations.ENTER_EMAIL);

        } else if (!email.matches(MyUtility.emailPattern)) {


            MyUtility.showSnack(parent, Validations.ENTER_VALID_EMAIL);

        } else if (password.equalsIgnoreCase("")) {

            MyUtility.showSnack(parent, Validations.ENTER_PASSWORD);

        } else if (confirm.equalsIgnoreCase("")) {

            MyUtility.showSnack(parent, "Enter Confirm Password");

        } else if (!password.equals(confirm)) {

            MyUtility.showSnack(parent, " Password not match");

        } else {


            if (MyUtility.isConnected(this)) {

                CallApi();


            } else {

                MyUtility.showSnack(parent, MyUtility.INTERNET_ERROR);
            }

        }

    }

    private void CallApi() {
        Utility.showProgressDialog(this, "Please wait..");
        Utility.progressDialog.show();

        List<NameValuePair> params = new ArrayList<>();


        params.add(new BasicNameValuePair("user_email", email));
        params.add(new BasicNameValuePair("user_password", password));
        params.add(new BasicNameValuePair("conf_password", confirm));
        params.add(new BasicNameValuePair("app_duration", myduration));
        params.add(new BasicNameValuePair("start_con_time", fromtimes));
        params.add(new BasicNameValuePair("end_con_time", totimes));
        params.add(new BasicNameValuePair("day", mydays));

        GetData getData = new GetData(params);
        getData.setResultListner(new GetData.ResultListner() {

            int success = 0;
            private String id, clinic_id, username, phone, email1, image, mobileVerify, emailVerify, otp, socialType, socialId;

            @Override
            public void success(JSONObject jsonObject) {

                if (Utility.progressDialog.isShowing()) {
                    Utility.progressDialog.dismiss();
                }

                try {

                    success = jsonObject.getInt("responce");

                    Log.e(TAG, "Status:" + success);

                    if (success == 1) {

                        JSONArray arr = jsonObject.getJSONArray("data");
                        for (int i = 0; i < arr.length(); i++) {

                            JSONObject json = arr.getJSONObject(i);

                            id = json.getString("user_id");
                            username = json.getString("bus_title");
                            email1 = json.getString("bus_email");
                            clinic_id = json.getString("bus_id");

                        }

                        SharedPreferences sharedPreferences = getSharedPreferences(MyPreferences.My_PREFRENCES, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putString(MyPreferences.USER_ID, id);
                        editor.putString(MyPreferences.CLINIC_ID, clinic_id);
                        editor.putString(MyPreferences.USER_NAME, username);
                        editor.putString(MyPreferences.USER_EMAIL, email1);
                        editor.putString(MyPreferences.USER_PHONE, phone);
                        editor.putString(MyPreferences.USER_IMAGE, image);

                        editor.commit();

                        if (!MyPreferences.CLINIC_ID.isEmpty()) {
                            finish();
                            Utility.showSnackbar(parent, getString(R.string.success));
                            // clearFields();//.....call clear field...
                            Intent intent = new Intent(StepOneActivity.this, StepTwoActivity.class);
                            intent.putExtra(MyPreferences.USER_ID, id);
                            intent.putExtra(MyPreferences.CLINIC_ID, clinic_id);
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
        getData.execute(URLListner.BASEURL + URLListner.register_clinic);


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

    }


    @Override
    public void selectedIndices(List<Integer> indices) {

    }

    @Override
    public void selectedStrings(List<String> strings) {
      //  Toast.makeText(this, strings.toString(), Toast.LENGTH_LONG).show();
        mydays = strings.toString();


    }
}

