package com.food.roulette.ui.preferences;

import android.content.Context;
import android.util.Log;

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
import java.util.Objects;

public class PreferencesHandler {

    private static JSONObject userPref;
    private static String userPrefFile;

    private static ArrayList<String> BreakfastList= new ArrayList<>();
    private static ArrayList<String> LunchList= new ArrayList<>();
    private static ArrayList<String> DinnerList= new ArrayList<>();

    public PreferencesHandler()
    {

    }

    public static void InitUserPref(String userFoodlistPath)
    {
        userPrefFile = userFoodlistPath;
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

        InitBreakfastList();
        InitLunchList();
        InitDinnerList();
    }

    public static JSONObject getUserPref()
    {
        return userPref;
    }

    public static void InitBreakfastList()
    {
        try {
            JSONArray breakFastList = userPref.getJSONArray("breakfast");

            for (int i = 0; i < breakFastList.length(); i++) {
                BreakfastList.add(breakFastList.getString(i));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void InitLunchList()
    {
        try {

            JSONObject lunchObj = userPref.getJSONArray("lunch").getJSONObject(0);
            JSONArray noPrefArray = lunchObj.getJSONArray("no pref");
            JSONArray weekendArray = lunchObj.getJSONArray("weekend");

            for (int i = 0; i < noPrefArray.length(); i++)
                LunchList.add(noPrefArray.getString(i));

            for (int i = 0; i < weekendArray.length(); i++)
                LunchList.add(weekendArray.getString(i));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void InitDinnerList()
    {
        try {
            JSONObject dinnerObj = userPref.getJSONArray("dinner").getJSONObject(0);
            JSONArray noPrefArray = dinnerObj.getJSONArray("no pref");
            JSONArray weekendArray = dinnerObj.getJSONArray("weekend");

            for (int i = 0; i < noPrefArray.length(); i++)
                DinnerList.add(noPrefArray.getString(i));

            for (int i = 0; i < weekendArray.length(); i++)
                DinnerList.add(weekendArray.getString(i));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getBreakfastList() {
        return BreakfastList;
    }
    public ArrayList<String>  getLunchList() {
        return LunchList;
    }
    public ArrayList<String>  getDinnerList() {
        return DinnerList;
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
            updateDB();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void deleteItem(String time, int deletedItemIdx, String item)
    {
        try {
            JSONArray arr = userPref.getJSONArray(time);
            if (time.equalsIgnoreCase("breakfast")) {
                arr.remove(deletedItemIdx);
            }
            else
            {
                JSONArray noPrefArr = arr.getJSONObject(0).getJSONArray("no pref");
                JSONArray weekendArr = arr.getJSONObject(0).getJSONArray("weekend");

                if(noPrefArr.length() > deletedItemIdx && Objects.equals(item, noPrefArr.getString(deletedItemIdx))) {
                    noPrefArr.remove(deletedItemIdx);
                    arr.getJSONObject(0).put("no pref",noPrefArr);
                }

                else if(((weekendArr.length()+noPrefArr.length()) > deletedItemIdx) &&
                        Objects.equals(item, weekendArr.getString(deletedItemIdx-noPrefArr.length()))) {
                    weekendArr.remove(deletedItemIdx-noPrefArr.length());
                    arr.getJSONObject(0).put("weekend",weekendArr);
                }
            }
            userPref.put(time, arr);
            updateDB();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void updateDB()
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
