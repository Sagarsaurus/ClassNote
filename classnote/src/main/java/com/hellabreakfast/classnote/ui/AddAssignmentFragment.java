package com.hellabreakfast.classnote.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.hellabreakfast.classnote.model.Assignment;
import com.hellabreakfast.classnote.model.Course;
import com.hellabreakfast.classnote.model.Datamart;
import com.hellabreakfast.classnote.R;

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
        courseSpinner.setAdapter(new ArrayAdapter(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, Datamart.getInstance().getCourseTitles()));

        titleField = (EditText)rootView.findViewById(R.id.titleField);
        descField = (EditText)rootView.findViewById(R.id.descField);
        datePicker = (DatePicker)rootView.findViewById(R.id.datePicker);
        timePicker = (TimePicker)rootView.findViewById(R.id.timePicker);

        Button saveButton = (Button)rootView.findViewById(R.id.saveAssignmentButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar date = new GregorianCalendar(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), timePicker.getCurrentHour(), timePicker.getCurrentMinute());
                Course c = Datamart.getInstance().getCourseByTitle((String)courseSpinner.getSelectedItem());
                Assignment assignment = new Assignment(null, titleField.getText().toString(), descField.getText().toString(), true, date.getTime());
                c.addAssignment(assignment);

                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(descField.getWindowToken(), 0);

                ((MainActivity) getActivity() ).onNavigationDrawerItemSelected(1);
            }
        });

        return rootView;
    }
}