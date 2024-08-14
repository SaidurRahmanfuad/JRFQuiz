package com.example.saidur.supperquiz.domain;

public class Model_Question {
    String question;
    Model_Answer answers;
    String questionImageUrl;
    String correctAnswer;
    int score;

    public Model_Question() {
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Model_Answer getAnswers() {
        return answers;
    }

    public void setAnswers(Model_Answer answers) {
        this.answers = answers;
    }

    public String getQuestionImageUrl() {
        return questionImageUrl;
    }

    public void setQuestionImageUrl(String questionImageUrl) {
        this.questionImageUrl = questionImageUrl;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
