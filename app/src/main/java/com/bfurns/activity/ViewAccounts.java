package com.bfurns.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.bfurns.R;
import com.bfurns.adapter.MyAcccountsAdapter;
import com.bfurns.adapter.SummeryAdapter;
import com.bfurns.model.AppointementModel;
import com.bfurns.model.BillModel;
import com.bfurns.model.UserModel;
import com.bfurns.utility.MyPreferences;
import com.bfurns.utility.MyUtility;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ViewAccounts extends AppCompatActivity {

    private ArrayList<UserModel> arrayList;
    private ListAdapter listAdapter;
    ListView listView;
    private ArrayList<String> stringArrayList;
    String email, name, id;
    private RecyclerView recyclerView, recyclerView1;
    private MyAcccountsAdapter appointmentAdapter;
    RecyclerView.LayoutManager layoutManager, layoutmanager1;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_accounts);


        SharedPreferences sharedPreferences = getSharedPreferences(MyPreferences.My_PREFRENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        email = sharedPreferences.getString(MyPreferences.USER_EMAIL, "");
        name = sharedPreferences.getString(MyPreferences.USER_NAME, "");
        id = sharedPreferences.getString(MyPreferences.USER_ID, "");

        setUpview();

    }

    private void setUpview() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("My Accounts");


        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        arrayList = new ArrayList<UserModel>();
        stringArrayList = new ArrayList<String>();

        UserModel userModel = new UserModel();
        userModel.setName(name);
        userModel.setEmail(email);
        userModel.setUser(id);
        arrayList.add(userModel);


        Type listType = new TypeToken<List<UserModel>>() {
        }.getType();
        //convert list to string
        String listStr = new Gson().toJson(arrayList, listType);
        // convert string to list
       // List<UserModel> ObjectList = new Gson().fromJson(listStr, listType);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        relativeLayout = (RelativeLayout) findViewById(R.id.parent);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        LinearLayoutManager verticalLayoutmanager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(verticalLayoutmanager);
        recyclerView.setHasFixedSize(true);
        //List<String> input = new ArrayList<>();

        List<UserModel> ObjectList = new Gson().fromJson(listStr, listType);
        appointmentAdapter = new MyAcccountsAdapter(ObjectList);
        recyclerView.setAdapter(appointmentAdapter);

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
