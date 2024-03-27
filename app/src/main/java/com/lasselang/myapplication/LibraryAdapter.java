package com.lasselang.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Objects;

public class LibraryAdapter extends BaseAdapter {

    Context context;
    AppInfo[] apps;
    LayoutInflater inflater;

    public LibraryAdapter(Context ctx, AppInfo[] apps) {
        this.context = ctx;
        this.apps = apps;
        inflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return apps.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.activity_applibrary_item, null);
        Button txtView = convertView.findViewById(R.id.textView);
        ImageView imgView = convertView.findViewById(R.id.imageView);
        txtView.setText(apps[position].name);
        imgView.setImageDrawable(apps[position].icon);
        txtView.setOnClickListener(v -> {
            if (Objects.equals(apps[position].name, context.getString(R.string.app_name))) {
                Intent intent = new Intent(context, settings.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                return;
            }
            apps[position].Start(context);
        });
        return convertView;
    }
}
