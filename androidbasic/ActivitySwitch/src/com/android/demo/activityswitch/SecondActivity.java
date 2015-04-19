package com.android.demo.activityswitch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main2);
		Intent intent = getIntent();
		String value = intent.getStringExtra("string_value");
		int textSize = intent.getIntExtra("text_size", 15);
		TextView text = (TextView) findViewById(R.id.textView1);
		text.setText(value);
		text.setTextSize(textSize);
	}
}


