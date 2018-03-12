package com.bfurns.application;


import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.bfurns.utility.FontsOverride;
import com.bfurns.utility.MyPreferences;
import com.bfurns.utility.Preferences;


import java.util.ArrayList;


/**
 * Created by Mahesh on 27/11/15.
 */
public class AppClass extends Application {


    private static SharedPreferences sharedPreferences;
    boolean checkee = true;


    @Override
    public void onCreate() {
        super.onCreate();

        sharedPreferences = getSharedPreferences(Preferences.MyPREFERENCES, Context.MODE_PRIVATE);


        FontsOverride.setDefaultFont(this, "DEFAULT", "proxima.ttf");
        FontsOverride.setDefaultFont(this, "MONOSPACE", "proxima.ttf");
        FontsOverride.setDefaultFont(this, "SERIF", "proxima.ttf");
        FontsOverride.setDefaultFont(this, "SANS_SERIF", "proxima.ttf");

    }


    public static void setsession(String id, String email, String username, String phone, String image, String clinic_id) {
        SharedPreferences.Editor editor = sharedPreferences.edit();


        editor.putString(MyPreferences.USER_ID, id);
        editor.putString(MyPreferences.USER_NAME, username);
        editor.putString(MyPreferences.USER_EMAIL, email);
        editor.putString(MyPreferences.USER_PHONE, phone);
        editor.putString(MyPreferences.USER_IMAGE, image);
        editor.putString(MyPreferences.CLINIC_ID, clinic_id);
      //  editor.clear();
        editor.commit();
        editor.apply();


    }


    public static void setProfileSession(String doctor_id, String doct_experience, String email, String doctor_name, String gender, String doct_degree, String doct_college, String doct_year,
                                         String doct_phone, String d_city, String d_reg_no, String d_reg_con, String d_reg_year,
                                         String d_reg_proof, String d_qua_proof, String d_id_proof, String doct_photo, String doct_speciality, String passwd, String doctor_email, String spec_id,
                                         String Alternate_number, String Award) {
        SharedPreferences.Editor editor = sharedPreferences.edit();


        editor.putString(MyPreferences.USER_ID, doctor_id);
        editor.putString(MyPreferences.USER_FULLNAME, doctor_name);
        editor.putString(MyPreferences.USER_EMAIL, email);
        editor.putString(MyPreferences.CONTACT, doct_phone);
        editor.putString(MyPreferences.r_proof_photo, d_reg_proof);
        editor.putString(MyPreferences.r_proof_qualification, d_qua_proof);
        editor.putString(MyPreferences.r_proof_id, d_id_proof);
        editor.putString(MyPreferences.doct_photo, doct_photo);
        editor.putString(MyPreferences.CITY, d_city);
        editor.putString(MyPreferences.doct_experience, doct_experience);
        editor.putString(MyPreferences.d_reg_no, d_reg_no);
        editor.putString(MyPreferences.d_reg_con, d_reg_con);
        editor.putString(MyPreferences.d_reg_year, d_reg_year);
        editor.putString(MyPreferences.GENDER, gender);
        editor.putString(MyPreferences.doct_speciality, doct_speciality);
        editor.putString(MyPreferences.doct_degree, doct_degree);
        editor.putString(MyPreferences.doct_college, doct_college);
        editor.putString(MyPreferences.doct_year, doct_year);
        editor.putString(MyPreferences.DOCTOR_EMAIL, doctor_email);
        editor.putString(MyPreferences.password, passwd);
        editor.putString(MyPreferences.SPECIALIZATION_ID, spec_id);
        editor.putString(MyPreferences.ALTERNATE_NUMBER, Alternate_number);
        editor.putString(MyPreferences.AWARDS, Award);


        editor.commit();
        editor.apply();


    }



    public static void setuserid(String user_id) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(MyPreferences.USER_ID, user_id);
      // editor.clear();
        editor.commit();
        editor.apply();


    }



    public static void setspeciality_iid(String spec_id) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(MyPreferences.SPECIALIZATION_ID, spec_id);

        editor.commit();
        editor.apply();


    }

    public static void setcomman_account_status(String status) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(MyPreferences.COMMAN_ACCOUNT_STAUS, status);
      //  editor.clear();
        editor.commit();
        editor.apply();


    }

    public static String getcomman_account_status() {
        return sharedPreferences.getString(MyPreferences.COMMAN_ACCOUNT_STAUS, "");

    }

    public static void setsessionclinic_doctor_id(String doct_id) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(MyPreferences.CLINIC_DOCTOR_ID, doct_id);

        editor.commit();
        editor.apply();

    }

    public static String getsessionclinic_doctor_id() {
        return sharedPreferences.getString(MyPreferences.CLINIC_DOCTOR_ID, "");

    }

    public static void setsessionmerge(String doctor_id) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(MyPreferences.MERGE_DOCTOR_IDS, doctor_id);

        editor.commit();
        editor.apply();


    }

    public static String getmerge_doctor_id() {
        return sharedPreferences.getString(MyPreferences.MERGE_DOCTOR_IDS, "");

    }

    public static String getAward() {
        return sharedPreferences.getString(MyPreferences.AWARDS, "");

    }

    public static String getAlternateNumber() {
        return sharedPreferences.getString(MyPreferences.ALTERNATE_NUMBER, "");

    }

    public static String getCITY() {
        return sharedPreferences.getString(MyPreferences.CITY, "");

    }

    public static String getUSER_FULLNAME() {
        return sharedPreferences.getString(MyPreferences.USER_FULLNAME, "");

    }

    public static String getGENDER() {
        return sharedPreferences.getString(MyPreferences.GENDER, "");

    }

    public static String getr_proof_photo() {
        return sharedPreferences.getString(MyPreferences.r_proof_photo, "");

    }


    public static String getr_proof_qualification() {
        return sharedPreferences.getString(MyPreferences.r_proof_qualification, "");

    }

    public static String getr_proof_id() {
        return sharedPreferences.getString(MyPreferences.r_proof_id, "");

    }

    public static String getdoct_photo() {
        return sharedPreferences.getString(MyPreferences.doct_photo, "");

    }

    public static String getdoct_experience() {
        return sharedPreferences.getString(MyPreferences.doct_experience, "");

    }

    public static String getd_reg_no() {
        return sharedPreferences.getString(MyPreferences.d_reg_no, "");

    }


    public static String getd_reg_con() {
        return sharedPreferences.getString(MyPreferences.d_reg_con, "");

    }

    public static String getd_reg_year() {
        return sharedPreferences.getString(MyPreferences.d_reg_year, "");

    }


    public static String getdoct_speciality() {
        return sharedPreferences.getString(MyPreferences.doct_speciality, "");

    }


    public static String getdoct_degree() {
        return sharedPreferences.getString(MyPreferences.doct_degree, "");

    }

    public static String getdoct_college() {
        return sharedPreferences.getString(MyPreferences.doct_degree, "");

    }

    public static String getdoct_year() {
        return sharedPreferences.getString(MyPreferences.doct_year, "");

    }

    public static String getDOCTOR_EMAIL() {
        return sharedPreferences.getString(MyPreferences.DOCTOR_EMAIL, "");

    }

    public static String getpassword() {
        return sharedPreferences.getString(MyPreferences.password, "");

    }


    public static String getSPECIALIZATION_ID() {
        return sharedPreferences.getString(MyPreferences.SPECIALIZATION_ID, "");

    }

    public static String getuserId() {//doctorId
        return sharedPreferences.getString(MyPreferences.USER_ID, "");

    }

    public static String getemail() {
        return sharedPreferences.getString(MyPreferences.USER_EMAIL, "");

    }

    public static String getUsername() {
        return sharedPreferences.getString(MyPreferences.USER_NAME, "");

    }

    public static String getphone() {
        return sharedPreferences.getString(MyPreferences.CONTACT, "");

    }

    public static String getImage() {
        return sharedPreferences.getString(MyPreferences.USER_IMAGE, "");

    }

    public static String setUserName() {
        return sharedPreferences.getString(MyPreferences.USER_NAME, "");

    }

    public static void setClinic_name(String clinic_name) {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MyPreferences.CLINIC_NAME, clinic_name);
        editor.commit();
        editor.apply();


    }

    public static String getclinic_name() {
        return sharedPreferences.getString(MyPreferences.CLINIC_NAME, "");

    }

    public static void setClinic_id(String clinic_id) {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MyPreferences.CLINIC_ID, clinic_id);
      //  editor.clear();
        editor.commit();
        editor.apply();


    }

    public static String getclinicId() {
        return sharedPreferences.getString(MyPreferences.CLINIC_ID, "");

    }


    public static void setDoctorId(String doctor_id) {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MyPreferences.DOCTOR_ID, doctor_id);
        editor.commit();
        editor.apply();


    }

    public static String getdoctor_id() {
        return sharedPreferences.getString(MyPreferences.DOCTOR_ID, "");

    }


    public static void setQueryId(String queryId) {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MyPreferences.QUERY_ID, queryId);
        editor.commit();
        editor.apply();


    }


    public static String getqueryId() {
        return sharedPreferences.getString(MyPreferences.QUERY_ID, "");

    }


    public static void setAppointmentId(String app_id) {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MyPreferences.ID, app_id);
        editor.commit();
        editor.apply();


    }

    public static String getAppointmentId() {
        return sharedPreferences.getString(MyPreferences.ID, "");

    }


    public static void setSubID(String subId) {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MyPreferences.sub_id, subId);
        editor.commit();
        editor.apply();


    }

    public static String getSubId() {
        return sharedPreferences.getString(MyPreferences.sub_id, "");

    }


    public static void setAppStatus(String appStatus) {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MyPreferences.APP_STATUS, appStatus);
        editor.commit();

    }

    public static String getAppStatus() {
        return sharedPreferences.getString(MyPreferences.APP_STATUS, "");

    }


    public static void SetUUserId(String userIdApp) {//userId

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MyPreferences.UUserIdApp, userIdApp);
        editor.commit();

    }

    public static String getUUserId() {
        return sharedPreferences.getString(MyPreferences.UUserIdApp, "");

    }

    public static void setMergeUserId(String merge_user_id) {//userId

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MyPreferences.MergeUserId, merge_user_id);
        editor.commit();

    }

    public static String getMergeUserId() {
        return sharedPreferences.getString(MyPreferences.MergeUserId, "");

    }

    public static void setCITY(String city) {//userId

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MyPreferences.CITY, city);
        editor.commit();

    }

    public static void setdoctorspeciality(String speciality) {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MyPreferences.doct_speciality, speciality);
        editor.commit();

    }


}
