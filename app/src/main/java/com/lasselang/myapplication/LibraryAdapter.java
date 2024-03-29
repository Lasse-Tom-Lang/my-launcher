package com.lasselang.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.ViewHolder> {

    private final Context context;
    private final AppInfo[] apps;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final Button textView;
        private final ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.textView);
            imageView = view.findViewById(R.id.imageView);
        }

        public Button getTextView() {
            return textView;
        }
        public ImageView getImageView() {
            return imageView;
        }
    }

    public LibraryAdapter(Context context, AppInfo[] apps) {
        this.context = context;
        this.apps = apps;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_applibrary_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getTextView().setText(apps[position].name);
        viewHolder.getImageView().setImageDrawable(apps[position].icon);
        viewHolder.getTextView().setOnClickListener(v -> {
            if (Objects.equals(apps[position].name, context.getString(R.string.app_name))) {
                Intent intent = new Intent(context, settings.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                return;
            }
            apps[position].Start(context);
        });
    }

    @Override
    public int getItemCount() {
        return apps.length;
    }
}
