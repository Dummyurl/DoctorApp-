package com.bfurns.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bfurns.R;
import com.bfurns.activity.HomeActivity;
import com.bfurns.model.UserModel;
import com.bfurns.utility.MyPreferences;

import java.util.ArrayList;

/**
 * Created by Explicate1 on 12/9/2017.
 */

public class MergeAccountClinicAdapter extends RecyclerView.Adapter<MergeAccountClinicAdapter.MyViewHolerModule>{


    private ArrayList<UserModel> arrayList;
    private Context context;
    public static boolean isSignedIn = false;


    public MergeAccountClinicAdapter(ArrayList<UserModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }


    @Override
    public MergeAccountClinicAdapter.MyViewHolerModule onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_account_list_item, parent, false);

        return new MergeAccountClinicAdapter.MyViewHolerModule(layoutView,context,arrayList);
    }

    @Override
    public void onBindViewHolder(MergeAccountClinicAdapter.MyViewHolerModule holder, int position) {

        UserModel item=arrayList.get(position);
        String clinic_name=item.getName();

        holder.name.setText(clinic_name);



    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static final class MyViewHolerModule extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name,category,mode,time,date,clinic_name,payment,paid,remain;
        private ArrayList<UserModel> arrayList;
        private Context context;
        String id,user_id,bus_id,sub_id,doctor_id,app_status;

        public MyViewHolerModule(View itemView,Context context,ArrayList<UserModel> arrayList) {
            super(itemView);
            this.arrayList = arrayList;
            this.context = context;
            name = (TextView) itemView.findViewById(R.id.name);
            /*category = (TextView) itemView.findViewById(R.id.name1);
            mode = (TextView) itemView.findViewById(R.id.name2);
            time = (TextView) itemView.findViewById(R.id.name3);
            date = (TextView) itemView.findViewById(R.id.name4);*/

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position=getAdapterPosition();
//            String bus,doc,bustitle;
//            UserModel businessImage=this.arrayList.get(position);
//            bus=  businessImage.getBus_id();
//            doc=  businessImage.getDoct_id();
//            bustitle=  businessImage.getBus_title();
//
//            Intent intent=new Intent(this.context,HomeActivity.class);
//            intent.putExtra(MyPreferences.DOCTOR_ID,doc);
//             intent.putExtra(MyPreferences.CLINIC_ID,bus);
//             intent.putExtra(MyPreferences.CLINIC_NAME,bustitle);
//
//            this.context.startActivity(intent);
        }
    }
}
