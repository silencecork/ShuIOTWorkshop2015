
package com.example.arduinosensorreceiver;

import java.util.List;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.android.utility.bluetooth.BluetoothListAdapter;
import com.android.utility.bluetooth.LocalBluetoothManager;
import com.android.utility.bluetooth.OnBluetoothDiscoverEventListener;

public class DiscoveryActivity extends Activity {
    
    private ListView mListView;
    private BluetoothListAdapter mAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.discovery_activity);
        mListView = (ListView) findViewById(R.id.device_list);
        mAdapter = new BluetoothListAdapter(this);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(mItemClickListener);
        
        LocalBluetoothManager.getInstance().startSession(this);
        if (!LocalBluetoothManager.getInstance().isBluetoothTurnOn()) {
            LocalBluetoothManager.getInstance().turnOnBluetooth(this);
            return;
        }
        
        showBindDevices();
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.discover_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_scan) {
            LocalBluetoothManager.getInstance().discoverDevice(mDiscoverListener);
            return true;
        }
        return true;
    }
    
    private void showBindDevices() {
        List<BluetoothDevice> list = LocalBluetoothManager.getInstance().getPairedDevices();
        if (mAdapter != null) {
            mAdapter.setDeviceList(list);
        }
    }

    private OnBluetoothDiscoverEventListener mDiscoverListener = new OnBluetoothDiscoverEventListener() {
        
        @Override
        public void discoverFinish() {
            Toast.makeText(DiscoveryActivity.this, "discoverFinish", Toast.LENGTH_LONG).show();
        }
        
        @Override
        public void discoveredDevice(BluetoothDevice device, int rssi) {
            if (mAdapter != null) {
                mAdapter.addItem(device);
            }
        }
    };
    
    private OnItemClickListener mItemClickListener = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            BluetoothDevice device = (BluetoothDevice) parent.getAdapter().getItem(position);
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
