package com.tcl.multiuserlauncher;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.LauncherActivityInfo;
import android.os.Bundle;
import android.os.UserHandle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class AppsFragment extends Fragment {

    private List<LauncherActivityInfo> apps;
    private int id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_apps, null);
        return view;
    }

    public void setApps(List<LauncherActivityInfo> apps) {
        this.apps = apps;
    }

    public void setUser(UserHandle user) {
        id = UserManagerCompat.getIdentifier(user);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ListView listView = getView().findViewById(R.id.list_apps);
        AppsAdapter adapter = new AppsAdapter(getActivity(), apps);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public class AppsAdapter extends BaseAdapter {
        private Context context;
        private List<LauncherActivityInfo> list;
        private int iconDpi;

        public AppsAdapter(Context context, List<LauncherActivityInfo> list) {
            this.context = context;
            this.list = list;

            ActivityManager activityManager = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
            iconDpi = activityManager.getLauncherLargeIconDensity();
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder vh;
            if (view == null) {
                vh = new ViewHolder();
                view = LayoutInflater.from(context).inflate(R.layout.item_app, null);
                vh.icon = view.findViewById(R.id.icon);
                vh.user = view.findViewById(R.id.user);
                vh.title = view.findViewById(R.id.title);
                vh.packageName = view.findViewById(R.id.package_name);
                view.setTag(vh);
            } else {
                vh = (ViewHolder) view.getTag();
            }

            LauncherActivityInfo info = list.get(i);
            vh.icon.setImageDrawable(info.getIcon(iconDpi));
            vh.user.setImageResource(R.drawable.ic_user);
            vh.title.setText(info.getLabel());
            vh.packageName.setText(info.getName());

            vh.user.setVisibility(id > 0 ? View.VISIBLE : View.GONE);
            return view;
        }

        public class ViewHolder {
            ImageView icon;
            ImageView user;
            TextView title;
            TextView packageName;
        }
    }
}
