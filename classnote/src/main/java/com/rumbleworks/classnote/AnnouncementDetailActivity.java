package com.rumbleworks.classnote;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class AnnouncementDetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement_details);
        setTitle("Announcement");
        Bundle b = getIntent().getExtras();
        TextView courseName = (TextView)findViewById(R.id.announcement_details_class);
        TextView description = (TextView)findViewById(R.id.announcement_details_description);
        TextView dueDate = (TextView)findViewById(R.id.announcement_details_date);
        courseName.setText("Course: " + b.getString("COURSE_NAME"));
        dueDate.setText("Posted: "+b.getString("DUE_DATE")+"\n");
        description.setText("Message: \n\n"+b.getString("DESCRIPTION"));
    }

}
