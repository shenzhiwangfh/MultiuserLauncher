package com.tcl.multiuserlauncher;

import android.content.Context;
import android.content.pm.LauncherActivityInfo;
import android.content.pm.LauncherApps;
import android.os.UserHandle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.astuetz.PagerSlidingTabStrip;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MultiuserLauncher";

    private List<UserHandle> users;
    private HashMap<UserHandle, List<LauncherActivityInfo>> maps = new HashMap<>();
    private ArrayList<Fragment> fragments;

    //private UserManagerCompat compat;

    // 定义ViewPager对象
    //private ViewPager viewPager;
    //private PagerSlidingTabStrip pst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initMultiuser();

        PagerSlidingTabStrip tabs = findViewById(R.id.tabs);
        ViewPager viewPager = findViewById(R.id.viewpager);

        fragments = new ArrayList<>();
        for (UserHandle user : users) {
            AppsFragment fragment = new AppsFragment();
            List<LauncherActivityInfo> apps = maps.get(user);
            fragment.setApps(apps);
            fragment.setUser(user);
            fragments.add(fragment);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        viewPager.setAdapter(new AppsPagerAdapter(fragmentManager, fragments, users));
        viewPager.setCurrentItem(0);

        tabs.setViewPager(viewPager);
        tabs.setAllCaps(true);
        tabs.setShouldExpand(true); //平分
    }

    private void initMultiuser() {
        UserManagerCompat compat = new UserManagerCompat(this);
        compat.enableAndResetCache();
        users = compat.getUserProfiles();

        LauncherApps mLauncherApps = (LauncherApps) this.getSystemService(Context.LAUNCHER_APPS_SERVICE);
        for (UserHandle user : users) {
            List<LauncherActivityInfo> info = mLauncherApps.getActivityList(null, user);
            maps.put(user, info);
        }
    }
}
