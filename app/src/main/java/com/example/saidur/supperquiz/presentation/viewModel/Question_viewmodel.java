package com.example.saidur.supperquiz.presentation.viewModel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.saidur.supperquiz.data.Repository.Question_repository;
import com.example.saidur.supperquiz.domain.Model_Quiz;

import javax.inject.Inject;

public class Question_viewmodel extends ViewModel {

   Context context;
   private Question_repository repository;
   private MutableLiveData<Model_Quiz> finalQs;

   @Inject
    public Question_viewmodel(Context context) {
        this.context = context;
        repository=new Question_repository(context);
        finalQs=new MutableLiveData<>();
    }

    public MutableLiveData<Model_Quiz> getFinalQs() {
       finalQs=repository.getAllQuestion();
        return finalQs;
    }

}
