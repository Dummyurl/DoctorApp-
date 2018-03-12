package com.bfurns.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.bfurns.R;
import com.bfurns.model.BillModel;

import java.util.ArrayList;

/**
 * Created by Mahesh on 21/08/16.
 */
public class BillActivity extends AppCompatActivity implements View.OnClickListener {


    private Toolbar toolbar;
    Button button;
    private ArrayList<BillModel> arrayList;
    private ListAdapter filmListAdapter;
    ListView recyclerView;
    private ArrayList<String> billNamesArray;
String product,discount,amount;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_add_bill_details);
        setUpViews();
    }

    private void setUpViews() {


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Prescription");

        Intent intent=getIntent();
        product=intent.getStringExtra("service");
        discount=intent.getStringExtra("myamount");
        amount=intent.getStringExtra("mydiscount");

/*

        recyclerView=(ListView) findViewById(R.id.recyclerview);

        arrayList = new ArrayList<BillModel>();
        billNamesArray = new ArrayList<String>();


        // Setting Film attributes
        for(int i=0 ; i<4 ; i++ ){
            // Initialize a Film object in order to set it's attributes.
            // We have to create a Film object each time this loop traverse
            BillModel film = new BillModel();

            // // Here we set the film name and star name attribute for a film in one loop
            film.setProduct(product);
            film.setAmount(amount);
            film.setDiscount(discount);

            // Pass the Film object to the array of Film objects
            arrayList.add(film);

            // Add the film name to array of film names
            billNamesArray.add(film.getProduct());
            billNamesArray.add(film.getAmount());
            billNamesArray.add(film.getDiscount());

            // Add the star name to array of film names
        }

        // Plug the data set (starNamesArray) to the adapter
        filmListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, billNamesArray);

        // Plug the adapter with the UI component (starListView)
        recyclerView.setAdapter(filmListAdapter);
*/



        button=(Button) findViewById(R.id.addmore);
        button.setOnClickListener(this);


        Button add=(Button) findViewById(R.id.add);
        add.setOnClickListener(this);

    }




    @Override
    public void onClick(View v) {

        switch (v.getId()){


            case R.id.addmore:

                startActivity(new Intent(BillActivity.this,PrescribeActivity.class));

                break;


            case R.id.add:

                finish();

                break;


        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case android.R.id.home:

                onBackPressed();

                break;
        }

        return super.onOptionsItemSelected(item);
    }


}

