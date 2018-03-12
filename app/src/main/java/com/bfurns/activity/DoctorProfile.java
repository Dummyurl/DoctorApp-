package com.bfurns.activity;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.internal.http.multipart.MultipartEntity;
import com.bfurns.R;
import com.bfurns.adapter.SummeryAdapter;
import com.bfurns.application.AppClass;
import com.bfurns.model.AppointementModel;
import com.bfurns.utility.DoctorMultiSelectionSpinner;
import com.bfurns.utility.GetData;
import com.bfurns.utility.MultiSelectionSpinner;
import com.bfurns.utility.MyPreferences;
import com.bfurns.utility.MyUtility;
import com.bfurns.utility.URLListner;
import com.bfurns.utility.Utility;
import com.bfurns.utility.Validations;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class DoctorProfile extends AppCompatActivity {
    EditText name, phone, eemail, city, degree, collage, completion_year, Reg_number, Reg_year, Regi_concil, password, speciality, gender1, exp, alter_number, awards;
    RadioGroup radioGroup;
    RadioButton maleORFemale;
    Spinner year, specialization;
    String mygender, myname, myemail, mycity, mydegree, myphone, mycollage, com_year, r_number, r_year, r_concil, myyear, myspecialization, mypassword;
    //  MultiSelectionSpinner multiSelectionSpinner;

    String doctor_name, doct_speciality_id, myaward, my_alter_number, gender, user_fullname, bdy, doct_degree, doct_college, doct_year, doct_phone, doct_experience, d_city, d_reg_no, d_reg_con, d_reg_year, d_reg_proof, d_qua_proof, d_id_proof, doct_photo, doct_about, doct_status, doct_speciality;
    String id, bus_id, doct_id, doct_email, doct_pass;
    Button button;
    RelativeLayout parent;
    ImageView imageView, imageView1, imageView2, imageView3;
    private Uri fileUri;
    private static final int REQUEST_CAMERA = 100;
    private static final int SELECT_FILE = 200;
    private float totalSize = 0;
    List<File> addImages;
    int selectedImage = 0;
    String clinic_id, user_id, doctor_id, email, user_name;

    private static final String TAG = DoctorProfile.class.getSimpleName();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_doctor_profile);
        addImages = new ArrayList<>();
        Intent intent = getIntent();
        clinic_id = AppClass.getclinicId();
        user_id = AppClass.getuserId();
        doctor_id = AppClass.getdoctor_id();
        email = AppClass.getemail();
        user_name = AppClass.getUsername();


        setUpview();
    }

    private void setUpview() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Doctor Profile");

        name = (EditText) findViewById(R.id.name);
        eemail = (EditText) findViewById(R.id.edt_email);
        phone = (EditText) findViewById(R.id.contact);
        city = (EditText) findViewById(R.id.city);
        degree = (EditText) findViewById(R.id.degree);
        collage = (EditText) findViewById(R.id.univercity);
        completion_year = (EditText) findViewById(R.id.c_year);
        Reg_number = (EditText) findViewById(R.id.reg_number);
        Reg_year = (EditText) findViewById(R.id.r_year);
        Regi_concil = (EditText) findViewById(R.id.reg_concil);
        speciality = (EditText) findViewById(R.id.speciality);
        gender1 = (EditText) findViewById(R.id.gender);
        exp = (EditText) findViewById(R.id.year_exp);
        alter_number = (EditText) findViewById(R.id.alternate_contact);
        awards = (EditText) findViewById(R.id.awards);
        radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
        //   year = (Spinner) findViewById(R.id.s_experence);
        //  button = (Button) findViewById(R.id.create);
        parent = (RelativeLayout) findViewById(R.id.rl_loginActivity);
        imageView = (ImageView) findViewById(R.id.imageregistrationproof);
        imageView1 = (ImageView) findViewById(R.id.imageequproof);
        imageView2 = (ImageView) findViewById(R.id.imageidproof);
        imageView3 = (ImageView) findViewById(R.id.imageproof);
        password = (EditText) findViewById(R.id.password);
        eemail.setText(email);
        AppClass.setUserName();

        getDoctorInfo();


    }

    private void getDoctorInfo() {
        // speciality.setVisibility(View.VISIBLE);
        Utility.showProgressDialog(DoctorProfile.this, "Please wait...");
        Utility.progressDialog.show();


        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("doct_id", user_id));


        GetData getData = new GetData(params);
        getData.setResultListner(new GetData.ResultListner() {

            int success = 0;


            @Override
            public void success(JSONObject jsonObject) {

                if (Utility.progressDialog.isShowing()) {
                    Utility.progressDialog.dismiss();
                }


                try {

                    success = jsonObject.getInt("responce");


                    if (success == 1) {
                        // jsonObject.getString("CharterProduct");
                        JSONArray arr = jsonObject.getJSONArray("data");
                        for (int i = 0; i < arr.length(); i++) {

                            JSONObject json = arr.getJSONObject(i);

                            doctor_name = json.getString("doct_name");
                            gender = json.getString("d_gender");
                            bdy = json.getString("doct_bdate");
                            doct_degree = json.getString("doct_degree");
                            doct_college = json.getString("doct_college");
                            doct_year = json.getString("doct_year");
                            doct_phone = json.getString("doct_phone");
                            doct_speciality_id = json.getString("doct_speciality");
                            doct_experience = json.getString("doct_experience");
                            d_city = json.getString("city");
                            d_reg_no = json.getString("d_reg_no");
                            d_reg_con = json.getString("d_reg_con");
                            d_reg_year = json.getString("d_reg_year");
                            d_reg_proof = json.getString("d_reg_proof");
                            d_qua_proof = json.getString("d_qua_proof");
                            d_id_proof = json.getString("d_id_proof");
                            doct_photo = json.getString("doct_photo");
                            doct_about = json.getString("doct_about");
                            //   user_fullname = json.getString("user_fullname");
                            bus_id = json.getString("bus_id");
                            //user_id = json.getString("user_id");
                            doct_id = json.getString("doct_id");
                            doct_email = json.getString("doct_email");
                            doct_pass = json.getString("user_password");
                            doct_speciality = json.getString("doct_speciality_title");
                            myaward = json.getString("awards");
                            my_alter_number = json.getString("other_number");


                            name.setText(doctor_name);
                            phone.setText(doct_phone);
                            city.setText(d_city);
                            degree.setText(doct_degree);
                            collage.setText(doct_college);
                            completion_year.setText(doct_year);
                            Reg_number.setText(d_reg_no);
                            Reg_year.setText(d_reg_year);
                            Regi_concil.setText(d_reg_con);
                            password.setText(doct_pass);
                            speciality.setText(doct_speciality);
                            gender1.setText(gender);
                            exp.setText(doct_experience);


                            awards.setText(myaward);


                            alter_number.setText(my_alter_number);


                            if (!d_reg_proof.equalsIgnoreCase("null")) {

                                Picasso.with(getApplicationContext())
                                        .load(URLListner.BASEURLL + d_reg_proof)
                                        .fit()
                                        .centerCrop()
                                        .into(imageView);
                            } else {

                                Picasso.with(getApplicationContext()).load(R.drawable.placeholder_user).resize(40, 40).into(imageView);
                            }


                        }

                        if (!d_qua_proof.equalsIgnoreCase("null")) {

                            Picasso.with(getApplicationContext())
                                    .load(URLListner.BASEURLL + d_qua_proof)
                                    .fit()
                                    .centerCrop()
                                    .into(imageView1);
                        } else {

                            Picasso.with(getApplicationContext()).load(R.drawable.placeholder_user).resize(40, 40).into(imageView1);
                        }


                        if (!d_id_proof.equalsIgnoreCase("null")) {

                            Picasso.with(getApplicationContext())
                                    .load(URLListner.BASEURLL + d_id_proof)
                                    .fit()
                                    .centerCrop()
                                    .into(imageView2);
                        } else {

                            Picasso.with(getApplicationContext()).load(R.drawable.placeholder_user).resize(40, 40).into(imageView2);
                        }


                        if (!doct_photo.equalsIgnoreCase("null")) {

                            Picasso.with(getApplicationContext())
                                    .load(URLListner.BASEURLL + doct_photo)
                                    .fit()
                                    .centerCrop()
                                    .into(imageView3);
                        } else {

                            Picasso.with(getApplicationContext()).load(R.drawable.placeholder_user).resize(40, 40).into(imageView3);
                        }

                        AppClass.setProfileSession(doct_id, doct_experience, email, doctor_name, gender, doct_degree, doct_college, doct_year, doct_phone, d_city, d_reg_no, d_reg_con,
                                d_reg_year, d_reg_proof, d_qua_proof, d_id_proof, doct_photo, doct_speciality, doct_pass, doct_email, doct_speciality_id, my_alter_number, myaward);


                    }


                    if (success != 1) {
                        Utility.showSnackbar(parent, getString(R.string.notavailable1));
                    }

                } catch (Exception e) {

                    e.printStackTrace();
                }
            }

            @Override
            public void fail() {

                if (Utility.progressDialog.isShowing()) {
                    Utility.progressDialog.dismiss();
                }

                if (success != 1) {
                    Utility.showToast(getApplicationContext(), getString(R.string.failed));
                }


            }
        });
        getData.execute(URLListner.BASEURL + URLListner.get_doctor_info);


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.update_doctor:

                Intent intent = new Intent(DoctorProfile.this, UpdateProfile.class);

                startActivity(intent);
                return true;

            case android.R.id.home:

                onBackPressed();

                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.update_menu, menu);
        return true;
    }
}
