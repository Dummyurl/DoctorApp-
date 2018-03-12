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
import com.bfurns.activity.PatientDetailsForm;
import com.bfurns.model.AppointementModel;
import com.bfurns.utility.MyPreferences;
import com.bfurns.utility.URLListner;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Explicate1 on 12/9/2017.
 */

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.MyViewHolerModule>{


    private ArrayList<AppointementModel> arrayList;
    private Context context;
    public static boolean isSignedIn = false;


    public PatientAdapter(ArrayList<AppointementModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }


    @Override
    public PatientAdapter.MyViewHolerModule onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.patient_list_item, parent, false);

        return new PatientAdapter.MyViewHolerModule(layoutView,context,arrayList);
    }

    @Override
    public void onBindViewHolder(PatientAdapter.MyViewHolerModule holder, int position) {

        AppointementModel item=arrayList.get(position);

        holder.name.setText(item.getPatient_name());
        holder.category.setText(item.getContact());
        holder.mode.setText(item.getEmail());

        String image=item.getImage();


        if(!image.equalsIgnoreCase("")){

            Picasso.with(context)
                    .load(URLListner.PATIENT_BASEURLL+image)
                    .into(holder.imageView);
        }else{

            Picasso.with(context).load(R.drawable.placeholder_user).resize(40,40).into(holder.imageView);
        }



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
        String id;

        public MyViewHolerModule(View itemView,Context context,ArrayList<AppointementModel> arrayList) {
            super(itemView);
            this.arrayList = arrayList;
            this.context = context;
            imageView = (CircleImageView) itemView.findViewById(R.id.imageview);
            name = (TextView) itemView.findViewById(R.id.name);
            category = (TextView) itemView.findViewById(R.id.category);
            mode = (TextView) itemView.findViewById(R.id.mode);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position=getAdapterPosition();
            AppointementModel businessImage=this.arrayList.get(position);
            String user_id=businessImage.getUser_id();
            id=businessImage.getId();
             Intent intent=new Intent(this.context,PatientDetailsForm.class);
             intent.putExtra(MyPreferences.USER_ID,user_id);
             intent.putExtra(MyPreferences.ID,id);
            this.context.startActivity(intent);
        }
    }
}
