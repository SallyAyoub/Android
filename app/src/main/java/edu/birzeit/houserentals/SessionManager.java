package edu.birzeit.houserentals;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.Intent;

public class SessionManager {
    SharedPreferences sharedPreferencese;
    SharedPreferences.Editor editor;
    String Shared_Pref_Name = "session";
    String SESSION_KEY = "session_user";
    public SessionManager(Context context){
        sharedPreferencese = context.getSharedPreferences(Shared_Pref_Name,Context.MODE_PRIVATE);
        editor = sharedPreferencese.edit();
        editor.apply();
    }
    public void saveSession(String email){
        // to save the session for the user
        editor.putString(SESSION_KEY,email).commit();

    }
    public String getSession(){
        // returns the email of the user which is currently active on the session
        return sharedPreferencese.getString(SESSION_KEY, null);
    }
    public void removeSession(){
        editor.putString(SESSION_KEY,null).commit();
    }
}
