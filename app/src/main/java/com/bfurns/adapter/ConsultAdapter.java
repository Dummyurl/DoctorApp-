package com.bfurns.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bfurns.R;
import com.bfurns.activity.ConsultReplyActivity;
import com.bfurns.activity.PatientDetailsForm;
import com.bfurns.application.AppClass;
import com.bfurns.model.ConsultModel;
import com.bfurns.utility.GetData;
import com.bfurns.utility.MyPreferences;
import com.bfurns.utility.URLListner;
import com.bfurns.utility.Utility;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Explicate1 on 12/9/2017.
 */

public class ConsultAdapter extends RecyclerView.Adapter<ConsultAdapter.MyViewHolerModule>{


    private ArrayList<ConsultModel> arrayList;
    private Context context;
    public static boolean isSignedIn = false;
    String flag,quryId,chattype,read;



    public ConsultAdapter(ArrayList<ConsultModel> arrayList, Context context,String flag) {
        this.arrayList = arrayList;
        this.context = context;
        this.flag = flag;
    }


    @Override
    public ConsultAdapter.MyViewHolerModule onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.doctor_patient_query_item, parent, false);

        return new ConsultAdapter.MyViewHolerModule(layoutView,context,arrayList,flag);
    }

    @Override
    public void onBindViewHolder(ConsultAdapter.MyViewHolerModule holder, int position) {

        ConsultModel item=arrayList.get(position);

  quryId=item.getQuery_id();
  chattype=item.getChat_type();

        holder.name.setText(item.getName());
        holder.title.setText(item.getTitle());
        holder.time.setText(item.getDate());
        read=item.getRead();
        if (read.equals("1")){
           // holder.cardView.setBackgroundColor(Color.WHITE);
        }else {
            holder.cardView.setBackgroundColor(Color.parseColor("#e5e5e5"));
        }



    }



    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static final class MyViewHolerModule extends RecyclerView.ViewHolder implements View.OnClickListener {
        CircleImageView imageView;
        CardView cardView;
        TextView name,title,description,time,date;
        private ArrayList<ConsultModel> arrayList;
        private Context context;

        String p_name,p_title,p_time,p_description,id,Doctor_name,p_from,p_to,reply_id,chat_type,d_to,d_from,flag,read;


        public MyViewHolerModule(View itemView,Context context,ArrayList<ConsultModel> arrayList,String flag) {
            super(itemView);
            this.arrayList = arrayList;
            this.context = context;
            this.flag = flag;
            name = (TextView) itemView.findViewById(R.id.name);
            title = (TextView) itemView.findViewById(R.id.title);
            time = (TextView) itemView.findViewById(R.id.time);
            cardView = (CardView) itemView.findViewById(R.id.cv_scanin);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position=getAdapterPosition();
            ConsultModel businessImage=this.arrayList.get(position);
            p_name=businessImage.getName();
            p_title=businessImage.getTitle();
            p_time=businessImage.getDate();
            p_description=businessImage.getDescription();
            id=businessImage.getQuery_id();
            Doctor_name=businessImage.getDoctor_name();
            p_from=businessImage.getP_to();
            p_to=businessImage.getP_from();
            reply_id=businessImage.getReply_id();
            chat_type=businessImage.getChat_type();
            d_from=businessImage.getD_to();
            d_to=businessImage.getD_from();
            flag=businessImage.getFlag();
            read=businessImage.getRead();
            AppClass.setQueryId(id);

            Intent intent=new Intent(this.context,ConsultReplyActivity.class);
            intent.putExtra(MyPreferences.PATIENT_NAME,p_name);
            intent.putExtra(MyPreferences.TITLE,p_title);
            intent.putExtra(MyPreferences.DATE,p_time);
            intent.putExtra(MyPreferences.DESCRIPTION,p_description);
            intent.putExtra(MyPreferences.QUERY_ID,id);
            intent.putExtra(MyPreferences.DOCTOR_NAME,Doctor_name);
            intent.putExtra(MyPreferences.P_TO,p_to);
            intent.putExtra(MyPreferences.P_FROM,p_from);
            intent.putExtra(MyPreferences.REPLY_ID,reply_id);
            intent.putExtra(MyPreferences.CCHAT_TYPE,chat_type);
            intent.putExtra(MyPreferences.D_to,d_to);
            intent.putExtra(MyPreferences.D_from,d_from);
            intent.putExtra(MyPreferences.flag,flag);
            intent.putExtra(MyPreferences.read,read);
            this.context.startActivity(intent);
        }
    }
}
