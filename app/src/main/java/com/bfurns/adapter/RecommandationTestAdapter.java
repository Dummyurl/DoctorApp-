package com.bfurns.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bfurns.R;
import com.bfurns.activity.PostMail;
import com.bfurns.application.AppClass;
import com.bfurns.model.AppointementModel;
import com.bfurns.utility.MyPreferences;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Explicate1 on 12/9/2017.
 */

public class RecommandationTestAdapter extends RecyclerView.Adapter<RecommandationTestAdapter.MyViewHolerModule> {


    private ArrayList<AppointementModel> arrayList;
    private Context context;
    public static boolean isSignedIn = false;
    String Doctor_name, doctor_id,check;
    boolean checked;
    private SparseBooleanArray mCheckedItems = new SparseBooleanArray();






    public RecommandationTestAdapter(ArrayList<AppointementModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
        this.Doctor_name = Doctor_name;
        this.doctor_id = doctor_id;
    }


    @Override
    public RecommandationTestAdapter.MyViewHolerModule onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recommandation_test_row_item, parent, false);

        return new RecommandationTestAdapter.MyViewHolerModule(layoutView, context, arrayList);
    }

    @Override
    public void onBindViewHolder(final RecommandationTestAdapter.MyViewHolerModule holder, int position) {

        AppointementModel item = arrayList.get(position);

        holder.name.setText(item.getTest_name());
        String cate = item.getPatent_category();
        if (cate.equals("1")) {
            holder.category.setText("Radiology");


        } else {
            holder.category.setText("Pathology ");

        }




        if (position==0 /*&& holder.checkBox.isChecked()*/){
            holder.cardView.setBackgroundColor(Color.parseColor("#2196F3"));
          /*  PreferenceManager.getDefaultSharedPreferences(context).edit()
                    .putBoolean("checkBox",checked ).commit();*/
        }else if (position==1){
            holder.cardView.setBackgroundColor(Color.parseColor("#FFAB40"));


        }else if (position==2){
            holder.cardView.setBackgroundColor(Color.parseColor("#F44336"));


        }else if (position==3){
            holder.cardView.setBackgroundColor(Color.parseColor("#00BCD4"));


        }else if (position==4){
            holder.cardView.setBackgroundColor(Color.parseColor("#009688"));


        }else if (position==5){
            holder.cardView.setBackgroundColor(Color.parseColor("#0288D1"));


        }else if (position==6){
            holder.cardView.setBackgroundColor(Color.parseColor("#558B2F"));


        }else if (position==7){
            holder.cardView.setBackgroundColor(Color.parseColor("#E65100"));


        }else if (position==8){
            holder.cardView.setBackgroundColor(Color.parseColor("#F50057"));


        }else if (position==9){
            holder.cardView.setBackgroundColor(Color.parseColor("#5C6BC0"));


        }else if (position==10){
          //  holder.cardView.setBackgroundColor(Color.RED);


        }else if (position==11){
          //  holder.cardView.setBackgroundColor(Color.RED);


        }else if (position==12){
           // holder.cardView.setBackgroundColor(Color.RED);


        }else if (position==13){
          //  holder.cardView.setBackgroundColor(Color.RED);


        }else if (position==14){
            holder.cardView.setBackgroundColor(Color.RED);


        }else if (position==15){
           // holder.cardView.setBackgroundColor(Color.RED);


        }else if (position==16){
           // holder.cardView.setBackgroundColor(Color.RED);

        }


    }



    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static final class MyViewHolerModule extends RecyclerView.ViewHolder implements View.OnClickListener {
        CircleImageView imageView;
        TextView name, category;
        CardView cardView;
        private ArrayList<AppointementModel> arrayList;
        private Context context;
        CheckBox checkBox;
        String id, user_id, bus_id, sub_id, doctor_id, app_status, doctor_name, doct_name, main_doct_id;


        public MyViewHolerModule(View itemView, Context context, ArrayList<AppointementModel> arrayList) {
            super(itemView);
            this.arrayList = arrayList;
            this.context = context;
            this.main_doct_id = main_doct_id;
            name = (TextView) itemView.findViewById(R.id.industrialGoodImage);
            category = (TextView) itemView.findViewById(R.id.text1);
            cardView = (CardView) itemView.findViewById(R.id.industrialGood);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkbox);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();

            AppointementModel businessImage = this.arrayList.get(position);

          /*  final boolean newValue = !checkBox.isChecked();


            mCheckedItems.put(position, newValue);
            checkBox.setChecked(newValue);
            //display the text accordingly with the newValue value
            Snackbar snackbar = Snackbar.make(v, "Item Favorited", Snackbar.LENGTH_SHORT);
            snackbar.show();*/

        }

    }
}
