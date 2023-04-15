package com.food.roulette.ui.preferences;

import android.content.Context;
import android.util.Log;

import com.food.roulette.ui.tabs.DinnerFragment;
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
import java.util.Collections;
import java.util.Objects;

public class PreferencesHandler {

    private static JSONObject userPref;
    private static String userPrefFile;

    private static ArrayList<String> BreakfastList= new ArrayList<>();
    private static ArrayList<String> LunchList= new ArrayList<>();
    private static ArrayList<String> DinnerList= new ArrayList<>();

    private static PreferencesHandler instance=null;

    private PreferencesHandler()
    {
    }

    public static PreferencesHandler getInstance()
    {
        if(instance == null)
            instance = new PreferencesHandler();
        return instance;
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
        BreakfastList.clear();
        try {
            JSONArray breakFastList = userPref.getJSONArray("breakfast");

            for (int i = 0; i < breakFastList.length(); i++)
                BreakfastList.add(breakFastList.getString(i));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void InitLunchList()
    {
        LunchList.clear();
        try
        {
            JSONArray lunchList = userPref.getJSONArray("lunch");

            for (int i = 0; i < lunchList.length(); i++)
                LunchList.add(lunchList.getString(i));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void InitDinnerList()
    {
        DinnerList.clear();
        try {
            JSONArray dinnerList = userPref.getJSONArray("dinner");

            for (int i = 0; i < dinnerList.length(); i++)
                DinnerList.add(dinnerList.getString(i));
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

    private void sortedAdd(ArrayList<String> list, String item)
    {
        list.add(item);
        Collections.sort(list);
    }

    private ArrayList<String> getList(String time)
    {
        if (time.equalsIgnoreCase("Breakfast"))
            return BreakfastList;
        else if(time.equalsIgnoreCase("Lunch"))
            return LunchList;
        else if(time.equalsIgnoreCase("Dinner"))
            return DinnerList;
        return null;
    }

    public void addItem(String foodItem, String time, String pref)
    {
        try {
            if(pref.equalsIgnoreCase("weekend")) foodItem+="_w";

            sortedAdd(getList(time), foodItem);
            userPref.put(time, new JSONArray(getList(time)));
            updateDB();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void deleteItem(String time, int deletedItemIdx)
    {
        try {
            JSONArray arr = userPref.getJSONArray(time);
            arr.remove(deletedItemIdx);
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
//            InitBreakfastList();
//            InitLunchList();
//            InitDinnerList();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
