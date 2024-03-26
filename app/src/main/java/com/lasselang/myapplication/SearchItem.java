package com.lasselang.myapplication;

import android.graphics.drawable.Drawable;

public class SearchItem {
    String text;
    Integer layout;
    Drawable icon;
    String packageName;
    public SearchItem(String text, Integer layout, Drawable icon, String packageName) {
        this.text = text;
        this.layout = layout;
        this.icon = icon;
        this.packageName = packageName;
    }
    public SearchItem(String text, Integer layout) {
        this.text = text;
        this.layout = layout;
    }
}
