package com.hellabreakfast.classnote.ui;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;

import com.hellabreakfast.classnote.model.Datamart;
import com.hellabreakfast.classnote.R;
import com.hellabreakfast.classnote.model.TSquareAPI;

import java.util.Date;


//MainActivity is the new MainActivity.  It is effectively the back activity for all the fragments shown in the app and the drawer fragment

public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        this.requireLogin();
    }

    private void requireLogin() {
        if (!Datamart.isLoggedIn()) {
            finish();
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, LoginActivity.class);

            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Date now = new Date();
        Date lastRefreshed = Datamart.getInstance().getLastRefreshed();
        if (!Datamart.getInstance().isOffline() && (lastRefreshed == null || lastRefreshed.before(new Date(now.getTime()-1000*60*15)))) { // if more than 15 mins ago
            TSquareAPI.refreshAll();
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();

        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new AnnouncementListFragment();
                mTitle = getString(R.string.title_section0);
                break;
            case 1:
                fragment = new CurrentAssignmentListFragment();
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                fragment = new PastAssignmentListFragment();
                mTitle = "Past Assignments";
                break;
            case 3:
                fragment = new AssignmentCalendarFragment();
                mTitle = getString(R.string.title_section2);
                break;
            case 4:
                fragment = new GradebookFragment();
                mTitle = getString(R.string.title_section3);
                break;
            case 5:
                fragment = new AddAssignmentFragment();
                mTitle = getString(R.string.title_section4);
                break;
            case 6:
                fragment = new SettingsFragment();
                mTitle = getString(R.string.title_section5);
                break;
        }
        if (fragment == null) return;
        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
        Datamart.getInstance().setCurrentScreen(position);
        if (Datamart.getInstance().getVisited()[position] == false) {
            this.showHelp();
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            TSquareAPI.refreshAll();
            return true;
        }
        if (id == R.id.action_help) {
            this.showHelp();
            return true;
        }
        if (id == R.id.action_logout) {
            Datamart.clearInstance();
            this.requireLogin();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showHelp() {
        Datamart.getInstance().setVisited(Datamart.getInstance().getCurrentScreen(), true);
        Intent intent = new Intent();
        intent.setClass(this, HelpOverlayActivity.class);
        intent.putExtra("title", getSupportActionBar().getTitle());
        startActivity(intent);
    }

}
