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

/**
 * The fragment for displaying current assignments (i.e. the ones with a due date in the future).
 * This class is an observer of the Datamart and updates its content when the Datamart updates.
 */
public class CurrentAssignmentListFragment extends ListFragment implements Observer {

    @Override
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Datamart.getInstance().addObserver(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Datamart.getInstance().deleteObserver(this);
    }

    @Override
    public void update(Observable observable, Object data) {
        this.setListAdapter(new AssignmentAdapter(Datamart.getInstance().getUpcomingAssignments(), this.getActivity()));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        AssignmentAdapter adapter = (AssignmentAdapter)getListAdapter();
        Assignment a = (Assignment)adapter.getItem(position);
        Intent i = new Intent(this.getActivity(), AssignmentDetailActivity.class);
        i.putExtra("ASSIGNMENT", a);
        startActivity(i);
    }

}