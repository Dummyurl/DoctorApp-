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
import com.bfurns.utility.MyPreferences;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Explicate1 on 12/9/2017.
 */

public class SummeryAdapter extends RecyclerView.Adapter<SummeryAdapter.MyViewHolerModule>{


    private ArrayList<AppointementModel> arrayList;
    private Context context;
    public static boolean isSignedIn = false;


    public SummeryAdapter(ArrayList<AppointementModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }


    @Override
    public SummeryAdapter.MyViewHolerModule onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.summery_layout_item, parent, false);

        return new SummeryAdapter.MyViewHolerModule(layoutView,context,arrayList);
    }

    @Override
    public void onBindViewHolder(SummeryAdapter.MyViewHolerModule holder, int position) {

        AppointementModel item=arrayList.get(position);

        holder.name.setText(item.getPatient_name());
        holder.time.setText(item.getTime());
        holder.category.setText(item.getApp_type());
        holder.date.setText(item.getDate());
        holder.clinic_name.setText(item.getClinic_name());
        holder.payment.setText(item.getTotal_amount());
       // holder.paid.setText(item.getPayed_amount());
      //  holder.remain.setText(item.getRemaining_amount());
        String mode=item.getMode_of_appointement();
        if (mode.equals("0")){
            holder.mode.setText("App");

        }else if(mode.equals("1")){
            holder.mode.setText("Walkin");
        }else {
            holder.mode.setText("Telephonic");
        }



    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static final class MyViewHolerModule extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name,category,mode,time,date,clinic_name,payment,paid,remain;
        private ArrayList<AppointementModel> arrayList;
        private Context context;
        String id,user_id,bus_id,sub_id,doctor_id,app_status;

        public MyViewHolerModule(View itemView,Context context,ArrayList<AppointementModel> arrayList) {
            super(itemView);
            this.arrayList = arrayList;
            this.context = context;
            name = (TextView) itemView.findViewById(R.id.name);
            category = (TextView) itemView.findViewById(R.id.category);
            mode = (TextView) itemView.findViewById(R.id.mode);
            date = (TextView) itemView.findViewById(R.id.Date);
            time = (TextView) itemView.findViewById(R.id.time);
            clinic_name = (TextView) itemView.findViewById(R.id.hospital_name);
            payment = (TextView) itemView.findViewById(R.id.total_amount);
           // paid = (TextView) itemView.findViewById(R.id.payed_amount);
           // remain = (TextView) itemView.findViewById(R.id.due_amount);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position=getAdapterPosition();
            AppointementModel businessImage=this.arrayList.get(position);
            id=businessImage.getId();
            user_id=businessImage.getUser_id();
            bus_id=businessImage.getBus_id();
            sub_id=businessImage.getSub_user_id();
            doctor_id=businessImage.getDoct_id();
            app_status=businessImage.getApp_staus();
             Intent intent=new Intent(this.context,PatientProfileActivity.class);
             intent.putExtra(MyPreferences.USER_ID,user_id);
             intent.putExtra(MyPreferences.ID,id);
             intent.putExtra(MyPreferences.sub_id,sub_id);
             intent.putExtra(MyPreferences.DOCTOR_ID,doctor_id);
             intent.putExtra(MyPreferences.CLINIC_ID,bus_id);
             intent.putExtra(MyPreferences.APP_STATUS,app_status);

            this.context.startActivity(intent);
        }
    }
}
