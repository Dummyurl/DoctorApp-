package com.bfurns.async;

import android.os.AsyncTask;
import com.bfurns.utility.JSONParser;
import org.apache.http.NameValuePair;
import org.json.JSONObject;
import java.util.List;

/**
 * Created by Mahesh on 05/03/16.
 */
public class SendRequest extends AsyncTask<String, Void, Boolean> {

    private JSONParser jParser = new JSONParser();
    ResultListner resultListener;
    int  status;
    private JSONObject response;
    List<NameValuePair> params;


    public SendRequest(List<NameValuePair> params){

        this.params=params;
    }

    public void setResultListener(ResultListner listener) {
        this.resultListener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected Boolean doInBackground(String... urls) {

        String url=urls[0];

        try {
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url, "POST", params);
            response=json;
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    protected void onPostExecute(Boolean result) {

        if(result){

            resultListener.onSuccess(response);
        }else {

            resultListener.onFailed();

        }

    }

    public static interface ResultListner{

        void onSuccess(JSONObject value);
        void onFailed();
    }

}
