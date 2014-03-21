package com.somethingprofane.tomato;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by somethingPr0fane on 2/25/14.
 */
public class Router implements Parcelable {
    /**
     * This class with hold all the informaiton about the router:
     * IP address
     * MAC Address
     * Devices Connected
     * etc.
     */

    String routerName;
    String wanHwAddr;
    String lanIpAddr;
    String modelName;
    String uptime;
    String totalRam;
    String usrname;
    String url;
    String pswrd;


    public String getHttpId() {
        return httpId;
    }

    public void setHttpId(String httpId) {
        this.httpId = httpId;
    }

    String httpId;
    String freeRam;
    ArrayList<Device> deviceList = new ArrayList<Device>();

    /**
     *
     * @param url
     * @param usrname
     * @param pswrd
     */
    public Router (String url, String usrname, String pswrd){

        this.url = url;
        this.usrname = usrname;
        this.pswrd = pswrd;
        Connection conn = new Connection();
        String returnedHtml = null;
        setHttpId(conn.GetRouterHTTPId());
        HashMap <String, String> tempHashMap = conn.buildParamsMap("_http_id", getHttpId());

        try {
            returnedHtml = conn.PostToWebadress(url+"/status-data.jsx","root","admin", tempHashMap);
            //Set all the values with the returned HTML
            setRouterName(returnedHtml);
            setFreeRam(returnedHtml);
            setLanIpAddr(returnedHtml);
            setModelName(returnedHtml);
            setTotalRam(returnedHtml);
            setUptime(returnedHtml);
            setWanHwAddr(returnedHtml);
            setDeviceList();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getUrl() {
        return url;
    }

    public String getPswrd() {
        return pswrd;
    }

    public String getUsrname(){
        return usrname;
    }

    public String getRouterName() {
        return routerName;
    }

    /**
     *
     * @param html string of html regex is run on to extract routerName
     */
    public void setRouterName(String html) {

       routerName = new Parser().parserRouterName(html);

    }

    public String getWanHwAddr() {
        return wanHwAddr;
    }

    /**
     *
     * @param html string of html regex is run on to extract wanAddress
     */
    public void setWanHwAddr(String html) {

     wanHwAddr = new Parser().parseWanHwAddr(html);

    }

    public String getLanIpAddr() {
        return lanIpAddr;
    }

    /**
     *
     * @param html string of html regex is run on to extract lan ip address
     */
    public void setLanIpAddr(String html) {

        lanIpAddr = new Parser().parseLanIpAddr(html);

    }


    public String getModelName() {
        return modelName;
    }

    /**
     *
     * @param html - string of html regex is run on to extract modelName
     */
    public void setModelName(String html) {

        modelName = new Parser().parseModelName(html);

    }

    public String getUptime() {
        return uptime;
    }

    /**
     *
     * @param html string of html regex is run on to extract setuptime
     */
    public void setUptime(String html) {

        uptime = new Parser().parseUptime(html);

    }

    public String getTotalRam() {
        return totalRam;
    }

    /**
     *
     * @param html - string of html regex is run on to extract totalRam
     */
    public void setTotalRam(String html) {

        totalRam = new Parser().parseTotalRam(html);

    }

    public String getFreeRam() {
        return freeRam;
    }

    public void setFreeRam(String html) {

        freeRam = new Parser().parseFreeRam(html);
    }

    public ArrayList<Device> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList() {
        Connection conn = new Connection();
        String[] deviceInfoArray;

        String deviceHTML = "";
        deviceHTML = conn.GetHTMLFromURL("http://192.168.1.1/status-devices.asp", "root", "admin");
        String pattern = "dhcpd_lease([^;]*)";
        Pattern r = Pattern.compile(pattern, Pattern.DOTALL);
        Matcher m = r.matcher(deviceHTML);
        if(m.find()){
            pattern = "(?<=\\[)(.*?)(?=\\])";
            Pattern r2 = Pattern.compile(pattern, Pattern.DOTALL);
            Matcher m2 = r2.matcher(m.group(1));
            while(m2.find()){
                deviceInfoArray = m2.group(1).trim().replaceAll("[\\[']", "").split(",");
                Device device = new Device();
                device.setDeviceName(deviceInfoArray[0]);
                device.setDeviceIPAddr(deviceInfoArray[1]);
                device.setDeviceMacAddr(deviceInfoArray[2]);
                device.setDeviceConnTime(deviceInfoArray[3] + deviceInfoArray[4]);
                this.deviceList.add(device);
            }
        }
    }

    public ArrayList getDeviceListNames() {
        ArrayList<String> deviceListName = new ArrayList<String>();

        for (int x=0; x<this.getDeviceList().size(); x++){
            deviceListName.add(this.getDeviceList().get(x).getDeviceName());
        }
        return deviceListName;

    }

    public ArrayList getDeviceListIPs(){

        ArrayList<String> deviceListIp = new ArrayList<String>();

        for (int x=0; x<this.getDeviceList().size(); x++){
            deviceListIp.add( "IP: "+this.getDeviceList().get(x).getDeviceIPAddr());
        }
        return deviceListIp;
    }

    /**
     * Used to refresh router information - namely FreeRam, TotalRam, Uptime, and DeviceList
     */
    public void refresh(){
        Connection conn = new Connection();
        String returnedHtml = null;
        setHttpId(conn.GetRouterHTTPId());
        HashMap <String, String> tempHashMap = conn.buildParamsMap("_http_id", getHttpId());

        try {
            returnedHtml = conn.PostToWebadress(url+"/status-data.jsx","root","admin", tempHashMap);
            //Set all the values with the returned HTML
            setRouterName(returnedHtml);
            setFreeRam(returnedHtml);
            setTotalRam(returnedHtml);
            setUptime(returnedHtml);
            setDeviceList();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static final Parcelable.Creator<Router> CREATOR
            = new Parcelable.Creator<Router>() {
        public Router createFromParcel(Parcel in) {
            return new Router(in);
        }

        public Router[] newArray(int size) {
            return new Router[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeString(routerName);
        out.writeString(wanHwAddr);
        out.writeString(lanIpAddr);
        out.writeString(modelName);
        out.writeString(uptime);
        out.writeString(totalRam);
        out.writeString(usrname);
        out.writeString(url);
        out.writeString(pswrd);
        out.writeString(httpId);
        out.writeList(deviceList);

    }

    private Router (Parcel in){
        deviceList = new ArrayList<Device>();
        routerName = in.readString();
        wanHwAddr = in.readString();
        lanIpAddr = in.readString();
        modelName = in.readString();
        uptime = in.readString();
        totalRam = in.readString();
        usrname = in.readString();
        url = in.readString();
        pswrd = in.readString();
        httpId = in.readString();
        in.readList(deviceList,getClass().getClassLoader());
    }


}