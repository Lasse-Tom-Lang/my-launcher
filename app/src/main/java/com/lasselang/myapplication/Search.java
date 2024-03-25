package com.lasselang.myapplication;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Search {

    public static void search(Context context, String string, ListView searchResults, AppInfo apps[]) {
        ArrayList<String> contactsList = GetContacts.fetchContacts(context);
        ArrayList<SearchItem> searchResultsList = new ArrayList<>();
        if (string.length() > 0) {
            searchResultsList.add(new SearchItem(context.getString(R.string.contacts), R.layout.activity_search_section_heading));
            for (String element : contactsList) {
                if (element.toLowerCase().contains(string.toLowerCase())) {
                    searchResultsList.add(new  SearchItem(element, android.R.layout.simple_list_item_1));
                }
            }
            searchResultsList.add(new SearchItem(context.getString(R.string.apps), R.layout.activity_search_section_heading));
            for (AppInfo element : apps) {
                if (element.name.toLowerCase().contains(string.toLowerCase())) {
                    searchResultsList.add(new  SearchItem(element.name, android.R.layout.simple_list_item_1));
                }
            }
        }
        SearchAdapter searchAdapter = new SearchAdapter(context, searchResultsList.toArray(new SearchItem[0]));
        searchResults.setAdapter(searchAdapter);
    }
}
