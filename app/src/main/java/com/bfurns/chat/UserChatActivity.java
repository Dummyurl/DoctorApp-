package com.bfurns.chat;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bfurns.R;
import com.bfurns.adapter.AskQuestionRepliesAdapter;
import com.bfurns.application.AppClass;
import com.bfurns.model.AskQuesMessage;
import com.bfurns.utility.GetData;
import com.bfurns.utility.MyUtility;
import com.bfurns.utility.URLListner;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Mahesh on 21/05/17.
 */

public class UserChatActivity extends AppCompatActivity {

    private TextView title,desc,doctor;
    String messageText;

    private EditText messageET;
    private ListView messagesContainer;
    private ImageView sendBtn;
    private AskQuestionRepliesAdapter adapter;
    private ArrayList<AskQuesMessage> chatHistory;
    private ProgressBar load;
    private ProgressDialog dialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_ques_replies);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Conversation");


        setUpViews();
    }

    private void setUpViews() {

        this.title = (TextView)findViewById(R.id.title);
        this.desc = (TextView)findViewById(R.id.desc);
        this.doctor = (TextView)findViewById(R.id.doctorName);

        title.setText(getIntent().getStringExtra("subject"));
        desc.setText("Description: "+getIntent().getStringExtra("desc"));
        doctor.setText("Doctor: "+getIntent().getStringExtra("doctor_name"));


        load=(ProgressBar)findViewById(R.id.chatload);


        messagesContainer = (ListView) findViewById(R.id.messagesContainer);


        messageET = (EditText) findViewById(R.id.msg_edit);
        sendBtn = (ImageView) findViewById(R.id.send_btn);


        chatHistory = new ArrayList<AskQuesMessage>();
        if(!chatHistory.isEmpty()){
            chatHistory.clear();
        }

        adapter = new AskQuestionRepliesAdapter(UserChatActivity.this, chatHistory);
        messagesContainer.setAdapter(adapter);
        loadData();
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 messageText = messageET.getText().toString();

                if (TextUtils.isEmpty(messageText)) {
                    return;

                }

                if(MyUtility.isConnected(UserChatActivity.this)){

                        messageET.setText("");

                        //new SetSupport().execute(messageText);
                        saveSupportMessage(messageText);

                }else{

                    Toast.makeText(UserChatActivity.this,"Check your internet connection", Toast.LENGTH_LONG).show();
                }


            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case android.R.id.home:

                finish();
                onBackPressed();

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void displayMessage(AskQuesMessage message) {
        adapter.add(message);
        adapter.notifyDataSetChanged();
        scroll();
    }

    private void scroll() {
        messagesContainer.setSelection(messagesContainer.getCount() - 1);
    }

    private void loadData(){



        if(MyUtility.isConnected(this)){

            getDiscussion();

        }else{

            Toast.makeText(this,"Check your internet connection...", Toast.LENGTH_LONG);
        }


    }




   private  void getDiscussion(){


       if(!MyUtility.isConnected(this)) {
           MyUtility.showAlertMessage(this,MyUtility.INTERNET_ERROR);
           return;
       }



       List<NameValuePair> params=new ArrayList<>();
       params.add(new BasicNameValuePair("question_id", getIntent().getStringExtra("id")));

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

               load.setVisibility(View.GONE);

               try {

                   JSONArray vjsonArray1 = jsonObject.getJSONArray("data");

                   if(vjsonArray1.length()>0) {

                       for (int i = 0; i < vjsonArray1.length(); i++) {

                           JSONObject c = vjsonArray1.getJSONObject(i);

                           AskQuesMessage msg = new AskQuesMessage();
                           msg.setId(1);
                           msg.setFirst(false);
                           msg.setMe(true);
                           if (c.getString("reply_from").equalsIgnoreCase(AppClass.getuserId())) {
                               msg.setMe(true);
                           } else  {
                               msg.setMe(false);
                           }
                           msg.setSupportId("");
                           msg.setImage("");
                           msg.setMessage(c.getString("description"));
                           msg.setDate(c.getString("created"));
                           chatHistory.add(msg);


                       }

                       adapter.notifyDataSetChanged();
                       scroll();

                   }


               } catch (JSONException e) {
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

    private void saveSupportMessage(final String messageText){


        showPrgressBar();

        List<NameValuePair> params=new ArrayList<>();
        params.add(new BasicNameValuePair("doct_id", getIntent().getStringExtra("doctor_id")));
        params.add(new BasicNameValuePair("question_id", getIntent().getStringExtra("id")));
        params.add(new BasicNameValuePair("user_id", AppClass.getuserId()));
        params.add(new BasicNameValuePair("description", messageText));

        Log.e("Add replies",new Gson().toJson(params));

        GetData getData = new GetData(params);
        getData.setResultListner(new GetData.ResultListner() {

            @Override
            public void success(JSONObject jsonObject) {

                hideProgressBar();

                try {

                    int status=jsonObject.getInt("responce");

                    if(status==1){


                        AskQuesMessage message = new AskQuesMessage();
                        message.setId(1);
                        message.setDate(System.currentTimeMillis()+"");
                        message.setMe(true);
                        message.setSupportId("");
                        message.setImage("");
                        message.setFirst(false);
                        message.setMessage(messageText);
                        displayMessage(message);

                    }else{
                        MyUtility.showToast("Failed to Add",UserChatActivity.this);
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
