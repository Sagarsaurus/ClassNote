package com.rumbleworks.classnote;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class AddAssignmentFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    public static final String ARG_SECTION_NUMBER = "section_number";

    private Spinner courseSpinner;
    private EditText titleField, descField;
    private DatePicker datePicker;
    private TimePicker timePicker;


    public AddAssignmentFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_assignment, container, false);

        courseSpinner = (Spinner)rootView.findViewById(R.id.courseSpinner);
        courseSpinner.setAdapter(new ArrayAdapter(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, Datamart.getInstance().getCourseIds()));

        titleField = (EditText)rootView.findViewById(R.id.titleField);
        descField = (EditText)rootView.findViewById(R.id.descField);
        datePicker = (DatePicker)rootView.findViewById(R.id.datePicker);
        timePicker = (TimePicker)rootView.findViewById(R.id.timePicker);

        Button saveButton = (Button)rootView.findViewById(R.id.saveAssignmentButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar date = new GregorianCalendar(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), timePicker.getCurrentHour(), timePicker.getCurrentMinute());
                Assignment assignment = new Assignment(titleField.getText().toString(), descField.getText().toString(), true, date.getTime(), (String)courseSpinner.getSelectedItem(), -1, -1, -1, -1);
                //AddAssignmentActivity.this.finish();
            }
        });

        return rootView;
    }
}