package com.bfurns.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bfurns.R;
import com.bfurns.model.DoctorModel;
import com.bfurns.utility.URLListner;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by Mahesh on 11/01/18.
 */

public class MyDoctorAdapter extends RecyclerView.Adapter<MyDoctorAdapter.ProductHolder> {

    ArrayList<DoctorModel> list;
    Activity activity;
    String type;
    public MyDoctorAdapter(Activity activity, ArrayList<DoctorModel> list, String type) {
        this.list = list;
        this.activity = activity;
        this.type=type;

    }

    @Override
    public ProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_my_doctors,parent,false);

        return new ProductHolder(view);
    }

    @Override
    public void onBindViewHolder(final ProductHolder holder, final int position) {
        final DoctorModel categoryModel = list.get(position);
        String path = categoryModel.getDoct_photo();

        Picasso.with(activity).load(URLListner.MAIN_URL + "/uploads/profile/" + path).into(holder.icon_image);

       // Log.e("Image",ConstValue.BASE_URL + "/uploads/profile/" + path);


        holder.lbl_title.setText(categoryModel.getDoct_name());
        holder.lbl_clinic_name.setText("Clinic Name:"+categoryModel.getBus_title());
        holder.lbl_speciality.setText(categoryModel.getDoct_speciality());
        holder.lbl_degree.setText(categoryModel.getDoct_degree()+","+categoryModel.getDoct_experience()+" year");


        if(type.equalsIgnoreCase("ask")){

            holder.book.setText("Select");
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ProductHolder extends RecyclerView.ViewHolder {
        ImageView icon_image;
        TextView lbl_title;
        TextView lbl_degree;
        TextView lbl_clinic_name;
        TextView lbl_speciality;

        Button book;

        public ProductHolder(View itemView) {
            super(itemView);
            icon_image = (ImageView) itemView.findViewById(R.id.imageView);
            lbl_title = (TextView) itemView.findViewById(R.id.title);
            lbl_clinic_name = (TextView) itemView.findViewById(R.id.clinic);
            lbl_degree = (TextView) itemView.findViewById(R.id.degree);
            lbl_speciality=(TextView)itemView.findViewById(R.id.specilaity);


            book=(Button)itemView.findViewById(R.id.book);
        }
    }


}
