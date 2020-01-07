package com.example.instorage_caller.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.example.instorage_caller.retrofit.model.SyncInfo;
import com.google.gson.Gson;


/**
 * Created by Hi on 1/5/2018.
 */

public class SaveInformationUtil {

    public static final String USER_INFORMATION_CONSTANT = "syncinformation";

    public static void saveSyncInfo(Context context, SyncInfo syncInfo){

        try {

            SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences (context);
            SharedPreferences.Editor prefsEditor = mPrefs.edit ();
            Gson gson = new Gson ();
            String json = gson.toJson (syncInfo);
            prefsEditor.putString (USER_INFORMATION_CONSTANT, json);
            prefsEditor.commit ();
        } catch (Exception ex){

        }
    }

    public static SyncInfo getSyncInfo(Context context){

        try {
            SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences( context);
            Gson gson = new Gson();
            String json = mPrefs.getString(USER_INFORMATION_CONSTANT, "");
            if (json != null && json.length() > 1  ){
                return  gson.fromJson(json, SyncInfo.class);
            }
            return null ;
        } catch (Exception ex){
            return null;
        }

    }

    public static void clearUserInfo(Context context){

        try{
            SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences( context);
            SharedPreferences.Editor prefsEditor = mPrefs.edit();
            prefsEditor.remove(USER_INFORMATION_CONSTANT);
            prefsEditor.commit();
        } catch (Exception ex){

        }

    }

}
