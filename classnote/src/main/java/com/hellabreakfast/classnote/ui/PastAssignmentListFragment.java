package com.hellabreakfast.classnote.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.hellabreakfast.classnote.model.Assignment;
import com.hellabreakfast.classnote.model.Datamart;
import com.hellabreakfast.classnote.R;

import java.util.Observable;
import java.util.Observer;

public class PastAssignmentListFragment extends ListFragment implements Observer {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    public static final String ARG_SECTION_NUMBER = "section_number";

    public PastAssignmentListFragment() {

    }


    public void onResume() {
        super.onResume();
        AssignmentAdapter assignmentAdapter = (AssignmentAdapter)this.getListAdapter();
        assignmentAdapter.setList(Datamart.getInstance().getPastAssignments());
        assignmentAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_assignment_list, container, false);

        this.setListAdapter(new AssignmentAdapter(Datamart.getInstance().getPastAssignments(), this.getActivity()));

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
        this.setListAdapter(new AssignmentAdapter(Datamart.getInstance().getPastAssignments(), this.getActivity()));
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        AssignmentAdapter adapter = (AssignmentAdapter)getListAdapter();
        Assignment a = (Assignment)adapter.getItem(position);
        Intent i = new Intent(this.getActivity(), AssignmentDetailActivity.class);
        i.putExtra("ASSIGNMENT", a);
        startActivity(i);
    }


}