package com.tcl.multiuserlauncher;

import android.os.UserHandle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class AppsPagerAdapter extends FragmentPagerAdapter {

    //private String[] titles;
    private ArrayList<Fragment> fragments;
    private List<UserHandle> users;

    //根据需求定义构造方法，方便外部调用
    public AppsPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments, List<UserHandle> users) {
        super(fm);
        //this.titles = titles;
        this.fragments = fragments;
        this.users = users;
    }

    //设置每页的标题
    @Override
    public CharSequence getPageTitle(int position) {
        UserHandle user = users.get(position);
        return user.toString(); //titles[position];
    }

    //设置每一页对应的fragment
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    //fragment的数量
    @Override
    public int getCount() {
        return fragments.size();
    }
}
