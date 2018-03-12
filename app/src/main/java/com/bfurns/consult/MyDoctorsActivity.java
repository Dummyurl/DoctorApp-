package com.bfurns.consult;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bfurns.R;
import com.bfurns.adapter.MyDoctorAdapter;
import com.bfurns.application.AppClass;
import com.bfurns.model.DoctorModel;
import com.bfurns.utility.GetData;
import com.bfurns.utility.MyUtility;
import com.bfurns.utility.RecyclerItemClickListener;
import com.bfurns.utility.URLListner;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Mahesh on 11/01/18.
 */

public class MyDoctorsActivity extends AppCompatActivity {

    public static ArrayList<DoctorModel> mDoctorArray;
    MyDoctorAdapter doctorAdapter;
    RecyclerView recyclerView;
    private TextView tvNotFound;
    private ProgressDialog dialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_doctors);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Select Doctor");


        mDoctorArray = new ArrayList<>();

        setResult(Activity.RESULT_CANCELED);

        tvNotFound=(TextView)findViewById(R.id.tvnotfound);
        tvNotFound.setVisibility(View.GONE);


        recyclerView = (RecyclerView)findViewById(R.id.rv_list);
        final LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        String type=null;
        if(getIntent().hasExtra("from")){

            type ="ask";

        }else{

            type ="chat";
        }

        doctorAdapter = new MyDoctorAdapter(this,mDoctorArray,type);
        recyclerView.setAdapter(doctorAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                    Bundle b = new Bundle();
                    b.putString("id",mDoctorArray.get(position).getDoct_id());
                    b.putString("name",mDoctorArray.get(position).getDoct_name());
                    Intent intent = new Intent();
                    intent.putExtras(b);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
            }

        }));


        loadData();




    }
    public void loadData(){

        List<NameValuePair> params=new ArrayList<>();
        params.add(new BasicNameValuePair("user_id", AppClass.getuserId()));

        String url=null;
        if(getIntent().hasExtra("from")){

            url ="";

        }else{

            url="";
        }

        showPrgressBar();

        GetData getData = new GetData(params);
        getData.setResultListner(new GetData.ResultListner() {

            @Override
            public void success(JSONObject jsonObject) {

                hideProgressBar();
                try {
                    JSONArray data=jsonObject.getJSONArray("data");
                    Gson gson = new Gson();
                    Type listType = new TypeToken<List<DoctorModel>>() {
                    }.getType();
                    mDoctorArray.clear();
                    mDoctorArray.addAll((Collection<? extends DoctorModel>) gson.fromJson(data.toString(), listType));

                    if(mDoctorArray.isEmpty()){
                        tvNotFound.setVisibility(View.VISIBLE);
                    }else{
                        tvNotFound.setVisibility(View.GONE);
                    }

                    doctorAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    tvNotFound.setVisibility(View.VISIBLE);
                    e.printStackTrace();
                }

            }
            @Override
            public void fail() {
                tvNotFound.setVisibility(View.GONE);
                hideProgressBar();
            }
        });
        getData.execute(URLListner.BASEURL+URLListner.getDoctorData);





    }


    private void showPrgressBar() {
        dialog=new ProgressDialog(this,ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Please wait");
        dialog.show();
    }

    private void hideProgressBar() {
        if(dialog!=null){
            if(dialog.isShowing()){
                dialog.dismiss();
            }
        }
    }

}
