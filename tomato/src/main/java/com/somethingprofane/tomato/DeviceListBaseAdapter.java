package com.somethingprofane.tomato;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by somethingPr0fane on 3/18/14.
 */
public class DeviceListBaseAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Device> deviceList;

    public DeviceListBaseAdapter(Context context, ArrayList<Device> devices){
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
            convertView = inflater.inflate(R.layout.list_item_device, null);
            holder = new ViewHolder();
            holder.txtDeviceIP = (TextView) convertView.findViewById(R.id.deviceRowIP);
            holder.txtDeviceName = (TextView) convertView.findViewById(R.id.deviceRowName);
            holder.imgDeviceIcon = (ImageView) convertView.findViewById(R.id.deviceRowWifiImg);
            holder.wifiToggle = (ToggleButton) convertView.findViewById(R.id.deviceRowToggleBtn);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Device device = (Device) getItem(position);
        // Set the info for each row:
        holder.txtDeviceName.setText(device.getDeviceName());
        holder.txtDeviceIP.setText(device.getDeviceIPAddr());
        // THIS IS FOR TESTING:
        //holder.imgDeviceIcon.setImageResource(R.drawable.devices_wifi_on);
        holder.wifiToggle.setChecked(false);
        //TODO Check to see if the device is currently connected via wifi and display the appropriate icon
        if(device.getDeviceType() == "wireless") {
            if(device.isDeviceWifiConnected()){
                holder.imgDeviceIcon.setImageResource(R.drawable.devices_wifi_on);
            }else {
                holder.imgDeviceIcon.setImageResource(R.drawable.devices_wifi_off);
            }
        }else {
            holder.imgDeviceIcon.setImageResource(R.drawable.device_list_icon);
        }
        return convertView;
    }

    @Override
    public int getCount(){
        return deviceList.size();
    }

    @Override
    public Object getItem(int i) {
        return deviceList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return deviceList.indexOf(getItem(i));
    }


}
