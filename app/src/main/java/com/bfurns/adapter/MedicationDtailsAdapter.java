package com.bfurns.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bfurns.R;
import com.bfurns.model.AppointementModel;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Explicate1 on 12/9/2017.
 */

public class MedicationDtailsAdapter extends RecyclerView.Adapter<MedicationDtailsAdapter.MyViewHolerModule> {


    private ArrayList<AppointementModel> arrayList;
    private Context context;
    public static boolean isSignedIn = false;
    String app_date;


    public MedicationDtailsAdapter(ArrayList<AppointementModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
        this.app_date = app_date;
    }


    @Override
    public MedicationDtailsAdapter.MyViewHolerModule onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_layout_medication, parent, false);

        return new MedicationDtailsAdapter.MyViewHolerModule(layoutView, context, arrayList);
    }

    @Override
    public void onBindViewHolder(MedicationDtailsAdapter.MyViewHolerModule holder, int position) {

        AppointementModel item = arrayList.get(position);

        String medicine_name = item.getDrug_name();
        String strength = item.getStrength();
        String take = item.getTake();
        String slot = item.getSlot();
        String quantity = item.getQuantity();
        String duration = item.getDuration();

        String slot1 = slot.replaceAll("\\[", "");
        String slot2 = slot1.replaceAll("\\]", "");
       // String slot3 = slot2.replaceAll(",", "");


        holder.name.setText(medicine_name);
        holder.strength.setText(strength);
        holder.take.setText(take);
        holder.duration.setText(duration);
        holder.quantity.setText(quantity);
        holder.slot.setText(slot2);

/*
        ArrayList aList = new ArrayList(Arrays.asList(slot3));
        for (int i = 0; i < aList.size(); i++) {

            if (myslot.equals(aList.get(i))){

                holder.checkBox1.setChecked(true);

            }else {
                holder.checkBox1.setChecked(false);


            }

            if ("Afternoon".equals(aList.get(i))){
                holder.checkBox2.setChecked(true);

            }else{
                holder.checkBox2.setChecked(false);

            }


            if ("Evening".equals(aList.get(i))){
                holder.checkBox3.setChecked(true);

            }else{
                holder.checkBox3.setChecked(false);

            }


            if ("Night".equals(aList.get(i))){
                holder.checkBox4.setChecked(true);

            }else{
                holder.checkBox4.setChecked(false);

            }


             if ("Week".equals(aList.get(i))){
                holder.checkBox5.setChecked(true);

            }else {
                 holder.checkBox5.setChecked(false);

             }



        }*/
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static final class MyViewHolerModule extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name, strength, take, duration, slot, app, quantity;
        private ArrayList<AppointementModel> arrayList;
        private Context context;
        String app_date;
        CheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5;

        public MyViewHolerModule(View itemView, Context context, ArrayList<AppointementModel> arrayList) {
            super(itemView);
            this.arrayList = arrayList;
            this.context = context;
            this.app_date = app_date;
            name = (TextView) itemView.findViewById(R.id.drug_name);
            app = (TextView) itemView.findViewById(R.id.date);
            quantity = (TextView) itemView.findViewById(R.id.quantity);

            strength = (TextView) itemView.findViewById(R.id.strength);
            take = (TextView) itemView.findViewById(R.id.take);
            slot = (TextView) itemView.findViewById(R.id.slot);
            duration = (TextView) itemView.findViewById(R.id.duration);
           /* checkBox1=(CheckBox)itemView.findViewById(R.id.morning);
            checkBox2=(CheckBox)itemView.findViewById(R.id.afternoon);
            checkBox3=(CheckBox)itemView.findViewById(R.id.evening);
            checkBox4=(CheckBox)itemView.findViewById(R.id.night);
            checkBox5=(CheckBox)itemView.findViewById(R.id.week);
*/
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
//            int position=getAdapterPosition();
//            AppointementModel businessImage=this.arrayList.get(position);
//            String user_id=businessImage.getUser_id();
//             Intent intent=new Intent(this.context,PatientDetailsForm.class);
//             intent.putExtra(MyPreferences.USER_ID,user_id);
//            this.context.startActivity(intent);
        }
    }
}
