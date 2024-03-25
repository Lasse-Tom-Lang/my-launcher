package com.lasselang.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;

public class AppInfo {
    String name;
    String packageName;
    Drawable icon;

    public AppInfo(String name, String packageName, Drawable icon) {
        this.name = name;
        this.packageName = packageName;
        this.icon = icon;
    }
    public void Start(Context context) {
        Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(this.packageName);
        context.startActivity(launchIntent);
    }
}
