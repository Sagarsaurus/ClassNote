package com.rumbleworks.classnote;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Observable;
import java.util.Observer;

public class AssignmentListFragment extends ListFragment implements Observer {
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
        assignmentAdapter.setList(Datamart.getInstance().getAllAssignments());
        assignmentAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_assignment_list, container, false);

        this.setListAdapter(new AssignmentAdapter(Datamart.getInstance().getAllAssignments(), this.getActivity()));

        if ( Datamart.getInstance().getVisited()[1] == false ) {
            Datamart.getInstance().setVisited(1, true);
            Intent intent = new Intent();
            intent.setClass(getActivity(), OverlayActivity.class);
            startActivity(intent);
            //finish();
        }

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

    public void update(Observable observable, Object data) {
        this.setListAdapter(new AssignmentAdapter(Datamart.getInstance().getAllAssignments(), this.getActivity()));
    }

}