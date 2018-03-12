package com.bfurns.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.bfurns.R;
import com.bfurns.model.BillModel;
import com.bfurns.utility.MyUtility;
import com.bfurns.utility.Validations;

import java.util.ArrayList;

/**
 * Created by Mahesh on 21/08/16.
 */
public class AddBillActivity extends AppCompatActivity  {

    EditText service,amount,discount;
    String myservice,myamount,mydiscount;
    RelativeLayout parent;

    private Toolbar toolbar;


    private ArrayList<BillModel> arrayList;
    private ListAdapter filmListAdapter;
    ListView recyclerView;
    private ArrayList<String> billNamesArray;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_add_bill);
        setUpViews();
    }

    private void setUpViews() {


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Add Item");
        parent=(RelativeLayout)findViewById(R.id.rl_loginActivity);
        service=(EditText)findViewById(R.id.product);
        amount=(EditText)findViewById(R.id.amount);
        discount=(EditText)findViewById(R.id.discount);


        arrayList = new ArrayList<BillModel>();
        billNamesArray = new ArrayList<String>();


        recyclerView=(ListView) findViewById(R.id.recyclerview);



        Button more=(Button) findViewById(R.id.add);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addservice();
            }
        });


    }

    private void addservice() {
        MyUtility.hideKeyboard(amount, AddBillActivity.this);
        myservice = service.getText().toString().trim();
        myamount = amount.getText().toString().trim();
        mydiscount = discount.getText().toString().trim();


        if (myservice.equalsIgnoreCase("")) {


            MyUtility.showSnack(parent, Validations.ENTER_SERVICE);

        }  else if (mydiscount.equalsIgnoreCase("")) {

            MyUtility.showSnack(parent, Validations.ENTER_DISCOUNT);

        } else if (myamount.equalsIgnoreCase("")) {

            MyUtility.showSnack(parent, Validations.ENTER_AMOUNT);

        } else {


            if (MyUtility.isConnected(this)) {




                // Setting Film attributes
              //  for(int i=0 ; i<4 ; i++ ){
                    // Initialize a Film object in order to set it's attributes.
                    // We have to create a Film object each time this loop traverse
                    BillModel film = new BillModel();

                    // // Here we set the film name and star name attribute for a film in one loop
                  /*  film.setProduct(myservice);
                    film.setAmount(myamount);
                    film.setDiscount(myservice);

                    // Pass the Film object to the array of Film objects
                    arrayList.add(film);

                    // Add the film name to array of film names
                    billNamesArray.add(film.getProduct());
                    billNamesArray.add(film.getAmount());
                    billNamesArray.add(film.getDiscount());*/

                    // Add the star name to array of film names
            //    }

                // Plug the data set (starNamesArray) to the adapter
                filmListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, billNamesArray);

                // Plug the adapter with the UI component (starListView)
                recyclerView.setAdapter(filmListAdapter);

/*

                //  CallApi();
                Intent intent=new Intent(AddBillActivity.this,BillActivity.class);
                intent.putExtra("service",myservice);
                intent.putExtra("myamount",myamount);
                intent.putExtra("mydiscount",mydiscount);
                startActivity(intent);
*/



            } else {

                MyUtility.showSnack(parent, MyUtility.INTERNET_ERROR);
            }

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

