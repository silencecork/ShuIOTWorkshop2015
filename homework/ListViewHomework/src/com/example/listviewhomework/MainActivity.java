package com.example.listviewhomework;

import com.example.listvieweventexample.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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
		listView.setOnItemClickListener(mOnItemClickListener);
	}
	private OnItemClickListener mOnItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Intent intent = new Intent(MainActivity.this, SecondActivity.class);
			intent.putExtra("title", NAMES[position]);
			startActivity(intent);
		}
	};
}
