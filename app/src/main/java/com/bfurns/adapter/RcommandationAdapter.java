package com.bfurns.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bfurns.R;
import com.bfurns.activity.PatientProfileActivity;
import com.bfurns.model.AppointementModel;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Explicate1 on 12/9/2017.
 */

public class RcommandationAdapter extends RecyclerView.Adapter<RcommandationAdapter.MyViewHolerModule>{


    private ArrayList<AppointementModel> arrayList;
    private Context context;
    public static boolean isSignedIn = false;


    public RcommandationAdapter(ArrayList<AppointementModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }


    @Override
    public RcommandationAdapter.MyViewHolerModule onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recommandation_item_list, parent, false);

        return new RcommandationAdapter.MyViewHolerModule(layoutView,context,arrayList);
    }

    @Override
    public void onBindViewHolder(RcommandationAdapter.MyViewHolerModule holder, int position) {

        AppointementModel item=arrayList.get(position);

        holder.name.setText(item.getPatient_name());
        holder.text.setText(item.getContact());
        if((position % 2 == 0)){
            holder.cardView.setCardBackgroundColor(R.color.green);
        }else{
            holder.cardView.setCardBackgroundColor(R.color.colorPrimary);
        }


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static final class MyViewHolerModule extends RecyclerView.ViewHolder implements View.OnClickListener {
        CircleImageView imageView;
        TextView name,text,number,time,date;
        private ArrayList<AppointementModel> arrayList;
        private Context context;
        CardView cardView;

        public MyViewHolerModule(View itemView,Context context,ArrayList<AppointementModel> arrayList) {
            super(itemView);
            this.arrayList = arrayList;
            this.context = context;
            name = (TextView) itemView.findViewById(R.id.name);
            text = (TextView) itemView.findViewById(R.id.text);
            number = (TextView) itemView.findViewById(R.id.number);
            cardView=(CardView)itemView.findViewById(R.id.cv_scanin);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position=getAdapterPosition();
           /* AppointementModel businessImage=this.arrayList.get(position);
             Intent intent=new Intent(this.context,PatientProfileActivity.class);
            this.context.startActivity(intent);*/
        }
    }
}
