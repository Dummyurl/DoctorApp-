package com.bfurns.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bfurns.R;
import com.bfurns.activity.HomeActivity;
import com.bfurns.activity.PatientProfileActivity;
import com.bfurns.application.AppClass;
import com.bfurns.model.AppointementModel;
import com.bfurns.model.UserModel;
import com.bfurns.utility.MyPreferences;

import java.util.ArrayList;

/**
 * Created by Explicate1 on 12/9/2017.
 */

public class MergeAccountAdapter extends RecyclerView.Adapter<MergeAccountAdapter.MyViewHolerModule>{


    private ArrayList<UserModel> arrayList;
    private Context context;
    public static boolean isSignedIn = false;


    public MergeAccountAdapter(ArrayList<UserModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }


    @Override
    public MergeAccountAdapter.MyViewHolerModule onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_account_list_item, parent, false);

        return new MergeAccountAdapter.MyViewHolerModule(layoutView,context,arrayList);
    }

    @Override
    public void onBindViewHolder(MergeAccountAdapter.MyViewHolerModule holder, int position) {

        UserModel item=arrayList.get(position);
        String clinic_name=item.getBus_title();

        holder.mname.setText(clinic_name);



    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static final class MyViewHolerModule extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mname,category,mode,time,date,clinic_name,payment,paid,remain;
        private ArrayList<UserModel> arrayList;
        private Context context;
        String id,user_id,bus_id,sub_id,doctor_id,app_status;

        public MyViewHolerModule(View itemView,Context context,ArrayList<UserModel> arrayList) {
            super(itemView);
            this.arrayList = arrayList;
            this.context = context;
            mname = (TextView) itemView.findViewById(R.id.name);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position=getAdapterPosition();
            String bus,doc,bustitle;
            UserModel businessImage=this.arrayList.get(position);
            bus= businessImage.getBus_id();
            doc= businessImage.getDoct_id();
            bustitle= businessImage.getBus_title();
            AppClass.setClinic_id(bus);
            AppClass.setuserid(doc);

            Intent intent=new Intent(this.context,HomeActivity.class);
            this.context.startActivity(intent);
        }
    }
}
