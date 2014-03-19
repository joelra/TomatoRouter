package com.somethingprofane.tomato;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.List;

/**
 * Created by somethingPr0fane on 3/18/14.
 */
public class DeviceListBaseAdapter extends BaseAdapter {

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
        int position;
        View root;

        public ViewHolder(int postition, View view){
            super();
            this.position = position;
            this.root = view;
            setView();
        }

        private void setView() {
            // TODO: place in an onclick listner on the WiFi toggle that will turn the wifi on and off.
        }
    }

    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder holder = null;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null) {
//            convertView = inflater.inflate(R.layout.list_item, null);
//            holder = new ViewHolder(position, convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Device device = (Device) getItem(position);
        // Set the info for each row:
        holder.txtDeviceName.setText(device.getDeviceName());
        holder.txtDeviceIP.setText(device.getDeviceIPAddr());

        //TODO Check to see if the device is currently connected via wifi and display the appropriate icon

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
