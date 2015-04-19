	package com.android.demo.activityswitch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Main extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(new OnClickListener() {

    		@Override
    		public void onClick(View v) {
    			Intent intent = new Intent(Main.this, SecondActivity.class);
    			intent.putExtra("string_value", "This is Main Activity");
    			intent.putExtra("text_size", 25);
    			startActivity(intent);
    		}
        	
        });
    }
}

