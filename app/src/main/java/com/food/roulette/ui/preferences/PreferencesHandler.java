package com.food.roulette.ui.preferences;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class PreferencesHandler {


    JSONObject userPref;

    public PreferencesHandler()
    {
    }
    public PreferencesHandler(Context context)
    {
        userPref = getUserPref(context);
    }

    public JSONObject getUserPref(Context context)
    {
        JSONObject json = null;
        try{
            File userJsonFile = new File(context.getExternalFilesDir("userData"), "userFoodList.json");
            Log.d("userJsonFile", "getUserPref: "+ userJsonFile);
            FileInputStream fileInputStream = new FileInputStream(userJsonFile);
            int size = fileInputStream.available();
            byte[] buffer = new byte[size];
            fileInputStream.read(buffer);
            fileInputStream.close();
            json = new JSONObject(new String(buffer, StandardCharsets.UTF_8));
        }
        catch(IOException | JSONException e)
        {
            e.printStackTrace();
        }
        return json;
    }

    public ArrayList<String> getBreakfastList() {
        try {
            JSONArray breakFastList = userPref.getJSONArray("breakfast");

            ArrayList<String>  returnList = new ArrayList<>();

            for (int i = 0; i < breakFastList.length(); i++) {
                returnList.add(breakFastList.getString(i));
            }
            return returnList;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    public ArrayList<String>  getLunchList() {
        try {

            JSONObject lunchObj = userPref.getJSONArray("lunch").getJSONObject(0);
            JSONArray noPrefArray = lunchObj.getJSONArray("noPref");
            JSONArray weekendArray = lunchObj.getJSONArray("weekend");

            ArrayList<String>  returnList = new ArrayList<>();

            for (int i = 0; i < noPrefArray.length(); i++)
                returnList.add(noPrefArray.getString(i));

            for (int i = 0; i < weekendArray.length(); i++)
                returnList.add(weekendArray.getString(i));

            return returnList;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    public ArrayList<String>  getDinnerList() {
        try {
            JSONObject dinnerObj = userPref.getJSONArray("dinner").getJSONObject(0);
            JSONArray noPrefArray = dinnerObj.getJSONArray("noPref");
            JSONArray weekendArray = dinnerObj.getJSONArray("weekend");

            ArrayList<String>  returnList = new ArrayList<>();

            for (int i = 0; i < noPrefArray.length(); i++)
                returnList.add(noPrefArray.getString(i));

            for (int i = 0; i < weekendArray.length(); i++)
                returnList.add(weekendArray.getString(i));

            return returnList;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public void addItem()
    {

    }
}
