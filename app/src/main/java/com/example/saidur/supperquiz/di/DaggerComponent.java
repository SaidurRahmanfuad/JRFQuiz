package com.example.saidur.supperquiz.di;

import com.example.saidur.supperquiz.data.Repository.Question_repository;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class,AppModule.class})
public interface DaggerComponent {

    public void inject(Question_repository questionRepository);
}
