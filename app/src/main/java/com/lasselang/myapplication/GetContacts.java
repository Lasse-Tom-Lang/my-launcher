package com.lasselang.myapplication;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import java.util.ArrayList;

public class GetContacts {

    public static ArrayList<String> fetchContacts(Context context) {
        ArrayList<String> contactsList = new ArrayList<>();

        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                contactsList.add(contactName);
            }
            cursor.close();
        }
        return contactsList;
    }

    public static ContactData getContactDetails(ContentResolver contentResolver, String name) {
        Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] phoneProjection = {
                ContactsContract.CommonDataKinds.Phone.NUMBER
        };
        String phoneSelection = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " = ?";
        String[] phoneSelectionArgs = {name};
        Cursor phoneCursor = contentResolver.query(phoneUri, phoneProjection, phoneSelection, phoneSelectionArgs, null);

        ContactData contactData = new ContactData(name);

        if (phoneCursor != null && phoneCursor.moveToFirst()) {
            int numberIndex = phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            do {
                contactData.phoneNumber = phoneCursor.getString(numberIndex);
            } while (phoneCursor.moveToNext());
            phoneCursor.close();
        }

        return contactData;
    }
}
