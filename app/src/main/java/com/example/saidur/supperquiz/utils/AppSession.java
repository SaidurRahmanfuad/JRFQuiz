package com.example.saidur.supperquiz.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class AppSession {
    // Shared Preferences
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "JRFTaskSession";
    private static final String KEY_HighScore = "HIGHScore";
    private static final String KEY_AnsweredQs = "AnsweredQuestion";
    private static final String KEY_GainedPoint = "GainedPoint";

    public AppSession(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void saveHighscoreAppSession(int score){
        editor.putInt(KEY_HighScore, score);
        editor.commit();
    }
    public void saveAnswerQs(int ans){
        editor.putInt(KEY_AnsweredQs, ans);
        editor.commit();
    }
    public void savePoint(int point){
        editor.putInt(KEY_GainedPoint, point);
        editor.commit();
    }

    /**
     * Get stored session data
     * */


    public int getHighScore(){
        return  pref.getInt(KEY_HighScore, 0);
    }
    public int getAnsweredQs(){
        return  pref.getInt(KEY_AnsweredQs, 0);
    }
    public int getGainedPoint(){
        return  pref.getInt(KEY_GainedPoint, 0);
    }

    public void clear_saved(){
        pref.edit().remove(KEY_AnsweredQs).commit();
        pref.edit().remove(KEY_GainedPoint).commit();
    }
    public void clear_highscore(){
        pref.edit().remove(KEY_HighScore).commit();
    }

}
