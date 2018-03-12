package com.bfurns.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bfurns.R;
import com.bfurns.activity.DetailsMedication;
import com.bfurns.model.AppointementModel;
import com.bfurns.utility.MyPreferences;

import java.util.ArrayList;

/**
 * Created by Explicate1 on 12/9/2017.
 */

public class MedicationAdapter extends RecyclerView.Adapter<MedicationAdapter.MyViewHolerModule>{


    private ArrayList<AppointementModel> arrayList;
    private Context context;
    public static boolean isSignedIn = false;
    String app_date,user_id,sub_id;


    public MedicationAdapter(ArrayList<AppointementModel> arrayList, Context context,  String app_date,String user_id,String sub_id) {
        this.arrayList = arrayList;
        this.context = context;
        this.app_date = app_date;
        this.user_id = user_id;
        this.sub_id = sub_id;
    }


    @Override
    public MedicationAdapter.MyViewHolerModule onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.medication_history_row_item, parent, false);

        return new MedicationAdapter.MyViewHolerModule(layoutView,context,arrayList,app_date,user_id,sub_id);
    }

    @Override
    public void onBindViewHolder(MedicationAdapter.MyViewHolerModule holder, int position) {

        AppointementModel item=arrayList.get(position);
      /*String drug=item.getDrug_name();

       drug=drug.replaceAll(",", "\n");
        System.out.println(drug);
       holder.name.setText(drug);*/
        holder.app.setText(item.getDate());



    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static final class MyViewHolerModule extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name,strength,take,duration,slot,app,view;
        private ArrayList<AppointementModel> arrayList;
        private Context context;
        String app_date,user_id,sub_id;

        public MyViewHolerModule(View itemView,Context context,ArrayList<AppointementModel> arrayList,String app_date,String user_id,String sub_id) {
            super(itemView);
            this.arrayList = arrayList;
            this.context = context;
            this.app_date = app_date;
            this.user_id = user_id;
            this.sub_id = sub_id;
            name = (TextView) itemView.findViewById(R.id.drug_name);
            app = (TextView) itemView.findViewById(R.id.date);
            view = (TextView) itemView.findViewById(R.id.view);




            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position=getAdapterPosition();
           AppointementModel businessImage=this.arrayList.get(position);
           String id=businessImage.getId();
           String date=businessImage.getDate();
           String quantity=businessImage.getQuantity();
             Intent intent=new Intent(this.context,DetailsMedication.class);
             intent.putExtra(MyPreferences.ID,id);
             intent.putExtra(MyPreferences.quantity,quantity);
             intent.putExtra(MyPreferences.DATE,date);
           this.context.startActivity(intent);
        }
    }
}
