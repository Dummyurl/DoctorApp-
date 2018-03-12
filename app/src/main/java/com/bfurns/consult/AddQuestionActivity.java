package com.bfurns.consult;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bfurns.R;
import com.bfurns.application.AppClass;
import com.bfurns.model.QuestionModel;
import com.bfurns.utility.GetData;
import com.bfurns.utility.MyUtility;
import com.bfurns.utility.URLListner;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Mahesh on 09/01/18.
 */

public class AddQuestionActivity extends AppCompatActivity {

    EditText txtDoctor,txtSubject, txtDesc;
    private String id="";
    private ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_questions);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Ask Question");


        id="";

        txtDoctor=(EditText)findViewById(R.id.txtDoctor);
        txtSubject = (EditText)findViewById(R.id.txtSubject);
        txtDesc = (EditText)findViewById(R.id.txtDescr);

        Button button = (Button)findViewById(R.id.btnSubmit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                register();
            }
        });

        txtDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(AddQuestionActivity.this,MyDoctorsActivity.class);
                i.putExtra("from","ques");
                startActivityForResult(i,100);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK){
            txtDoctor.setText(data.getExtras().getString("name"));
            id= data.getExtras().getString("id");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }




    public void register(){



        String sub = txtSubject.getText().toString();
        String desc = txtDesc.getText().toString();


        // Check for a valid email address.

        if (TextUtils.isEmpty(id)) {
            txtDoctor.setError("Select Doctor");
            txtDoctor.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(sub)) {
            txtSubject.setError("Enter Subject");
            txtSubject.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(desc)) {
            txtDesc.setError("Enter Description");
            txtDesc.requestFocus();
            return;
        }


        txtDoctor.setError(null);
        txtSubject.setError(null);
        txtDesc.setError(null);



            showPrgressBar();

        List<NameValuePair> params=new ArrayList<>();
        params.add(new BasicNameValuePair("title", sub));
        params.add(new BasicNameValuePair("description", desc));
        params.add(new BasicNameValuePair("doct_id", id));
        params.add(new BasicNameValuePair("user_id", AppClass.getuserId()));



        Log.e("ID",AppClass.getuserId());

        GetData getData = new GetData(params);
        getData.setResultListner(new GetData.ResultListner() {

            @Override
            public void success(JSONObject jsonObject) {


                hideProgressBar();

                try {

                    int status=jsonObject.getInt("responce");

                    if(status==1){

                        finish();

                        MyUtility.showToast("Question Added Successfully!!!",AddQuestionActivity.this);


                    }else{

                        MyUtility.showToast("Failed to Add",AddQuestionActivity.this);


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


