package com.somethingprofane.tomato;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by somethingPr0fane on 2/25/14.
 */
public class Device implements Parcelable {
    /**
     * Contains all the information about a connected deivce to the router:
     * MAC address
     * Name
     * IP address assigned
     * How long connected
     * etc.
     */

    // Variables
    String deviceMacAddr;
    String deviceName;
    String deviceIPAddr;
    String deviceConnTime;

    /**
     * Constructor
     */
    public Device(){

        //parser.PostToWebadress(routerIP, username, password,);
    }

    public String getDeviceMacAddr() {
        return deviceMacAddr;
    }

    public void setDeviceMacAddr(String deviceMacAddr) {
        this.deviceMacAddr = deviceMacAddr;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceIPAddr() {
        return deviceIPAddr;
    }

    public void setDeviceIPAddr(String deviceIPAddr) {
        this.deviceIPAddr = deviceIPAddr;
    }

    public String getDeviceConnTime() {
        return deviceConnTime;
    }

    public void setDeviceConnTime(String deviceConnTime) {
        this.deviceConnTime = deviceConnTime;
    }

    public static final Parcelable.Creator<Device> CREATOR
            = new Parcelable.Creator<Device>() {
        public Device createFromParcel(Parcel in) {
            return new Device(in);
        }

        public Device[] newArray(int size) {
            return new Device[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeString(deviceMacAddr);
        out.writeString(deviceName);
        out.writeString(deviceIPAddr);
        out.writeString(deviceConnTime);

    }

    private Device(Parcel in){
        deviceMacAddr = in.readString();
        deviceName = in.readString();
        deviceIPAddr = in.readString();
        deviceConnTime = in.readString();
    }



}
