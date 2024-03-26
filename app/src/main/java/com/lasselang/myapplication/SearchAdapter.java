package com.lasselang.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SearchAdapter extends BaseAdapter {

    Context context;
    SearchItem searchItems[];
    LayoutInflater inflater;

    public SearchAdapter(Context context, SearchItem searchItem[]) {
        this.context = context;
        this.searchItems = searchItem;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {return searchItems.length;}

    @Override
    public Object getItem(int position) {return null;}

    @Override
    public long getItemId(int position) {return 0;}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(searchItems[position].layout, null);
        TextView txtView = null;
        if (searchItems[position].layout == android.R.layout.simple_list_item_1) {
            txtView = convertView.findViewById(android.R.id.text1);
        }
        if (searchItems[position].layout == R.layout.activity_search_section_heading) {
            txtView = convertView.findViewById(R.id.textView);
        }
        if (searchItems[position].layout == R.layout.activity_search_app) {
            Button buttonView = convertView.findViewById(R.id.textView);
            buttonView.setText(searchItems[position].text);
            ImageView imageView = convertView.findViewById(R.id.imageView);
            imageView.setImageDrawable(searchItems[position].icon);
            buttonView.setOnClickListener(v -> {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                if (searchItems[position].text == context.getString(R.string.app_name)) {
                    Intent intent = new Intent(context, settings.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    return;
                }
                Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(searchItems[position].packageName);
                context.startActivity(launchIntent);
            });
        }
        if (txtView != null) {
            txtView.setText(searchItems[position].text);
        }
        return convertView;
    }
}
