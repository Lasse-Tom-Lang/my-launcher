package com.lasselang.myapplication;

import android.content.Context;
import android.widget.ListView;

import java.util.ArrayList;

public class Search {

    public static void search(Context context, String string, ListView searchResults, AppInfo[] apps) {
        ArrayList<String> contactsList = GetContacts.fetchContacts(context);
        ArrayList<SearchItem> searchResultsList = new ArrayList<>();
        int length = 1;
        if (string.length() > 0) {
            searchResultsList.add(new SearchItem(context.getString(R.string.contacts), R.layout.activity_search_section_heading));
            for (String element : contactsList) {
                if (element.toLowerCase().contains(string.toLowerCase())) {
                    searchResultsList.add(new  SearchItem(element, R.layout.activity_search_contact));
                }
            }
            if (length == searchResultsList.size()) {
                searchResultsList.remove(0);
            }
            searchResultsList.add(new SearchItem(context.getString(R.string.apps), R.layout.activity_search_section_heading));
            length = searchResultsList.size();
            for (AppInfo element : apps) {
                if (element.name.toLowerCase().contains(string.toLowerCase())) {
                    searchResultsList.add(new  SearchItem(element.name, R.layout.activity_search_app, element.icon, element.packageName));
                }
            }
            if (length == searchResultsList.size()) {
                searchResultsList.remove(length - 1);
            }
        }
        SearchAdapter searchAdapter = new SearchAdapter(context, searchResultsList.toArray(new SearchItem[0]));
        searchResults.setAdapter(searchAdapter);
    }
}
