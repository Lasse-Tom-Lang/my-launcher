package com.lasselang.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements TextWatcher, GestureDetector.OnGestureListener {

    private ListView libaryListView;
    private AppInfo[] apps;
    private LinearLayout search;
    private LinearLayout libraryView;
    private EditText searchInput;
    private ListView searchResults;
    private Animation slideUp;
    private Animation slideDown;
    private Animation showLibraryAnim;
    private Animation hideLibraryAnim;
    private GestureDetector gestureDetector;
    private static final int SWIPE_THRESHOLD = 100;
    private static final int SWIPE_VELOCITY_THRESHOLD = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        libaryListView = findViewById(R.id.appList);
        libraryView = findViewById(R.id.appList_container);

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

        gestureDetector = new GestureDetector(this, this);
    }

    public void showLibrary(View view) {
        if (libraryView.getVisibility() == View.INVISIBLE) {
            libraryView.setVisibility(View.VISIBLE);
            libraryView.startAnimation(showLibraryAnim);
        }
        else {
            libraryView.startAnimation(hideLibraryAnim);
            libraryView.setVisibility(View.INVISIBLE);
            setLibrary();
        }
    }

    private void setLibrary() {
        apps = GetApps.getApps(getApplicationContext());
        LibraryAdapter libraryAdapter = new LibraryAdapter(getApplicationContext(), apps);
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
        TextView dateTimeDisplay = findViewById(R.id.dateTextView);

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
        Search.search(getApplicationContext(), string.toString(), searchResults, apps);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event) || super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(@NonNull MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(@NonNull MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(@NonNull MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(@Nullable MotionEvent e1, @NonNull MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(@NonNull MotionEvent e) {

    }

    @Override
    public boolean onFling(@Nullable MotionEvent e1, @NonNull MotionEvent e2, float velocityX, float velocityY) {
        float diffX = e2.getX() - e1.getX();
        float diffY = e2.getY() - e1.getY();

        if (Math.abs(diffX) > Math.abs(diffY)) {
            if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                if (diffX > 0) {
                    // SwipeRight
                } else {
                    // SwipeLeft
                }
            }
        } else {
            if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                if (diffY > 0) {
                    // SwipeDown
                    if (libraryView.getVisibility() == View.INVISIBLE) {
                        openSearch(null);
                    }
                } else {
                    // SwipeUp
                    showLibrary(null);
                }
            }
        }
        return false;
    }
}