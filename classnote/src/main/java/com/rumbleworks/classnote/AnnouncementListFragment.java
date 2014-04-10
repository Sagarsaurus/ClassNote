package com.rumbleworks.classnote;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.Observable;
import java.util.Observer;

public class AnnouncementListFragment extends ListFragment implements Observer {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    public static final String ARG_SECTION_NUMBER = "section_number";

    public AnnouncementListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_announcement_list, container, false);

        setListAdapter(new AnnouncementAdapter(Datamart.getInstance().getAllAnnouncements(), getActivity()));
        return rootView;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Datamart.getInstance().addObserver(this);
    }

    public void onDestroy() {
        super.onDestroy();
        Datamart.getInstance().deleteObserver(this);
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        AnnouncementAdapter adapter = (AnnouncementAdapter)getListAdapter();
        Announcement a = (Announcement)adapter.getItem(position);
        Intent i = new Intent(this.getActivity(), AnnouncementDetails.class);
        i.putExtra("COURSE_NAME", a.getCourseName());
        i.putExtra("DESCRIPTION", a.getDescription());
        i.putExtra("DUE_DATE", a.getDueDate().toString());
        startActivity(i);
    }

    public void update(Observable observable, Object data) {
        setListAdapter(new AnnouncementAdapter(Datamart.getInstance().getAllAnnouncements(), getActivity()));
    }

}