package com.food.roulette;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.food.roulette.ui.preferences.PreferencesHandler;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.food.roulette.ui.home.FoodMenuGenerator;
import com.food.roulette.ui.home.HomeFragment;
import com.food.roulette.ui.preferences.PreferencesFragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    String userFilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView mBottomNavigationView = findViewById(R.id.bottom_navigation);

        userFilePath = getExternalFilesDir("userData") + File.separator + "userFoodList.json";

        if(!(new File(userFilePath).exists()))
            InitializeUserData();

        PreferencesHandler.InitUserPref(userFilePath);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, new HomeFragment())
                .commit();

        // Set up bottom navigation listener
        mBottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content_frame, new HomeFragment())
                            .commit();
                    return true;
                case R.id.navigation_preferences:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content_frame, new PreferencesFragment())
                            .commit();
                    return true;
            }
            return false;
        });
    }

    public void InitializeUserData() {
        try {

            InputStream is = getAssets().open("DefaultFoodList.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String DefaultFoodList = new String(buffer, StandardCharsets.UTF_8);

            String extStorageState = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
                File userJsonFile = new File(userFilePath);
                FileOutputStream fileOutputStream = new FileOutputStream(userJsonFile);
                fileOutputStream.write(DefaultFoodList.getBytes());
                fileOutputStream.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}