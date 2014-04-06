package com.somethingprofane.tomato;

import android.os.Parcel;
import android.os.Parcelable;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


/**
 * Created by somethingPr0fane on 2/25/14.
 */
@DatabaseTable
public class Device implements Parcelable {
    /**
     * The Device class contains information regarding a device connected to the router.
     * To know if a device is connect, the DHCP_lease of the router is used. This could
     * give a device that is in that list, but actually isn't currently connected.
     */

    // Variables
    @DatabaseField(id=true)
    String deviceMacAddr;
    @DatabaseField
    String deviceName;
    @DatabaseField
    String deviceIPAddr;
    @DatabaseField
    String deviceConnTime;
    @DatabaseField
    String deviceType = "";
    @DatabaseField
    boolean deviceWifiConnected;

    @DatabaseField
    boolean deviceRestricted;
    @DatabaseField(foreign=true,foreignAutoRefresh=true)
    DeviceGroup deviceGroup;

    /**
     * Constructor
     */
    public Device(){

        //parser.PostToWebadress(routerIP, username, password,);
    }

    /**
     * Get a devices' MAC address.
     * @return MAC address
     */
    public String getDeviceMacAddr() {
        return deviceMacAddr;
    }

    /**
     * Set a devices' MAC address
     * @param deviceMacAddr The MAC address set to the device
     */
    public void setDeviceMacAddr(String deviceMacAddr) {
        this.deviceMacAddr = deviceMacAddr;
    }

    /**
     * Get the device name.
     * @return String of the device name
     */
    public String getDeviceName() {
        return deviceName;
    }

    /**
     * Set the name of the device.
     * @param deviceName String of the device name
     */
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    /**
     * Get the device IP address.
     * @return String of IP address
     */
    public String getDeviceIPAddr() {
        return deviceIPAddr;
    }

    /**
     * Set the device IP address. Is generally assigned from DHCP.
     * @param deviceIPAddr Device IP address
     */
    public void setDeviceIPAddr(String deviceIPAddr) {
        this.deviceIPAddr = deviceIPAddr;
    }

    /**
     * Get the device connection time to the router.
     * Currently unused.
     * @return
     */
    public String getDeviceConnTime() {
        return deviceConnTime;
    }

    /**
     * Set the device connection time to the router.
     * Currently unused.
     * @param deviceConnTime
     */
    public void setDeviceConnTime(String deviceConnTime) {
        this.deviceConnTime = deviceConnTime;
    }

    /**
     * Get device connection type. May either be 'wireless' or 'wired'.
     * @return wireless or wired
     */
    public String getDeviceType() {
        return deviceType;
    }

    /**
     * Set the device type. The device type is either wired or wireless.
     * @param deviceType String either wired or wireless
     */
    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    /**
     * True false statement if the device is connected to the Wifi.
     * This is found by making HTTP request to router device page and seeing
     * if the device is contained within the wl_lan array.
     * @return True/False
     */
    public boolean isDeviceWifiConnected() {
        return deviceWifiConnected;
    }

    /**
     * Set true is the device is currently connected to the wifi.
     * @param deviceWifiConnected
     */
    public void setDeviceWifiConnected(boolean deviceWifiConnected) {
        this.deviceWifiConnected = deviceWifiConnected;
    }

    public boolean isDeviceRestricted() {
        return deviceRestricted;
    }

    public void setDeviceRestricted(boolean deviceRestricted) {
        this.deviceRestricted = deviceRestricted;
    }

    /**
     * Get the device group that the device belongs to.
     * @return DeviceGroup object
     */
    public DeviceGroup getDeviceGroup(){
        return deviceGroup;
    }

    /**
     * Set the device group that the device belongs to.
     * @param group
     */
    public void setDeviceGroup(DeviceGroup group){
        this.deviceGroup = group;
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
        out.writeString(deviceType);

    }

    private Device(Parcel in){
        deviceMacAddr = in.readString();
        deviceName = in.readString();
        deviceIPAddr = in.readString();
        deviceConnTime = in.readString();
        deviceType = in.readString();
    }



}
