package com.bfurns.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bfurns.R;
import com.bfurns.application.AppClass;
import com.bfurns.fragment.FragmentDrawer;
import com.bfurns.fragment.MainFragment;
import com.bfurns.utility.GetData;
import com.bfurns.utility.MyPreferences;
import com.bfurns.utility.Preferences;
import com.bfurns.utility.URLListner;
import com.bfurns.utility.Utility;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Mahesh on 18/07/16.
 */


public class HomeActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener
{
    private static String TAG = HomeActivity.class.getSimpleName();
    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    final CharSequence[] options = {"Call", "Email"};
    protected int i = 0;
    SharedPreferences sharedpreferences;
    String back = "";
    FragmentManager fragmentManager;
    Fragment fragment = null;
    String clinic_id, user_id, email, count, u_name,clinic_name,image,merge_doctor_id,status;
    private EditText editLanguage;


    //   GoogleApiClient mGoogleApiClient;
    //   boolean mSignInClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_drawer);

        Intent intent = getIntent();
        status=intent.getStringExtra(MyPreferences.COMMAN_ACCOUNT_STAUS);
        clinic_id = AppClass.getclinicId();
        user_id = AppClass.getuserId();
        email =AppClass.getemail();
        u_name = AppClass.getUsername();
        clinic_name = AppClass.getclinic_name();
        image =AppClass.getImage();
        merge_doctor_id=AppClass.getmerge_doctor_id();
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setTitle("");
        }

        sharedpreferences = getSharedPreferences(Preferences.MyPREFERENCES, Context.MODE_PRIVATE);
        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);
        displayView(0);


    }

    @Override
    protected void onStart() {
        super.onStart();
        CallApi();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_logout:

                logout();

                return true;


            case R.id.action_cart:


                return true;

        }

        return false;

    }


    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new MainFragment();
                title = "Home";
                break;


            default:


                break;
        }

        if (fragment != null) {
            fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.container_body, fragment);
            if (position == 0) {


            } else {

                fragmentTransaction.addToBackStack(null);

            }

            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }


    @Override
    public void onBackPressed() {

        int fragments = getFragmentManager().getBackStackEntryCount();
        if (fragments == 1) {
            // make layout invisible since last fragment will be removed

            if (back.equals("")) {

                Toast.makeText(this, "Press back button again to exit", Toast.LENGTH_LONG).show();
                back = "two";

            } else if (back.equals("two")) {

                finish();
            }


        }


        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void onDestroy() {
        super.onDestroy();
    }

    private void logout() {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are your sure?").setTitle("Sign Out");

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {


            }
        });
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {


                SharedPreferences.Editor editor = sharedpreferences.edit();

                editor.clear();
                editor.commit();

                if (!sharedpreferences.contains(Preferences.userId)) {

                    //logout
                }
            }
        });
        AlertDialog alert = builder.create();
        alert.show();


    }

    private void CallApi() {


        List<NameValuePair> params=new ArrayList<>();
        params.add(new BasicNameValuePair("doct_id",AppClass.getuserId()));

        Log.e("ID",AppClass.getuserId());

        GetData getData = new GetData(params);
        getData.setResultListner(new GetData.ResultListner() {

           private String id,username,phone,email1,image,merge_doctor_id,clinic_id;
            @Override
            public void success(JSONObject jsonObject) {


                try{

                   int success=jsonObject.getInt("responce");

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
                            merge_doctor_id = jsonObject1.getString("doct_id");
                        }

                        AppClass.setsession(id,email1,username,phone,image,clinic_id);
                        AppClass.setsessionmerge(merge_doctor_id);

                    }

                }catch (Exception e){

                    e.printStackTrace();
                }
            }

            @Override
            public void fail() {
            }
        });
        getData.execute(URLListner.BASEURL+URLListner.getDoctorData);

    }



}
