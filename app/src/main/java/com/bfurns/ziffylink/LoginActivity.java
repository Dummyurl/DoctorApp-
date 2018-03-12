package com.bfurns.ziffylink;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bfurns.R;
import com.bfurns.activity.HomeActivity;
import com.bfurns.application.AppClass;
import com.bfurns.model.UserModel;
import com.bfurns.utility.GetData;
import com.bfurns.utility.MyUtility;
import com.bfurns.utility.Preferences;
import com.bfurns.utility.URLListner;
import com.bfurns.utility.Utility;
import com.bfurns.utility.Validations;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mahesh on 21/08/16.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    private Button forgot,signin;
    private Toolbar toolbar;

    private RelativeLayout parent;
    private EditText eemail,epassword;
    private String email,password;
    private static final String TAG = LoginActivity.class.getSimpleName();
    ArrayList<UserModel>arrayList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setUpViews();
    }

    private void setUpViews() {



       arrayList=new ArrayList<>();
        forgot=(Button)findViewById(R.id.forgot);
        forgot.setOnClickListener(this);



        parent=(RelativeLayout) findViewById(R.id.parent);
        eemail=(EditText)findViewById(R.id.email);
        epassword=(EditText)findViewById(R.id.password);
        signin=(Button)findViewById(R.id.login);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginValidation();

            }
        });

    }



    private void loginValidation() {

        MyUtility.hideKeyboard(epassword,LoginActivity.this);

        email = eemail.getText().toString().trim();
        password = epassword.getText().toString().trim();


        if (email.equalsIgnoreCase("")) {


            MyUtility.showSnack(parent, Validations.ENTER_EMAIL);

        } else if (!email.matches(MyUtility.emailPattern) ) {


            MyUtility.showSnack(parent, Validations.ENTER_VALID_EMAIL);

        } else if (password.equalsIgnoreCase("") ) {

            MyUtility.showSnack(parent, Validations.ENTER_PASSWORD);

        }  else {


            if(MyUtility.isConnected(this)){

                CallApi();



            }else{

                MyUtility.showSnack(parent,MyUtility.INTERNET_ERROR);
            }

        }

    }

    private void CallApi() {

        Utility.showProgressDialog(this,"Please wait..");
        Utility.progressDialog.show();

        List<NameValuePair> params=new ArrayList<>();


        params.add(new BasicNameValuePair("user_email",email));
        params.add(new BasicNameValuePair("user_password",password));

        GetData getData = new GetData(params);
        getData.setResultListner(new GetData.ResultListner() {

            int success=0;
            private String id,username,phone,email1,image,personal_id,mergeclinic_id,merge_doctor_id,clinic_id,Merge_id;
            @Override
            public void success(JSONObject jsonObject) {

                if(Utility.progressDialog.isShowing())
                {
                    Utility.progressDialog.dismiss();
                }

                try{

                    success=jsonObject.getInt("responce");

                    Log.e(TAG,"Status:"+success);

                    if(success==1)
                    {

                        JSONArray arr = jsonObject.getJSONArray("data");
                        for(int i=0;i<arr.length();i++)
                        {


                            JSONObject json = arr.getJSONObject(i);

                            id = json.getString("doct_id");
                            username = json.getString("doct_name");
                            email1 = json.getString("user_email");
                            phone = json.getString("doct_phone");
                            image = json.getString("doct_photo");
                            clinic_id = json.getString("bus_id");

                        }

                        JSONArray jsonArray = jsonObject.getJSONArray("merge_account");
                        for(int i=0;i<jsonArray.length();i++) {

                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            Merge_id = jsonObject1.getString("id");
                            merge_doctor_id = jsonObject1.getString("doct_id");
                            mergeclinic_id = jsonObject1.getString("bus_id");
                        }

                        AppClass.setsession(id,email1,username,phone,image,clinic_id);
                        AppClass.setsessionmerge(merge_doctor_id);



                        if (AppClass.getuserId().length()>0) {
                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);

                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }


                        else
                        {
                            Utility.showSnackbar(parent,"Failed to store this session");
                        }



                    }
                    if(success!=1)
                    {
                        Utility.showSnackbar(parent,"Invalid Credentials");
                    }

                }catch (Exception e){

                    e.printStackTrace();
                }
            }

            @Override
            public void fail() {

                if(Utility.progressDialog.isShowing())
                {
                    Utility.progressDialog.dismiss();
                }

                if(success!=1)
                {
                    Utility.showSnackbar(parent,getString(R.string.failed));
                }


            }
        });
        getData.execute(URLListner.BASEURL+URLListner.login);


    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.forgot:

                startActivity(new Intent(LoginActivity.this,ForgotActivity.class));

                break;


        }
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

