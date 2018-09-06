package com.tcl.multiuserlauncher;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.ArrayMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserManagerCompat {
    private final UserManager mUserManager;
    //private final PackageManager mPm;
    //private final Context mContext;

    private ArrayMap<Long, UserHandle> mUsers;
    private ArrayMap<UserHandle, Long> mUserToSerialMap;

    UserManagerCompat(Context context) {
        mUserManager = (UserManager) context.getSystemService(Context.USER_SERVICE);
        //mPm = context.getPackageManager();
        //mContext = context;
    }

    public void enableAndResetCache() {
        synchronized (this) {
            mUsers = new ArrayMap<>();
            mUserToSerialMap = new ArrayMap<>();
            List<UserHandle> users = mUserManager.getUserProfiles();
            if (users != null) {
                for (UserHandle user : users) {
                    long serial = mUserManager.getSerialNumberForUser(user);
                    mUsers.put(serial, user);
                    mUserToSerialMap.put(user, serial);
                }
            }
        }
    }

    public List<UserHandle> getUserProfiles() {
        synchronized (this) {
            if (mUsers != null) {
                return new ArrayList<>(mUserToSerialMap.keySet());
            }
        }

        List<UserHandle> users = mUserManager.getUserProfiles();
        return users == null ? Collections.<UserHandle>emptyList() : users;
    }

    public static int getIdentifier(UserHandle user) {
        Pattern pattern1 = Pattern.compile(".*?\\{(.*?)\\}.*?");
        Matcher matcher1 = pattern1.matcher(user.toString());
        if (matcher1.matches()) {
            //String user2 = matcher1.group();
            int id = Integer.valueOf(matcher1.group(1));
            return id;
        }
        return 0;
    }
}
