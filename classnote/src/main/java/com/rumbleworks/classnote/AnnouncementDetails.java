package com.rumbleworks.classnote;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class AnnouncementDetails extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement_details);
        Bundle b = getIntent().getExtras();
        TextView courseName = (TextView)findViewById(R.id.announcement_details);
        TextView description = (TextView)findViewById(R.id.announcement_details);
        TextView dueDate = (TextView)findViewById(R.id.announcement_details);
        courseName.setText(b.getString("COURSE_NAME"));
        dueDate.setText(b.getString("DUE_DATE"));
        description.setText(b.getString("DESCRIPTION"));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.announcement_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
