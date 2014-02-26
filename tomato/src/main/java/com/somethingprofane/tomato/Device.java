package com.somethingprofane.tomato;

/**
 * Created by somethingPr0fane on 2/25/14.
 */
public class Device {
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
        Parser parser = new Parser();
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
}
