package com.rumbleworks.classnote;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.SimpleDateFormat;


public class AnnouncementDetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement_details);
        setTitle("Announcement");
        Announcement a = (Announcement)getIntent().getSerializableExtra("ANNOUNCEMENT");
        TextView courseName = (TextView)findViewById(R.id.announcement_details_class);
        TextView description = (TextView)findViewById(R.id.announcement_details_description);
        TextView dueDate = (TextView)findViewById(R.id.announcement_details_date);
        courseName.setText("Course: " + a.getCourse().getTitle());
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM d h:mm a");
        dueDate.setText("Posted: "+dateFormat.format(a.getDueDate())+"\n");
        description.setText("Message: \n\n"+a.getDescription());
    }

}
