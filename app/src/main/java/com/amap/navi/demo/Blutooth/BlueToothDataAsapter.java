package com.amap.navi.demo.Blutooth;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.navi.demo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlueToothDataAsapter extends BaseAdapter {
    private Context context;
    List<HashMap> list;
    HashMap hashMap;

    public BlueToothDataAsapter(Context context, List<HashMap> list){
        this.context=context;
        this.list=list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HoleView holeView;
        if(convertView==null){
            holeView=new HoleView();
            convertView= View.inflate(context, R.layout.devices,null);
            holeView.devicename= (TextView) convertView.findViewById(R.id.devicename);
            convertView.setTag(holeView);
        }
        else {
            holeView=(HoleView) convertView.getTag();
        }
        hashMap=list.get(position);
        holeView.devicename.setText((String)hashMap.get("blue_name"));
        return convertView;
    }


    static class HoleView{
        TextView devicename;
    }
}
