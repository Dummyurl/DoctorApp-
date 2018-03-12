package com.bfurns.model;

import android.content.SharedPreferences;

/**
 * Created by Explicate1 on 12/9/2017.
 */

public class AppointementModel {

    String time,patient_name,mode_of_appointement,patent_category,image,date,email,contact,user_id,id,sub_user_id,bus_id,doct_id,app_staus,clinic_name;
String payed_amount,remaining_amount,total_amount,doctor_name,gender,bdy,doct_degree,doct_college,doct_year,doct_phone,doct_experience,city,d_reg_no,d_reg_con,d_reg_year,d_reg_proof,d_qua_proof,d_id_proof,doct_photo,doct_about,doct_status,doct_speciality;
String monthly_revenue,weekly_revenue,daily_revenue;
String Strength,take,slot,drug_name,duration,quantity,app_type,city_name;
String test_name;

    private static final String PREFERENCES_NAMESPACE = "checkboxes_states";

    String recomandation = null;
    boolean selected = false;
    private SharedPreferences mSettings;
    private SharedPreferences.Editor mEditor;

    int count;

    public String getRecomandation() {
        return recomandation;
    }

    public void setRecomandation(String recomandation) {
        this.recomandation = recomandation;
    }

    public boolean isSelected() {
        return selected;
    }

   

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getTest_name() {
        return test_name;
    }

    public void setTest_name(String test_name) {
        this.test_name = test_name;
    }

    public String getApp_type() {
        return app_type;
    }

    public void setApp_type(String app_type) {
        this.app_type = app_type;
    }

    public String getDoct_speciality() {
        return doct_speciality;
    }

    public void setDoct_speciality(String doct_speciality) {
        this.doct_speciality = doct_speciality;
    }

    public String getDoct_degree() {
        return doct_degree;
    }

    public void setDoct_degree(String doct_degree) {
        this.doct_degree = doct_degree;
    }

    public String getDoct_college() {
        return doct_college;
    }

    public void setDoct_college(String doct_college) {
        this.doct_college = doct_college;
    }

    public String getDoct_year() {
        return doct_year;
    }

    public void setDoct_year(String doct_year) {
        this.doct_year = doct_year;
    }

    public String getDoct_phone() {
        return doct_phone;
    }

    public void setDoct_phone(String doct_phone) {
        this.doct_phone = doct_phone;
    }

    public String getDoct_experience() {
        return doct_experience;
    }

    public void setDoct_experience(String doct_experience) {
        this.doct_experience = doct_experience;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getD_reg_no() {
        return d_reg_no;
    }

    public void setD_reg_no(String d_reg_no) {
        this.d_reg_no = d_reg_no;
    }

    public String getD_reg_con() {
        return d_reg_con;
    }

    public void setD_reg_con(String d_reg_con) {
        this.d_reg_con = d_reg_con;
    }

    public String getD_reg_year() {
        return d_reg_year;
    }

    public void setD_reg_year(String d_reg_year) {
        this.d_reg_year = d_reg_year;
    }

    public String getD_reg_proof() {
        return d_reg_proof;
    }

    public void setD_reg_proof(String d_reg_proof) {
        this.d_reg_proof = d_reg_proof;
    }

    public String getD_qua_proof() {
        return d_qua_proof;
    }

    public void setD_qua_proof(String d_qua_proof) {
        this.d_qua_proof = d_qua_proof;
    }

    public String getD_id_proof() {
        return d_id_proof;
    }

    public void setD_id_proof(String d_id_proof) {
        this.d_id_proof = d_id_proof;
    }

    public String getDoct_photo() {
        return doct_photo;
    }

    public void setDoct_photo(String doct_photo) {
        this.doct_photo = doct_photo;
    }

    public String getDoct_about() {
        return doct_about;
    }

    public void setDoct_about(String doct_about) {
        this.doct_about = doct_about;
    }

    public String getDoct_status() {
        return doct_status;
    }

    public void setDoct_status(String doct_status) {
        this.doct_status = doct_status;
    }

    public String getBdy() {
        return bdy;
    }

    public void setBdy(String bdy) {
        this.bdy = bdy;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStrength() {
        return Strength;
    }

    public void setStrength(String strength) {
        Strength = strength;
    }

    public String getTake() {
        return take;
    }

    public void setTake(String take) {
        this.take = take;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public String getDrug_name() {
        return drug_name;
    }

    public void setDrug_name(String drug_name) {
        this.drug_name = drug_name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getMonthly_revenue() {
        return monthly_revenue;
    }

    public void setMonthly_revenue(String monthly_revenue) {
        this.monthly_revenue = monthly_revenue;
    }

    public String getWeekly_revenue() {
        return weekly_revenue;
    }

    public void setWeekly_revenue(String weekly_revenue) {
        this.weekly_revenue = weekly_revenue;
    }

    public String getDaily_revenue() {
        return daily_revenue;
    }

    public void setDaily_revenue(String daily_revenue) {
        this.daily_revenue = daily_revenue;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getPayed_amount() {
        return payed_amount;
    }

    public void setPayed_amount(String payed_amount) {
        this.payed_amount = payed_amount;
    }

    public String getRemaining_amount() {
        return remaining_amount;
    }

    public void setRemaining_amount(String remaining_amount) {
        this.remaining_amount = remaining_amount;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getClinic_name() {
        return clinic_name;
    }

    public void setClinic_name(String clinic_name) {
        this.clinic_name = clinic_name;
    }

    public String getApp_staus() {
        return app_staus;
    }

    public void setApp_staus(String app_staus) {
        this.app_staus = app_staus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSub_user_id() {
        return sub_user_id;
    }

    public void setSub_user_id(String sub_user_id) {
        this.sub_user_id = sub_user_id;
    }

    public String getBus_id() {
        return bus_id;
    }

    public void setBus_id(String bus_id) {
        this.bus_id = bus_id;
    }

    public String getDoct_id() {
        return doct_id;
    }

    public void setDoct_id(String doct_id) {
        this.doct_id = doct_id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public String getMode_of_appointement() {
        return mode_of_appointement;
    }

    public void setMode_of_appointement(String mode_of_appointement) {
        this.mode_of_appointement = mode_of_appointement;
    }

    public String getPatent_category() {
        return patent_category;
    }

    public void setPatent_category(String patent_category) {
        this.patent_category = patent_category;
    }


    public void setSelected(boolean selected) {
        if(this.selected != selected) { // update if changed
            mEditor.putBoolean(getRecomandation(), selected);
            mEditor.commit();
            this.selected = selected;
        }
    }
}
