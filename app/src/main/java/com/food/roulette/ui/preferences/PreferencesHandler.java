package com.food.roulette.ui.preferences;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class PreferencesHandler {

    private JSONObject userPref;
    private String userPrefFile;

    public PreferencesHandler()
    {
    }
    public PreferencesHandler(Context context)
    {
        userPrefFile = context.getExternalFilesDir("userData") + File.separator + "userFoodList.json";
        try{
            File userJsonFile = new File(userPrefFile);
            FileInputStream fileInputStream = new FileInputStream(userJsonFile);
            int size = fileInputStream.available();
            byte[] buffer = new byte[size];
            fileInputStream.read(buffer);
            fileInputStream.close();
            userPref = new JSONObject(new String(buffer, StandardCharsets.UTF_8));
        }
        catch(IOException | JSONException e)
        {
            e.printStackTrace();
        }
    }

    public JSONObject getUserPref(Context context)
    {
        return userPref;
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
            JSONArray noPrefArray = lunchObj.getJSONArray("no pref");
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
            JSONArray noPrefArray = dinnerObj.getJSONArray("no pref");
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

    public void addItem(String foodItem, String time, String pref)
    {
        try {
            JSONArray arr = userPref.getJSONArray(time);
            if (time.equalsIgnoreCase("breakfast")) {
                arr.put(foodItem);
            }
            else
            {
                arr.getJSONObject(0).getJSONArray(pref).put(foodItem);
            }
            userPref.put(time, arr);
            updateList();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void updateList()
    {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonElement je = JsonParser.parseString(userPref.toString());

            FileWriter file = new FileWriter(userPrefFile);
            file.write(gson.toJson(je));
            file.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
