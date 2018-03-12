package com.bfurns.app;

import com.bfurns.utility.URLListner;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class APIClient {

    private static Retrofit retrofit = null;

    public static String id;

    public static String USER_IMAGE_URL="http://unichronic.com/navbharat_api/uploads/profile/";
    public static String USER_THAMB_IMAGE_URL="http://unichronic.com/navbharat_api/uploads/profile/thumbs/";

    public static String AWARD_IMAGE_URL="http://localhost/navbharat/uploads/award/";
    public static String AWARD_THAMB_IMAGE_URL="http://localhost/navbharat/uploads/award/thumbs/";


    public  static Retrofit getClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(2, TimeUnit.MINUTES)
                .writeTimeout(2, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES);
        builder.addInterceptor(interceptor).build();
        OkHttpClient client = builder.build();

        retrofit = new Retrofit.Builder()
                .baseUrl(URLListner.BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit;
    }

}
