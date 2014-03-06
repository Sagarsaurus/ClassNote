package com.rumbleworks.classnote;

import android.app.ActionBar;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
//import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
//import android.view.Gravity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
//import android.view.MenuItem;


public class MainActivity extends FragmentActivity implements ActionBar.TabListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sect
     * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
     * will keep every loaded fragment in memory. If this becomes too memory
     * intensive, it may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    FrameLayout mFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent oldIntent = getIntent();
        String username = oldIntent.getExtras().getString("username");
        Log.v("cat", "cat " + username);

        //arrayList = oldIntent.getCharSequenceArrayListExtra("arrayList");


        // Set up the action bar.
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the app.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }


        /**
         * This part implements the overlay using a dialog
         */
        Dialog overlayInfo = new Dialog(MainActivity.this);
        // Making sure there's no title.
        overlayInfo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Making dialog content transparent.
        overlayInfo.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));
        // Removing window dim normally visible when dialog are shown.
        overlayInfo.getWindow().clearFlags(
                WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        // Setting position of content, relative to window.
        WindowManager.LayoutParams params = overlayInfo.getWindow().getAttributes();
        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = 100;
        params.y = 20;
        // If user taps anywhere on the screen, dialog will be cancelled.
        overlayInfo.setCancelable(true);
        // Setting the content using prepared XML layout file.

        //overlayInfo.setContentView(R.layout.overlay);

        overlayInfo.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add_assignment) {
            Intent intent = new Intent(this, AddAssignmentActivity.class);
            startActivity(intent);
            return true;
        }
        return false;
    }
    
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a DummySectionFragment (defined as a static inner class
            // below) with the page number as its lone argument.
        	Fragment fragment = null;
        	Bundle args = new Bundle();
            
            if ( position == 0 ) {
            	fragment = new AssignmentListFragment();
	            args.putInt(AssignmentListFragment.ARG_SECTION_NUMBER, position + 1);
            }
            if ( position == 1 ) {
            	fragment = new AssignmentCalendarFragment();
	            args.putInt(AssignmentCalendarFragment.ARG_SECTION_NUMBER, position + 1);
            }
            if ( position == 2 ) {
            	fragment = new AnnouncementListFragment();
	            args.putInt(AnnouncementListFragment.ARG_SECTION_NUMBER, position + 1);
            }
            if ( position == 3 ) {
                fragment = new GradebookFragment();
                args.putInt(GradebookFragment.ARG_SECTION_NUMBER, position + 1);
            }
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            //Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return "Upcoming Assignments";
                case 1:
                    return "Calendar";
                case 2:
                    return "Announcements";
                case 3:
                    return "Gradebook";
            }
            return null;
        }
    }
    
    @Override
    public void onBackPressed() {
        //start activity here
    	Intent intent = new Intent();

		intent.setClass(MainActivity.this, UpdateActivity.class);

		startActivity(intent);
		finish();
    	
    	super.onBackPressed();

    }

}
