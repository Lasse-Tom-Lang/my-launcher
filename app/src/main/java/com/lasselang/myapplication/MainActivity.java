package com.lasselang.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class MainActivity extends AppCompatActivity implements TextWatcher {

    private ListView libaryListView;
    private String[] stringArray;
    private Drawable[] drawableArray;
    private String[] packageArray;
    private LinearLayout search;
    private EditText searchInput;
    private ListView searchResults;
    private Animation slideUp;
    private Animation slideDown;
    private Animation showLibraryAnim;
    private Animation hideLibraryAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        libaryListView = findViewById(R.id.appList);

        search = findViewById(R.id.search);
        searchInput = findViewById(R.id.searchInput);
        searchResults = findViewById(R.id.searchResults);

        setDate();

        setLibrary();

        showHideSearch();

        slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        slideDown = AnimationUtils.loadAnimation(this, R.anim.slide_down);
        showLibraryAnim = AnimationUtils.loadAnimation(this, R.anim.show_library);
        hideLibraryAnim = AnimationUtils.loadAnimation(this, R.anim.hide_library);
        searchInput.addTextChangedListener(this);

    }

    public void showLibrary(View view) {
        if (libaryListView.getVisibility() == View.INVISIBLE) {
            libaryListView.setVisibility(View.VISIBLE);
            libaryListView.startAnimation(showLibraryAnim);
        }
        else {
            libaryListView.startAnimation(hideLibraryAnim);
            libaryListView.setVisibility(View.INVISIBLE);
            setLibrary();
        }
    }

    private void setLibrary() {
        List<ApplicationInfo> listApplicationInfo = getPackageManager().getInstalledApplications(PackageManager.GET_META_DATA);
        List<String> stringList = new ArrayList<>();
        List<Drawable> drawableList = new ArrayList<>();
        List<String> packageList = new ArrayList<>();

        for (ApplicationInfo applicationInfo: listApplicationInfo) {
            Intent launchIntent = getPackageManager().getLaunchIntentForPackage(applicationInfo.packageName);
            if (launchIntent != null) {
                stringList.add(applicationInfo.loadLabel(getPackageManager()).toString());
                packageList.add(applicationInfo.packageName);
                try {
                    drawableList.add(getPackageManager().getApplicationIcon(applicationInfo.packageName));
                }
                catch (PackageManager.NameNotFoundException e)
                {
                    e.printStackTrace();
                }
            }
        }

        stringArray = stringList.toArray(new String[0]);
        drawableArray = drawableList.toArray(new Drawable[0]);
        packageArray = packageList.toArray(new String[0]);

        LibraryAdapter libraryAdapter = new LibraryAdapter(getApplicationContext(), stringArray, drawableArray, packageArray);
        libaryListView.setAdapter(libraryAdapter);
    }

    public void openSearch(View view) {
        if (search.getVisibility() == View.GONE) {
            search.setVisibility(View.VISIBLE);
            search.startAnimation(slideDown);
            searchInput.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }
        else {
            search.startAnimation(slideUp);
            search.setVisibility(View.GONE);
            searchInput.clearFocus();
        }
    }

    private void setDate() {
        TextView dateTimeDisplay = (TextView) findViewById(R.id.dateTextView);

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat dateFormat = new SimpleDateFormat("EE, dd.MM.");
        String date = dateFormat.format(calendar.getTime());
        dateTimeDisplay.setText(date);
    }

    private void showHideSearch() {
        int translationY = getResources().getDimensionPixelSize(R.dimen.translation_y);

        View rootView = findViewById(android.R.id.content);
        rootView.setOnTouchListener((view, event) -> {
            view.performClick();
            if (search.getVisibility() == View.VISIBLE) {
                float x = event.getX();
                float y = event.getY();


                if (x < search.getLeft() || x > search.getRight() || y < search.getTop() || y > (search.getBottom() + translationY)) {
                    search.startAnimation(slideUp);
                    search.setVisibility(View.GONE);
                    searchInput.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    return true;
                }
            }
            return false;
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable string) {
        Search.Search(getApplicationContext(), string.toString(), searchResults);
    }
}