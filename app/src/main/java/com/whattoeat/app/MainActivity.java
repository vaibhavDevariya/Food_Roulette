package com.whattoeat.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.whattoeat.app.ui.CoreEngine.FoodMenuGenerator;
import com.whattoeat.app.ui.home.HomeFragment;
import com.whattoeat.app.ui.preferences.PreferencesFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView mBottomNavigationView;

    private FrameLayout mContentFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find views
        mBottomNavigationView = findViewById(R.id.bottom_navigation);
        mContentFrame = findViewById(R.id.content_frame);

        // Default home view
        mContentFrame.setVisibility(View.VISIBLE);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, new HomeFragment())
                .commit();

        // Set up bottom navigation listener
        mBottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    // Show content for Home tab
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content_frame, new HomeFragment())
                            .commit();
                    return true;
                case R.id.navigation_preferences:
                    // Show tabs for Preferences
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content_frame, new PreferencesFragment())
                            .commit();
                    // Set up tabs and view pager
                    //setUpTabs();
                    return true;
            }
            return false;
        });

        InitializeUserData();
        FoodMenuGenerator fmgObj = new FoodMenuGenerator();
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
                File userJsonFile = new File(getExternalFilesDir("userData"), "userFoodList.json");

                FileOutputStream fileOutputStream = new FileOutputStream(userJsonFile);
                fileOutputStream.write(DefaultFoodList.getBytes());
//              Toast.makeText(this, "Created user specific Food list in \n" + userJsonFile, Toast.LENGTH_SHORT).show();
                fileOutputStream.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void setUpTabs() {
        // Set up view pager
//        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
//        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                mViewPager.setCurrentItem(tab.getPosition(),false);
//            }
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {}
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {}
//        });
    }
}