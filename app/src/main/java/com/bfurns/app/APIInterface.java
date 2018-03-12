package com.bfurns.app;

import com.google.gson.JsonObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public  interface APIInterface {


    @Multipart
    @POST("edit_doctor_profile")
    Call<JsonObject> participant_media(@Part("user_fullname") RequestBody user_fullname,
                                       @Part("d_gender") RequestBody d_gender,
                                       @Part("user_password") RequestBody user_password,
                                       @Part("doct_degree") RequestBody doct_degree,
                                       @Part("doct_college") RequestBody doct_college,
                                       @Part("doct_year") RequestBody doct_year,
                                       @Part("doct_speciality") RequestBody doct_speciality,
                                       @Part("doct_experience") RequestBody doct_experience,
                                       @Part("city") RequestBody city,
                                       @Part("d_reg_no") RequestBody d_reg_no,
                                       @Part("d_reg_con") RequestBody d_reg_con,
                                       @Part("d_reg_year") RequestBody d_reg_year,
                                       @Part("user_phone") RequestBody user_phone,
                                       @Part("doct_id") RequestBody doct_id,
                                       @Part("awards") RequestBody awards,
                                       @Part("other_number") RequestBody other_number,
                                       @Part("doct_email") RequestBody doct_email,
                                       @Part MultipartBody.Part file, @Part MultipartBody.Part file1, @Part MultipartBody.Part file2, @Part MultipartBody.Part file3);


}