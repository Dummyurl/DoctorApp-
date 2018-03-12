package com.bfurns.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bfurns.R;

import org.kaerdan.twolevelexpandablerecyclerview.TwoLevelExpandableAdapter;
import org.kaerdan.twolevelexpandablerecyclerview.ViewHolderWithSetter;

import java.util.List;

/**
 * Created by Mahesh on 16/09/16.
 */
public class CategoryAdapter extends TwoLevelExpandableAdapter<Category> {

    private Context context;

    public CategoryAdapter(List<Category> data, Context context) {
        super(data);

        this.context=context;
    }

    @Override
    public ViewHolderWithSetter getSecondLevelViewHolder(ViewGroup parent) {


        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_sub_category, parent,false);
        CityAndCountryViewHolder mh = new CityAndCountryViewHolder(v);

        return mh;

    }

    @Override
    public ViewHolderWithSetter getTopLevelViewHolder(ViewGroup parent) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_category, parent,false);
        CityAndCountryViewHolder mh = new CityAndCountryViewHolder(v);

        return mh;

    }

    @Override
    public void onBindViewHolder(ViewHolderWithSetter holder, int position) {
        super.onBindViewHolder(holder, position);



    }


    public class CityAndCountryViewHolder extends ViewHolderWithSetter<String> {
        public CityAndCountryViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void setItem(String item) {
            ((TextView) itemView).setText(item);
        }
    }


}