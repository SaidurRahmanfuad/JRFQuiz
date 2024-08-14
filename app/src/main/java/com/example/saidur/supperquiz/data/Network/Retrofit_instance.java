package com.example.saidur.supperquiz.data.Network;

import com.example.saidur.supperquiz.utils.Consts;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit_instance {


    private static Retrofit retrofit = null;
    public static Retrofit getRetrofitInstance() {
        OkHttpClient clientz =new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1,TimeUnit.MINUTES)
                .readTimeout(1,TimeUnit.MINUTES)
                .build();
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Consts.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(clientz)
                    .build();
        }
        return retrofit;
    }


    private void getQuizApi(){
        retrofit.create(Api_Service.class);
    }
}
