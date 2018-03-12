package com.bfurns.activity;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bfurns.R;
import com.bfurns.app.APIClient;
import com.bfurns.app.APIInterface;
import com.bfurns.application.AppClass;
import com.bfurns.utility.MyPreferences;
import com.bfurns.utility.MyUtility;
import com.bfurns.utility.URLListner;
import com.bfurns.utility.Utility;
import com.bfurns.utility.Validations;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UpdateProfile extends AppCompatActivity implements View.OnClickListener/*AdapterView.OnItemSelectedListener*/ {
    EditText name, phone, myspeciality, eemail, city, degree, per_email, collage, completion_year, Reg_number, Reg_year, Regi_concil, password, speciality, gender1, exp, sp, awards, alternate_number;
    RadioGroup radioGroup;
    RadioButton maleORFemale, male, female;
    int year, month, day;
    String mgender, count_id, myexperience, mygender, myexp, mper_email, myname, myaward, myalter, myemail, mycity, mydegree, myphone, mycollage, com_year, r_number, r_year, r_concil, myyear, myspecialization, mypassword;

    String doctor_name, gender, user_fullname, bdy, doct_degree, doct_college, doct_year, doctor_email,
            doct_phone, doct_experience, d_city, d_reg_no, d_reg_con, d_reg_year,
            d_reg_proof, d_qua_proof, d_id_proof, doct_photo, doct_about, doct_status, doct_speciality, passwd, alter_number, award;
    String id, bus_id, doct_id, doct_email, doct_pass;
    Button button;
    RelativeLayout parent;
    ImageView imageView, imageView1, imageView2, imageView3;
    private Uri fileUri;
    private static final int REQUEST_CAMERA = 100;
    private static final int SELECT_FILE = 200;
    private float totalSize = 0;
  //  List<File> addImages;
    int selectedImage = 0;
    String clinic_id, user_id, doctor_id, email, cate_name, spec_id;
    int count;
    private static final String TAG = UpdateProfile.class.getSimpleName();

    private File file1,file2,file3,file4;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_doctor_profile1);

       // addImages = new ArrayList<>();


        Intent intent = getIntent();
        //  cate_name = AppClass.getspeciality_name();

        clinic_id = AppClass.getclinicId();
        user_id = intent.getStringExtra(MyPreferences.USER_ID);
        doctor_id = AppClass.getuserId();
        spec_id = AppClass.getSPECIALIZATION_ID();


        doctor_name = AppClass.getUSER_FULLNAME();
        email = AppClass.getemail();//doctor1
        doctor_email = AppClass.getDOCTOR_EMAIL();//doctoremail
        passwd = AppClass.getpassword();
        doct_phone = AppClass.getphone();
        d_city = AppClass.getCITY();
        mgender = AppClass.getGENDER();
        doct_experience = AppClass.getdoct_experience();
        doct_speciality = AppClass.getdoct_speciality();
        doct_degree = AppClass.getdoct_degree();
        doct_college = AppClass.getdoct_college();
        doct_year = AppClass.getdoct_year();
        d_reg_no = AppClass.getd_reg_no();
        d_reg_year = AppClass.getd_reg_year();
        d_reg_con = AppClass.getd_reg_con();
        d_reg_proof = AppClass.getr_proof_photo();
        d_qua_proof = AppClass.getr_proof_qualification();
        d_id_proof = AppClass.getr_proof_id();
        doct_photo = AppClass.getdoct_photo();
        alter_number = AppClass.getAlternateNumber();
        award = AppClass.getAward();


        setUpview();
    }

    private void setUpview() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Update Doctor Profile");

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
        exp = (EditText) findViewById(R.id.year_exp);
        password = (EditText) findViewById(R.id.password);
        sp = (EditText) findViewById(R.id.speciality);
        awards = (EditText) findViewById(R.id.awards);
        alternate_number = (EditText) findViewById(R.id.a_contact);
        per_email = (EditText) findViewById(R.id.persnal_email);
        male = (RadioButton) findViewById(R.id.male);
        female = (RadioButton) findViewById(R.id.female);
        //  myspeciality=(EditText) findViewById(R.id.myspeciality);


        radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
        button = (Button) findViewById(R.id.create);
        parent = (RelativeLayout) findViewById(R.id.rl_loginActivity);
        imageView = (ImageView) findViewById(R.id.imageregistrationproof);
        imageView1 = (ImageView) findViewById(R.id.imageequproof);
        imageView2 = (ImageView) findViewById(R.id.imageidproof);
        imageView3 = (ImageView) findViewById(R.id.imageproof);
        name.setText(doctor_name);
        eemail.setText(email);
        phone.setText(doct_phone);
        city.setText(d_city);
        degree.setText(doct_degree);
        collage.setText(doct_college);
        completion_year.setText(doct_year);
        Reg_number.setText(d_reg_no);
        Reg_year.setText(d_reg_year);
        Regi_concil.setText(d_reg_con);
        Regi_concil.setText(d_reg_con);
        exp.setText(doct_experience);
        sp.setText(doct_speciality);
        password.setText(passwd);
        per_email.setText(doctor_email);
        awards.setText(award);
        alternate_number.setText(alter_number);


        if (mgender.equals("Female")) {
            female.setChecked(true);
        } else {
            male.setChecked(true);
        }


        if (!d_reg_proof.equalsIgnoreCase("null")) {

            Picasso.with(getApplicationContext())
                    .load(URLListner.BASEURLL + d_reg_proof)
                    .fit()
                    .centerCrop()
                    .into(imageView);
        } else {

            Picasso.with(getApplicationContext()).load(R.drawable.placeholder_user).resize(40, 40).into(imageView);
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


        sp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateProfile.this, CategoryListActivity.class);
                startActivity(intent);

            }
        });


        city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UpdateProfile.this, CityListActivity.class);
                startActivity(intent);

            }
        });


        //   sp.setText(cate_name);
        Reg_year.setOnClickListener(this);
        completion_year.setOnClickListener(this);
        imageView.setOnClickListener(this);
        imageView1.setOnClickListener(this);
        imageView2.setOnClickListener(this);
        imageView3.setOnClickListener(this);
        button.setOnClickListener(this);


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:

                onBackPressed();

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {


            case R.id.imageregistrationproof:
                selectImage(1);

                break;

            case R.id.imageequproof:
                selectImage(2);

                break;

            case R.id.imageidproof:
                selectImage(3);

                break;

            case R.id.imageproof:
                selectImage(4);

                break;
            case R.id.c_year:
                DatePickerDialog dd = new DatePickerDialog(UpdateProfile.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                try {
                                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                                    String dateInString = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                    Date date = formatter.parse(dateInString);

                                    completion_year.setText(formatter.format(date).toString());

                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }, year, month, day);


                dd.show();

                break;
            case R.id.r_year:

                DatePickerDialog ddd = new DatePickerDialog(UpdateProfile.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                try {
                                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                                    String dateInString = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                    Date date = formatter.parse(dateInString);

                                    Reg_year.setText(formatter.format(date).toString());

                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }, year, month, day);


                ddd.show();
                break;

            case R.id.create:
                MyUtility.hideKeyboard(sp, UpdateProfile.this);
                MyUtility.hideKeyboard(completion_year, UpdateProfile.this);
                MyUtility.hideKeyboard(Reg_year, UpdateProfile.this);
                MyUtility.hideKeyboard(city, UpdateProfile.this);
                myname = name.getText().toString();
                mycity = city.getText().toString();
                mycollage = collage.getText().toString();
                com_year = completion_year.getText().toString();
                r_number = Reg_number.getText().toString();
                r_year = Reg_year.getText().toString();
                myphone = phone.getText().toString();
                r_concil = Regi_concil.getText().toString();
                mydegree = degree.getText().toString();
                mypassword = password.getText().toString();
                int selectedGenderId = radioGroup.getCheckedRadioButtonId();
                maleORFemale = (RadioButton) findViewById(selectedGenderId);
                mgender = maleORFemale.getText().toString();
                myexp = exp.getText().toString();
                myaward = awards.getText().toString();
                myalter = alternate_number.getText().toString();
                mper_email = per_email.getText().toString();


                if (myname.equalsIgnoreCase("")) {


                    MyUtility.showSnack(parent, Validations.ENTER_NAME);

                } else if (mper_email.equalsIgnoreCase("")) {
                    MyUtility.showSnack(parent, Validations.ENTER_EMAIL);


                }/*else if (mypassword.equalsIgnoreCase("")) {


                    MyUtility.showSnack(parent, Validations.ENTER_PASSWORD);

                }*/ else if (myphone.equalsIgnoreCase("")) {


                    MyUtility.showSnack(parent, Validations.ENTER_MOBILE);

                } else if (myphone.length() < 10) {


                    MyUtility.showSnack(parent, Validations.ENTER_VALID_Mobile);


                } else if (myphone.length() > 10) {


                    MyUtility.showSnack(parent, Validations.ENTER_VALID_Mobile);
                } else if (mycity.equalsIgnoreCase("")) {

                    MyUtility.showSnack(parent, Validations.ENTER_CITY);

                } else if (mydegree.equalsIgnoreCase("")) {

                    MyUtility.showSnack(parent, "Enter Degree");

                } else if (mycollage.equalsIgnoreCase("")) {

                    MyUtility.showSnack(parent, "Enter Collage Name");

                } else if (com_year.equalsIgnoreCase("")) {

                    MyUtility.showSnack(parent, "Enter Completion Year");

                } else if (r_number.equalsIgnoreCase("")) {

                    MyUtility.showSnack(parent, "Enter Registration Number ");

                } else if (r_year.equalsIgnoreCase("")) {

                    MyUtility.showSnack(parent, "Enter Registration Year");

                } else if (r_concil.equalsIgnoreCase("")) {

                    MyUtility.showSnack(parent, "Enter Registration Council Number");

                } else {


                    if (MyUtility.isConnected(this)) {

                        callAPI();


                    } else {

                        MyUtility.showSnack(parent, MyUtility.INTERNET_ERROR);
                    }

                }
        }

    }

    private void callAPI() {


        if (!MyUtility.isConnected(this)) {
            //  MyUtility.showAlertInternet(this);
            return;
        }

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Updating Account....");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();


        MultipartBody.Part filePart = null;
        MultipartBody.Part filePart2 = null;
        MultipartBody.Part filePart3 = null;
        MultipartBody.Part filePart1 = null;



            if (file1 != null) {

                filePart = MultipartBody.Part.createFormData("d_reg_proof", file1.getName(), RequestBody.create(MediaType.parse("image*//*"), file1));
            }

            if (file2 != null) {

                filePart1 = MultipartBody.Part.createFormData("d_qua_proof", file2.getName(), RequestBody.create(MediaType.parse("image*//*"), file2));

            }

            if (file3 != null) {

                filePart2 = MultipartBody.Part.createFormData("d_id_proof", file3.getName(), RequestBody.create(MediaType.parse("image*//*"), file3));
            }


            if (file4 != null) {

                filePart3 = MultipartBody.Part.createFormData("doct_photo", file4.getName(), RequestBody.create(MediaType.parse("image*//*"), file4));
            }


            APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
            Call<JsonObject> call = apiInterface.participant_media(
                    toRequestBody(myname),
                    toRequestBody(mgender),
                    toRequestBody(mypassword),
                    toRequestBody(mydegree),
                    toRequestBody(mycollage),
                    toRequestBody(com_year),
                    toRequestBody(spec_id),
                    toRequestBody(myexp),
                    toRequestBody(mycity),
                    toRequestBody(r_number),
                    toRequestBody(r_concil),
                    toRequestBody(r_year),
                    toRequestBody(myphone),
                    toRequestBody(doctor_id),
                    toRequestBody(myaward),
                    toRequestBody(myalter),
                    toRequestBody(mper_email),
                    filePart,
                    filePart1,
                    filePart2,
                    filePart3
            );


            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                    // Log.e("profile:", new Gson().toJson(response.body()));

                    if (dialog.isShowing()) {

                        dialog.dismiss();

                    }


                    switch (response.code()) {

                        case 200:


                            JsonObject jsonObject = response.body();


                            if (jsonObject != null) {

                                JsonArray array = jsonObject.getAsJsonArray("profile");

                                if (array.size() != 0) {

                                    Toast.makeText(UpdateProfile.this, "Sucessfully Updated", Toast.LENGTH_SHORT).show();
                                    finish();
                                    Intent loginIntent = new Intent(UpdateProfile.this, HomeActivity.class);
                                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(loginIntent);


                                } else {

                                    MyUtility.showSnack(parent, "Data Not Updated");

                                }

                                //  Log.e("Data:", new Gson().toJson(response.body()));

                            } else {

                                //  MyUtility.showAlertMessage(UploadData.this, jsonObject.get("error").toString());
                            }


                            break;


                    }

                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    call.cancel();
                    if (dialog.isShowing()) {

                        dialog.dismiss();

                    }
                    MyUtility.showSnack(parent, MyUtility.INTERNET_ERROR);

                }
            });


        }


    public static RequestBody toRequestBody(String value) {
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), value);
        return body;
    }




    //................OPEN SELECT IMAGE ALERT BOX...........
    private void selectImage(final int number) {

        final CharSequence[] items = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.alert_dialog_custom_title, null);
        TextView title = (TextView) view.findViewById(R.id.alertTitle);
        title.setText("Add Photo!");
        builder.setCustomTitle(view);

        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                boolean result = Utility.checkPermission(UpdateProfile.this);

                if (items[item].equals("Take Photo")) {

                    if (result)
                        cameraIntent(number);

                } else if (items[item].equals("Choose from Gallery")) {

                    if (result)
                        galleryIntent(number);

                } else if (items[item].equals("Cancel")) {

                    dialog.dismiss();
                }
            }
        });

        builder.show();
    }


    //.................TAKE IMAGE FROM CAMERA..............
    private void cameraIntent(int i) {

        selectedImage=i;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = Utility.getOutputMediaFileUri();
         intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        //intent.setType("image*//*");

        startActivityForResult(intent, REQUEST_CAMERA);
    }


    //.................CHOOSE IMAGE FROM GALLERY..............
    private void galleryIntent(int i) {
        selectedImage = i;
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == REQUEST_CAMERA && data != null && data.getData() != null) {

              //  Uri selectedImageUri = data.getData();
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), fileUri);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // External sdcard location
                File mediaStorageDir = new File(
                        Environment
                                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                        getString(R.string.app_name)); //Create folder fitkitchen in SD-Card

                // Create the storage directory if it does not exist
                if (!mediaStorageDir.exists()) {
                    if (!mediaStorageDir.mkdirs()) {
                    }
                }

                String title = String.valueOf(System.currentTimeMillis());
                File destination = new File(mediaStorageDir.getPath(),
                        title + ".jpg");
                FileOutputStream fo;
                try {


                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();

                   // addImages.add(destination);


                    switch (selectedImage) {

                        case 1:
                            Picasso.with(this)
                                    .load(destination)
                                    .resize(100, 100)
                                    .centerCrop()
                                    .placeholder(R.drawable.placeholder_user)
                                    .into(imageView);

                            file1=destination;

                            break;

                        case 2:
                            Picasso.with(this)
                                    .load(destination)
                                    .resize(100, 100)
                                    .centerCrop()
                                    .placeholder(R.drawable.placeholder_user)
                                    .into(imageView1);

                            file2=destination;

                            break;

                        case 3:
                            Picasso.with(this)
                                    .load(destination)
                                    .resize(100, 100)
                                    .centerCrop()
                                    .placeholder(R.drawable.placeholder_user)
                                    .into(imageView2);

                            file3=destination;

                            break;
                        case 4:
                            Picasso.with(this)
                                    .load(destination)
                                    .resize(100, 100)
                                    .centerCrop()
                                    .placeholder(R.drawable.placeholder_user)
                                    .into(imageView3);

                            file4=destination;

                            break;

                    }


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            } else if (requestCode == SELECT_FILE) {

               /*

                switch (selectedImage) {

                    case 1:
                        Picasso.with(this)
                                .load(selectedImageUri)
                                .resize(100, 100)
                                .centerCrop()
                                .placeholder(R.drawable.placeholder_user)
                                .into(imageView);
                        break;

                    case 2:
                        Picasso.with(this)
                                .load(selectedImageUri)
                                .resize(100, 100)
                                .centerCrop()
                                .placeholder(R.drawable.placeholder_user)
                                .into(imageView1);
                        break;

                    case 3:
                        Picasso.with(this)
                                .load(selectedImageUri)
                                .resize(100, 100)
                                .centerCrop()
                                .placeholder(R.drawable.placeholder_user)
                                .into(imageView2);
                        break;
                    case 4:
                        Picasso.with(this)
                                .load(selectedImageUri)
                                .resize(100, 100)
                                .centerCrop()
                                .placeholder(R.drawable.placeholder_user)
                                .into(imageView3);
                        break;

                }

                */


                Uri selectedImageUri = data.getData();
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();


                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), selectedImageUri);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                // External sdcard location
                File mediaStorageDir = new File(
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                        getString(R.string.app_name));//Create folder fitkitchen in SD-Card

                // Create the storage directory if it does not exist
                if (!mediaStorageDir.exists()) {
                    if (!mediaStorageDir.mkdirs()) {
                    }
                }

                String title = String.valueOf(System.currentTimeMillis());
                File destination = new File(mediaStorageDir.getPath(),
                        title + ".jpg");

                FileOutputStream fo;
                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();

                    //addImages.add(destination);

                    switch (selectedImage) {

                        case 1:
                            Picasso.with(this)
                                    .load(destination)
                                    .resize(100, 100)
                                    .centerCrop()
                                    .placeholder(R.drawable.placeholder_user)
                                    .into(imageView);

                            file1=destination;

                            break;

                        case 2:
                            Picasso.with(this)
                                    .load(destination)
                                    .resize(100, 100)
                                    .centerCrop()
                                    .placeholder(R.drawable.placeholder_user)
                                    .into(imageView1);

                            file2=destination;

                            break;

                        case 3:
                            Picasso.with(this)
                                    .load(destination)
                                    .resize(100, 100)
                                    .centerCrop()
                                    .placeholder(R.drawable.placeholder_user)
                                    .into(imageView2);

                            file3=destination;

                            break;
                        case 4:
                            Picasso.with(this)
                                    .load(destination)
                                    .resize(100, 100)
                                    .centerCrop()
                                    .placeholder(R.drawable.placeholder_user)
                                    .into(imageView3);

                            file4=destination;

                            break;

                    }


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }


}
