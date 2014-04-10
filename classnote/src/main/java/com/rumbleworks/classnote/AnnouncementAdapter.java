package com.rumbleworks.classnote;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

public class AnnouncementAdapter extends BaseAdapter {
    private List<Announcement> list;
    private Activity activity;
    private SimpleDateFormat dateFormat;

    public AnnouncementAdapter(List<Announcement> list, Activity activity) {
        this.setList(list);
        this.activity = activity;
        dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm");
    }

    public void setList(List<Announcement> list) {
        Collections.sort(list);
        Collections.reverse(list);
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

        Announcement announcement = (Announcement) getItem(i);
        TextView titleText = (TextView) view.findViewById(R.id.assignmentTitle);
        TextView dueDateText = (TextView) view.findViewById(R.id.assignmentDueDate);
        titleText.setText(announcement.getCourseName()+": "+announcement.getName());
        dueDateText.setText(dateFormat.format(announcement.getDueDate()));
        return view;
    }
}
