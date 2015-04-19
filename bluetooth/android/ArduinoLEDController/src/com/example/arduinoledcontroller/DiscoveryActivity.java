
package com.example.arduinoledcontroller;

import java.util.List;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.utility.bluetooth.BluetoothListAdapter;
import com.android.utility.bluetooth.LocalBluetoothManager;
import com.android.utility.bluetooth.OnBluetoothDiscoverEventListener;

public class DiscoveryActivity extends Activity {
    
    private ListView mListView;
    private BluetoothListAdapter mAdapter;
    private Button mScanButton;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.discovery_activity);
        mListView = (ListView) findViewById(R.id.device_list);
        mAdapter = new BluetoothListAdapter(this);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(mItemClickListener);
        mScanButton = (Button) findViewById(R.id.btn_scan);
        mScanButton.setOnClickListener(mScanButtonOnClickListener );
        LocalBluetoothManager.getInstance().startSession(this);
        if (!LocalBluetoothManager.getInstance().isBluetoothTurnOn()) {
            LocalBluetoothManager.getInstance().turnOnBluetooth(this);
        } else {
        	showBindDevices();
        }
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!LocalBluetoothManager.getInstance().isBluetoothTurnOn()) {
            finish();
        } else {
            showBindDevices();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBluetoothManager.getInstance().endSession();
    }
    
    private void showBindDevices() {
        List<BluetoothDevice> list = LocalBluetoothManager.getInstance().getPairedDevices();
        if (mAdapter != null) {
            mAdapter.setDeviceList(list);
        }
    }
    
    private OnClickListener mScanButtonOnClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			mScanButton.setEnabled(false);
			LocalBluetoothManager.getInstance().discoverDevice(mDiscoverListener);
		}
	};

    private OnBluetoothDiscoverEventListener mDiscoverListener = new OnBluetoothDiscoverEventListener() {
        
        @Override
        public void discoverFinish() {
            Toast.makeText(DiscoveryActivity.this, "discoverFinish", Toast.LENGTH_LONG).show();
            mScanButton.setEnabled(true);
        }
        
        @Override
        public void discoveredDevice(BluetoothDevice device, int rssi) {
            mAdapter.addItem(device);
        }
    };
    
    private OnItemClickListener mItemClickListener = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            BluetoothDevice device = (BluetoothDevice) mAdapter.getItem(position);
            BluetoothDevice newDevice = LocalBluetoothManager.getInstance().getDeviceWithLatestStatus(device);
            if (!LocalBluetoothManager.getInstance().isPairedDevice(newDevice)) {
                LocalBluetoothManager.getInstance().pairDevice(newDevice);
            } else {
                Intent intent = new Intent(DiscoveryActivity.this, ClientConnectionActivity.class);
                intent.putExtra("device", newDevice);
                startActivity(intent);
            }
        }
        
    };
}
