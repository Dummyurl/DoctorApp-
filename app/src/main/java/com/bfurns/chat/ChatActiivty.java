package com.bfurns.chat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bfurns.R;
import com.bfurns.adapter.QuestionAdapter;
import com.bfurns.application.AppClass;
import com.bfurns.consult.AddQuestionActivity;
import com.bfurns.consult.AskQuestionDetails;
import com.bfurns.model.QuestionModel;
import com.bfurns.utility.GetData;
import com.bfurns.utility.MyUtility;
import com.bfurns.utility.RecyclerItemClickListener;
import com.bfurns.utility.URLListner;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mahesh on 09/01/18.
 */

public class ChatActiivty extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<QuestionModel> arrayList;
    QuestionAdapter adapter;
    String type="";
    TextView txtNoData;

    private ProgressDialog dialog;

    //0 new msg 1 old msg

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_question);

        Toolbar  toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Consult");


        FloatingActionButton button=(FloatingActionButton)findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(ChatActiivty.this,AddQuestionActivity.class);
                startActivity(intent);
            }
        });

        txtNoData=(TextView)findViewById(R.id.txtNoData);

        arrayList = new ArrayList<>();
        if(getIntent().hasExtra("result")){
            // button.setVisibility(View.VISIBLE);
            type=getIntent().getStringExtra("result");
        }

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        final LinearLayoutManager layoutManager
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new QuestionAdapter(arrayList,getApplicationContext(),type);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {


                Intent i=new Intent(ChatActiivty.this,AskQuestionDetails.class);
                i.putExtra("subject",arrayList.get(position).getSubject());
                i.putExtra("desc",arrayList.get(position).getDescription());
                i.putExtra("id",arrayList.get(position).getId());
                i.putExtra("q_reply_id",arrayList.get(position).getQ_reply_id());
                i.putExtra("doctor_id",arrayList.get(position).getDoctor_id());
                i.putExtra("doctor_name",arrayList.get(position).getDoctorName());
                i.putExtra("flag",arrayList.get(position).getFlag());
                startActivity(i);


            }
        }));
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!arrayList.isEmpty()){
            arrayList.clear();
        }
        getQuestions();
    }

    private void getQuestions() {

        if(!MyUtility.isConnected(this)) {
            MyUtility.showAlertMessage(this,MyUtility.INTERNET_ERROR);
            return;
        }
        showPrgressBar();


        List<NameValuePair> params=new ArrayList<>();
        params.add(new BasicNameValuePair("doct_id", AppClass.getuserId()));


        Log.e("ID",AppClass.getuserId());

        GetData getData = new GetData(params);
        getData.setResultListner(new GetData.ResultListner() {

            @Override
            public void success(JSONObject jsonObject) {


                hideProgressBar();

                try {

                    JSONArray data=jsonObject.getJSONArray("data");

                    if(data.length()>0){

                        txtNoData.setVisibility(View.GONE);


                        for(int i=0;i<data.length();i++){


                            JSONObject jsonObject1=data.getJSONObject(i);

                            QuestionModel model=new QuestionModel();
                            model.setId(jsonObject1.getString("id"));
                            model.setSubject(jsonObject1.getString("title"));
                            model.setDescription(jsonObject1.getString("description"));
                            model.setQ_reply_id(jsonObject1.getString("q_reply_id"));
                            model.setCreated(jsonObject1.getString("created"));
                            model.setDoctor_id(jsonObject1.getString("p_to"));
                            model.setFlag(jsonObject1.getString("read"));
                            model.setDoctorName(jsonObject1.getString("doct_name"));

                            arrayList.add(model);
                        }


                        if (arrayList!=null){
                            adapter.notifyDataSetChanged();
                        }


                    }else{

                        txtNoData.setVisibility(View.VISIBLE);
                    }

                } catch (JSONException e) {
                    hideProgressBar();
                    e.printStackTrace();
                }
            }
            @Override
            public void fail() {
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
