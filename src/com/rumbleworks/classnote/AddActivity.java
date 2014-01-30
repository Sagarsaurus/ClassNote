package com.rumbleworks.classnote;

import android.app.Activity;
import android.os.Bundle;

import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class AddActivity extends Activity {

    private Spinner courseSpinner;
    private EditText titleField, descField;
    private DatePicker datePicker;
    private TimePicker timePicker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);

        courseSpinner = (Spinner)findViewById(R.id.courseSpinner);
        courseSpinner.setAdapter(new ArrayAdapter(AddActivity.this, android.R.layout.simple_spinner_item, Datamart.getInstance().getCourseIds()));

        titleField = (EditText)findViewById(R.id.titleField);
        descField = (EditText)findViewById(R.id.descField);
        datePicker = (DatePicker)findViewById(R.id.datePicker);
        timePicker = (TimePicker)findViewById(R.id.timePicker);

        Button saveButton = (Button)findViewById(R.id.saveAssignmentButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar date = new GregorianCalendar(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), timePicker.getCurrentHour(), timePicker.getCurrentMinute());
                Assignment assignment = new Assignment(titleField.getText().toString(), descField.getText().toString(), true, date.getTime(), (String)courseSpinner.getSelectedItem(), -1, -1, -1, -1);
            }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add, menu);
		return true;
	}

}
