package com.example.saidur.supperquiz.domain;

import java.util.List;

public class Model_Quiz {

    List<Model_Question> questions;

    public Model_Quiz() {
    }

    public List<Model_Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Model_Question> questions) {
        this.questions = questions;
    }
}
