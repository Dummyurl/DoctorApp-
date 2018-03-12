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
import com.bfurns.activity.PostMail;
import com.bfurns.application.AppClass;
import com.bfurns.model.AppointementModel;
import com.bfurns.utility.MyPreferences;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Explicate1 on 12/9/2017.
 */

public class DoctorListAdapter extends RecyclerView.Adapter<DoctorListAdapter.MyViewHolerModule>{


    private ArrayList<AppointementModel> arrayList;
    private Context context;
    public static boolean isSignedIn = false;
    String Doctor_name,doctor_id;


    public DoctorListAdapter(ArrayList<AppointementModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
        this.Doctor_name = Doctor_name;
        this.doctor_id = doctor_id;
    }


    @Override
    public DoctorListAdapter.MyViewHolerModule onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.autocompletetextview, parent, false);

        return new DoctorListAdapter.MyViewHolerModule(layoutView,context,arrayList);
    }

    @Override
    public void onBindViewHolder(DoctorListAdapter.MyViewHolerModule holder, int position) {

        AppointementModel item=arrayList.get(position);
        String doctor=item.getDoctor_name();
        String specialization=item.getPatient_name();

        holder.name.setText(doctor+""+","+ specialization);




    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static final class MyViewHolerModule extends RecyclerView.ViewHolder implements View.OnClickListener {
        CircleImageView imageView;
        TextView name,category,mode,time,date;
        private ArrayList<AppointementModel> arrayList;
        private Context context;
        String id,user_id,bus_id,sub_id,doctor_id,app_status,doctor_name,doct_name,main_doct_id;

        public MyViewHolerModule(View itemView,Context context,ArrayList<AppointementModel> arrayList) {
            super(itemView);
            this.arrayList = arrayList;
            this.context = context;
            this.main_doct_id = main_doct_id;
            name = (TextView) itemView.findViewById(R.id.doctor_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position=getAdapterPosition();

            AppointementModel businessImage=this.arrayList.get(position);
            doct_name=businessImage.getDoctor_name();
            doctor_id= businessImage.getDoct_id();
             Intent intent=new Intent(this.context,PostMail.class);
             intent.putExtra(MyPreferences.CHAT_DOCTOR_ID,doctor_id);
             intent.putExtra(MyPreferences.DOCTOR_NAME,doct_name);
            this.context.startActivity(intent);
        }
    }
}
