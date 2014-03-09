package com.rumbleworks.classnote;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AssignmentListFragment extends ListFragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    public static final String ARG_SECTION_NUMBER = "section_number";

    public AssignmentListFragment() {

    }


    public void onResume() {
        super.onResume();
        AssignmentAdapter assignmentAdapter = (AssignmentAdapter)this.getListAdapter();
        assignmentAdapter.setList(Datamart.getInstance().getUpcomingAssignments());
        assignmentAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_assignment_list, container, false);

        this.setListAdapter(new AssignmentAdapter(Datamart.getInstance().getUpcomingAssignments(), this.getActivity()));

        return rootView;
    }


}