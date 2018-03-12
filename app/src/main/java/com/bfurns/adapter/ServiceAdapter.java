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
import com.bfurns.model.DoctorStaffModel;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Explicate1 on 12/9/2017.
 */

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.MyViewHolerModule>{


    private ArrayList<DoctorStaffModel> arrayList;
    private Context context;
    public static boolean isSignedIn = false;


    public ServiceAdapter(ArrayList<DoctorStaffModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }


    @Override
    public ServiceAdapter.MyViewHolerModule onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.doctor_appointment_item_list, parent, false);

        return new ServiceAdapter.MyViewHolerModule(layoutView,context,arrayList);
    }

    @Override
    public void onBindViewHolder(ServiceAdapter.MyViewHolerModule holder, int position) {

        DoctorStaffModel item=arrayList.get(position);

        holder.name.setText(item.getService_name());
        holder.price.setText(item.getSet_price());
        holder.discount.setText(item.getService_discount());
        holder.tax.setText(item.getService_tax());
        holder.total.setText(item.getTotal_cost());




    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static final class MyViewHolerModule extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name,price,discount,tax,total;
        private ArrayList<DoctorStaffModel> arrayList;
        private Context context;

        public MyViewHolerModule(View itemView,Context context,ArrayList<DoctorStaffModel> arrayList) {
            super(itemView);
            this.arrayList = arrayList;
            this.context = context;
            name = (TextView) itemView.findViewById(R.id.name);
            price = (TextView) itemView.findViewById(R.id.price);
            discount = (TextView) itemView.findViewById(R.id.discount);
            tax = (TextView) itemView.findViewById(R.id.tax);
            total = (TextView) itemView.findViewById(R.id.total);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position=getAdapterPosition();
            DoctorStaffModel businessImage=this.arrayList.get(position);
             Intent intent=new Intent(this.context,PatientProfileActivity.class);
            this.context.startActivity(intent);
        }
    }
}
