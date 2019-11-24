package com.amap.navi.demo.Blutooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.amap.navi.demo.R;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;



public class Bluetooth extends AppCompatActivity{
    String TAG="Bluetooth";
    private List<HashMap> blueList = new ArrayList<HashMap>();  //数据存储
    private BlueToothDataAsapter adapter;   //适配器
    private List<BluetoothDevice> deviceList = new ArrayList<BluetoothDevice>();
    HashMap blueHashMap=null;
    BluetoothAdapter mBluetoothAdapter = null;
    private ConnectedThread mConnectedThread;
    EditText Receive, Send;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bluetooth);
        ListView list = (ListView) findViewById(R.id.bule_view);
        Receive = (EditText) findViewById(R.id.Receive);
        Send = (EditText) findViewById(R.id.Send);
        button = (Button) findViewById(R.id.button2);
        isSupportedBluetooth();
        Set<BluetoothDevice> pairedDevices=  mBluetoothAdapter.getBondedDevices();
        Log.d(getPackageName(),"获取已经配对devices"+pairedDevices.size());
        for (BluetoothDevice bluetoothDevice : pairedDevices) {
            blueHashMap = new HashMap();
            Log.d(TAG, "已经配对的蓝牙设备：");
            Log.d(TAG, bluetoothDevice.getName());
            Log.d(TAG, bluetoothDevice.getAddress());
            blueHashMap.put("blue_device", bluetoothDevice);
            blueHashMap.put("blue_name", bluetoothDevice.getName());
            blueHashMap.put("blue_address", bluetoothDevice.getAddress());
            blueList.add(blueHashMap);
            deviceList.add(bluetoothDevice);
        }
        adapter=new BlueToothDataAsapter(Bluetooth.this,blueList);
        list.setAdapter(adapter);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mConnectedThread == null){
                    Toast.makeText(getApplicationContext(), "设备输出流未就绪！！！", Toast.LENGTH_SHORT).show();
                } else {
                    if (Send.getText().toString().isEmpty()) {
                        return;
                    }else {
                        String sendStr = Send.getText().toString();
                        char[] chars = sendStr.toCharArray();
                        byte[] bytes = new byte[chars.length];
                        for (int i=0; i < chars.length; i++) {
                            bytes[i] = (byte) chars[i];
                        }
                        Send.setText("");
                        mConnectedThread.write(bytes);
                    }
                }
            }
        });
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Log.e(TAG, "开始连接");
                BluetoothDevice device = deviceList.get(position);
                Log.e(TAG, "想要连接的远程主机：" + device);
                Log.e(TAG,"蓝牙名称："+device.getName());
                BluetoothSocket socket = null;
                try {
                    // 蓝牙串口服务对应的UUID。如使用的是其它蓝牙服务，需更改下面的字符串
                    UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
                    socket = device.createRfcommSocketToServiceRecord(MY_UUID);
                } catch (Exception e) {
                    Log.d("log", "获取Socket失败");
                    Toast.makeText(getApplicationContext(), "获取Socket失败", Toast.LENGTH_SHORT).show();
                    return;
                }
                mBluetoothAdapter.cancelDiscovery();
                try {
                    // Connect the device through the socket. This will block
                    // until it succeeds or throws an exception
                    socket.connect();
                    Log.d("log", "连接成功");
                    Toast.makeText(getApplicationContext(), "连接成功", Toast.LENGTH_SHORT).show();
                    BluetoothUtils.setBluetoothSocket(socket);
                    //open_thread();
                    //progressbarSearchDevices.setVisibility(View.INVISIBLE);
                    // 连接成功，返回主界面
                    finish();
                } catch (IOException connectException) {
                    // Unable to connect; close the socket and get out
                    Log.d("log", "连接失败");
                    try {
                        socket.close();
                    } catch (IOException closeException) {
                        ;
                    }
                }
            }
        });
    }


    //判断是否支持蓝牙
    private void isSupportedBluetooth() {
        mBluetoothAdapter= BluetoothAdapter.getDefaultAdapter();//得到默认适配器
        if(mBluetoothAdapter!=null){//支持蓝牙
            //蓝牙是否打开
            if(!mBluetoothAdapter.isEnabled()){
                mBluetoothAdapter.enable();//启动蓝牙
            }
        }else {
            Toast.makeText(Bluetooth.this,"不支持蓝牙", Toast.LENGTH_SHORT).show();
        }
    }
    //-----------------------------消毁--------------------------------
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private void open_thread(){

        //回到主界面后检查是否已成功连接蓝牙设备
        if (BluetoothUtils.getBluetoothSocket() == null || mConnectedThread != null) {
            Toast.makeText(Bluetooth.this,"未连接", Toast.LENGTH_SHORT).show();
            return;
        }
        //已连接蓝牙设备，则接收数据，并显示到接收区文本框
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case ConnectedThread.MESSAGE_READ:
                        byte[] buffer = (byte[]) msg.obj;
                        int length = msg.arg1;
                        for (int i=0; i < length; i++) {
                            char c = (char) buffer[i];
                            Receive.getText().append(c);
                        }
                        break;
                }
            }
        };
        //启动蓝牙数据收发线程
        mConnectedThread = new ConnectedThread(BluetoothUtils.getBluetoothSocket(), handler);
        mConnectedThread.start();
    }
}
