package com.bfurns.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.TextKeyListener;
import android.util.Log;
import android.util.Size;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.bfurns.R;
import com.bfurns.application.AppClass;
import com.bfurns.model.BillModel;
import com.bfurns.model.UserModel;
import com.bfurns.utility.GetData;
import com.bfurns.utility.MultiSelectionSpinner;
import com.bfurns.utility.MyPreferences;
import com.bfurns.utility.MyUtility;
import com.bfurns.utility.URLListner;
import com.bfurns.utility.Utility;
import com.bfurns.utility.Validations;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Mahesh on 21/08/16.
 */
public class PrescribeActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, MultiSelectionSpinner.OnMultipleItemsSelectedListener {


    private Toolbar toolbar;
    EditText name, duration;
    Spinner Strength, Take;
    String listStr;
    private String mname, mduration, mystright, mtake, mwhen, total;
    private static final String TAG = PrescribeActivity.class.getSimpleName();
    Button add, addmore, Prescribe;
    RelativeLayout parent;
    MultiSelectionSpinner multiSelectionSpinner;
    String id, bus_id, sub_id, user_id, doct_id, app_status;

    private ArrayList<BillModel> arrayList;
    private ListAdapter filmListAdapter;
    ListView recyclerView;
    private ArrayList<String> billNamesArray;
    int i, duration1, quantity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prescription);

        Intent intent = getIntent();
        id = AppClass.getAppointmentId();
        bus_id = AppClass.getclinicId();
        sub_id = AppClass.getSubId();
        user_id = intent.getStringExtra(MyPreferences.USER_ID);
        doct_id = intent.getStringExtra(MyPreferences.DOCTOR_ID);
        app_status = intent.getStringExtra(MyPreferences.APP_STATUS);

        setUpViews();
    }

    private void setUpViews() {


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Prescription");

        name = (EditText) findViewById(R.id.medicine);
        duration = (EditText) findViewById(R.id.duration);
        addmore = (Button) findViewById(R.id.addmore);
        Prescribe = (Button) findViewById(R.id.Prescribe);
        parent = (RelativeLayout) findViewById(R.id.parent);
        addmore.setOnClickListener(this);
        Prescribe.setOnClickListener(this);

        arrayList = new ArrayList<BillModel>();
        billNamesArray = new ArrayList<String>();


        recyclerView = (ListView) findViewById(R.id.recyclerview);


        Strength = (Spinner) findViewById(R.id.Strength);
        Take = (Spinner) findViewById(R.id.Take);
        Strength.setOnItemSelectedListener(this);
        Take.setOnItemSelectedListener(this);

        String[] array = {"Morning", "Afternoon", "Evening", "Night"};
        multiSelectionSpinner = (MultiSelectionSpinner) findViewById(R.id.mySpinner);
        multiSelectionSpinner.setItems(array);
        //multiSelectionSpinner.setSelection(new int[]{0});
        multiSelectionSpinner.setListener(this);


        List<String> categories = new ArrayList<String>();
        categories.add("Select Strength");
        categories.add("10 MG");
        categories.add("50 MG");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Strength.setAdapter(dataAdapter);


        List<String> TotimeCate = new ArrayList<String>();
        TotimeCate.add("Select Take");
        TotimeCate.add("Before Meal");
        TotimeCate.add("After Meal");

        ArrayAdapter<String> TotimeCateAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, TotimeCate);
        TotimeCateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Take.setAdapter(TotimeCateAdapter);


    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.addmore:

                MyUtility.hideKeyboard(Take, PrescribeActivity.this);
                MyUtility.hideKeyboard(Strength, PrescribeActivity.this);
                MyUtility.hideKeyboard(multiSelectionSpinner, PrescribeActivity.this);
                mname = name.getText().toString().trim();
                mduration = duration.getText().toString().trim();


                if (mname.equalsIgnoreCase("")) {


                    Toast.makeText(this, "Enter Drug Name", Toast.LENGTH_SHORT).show();

                } else if (mduration.equalsIgnoreCase("")) {

                   MyUtility.showSnack(parent, Validations.ENTER_DURATION);
                   // Toast.makeText(this, Validations.ENTER_DURATION, Toast.LENGTH_SHORT).show();


                } else if (Strength.getSelectedItem().toString().trim().equals("Select Strength")) {

                    MyUtility.showSnack(parent, "Select Strength");

                } else if (Take.getSelectedItem().toString().trim().equals("Select Take")) {

                    MyUtility.showSnack(parent, "Select take");

                }else if (multiSelectionSpinner.getSelectedItem().equals("")) {

                    MyUtility.showSnack(parent, "Select When");

                }

                else {


                    if (MyUtility.isConnected(this)) {

                         adddData();

                    } else {

                        MyUtility.showSnack(parent, MyUtility.INTERNET_ERROR);
                    }

                }


                break;

            case R.id.Prescribe:
                if (listStr==null){
                    MyUtility.showSnack(parent,"Add Medication First");
                }else {

                   if (MyUtility.isConnected(this)){
                        CallApi();

                    }else {
                       MyUtility.showSnack(parent, MyUtility.INTERNET_ERROR);

                   }

                }

                break;


        }
    }

    private void adddData() {

        duration1 = Integer.parseInt(mduration);
        quantity = (duration1) * (i);
        total = Integer.toString(quantity);


        BillModel film = new BillModel();

        // // Here we set the film name and star name attribute for a film in one loop
        film.setDrug_name(mname);
        film.setDuration(mduration);
        film.setSlot(mwhen);
        film.setStrength(mystright);
        film.setTake(mtake);

        film.setQuantity(total);

        arrayList.add(film);

        // Add the film name to array of film names
        billNamesArray.add(film.getDrug_name());
        billNamesArray.add(film.getDuration());
        billNamesArray.add(film.getSlot());
        billNamesArray.add(film.getStrength());
        billNamesArray.add(film.getTake());
        billNamesArray.add(film.getQuantity());

        // Add the star name to array of film names
        //    }


        // Plug the data set (starNamesArray) to the adapter
        filmListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, billNamesArray);

        // Plug the adapter with the UI component (starListView)
        recyclerView.setAdapter(filmListAdapter);


        Type listType = new TypeToken<List<BillModel>>() {
        }.getType();

        listStr = new Gson().toJson(arrayList, listType);
        name.setText("");
        duration.setText("");
        Strength.setSelection(0);
        Take.setSelection(0);
        multiSelectionSpinner.setSelection(new int[]{});



    }

    private void CallApi() {

        Utility.showProgressDialog(this, "Please wait..");
        Utility.progressDialog.show();

        List<NameValuePair> params = new ArrayList<>();


        params.add(new BasicNameValuePair("data", listStr));
        params.add(new BasicNameValuePair("app_id", id));
        params.add(new BasicNameValuePair("user_id", user_id));
        params.add(new BasicNameValuePair("bus_id", bus_id));
        params.add(new BasicNameValuePair("doct_id", doct_id));
        params.add(new BasicNameValuePair("sub_user_id", sub_id));

        GetData getData = new GetData(params);
        getData.setResultListner(new GetData.ResultListner() {

            int success = 0;
            private String id, clinic_id,status,user_id,sub_user_id,app_id, username, phone, email1, image, mobileVerify, emailVerify, otp, socialType, socialId;

            @Override
            public void success(JSONObject jsonObject) {

                if (Utility.progressDialog.isShowing()) {
                    Utility.progressDialog.dismiss();
                }

                try {

                    success = jsonObject.getInt("responce");

                    Log.e(TAG, "Status:" + success);

                    if (success == 1) {

                        JSONArray arr = jsonObject.getJSONArray("prescription");
                        for (int i = 0; i < arr.length(); i++) {
                            JSONObject json = arr.getJSONObject(i);


                            id = json.getString("id");
                            clinic_id = json.getString("bus_id");
                            status = json.getString("status");
                            user_id = json.getString("user_id");
                            sub_user_id = json.getString("sub_user_id");
                            app_id = json.getString("app_id");


                        }

                        AppClass.setAppointmentId(app_id);
                        AppClass.setSubID(sub_user_id);
                       // AppClass.setAppStatus(status);
                        AppClass.setClinic_id(clinic_id);

                        MyUtility.showSnack(parent,"Data Successfully Added");
                        finish();
                        Intent intent=new Intent(PrescribeActivity.this,PatientProfileActivity.class);
                        intent.putExtra(MyPreferences.APP_STATUS,status);

                        startActivity(intent);


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
        getData.execute(URLListner.BASEURL + URLListner.add_prescription_new);


    }


    @Override
    public void selectedIndices(List<Integer> indices) {

    }

    @Override
    public void selectedStrings(List<String> strings) {
        mwhen = strings.toString();
        System.out.println(mwhen.trim().split(" ").length);
        i = mwhen.trim().split(" ").length;


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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (parent.getId()) {
            case R.id.Strength:


                mystright = Strength.getSelectedItem().toString();

                // do stuffs with you spinner 1
                break;
            case R.id.Take:

                mtake = Take.getSelectedItem().toString();

                // do stuffs with you spinner 2
                break;

            default:
                break;


        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

