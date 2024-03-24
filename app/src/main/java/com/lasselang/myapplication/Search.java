package com.lasselang.myapplication;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Search {

    public static void Search(Context context, String string, ListView searchResults, String[] applicationList) {
        ArrayList<String> contactsList = GetContacts.fetchContacts(context);
        ArrayList<String> searchResultsList = new ArrayList<>();
        if (string.length() > 0) {
            searchResultsList.add("Contacts:"); // TODO: Change to translatable string
            for (String element : contactsList) {
                if (element.toLowerCase().contains(string.toLowerCase())) {
                    searchResultsList.add(element);
                }
            }
            searchResultsList.add("Apps:"); // TODO: Change to translatable string
            for (String element : applicationList) {
                if (element.toLowerCase().contains(string.toLowerCase())) {
                    searchResultsList.add(element);
                }
            }
        }
        ArrayAdapter adapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_list_item_1,
                searchResultsList);
        searchResults.setAdapter(adapter);
    }
}
