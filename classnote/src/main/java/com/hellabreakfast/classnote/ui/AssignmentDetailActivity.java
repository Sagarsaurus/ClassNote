package com.hellabreakfast.classnote.ui;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.hellabreakfast.classnote.model.Assignment;
import com.hellabreakfast.classnote.R;

import java.text.SimpleDateFormat;


public class AssignmentDetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_details);
        setTitle("Assignment");
        Assignment a = (Assignment) getIntent().getSerializableExtra("ASSIGNMENT");
        TextView courseName = (TextView)findViewById(R.id.assignment_details_class);
        TextView title = (TextView)findViewById(R.id.assignment_details_title);
        TextView description = (TextView)findViewById(R.id.assignment_details_description);
        TextView dueDate = (TextView)findViewById(R.id.assignment_details_date);
        courseName.setText(a.getCourse().getTitle());
        title.setText(a.getName());
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM d h:mm a");
        dueDate.setText("Due: "+dateFormat.format(a.getDueDate())+"\n");
        description.setText(a.getDescription());
    }

}
