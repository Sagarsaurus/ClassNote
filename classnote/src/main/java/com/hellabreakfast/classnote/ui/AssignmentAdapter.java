package com.hellabreakfast.classnote.ui;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hellabreakfast.classnote.model.Assignment;
import com.hellabreakfast.classnote.R;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

/**
 * This class takes in a list of Assignments and uses it to populate a ListView
 */
public class AssignmentAdapter extends BaseAdapter {
    private List<Assignment> list;
    private Activity activity;
    private SimpleDateFormat dateFormat;

    public AssignmentAdapter(List<Assignment> list, Activity activity) {
        this.setList(list);
        this.activity = activity;
        dateFormat = new SimpleDateFormat("MMMM d h:mm a");
    }

    /**
     * Sets the list to use for the ListView, sorting it in order (by due date).
     * @param list
     */
    public void setList(List<Assignment> list) {
        Collections.sort(list);
        //Collections.reverse(list);
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater vi;
            vi = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R.layout.list_item_assignment, null);
        }

        Assignment assignment = (Assignment) getItem(i);
        TextView courseTitleText = (TextView) view.findViewById(R.id.courseTitle);
        TextView titleText = (TextView) view.findViewById(R.id.assignmentTitle);
        TextView dueDateText = (TextView) view.findViewById(R.id.assignmentDueDate);
        courseTitleText.setText(assignment.getCourse().getTitle());
        titleText.setText(assignment.getName());
        dueDateText.setText(dateFormat.format(assignment.getDueDate()));
        return view;
    }
}
