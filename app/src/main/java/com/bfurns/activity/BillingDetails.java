package com.bfurns.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bfurns.R;
import com.bfurns.adapter.ServiceAdapter;
import com.bfurns.model.DoctorStaffModel;
import com.bfurns.utility.GetData;
import com.bfurns.utility.MyPreferences;
import com.bfurns.utility.MyUtility;
import com.bfurns.utility.URLListner;
import com.bfurns.utility.Utility;
import com.bfurns.utility.Validations;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BillingDetails extends AppCompatActivity implements View.OnClickListener {

    EditText service, amount, discount;
    String bservise, bamount, bdiscount;
    Button add, More,mor1,mor2,mor3;
    Toolbar toolbar;
    RelativeLayout parent;
    String clinic_id,user_id,doctor_id;
    LinearLayout linearLayout,linearLayout1,linearLayout2,linearLayout3,linearLayout4;


    private RecyclerView recyclerView;
    private ArrayList<DoctorStaffModel> arrayList = new ArrayList<>();
    private ServiceAdapter appointmentAdapter;
    RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing_details);
        Intent intent = getIntent();
        clinic_id = intent.getStringExtra(MyPreferences.CLINIC_ID);
        user_id = intent.getStringExtra(MyPreferences.USER_ID);
        doctor_id = intent.getStringExtra(MyPreferences.DOCTOR_ID);

        setUpViews();
    }

    private void setUpViews() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Bill Generation");
        service = (EditText) findViewById(R.id.service);
        amount = (EditText) findViewById(R.id.amount);
        discount = (EditText) findViewById(R.id.discount);
        add = (Button) findViewById(R.id.add);
        More = (Button) findViewById(R.id.addmore);
        parent = (RelativeLayout) findViewById(R.id.parent);
        add.setOnClickListener(this);
        More.setOnClickListener(this);

        linearLayout=(LinearLayout)findViewById(R.id.linearlayout);
        linearLayout1=(LinearLayout)findViewById(R.id.linearlayout1);
        linearLayout2=(LinearLayout)findViewById(R.id.linearlayout2);
        linearLayout3=(LinearLayout)findViewById(R.id.linearlayout3);
        mor1=(Button)findViewById(R.id.addmore1);
        mor2=(Button)findViewById(R.id.addmore2);
        mor1.setOnClickListener(this);
        mor2.setOnClickListener(this);



        getApointements();




    }

    private void getApointements() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.add:
                MyUtility.hideKeyboard(discount, BillingDetails.this);
                MyUtility.hideKeyboard(amount, BillingDetails.this);

                bamount = amount.getText().toString().trim();
                bservise = service.getText().toString().trim();
                bdiscount = discount.getText().toString().trim();

                if (bservise.equalsIgnoreCase("")) {


                    MyUtility.showSnack(parent, Validations.ENTER_SERVICE);

                } else if (bamount.equalsIgnoreCase("")) {

                    MyUtility.showSnack(parent, Validations.ENTER_AMOUNT);

                } else if (bdiscount.equalsIgnoreCase("")) {

                    MyUtility.showSnack(parent, Validations.ENTER_DISCOUNT);


                } else {


                    if (MyUtility.isConnected(this)) {




                        CallApi();


                    } else {

                        MyUtility.showSnack(parent, MyUtility.INTERNET_ERROR);
                    }

                }


                break;


            case R.id.addmore:

                linearLayout1.setVisibility(View.VISIBLE);
                More.setVisibility(View.GONE);
                mor1.setVisibility(View.VISIBLE);
                break;


            case R.id.addmore1:

                linearLayout2.setVisibility(View.VISIBLE);
                mor1.setVisibility(View.GONE);
                mor2.setVisibility(View.VISIBLE);

                break;

            case R.id.addmore2:

                linearLayout3.setVisibility(View.VISIBLE);
                mor2.setVisibility(View.GONE);
                break;
        }

    }

    private void CallApi() {

/*
        arrayList.clear();



                DoctorStaffModel extrasItemModel = new DoctorStaffModel();


                extrasItemModel.setService_name(bservise);
                extrasItemModel.setAmount(bamount);
                extrasItemModel.setService_discount(bdiscount);

                arrayList.add(extrasItemModel);*/




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


    //....Clear all fields...
    private void clearFields() {

        service.setText("");
        amount.setText("");
        discount.setText("");
    }
}
