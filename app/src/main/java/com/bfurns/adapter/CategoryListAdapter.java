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
import com.bfurns.activity.PostMail;
import com.bfurns.activity.UpdateProfile;
import com.bfurns.application.AppClass;
import com.bfurns.model.AppointementModel;
import com.bfurns.utility.MyPreferences;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Explicate1 on 12/9/2017.
 */

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.MyViewHolerModule> {


    private ArrayList<AppointementModel> arrayList;
    private Context context;
    public static boolean isSignedIn = false;
    String doctor_id;

    public CategoryListAdapter(ArrayList<AppointementModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
        this.doctor_id = doctor_id;

    }


    @Override
    public CategoryListAdapter.MyViewHolerModule onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.autocompletetextview, parent, false);

        return new CategoryListAdapter.MyViewHolerModule(layoutView, context, arrayList);
    }

    @Override
    public void onBindViewHolder(CategoryListAdapter.MyViewHolerModule holder, int position) {

        AppointementModel item = arrayList.get(position);

        holder.name.setText(item.getDoct_speciality());


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static final class MyViewHolerModule extends RecyclerView.ViewHolder implements View.OnClickListener {
        CircleImageView imageView;
        TextView name, category, mode, time, date;
        private ArrayList<AppointementModel> arrayList;
        private Context context;
        String id, user_id, bus_id, sub_id, doctor_id, app_status, doct_name, main_doct_id, cate_name, cate_id;


        public MyViewHolerModule(View itemView, Context context, ArrayList<AppointementModel> arrayList) {
            super(itemView);
            this.arrayList = arrayList;
            this.context = context;

            name = (TextView) itemView.findViewById(R.id.doctor_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();

            AppointementModel businessImage = this.arrayList.get(position);
            cate_id = businessImage.getBus_id();
            cate_name = businessImage.getDoct_speciality();
            AppClass.setspeciality_iid(cate_id);
            AppClass.setdoctorspeciality(cate_name);
            ((Activity)context).finish();
            Intent intent = new Intent(this.context, UpdateProfile.class);
            this.context.startActivity(intent);
        }
    }
}
