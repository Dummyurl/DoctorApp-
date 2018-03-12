package com.bfurns.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bfurns.R;
import com.bfurns.activity.PatientProfileActivity;
import com.bfurns.model.AppointementModel;
import com.bfurns.model.UserModel;
import com.bfurns.utility.MyPreferences;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Explicate1 on 12/9/2017.
 */

public class MyAcccountsAdapter extends RecyclerView.Adapter<MyAcccountsAdapter.MyViewHolerModule>{


    private Context context;

    private List<UserModel> arrayList;
    public static boolean isSignedIn = false;


    public MyAcccountsAdapter(List<UserModel> arrayList) {
        this.arrayList = arrayList;
    }




    @Override
    public MyAcccountsAdapter.MyViewHolerModule onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_account_list_item, parent, false);

        return new MyAcccountsAdapter.MyViewHolerModule(layoutView);
    }

    @Override
    public void onBindViewHolder(MyAcccountsAdapter.MyViewHolerModule holder, int position) {

        UserModel item=arrayList.get(position);

        //holder.name.setText(item.getName());
        holder.name.setText(item.getBus_title());




    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static final class MyViewHolerModule extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name,category,mode,time,date,clinic_name,payment,paid,remain;
        private List<UserModel> arrayList;
        private Context context;
        public View layout;

        String id,user_id,bus_id,sub_id,doctor_id,app_status;

        public MyViewHolerModule(View itemView) {
            super(itemView);
            this.arrayList = arrayList;
            this.context = context;
            name = (TextView) itemView.findViewById(R.id.name);
           // clinic_name = (TextView) itemView.findViewById(R.id.email);

            itemView.setOnClickListener(this);
        }




        @Override
        public void onClick(View v) {
            int position=getAdapterPosition();
            UserModel businessImage=this.arrayList.get(position);

        }
    }
}
