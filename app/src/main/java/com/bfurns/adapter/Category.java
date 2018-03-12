package com.bfurns.adapter;

import org.kaerdan.twolevelexpandablerecyclerview.TwoLevelExpandableAdapter;

import java.util.List;

/**
 * Created by Mahesh on 16/09/16.
 */
public class Category implements TwoLevelExpandableAdapter.DataSet<String,String> {

    private final String name;
    private final List<String> cities;

    public Category(String name, List<String> cities) {
        this.name = name;
        this.cities = cities;
    }

    @Override
    public String getData() {
        return name;
    }

    @Override
    public List<String> getChildren() {
        return cities;
    }
}
