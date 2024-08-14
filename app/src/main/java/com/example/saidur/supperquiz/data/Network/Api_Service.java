package com.example.saidur.supperquiz.data.Network;

import com.example.saidur.supperquiz.domain.Model_Quiz;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api_Service {
    @GET("/quiz.json")
    Call<Model_Quiz> GetQuestions();
}
