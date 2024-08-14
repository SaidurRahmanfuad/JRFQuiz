package com.example.saidur.supperquiz.data.Repository;

import android.content.Context;
import androidx.lifecycle.MutableLiveData;

import com.example.saidur.supperquiz.data.Network.Api_Service;
import com.example.saidur.supperquiz.di.BaseApplication;
import com.example.saidur.supperquiz.domain.Model_Quiz;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Question_repository {

    Context context;
    private MutableLiveData<Model_Quiz> liveData;
    Api_Service apiService;

    @Inject
    Retrofit retrofit;
    @Inject
    public Question_repository(Context context) {
        this.context = context;
        ((BaseApplication)context.getApplicationContext()).getDaggerComponent().inject(this);
    }


    public MutableLiveData<Model_Quiz> getAllQuestion(){

        liveData =new MutableLiveData<>();

        apiService = retrofit.create(Api_Service.class);
        apiService.GetQuestions().enqueue(new Callback<Model_Quiz>() {
            @Override
            public void onResponse(Call<Model_Quiz> call, Response<Model_Quiz> response) {
                if(response.isSuccessful()){
                    liveData.postValue(response.body());
                }else {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<Model_Quiz> call, Throwable throwable) {

            }
        });


        return liveData;
    }



}
