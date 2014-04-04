package com.somethingprofane.tomato;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.somethingprofane.db.DatabaseManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by somethingPr0fane on 3/18/14.
 */
public class DeviceListBaseAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Device> deviceList;
    private AlertDialog alert;
    private DeviceScreen activity;

    public DeviceListBaseAdapter(Context context, ArrayList<Device> devices){
        this.context = context;
        this.deviceList = devices;
        this.activity = (DeviceScreen) context;
    }

    private class ViewHolder {
        ToggleButton wifiToggle;
        TextView txtDeviceName;
        TextView txtDeviceIP;
        ImageView imgDeviceIcon;
        TextView dialog_name;
        TextView dialog_ip;
        Button dialog_ok;
        Button dialog_cancel;
    }

    public View getView(final int position, View convertView, ViewGroup parent){
        ViewHolder holder = null;
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

        final Device device = (Device) getItem(position);
        // Set the info for each row:
        holder.txtDeviceName.setText(device.getDeviceName());
        holder.txtDeviceIP.setText(device.getDeviceIPAddr());

        if(device.isDeviceRestricted()){
            // The internet access to the device is restricted
            holder.wifiToggle.setChecked(false);
        } else {
            holder.wifiToggle.setChecked(true);
        }

        // OnClick Listener for the toggle button for Wifi:
        holder.wifiToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Change the wifi State of the Device object:
                device.setDeviceRestricted(!device.isDeviceRestricted());
                // Call back to the Activity to run Network Code to update the device to the router.
                activity.updateNetworkStatusForDevice(device);
            }
        });
        if(device.getDeviceType().equals("wireless")) {
            if(device.isDeviceWifiConnected()){
                holder.imgDeviceIcon.setBackgroundResource(R.drawable.devices_wifi_on);
            }else {
                holder.imgDeviceIcon.setBackgroundResource(R.drawable.devices_wifi_off);
            }
        }else {
            holder.imgDeviceIcon.setBackgroundResource(R.drawable.device_list_icon);
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewHolder holder = new ViewHolder();
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                View dialogView = inflater.inflate(R.layout.dialog_device, null);
                dialog.setTitle("Device Information");
                holder.dialog_name = (TextView) dialogView.findViewById(R.id.dialog_device_txtName);
                holder.dialog_ip = (TextView) dialogView.findViewById(R.id.dialog_device_txtIPAddress);
                holder.dialog_ok = (Button) dialogView.findViewById(R.id.dialog_device_btnOK);
                holder.dialog_cancel = (Button) dialogView.findViewById(R.id.dialog_device_btnCancel);

                holder.dialog_name.setText(device.getDeviceName());
                holder.dialog_ip.setText(device.getDeviceIPAddr());
                holder.dialog_ok.setOnClickListener(dialogDevice_UpdateOnClickListener);
                holder.dialog_cancel.setOnClickListener(dialogDevice_DismissOnClickListener);

                dialog.setView(dialogView);
                alert = dialog.create();
                alert.show();
            }
        });

        return convertView;
    }

    private Button.OnClickListener dialogDevice_UpdateOnClickListener = new Button.OnClickListener(){

        @Override
        public void onClick(View view) {
            //Todo Change the group that the device is currently in.
        }
    };
    private Button.OnClickListener dialogDevice_DismissOnClickListener = new Button.OnClickListener(){

        @Override
        public void onClick(View view) {
            alert.dismiss();
        }
    };

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
