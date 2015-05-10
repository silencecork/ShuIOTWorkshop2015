package com.example.loginexample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.linearlayoutlogin.R;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// 設定畫面
		setContentView(R.layout.activity_main);
		
		// 找到按鈕
		Button loginButton = (Button) findViewById(R.id.btn_ok);
		Button cancelButton = (Button) findViewById(R.id.btn_cancel);

		loginButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				EditText username = (EditText) findViewById(R.id.username);
				EditText password = (EditText) findViewById(R.id.password);
				
				// 取得帳號密碼
				String strUserName = username.getText().toString();
				String strPassword = password.getText().toString();
				
				// 檢查輸入內容
				if (!strUserName.equals("") && !strPassword.equals("")) {
					Toast.makeText(MainActivity.this, "Login Complete", Toast.LENGTH_LONG).show();
				}
				
			}
			
		});
		cancelButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
			
		});
		
	}

}
