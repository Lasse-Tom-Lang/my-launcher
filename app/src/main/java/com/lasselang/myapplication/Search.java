package com.lasselang.myapplication;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Search {

    public static void search(Context context, String string, ListView searchResults, AppInfo apps[]) {
        ArrayList<String> contactsList = GetContacts.fetchContacts(context);
        ArrayList<String> searchResultsList = new ArrayList<>();
        if (string.length() > 0) {
            searchResultsList.add(context.getString(R.string.contacts));
            for (String element : contactsList) {
                if (element.toLowerCase().contains(string.toLowerCase())) {
                    searchResultsList.add(element);
                }
            }
            searchResultsList.add(context.getString(R.string.apps));
            for (AppInfo element : apps) {
                if (element.name.toLowerCase().contains(string.toLowerCase())) {
                    searchResultsList.add(element.name);
                }
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_list_item_1,
                searchResultsList);
        searchResults.setAdapter(adapter);
    }
}
