package com.lasselang.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;

public class LibraryAdapter extends BaseAdapter {

    Context context;
    String[] names;
    Drawable[] images;
    String[] packages;
    LayoutInflater inflater;

    public LibraryAdapter(Context ctx, String[] names, Drawable[] images, String[] packages) {
        this.context = ctx;
        this.names = names;
        this.images = images;
        this.packages = packages;
        inflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return names.length;
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
        txtView.setText(names[position]);
        imgView.setImageDrawable(images[position]);
        txtView.setOnClickListener(v -> {
            if (names[position] == context.getString(R.string.app_name)) {
                Intent intent = new Intent(context, settings.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                return;
            }
            Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(packages[position]);
            context.startActivity(launchIntent);
        });
        return convertView;
    }
}
