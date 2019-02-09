package com.fai.alarmapp;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Fai on 09/02/2019.
 */

public class AlarmPreference {
    private final String PREF_NAME = "AlarmPreference";
    private final String KEY_ONE_TIME_DATE = "oneTimeDate";
    private final String KEY_ONE_TIME_TIME = "oneTimeTime";
    private final String KEY_ONE_TIME_NOTE = "oneTimeNote";
    private final String KEY_REPEATING_TIME = "repeatingTime";
    private final String KEY_REPEATING_NOTE = "repeatingNote";

    private SharedPreferences mSharedPreference;
    private SharedPreferences.Editor editor;

    public AlarmPreference(Context context){
        mSharedPreference = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = mSharedPreference.edit();
    }

    public void setOneTimeDate(String date){
        editor.putString(KEY_ONE_TIME_DATE, date);
        editor.commit();
    }

    public String getOneTimeDate(){
        return mSharedPreference.getString(KEY_ONE_TIME_DATE, null);
    }

    public void setOneTimeTime(String time){
        editor.putString(KEY_ONE_TIME_TIME, time);
        editor.commit();
    }

    public String getOneTimeTime(){
        return mSharedPreference.getString(KEY_ONE_TIME_TIME, null);
    }

    public void setOneTimeNote(String note){
        editor.putString(KEY_ONE_TIME_NOTE, note);
        editor.commit();
    }

    public String getOneTimeNote(){
        return mSharedPreference.getString(KEY_ONE_TIME_NOTE, null);
    }

    public void setRepeatingTime(String time){
        editor.putString(KEY_REPEATING_TIME, time);
        editor.commit();
    }

    public String getRepeatingTime(){
        return mSharedPreference.getString(KEY_REPEATING_TIME, null);
    }

    public void setRepeatingNote(String note){
        editor.putString(KEY_REPEATING_NOTE, note);
        editor.commit();
    }

    public String getRepeatingNote(){
        return mSharedPreference.getString(KEY_REPEATING_NOTE, null);
    }

    public void clear(){
        editor.clear();
        editor.commit();
    }

}
