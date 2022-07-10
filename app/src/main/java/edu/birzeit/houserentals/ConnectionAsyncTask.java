package edu.birzeit.houserentals;

import android.app.Activity;
import android.os.AsyncTask;

import java.util.List;

public class ConnectionAsyncTask extends AsyncTask<String, String, String> {
    Activity activity;

    public ConnectionAsyncTask(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        ((MainActivity) activity).setButtonText("connecting");
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        return HttpManeger.getData(params[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if (s != null) {
            List<Property>  properties = PropertyJSONParser.getObjectFromJason(s);
            ((MainActivity) activity).addPropertiesToDataBase( properties);
            ((MainActivity) activity).moveToSignInPage();
        }
        else{
            ((MainActivity) activity).setButtonText("Connect");
            ((MainActivity) activity).showNullMessage();
        }
    }
}