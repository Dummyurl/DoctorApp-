package com.bfurns.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bfurns.R;

import com.bfurns.activity.DoctorProfile;
import com.bfurns.model.DoctorStaffModel;

import java.util.ArrayList;

/**
 * Created by Explicate1 on 11/25/2017.
 */

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.MyViewHolerModule> {

    private ArrayList<DoctorStaffModel> arrayList;
    private Context context;

    public DoctorAdapter(ArrayList<DoctorStaffModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }


    @Override
    public DoctorAdapter.MyViewHolerModule onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.doctor_staff_list_item, parent, false);

        return new DoctorAdapter.MyViewHolerModule(layoutView,context,arrayList);
    }

    @Override
    public void onBindViewHolder(DoctorAdapter.MyViewHolerModule holder, int position) {

        DoctorStaffModel item=arrayList.get(position);

        holder.textView.setText(item.getName());
        holder.description.setText(item.getEmail());
       /* String image=item.getProduct_image();
        //holder.review_count.setText(item.getRewiewCount());

        if(!image.equalsIgnoreCase("null")){

            Picasso.with(context)
                    .load(URLListner.SUBIMAGE_UPLOAD+image)
                    .fit()
                    .centerCrop()
                    .into(holder.imageView);
        }else{

              Picasso.with(context).load(R.drawable.bfurn).resize(40,40).into(holder.imageView);
        }*/

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static final class MyViewHolerModule extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView textView,description;
        private ArrayList<DoctorStaffModel> arrayList;
        private Context context;

        public MyViewHolerModule(View itemView,Context context,ArrayList<DoctorStaffModel> arrayList) {
            super(itemView);
            this.arrayList = arrayList;
            this.context = context;
            imageView = (ImageView) itemView.findViewById(R.id.doctor_image);
            textView = (TextView) itemView.findViewById(R.id.doct_name);
            description = (TextView) itemView.findViewById(R.id.doct_email);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position=getAdapterPosition();
            DoctorStaffModel businessImage=this.arrayList.get(position);
           Intent intent=new Intent(this.context,DoctorProfile.class);
            this.context.startActivity(intent);
        }
    }
}
