package com.example.listviewexample;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
	private static final String[] NAMES = {
		"John", "Luke", "Matthew", "Peter", "Mark", 
		"John", "Luke", "Matthew", "Peter", "Mark", 
		"John", "Luke", "Matthew", "Peter", "Mark", 
		"John", "Luke", "Matthew", "Peter", "Mark"};	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ListView listView = (ListView) findViewById(R.id.listview);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, NAMES);
		listView.setAdapter(adapter);
	}
}
