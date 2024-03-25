package com.lasselang.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
        if (txtView != null) {
            txtView.setText(searchItems[position].text);
        }
        return convertView;
    }
}
