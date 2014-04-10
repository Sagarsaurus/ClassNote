package com.rumbleworks.classnote;

import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class OverlayActivity extends ActionBarActivity {

    private String[][] helpArray = {    { "Tap the top left of the screen or swipe from the left to open the menu for the application", "All announcements are present on this page and are sorted from most to least recent", "", "", "Tap anywhere to dismiss instructions" },
                                        { "Open the menu to add new assignments", "Tap on an assignment to view in more detail", "", "", "Tap anywhere to dismiss instructions"},
                                        { "Open the menu to add new assignments", "Tap on an assignment to view in more detail", "", "", "Tap anywhere to dismiss instructions"},
                                        { "Tap a day to create a new assignment due on that date", "Slide up or down to go from one month to another", "", "", "Tap anywhere to dismiss instructions" },
                                        { "Graded assignments appear here after being marked as returned on T-Square", "Manually added assignments appear here after their due date", "You can tap assignments you have added to grade them manually", "", "Tap anywhere to dismiss instructions"},
                                        { "Enter basic information about class including course number, name of assignment, and a brief description", "Either select the month and day on the left or select a date from the calendar on the right to select the due date", "Scroll down and set what time the assignment is due", "Tap “Save Assignment” when you are finished", "Tap anywhere to dismiss instructions"},
                                        { "", "", "", "", "" },
                                        { "", "", "", "", "" } };

    public int currentScreen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overlay);

        String title = getIntent().getStringExtra("title");
        if (title != null) setTitle(title);

        currentScreen = Datamart.getInstance().getCurrentScreen();

        TextView textView1 = (TextView) findViewById(R.id.textView1);
        TextView textView2 = (TextView) findViewById(R.id.textView2);
        TextView textView3 = (TextView) findViewById(R.id.textView3);
        TextView textView4 = (TextView) findViewById(R.id.textView4);
        TextView textView5 = (TextView) findViewById(R.id.textView5);

        textView1.setText( helpArray[ currentScreen ][0] );
        textView2.setText( helpArray[ currentScreen ][1] );
        textView3.setText( helpArray[ currentScreen ][2] );
        textView4.setText( helpArray[ currentScreen ][3] );
        textView5.setText( helpArray[ currentScreen ][4] );
    }

    public void understood(View view){
        finish();
    }
}