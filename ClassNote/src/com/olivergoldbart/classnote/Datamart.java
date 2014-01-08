package com.olivergoldbart.classnote;

import java.util.ArrayList;

public class Datamart {

	ArrayList<Assignment> datastore;
	
	public Datamart() {
		datastore = new ArrayList<Assignment>();
	}
	
	public void addAssignment( Assignment assignment ) {
		datastore.add( assignment );
	}
	
	public ArrayList<Assignment> getArrayList() {
		return datastore;
	}
	
}
