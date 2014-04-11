package com.rumbleworks.classnote;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;


public class AssignmentDetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_details);
        setTitle("Assignment");
        Bundle b = getIntent().getExtras();
        TextView courseName = (TextView)findViewById(R.id.assignment_details_class);
        TextView description = (TextView)findViewById(R.id.assignment_details_description);
        TextView dueDate = (TextView)findViewById(R.id.assignment_details_date);
        courseName.setText("Course: " + b.getString("COURSE_ID"));
        dueDate.setText("Posted: "+b.getString("DUE_DATE")+"\n");
        description.setText("Message: \n\n"+b.getString("DESCRIPTION"));
    }

}
