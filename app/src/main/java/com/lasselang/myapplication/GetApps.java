package com.lasselang.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.List;

public class GetApps {
    public static AppInfo[] getApps(Context context) {
        PackageManager packageManager = context.getPackageManager();
        List<ApplicationInfo> listApplicationInfo = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
        List<AppInfo> appsList = new ArrayList<>();

        for (ApplicationInfo applicationInfo: listApplicationInfo) {
            Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(applicationInfo.packageName);
            if (launchIntent != null) {
                try {
                    appsList.add(new AppInfo(
                            applicationInfo.loadLabel(packageManager).toString(),
                            applicationInfo.packageName,
                            packageManager.getApplicationIcon(applicationInfo.packageName)
                    ));
                }
                catch (PackageManager.NameNotFoundException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return appsList.toArray(new AppInfo[0]);
    }
}
