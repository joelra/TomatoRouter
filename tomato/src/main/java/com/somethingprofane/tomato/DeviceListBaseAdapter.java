package com.somethingprofane.tomato;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.List;

/**
 * Created by somethingPr0fane on 3/18/14.
 */
public class DeviceListBaseAdapter {

    private Context context;
    private List<Device> deviceList;

    public DeviceListBaseAdapter(Context context, List<Device> devices){
        this.context = context;
        this.deviceList = devices;
    }

    private class ViewHolder {
        ToggleButton wifiToggle;
        TextView txtDeviceName;
        TextView txtDeviceIP;
        ImageView imgDeviceIcon;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder holder = null;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null) {
//            convertView = inflater.inflate(R.layout.list_item, null);
//            holder = new ViewHolder(position, convertView);
        }

        return convertView;
    }
}
