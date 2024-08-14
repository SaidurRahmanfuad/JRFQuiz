package com.example.saidur.supperquiz.di;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    private String BaseUrl;
    public NetworkModule(String baseUrl) {
        this.BaseUrl=baseUrl;
    }


    @Provides
    @Singleton
    Gson provideGson(){
        GsonBuilder gson=new GsonBuilder();
        return gson.create();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson) {

        return new Retrofit.Builder().
                addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BaseUrl)
                .build();
    }
}
