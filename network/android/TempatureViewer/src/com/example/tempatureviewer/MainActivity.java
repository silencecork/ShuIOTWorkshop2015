package com.example.tempatureviewer;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.networkcommunication.volleymgr.NetworkManager;
import com.github.mikephil.charting.animation.AnimationEasing.EasingOption;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {
	
	private ProgressDialog mProgressDialog;
	private static final String URL = "http://example-tempature-api-server.herokuapp.com/api/queryDataPoint?limit=30";
	private LineChart mChart;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mProgressDialog = new ProgressDialog(this);
		mProgressDialog.setMessage("Please wait");
		
		mChart = (LineChart) findViewById(R.id.chart);
        fetchData();
	}
	
	private void fetchData() {
		mChart.setData(null);
		mProgressDialog.show();
		JsonArrayRequest request = new JsonArrayRequest(URL, mOnResponseListenr, mOnErrorListener);
		NetworkManager.getInstance(this).request(null, request);
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_refresh) {
			fetchData();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private Listener<JSONArray> mOnResponseListenr = new Listener<JSONArray>() {

		@Override
		public void onResponse(JSONArray array) {
			ArrayList<String> xVals = new ArrayList<String>();
			ArrayList<Entry> yVals = new ArrayList<Entry>();
			for (int i = 0; i < array.length(); i++) {
				try {
					JSONObject obj = array.getJSONObject(i);
					String time = obj.getString("time");
					float tempature = (float) obj.getDouble("value");
					
					xVals.add(time);
					yVals.add(new Entry((float) tempature, i));
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
			LineDataSet set1 = new LineDataSet(yVals, "Sensor 1");
			set1.setDrawCircles(true);
			
			ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
	        dataSets.add(set1);
	        
	        LineData data = new LineData(xVals, dataSets);
	        mChart.setData(data);
	        mChart.animateX(1500, EasingOption.EaseInOutQuart);
	        
	        Legend l = mChart.getLegend();
	        l.setForm(LegendForm.LINE);
	        
	        mProgressDialog.dismiss();
		}
	};

	private ErrorListener mOnErrorListener = new ErrorListener() {

		@Override
		public void onErrorResponse(VolleyError err) {
			mProgressDialog.dismiss();
		}
	};
	
}
