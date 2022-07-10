package edu.birzeit.houserentals;


import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    private static final String SHARED_PREF_NAME = "HouseRent Shared Preference";
    private static final int SHARED_PREF_PRIVATE = Context.MODE_PRIVATE;
    private static SharedPrefManager newInstance = null;
    private static SharedPreferences sharedPreferences = null;
    private SharedPreferences.Editor editor = null;

    static SharedPrefManager getInstance(Context context) {
        if (newInstance != null) {
            return newInstance;
        }
       newInstance = new SharedPrefManager(context);
        return newInstance;
    }

    private SharedPrefManager(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, SHARED_PREF_PRIVATE);
        editor = sharedPreferences.edit();
        editor.apply();
    }

    public boolean writeString(String key, String value) {
        editor.putString(key, value);
        return editor.commit();
    }

    public String readString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }
}
