package com.somethingprofane.tomato;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by somethingPr0fane on 2/25/14.
 */
public class Router {
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
    Parser parser;

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
        parser = new Parser();
        String returnedHtml = null;
        setHttpId(parser.GetRouterHTTPId());
        HashMap <String, String> tempHashMap = parser.buildParamsMap("_http_id", getHttpId());

        try {
            returnedHtml = parser.PostToWebadress(url+"/status-data.jsx","root","admin", tempHashMap);
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


    public String getRouterName() {
        return routerName;
    }

    /**
     *
     * @param html string of html regex is run on to extract routerName
     */
    public void setRouterName(String html) {

       routerName = parser.parserRouterName(html);

    }

    public String getWanHwAddr() {
        return wanHwAddr;
    }

    /**
     *
     * @param html string of html regex is run on to extract wanAddress
     */
    public void setWanHwAddr(String html) {

     wanHwAddr = parser.parseWanHwAddr(html);

    }

    public String getLanIpAddr() {
        return lanIpAddr;
    }

    /**
     *
     * @param html string of html regex is run on to extract lan ip address
     */
    public void setLanIpAddr(String html) {

        lanIpAddr = parser.parseLanIpAddr(html);

    }


    public String getModelName() {
        return modelName;
    }

    /**
     *
     * @param html - string of html regex is run on to extract modelName
     */
    public void setModelName(String html) {

        modelName = parser.parseModelName(html);

    }

    public String getUptime() {
        return uptime;
    }

    /**
     *
     * @param html string of html regex is run on to extract setuptime
     */
    public void setUptime(String html) {

        uptime = parser.parseUptime(html);

    }

    public String getTotalRam() {
        return totalRam;
    }

    /**
     *
     * @param html - string of html regex is run on to extract totalRam
     */
    public void setTotalRam(String html) {

        totalRam = parser.parseTotalRam(html);

    }

    public String getFreeRam() {
        return freeRam;
    }

    public void setFreeRam(String html) {

        freeRam = parser.parseFreeRam(html);
    }

    public ArrayList<Device> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList() {
        Parser parser = new Parser();
        String[] deviceInfoArray;

        String deviceHTML = "";
        deviceHTML = parser.ParseHTMLFromURL("http://192.168.1.1/status-devices.asp", "root", "admin");
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

        String returnedHtml = null;
        setHttpId(parser.GetRouterHTTPId());
        HashMap <String, String> tempHashMap = parser.buildParamsMap("_http_id", getHttpId());

        try {
            returnedHtml = parser.PostToWebadress(url+"/status-data.jsx","root","admin", tempHashMap);
            //Set all the values with the returned HTML
            setFreeRam(returnedHtml);
            setTotalRam(returnedHtml);
            setUptime(returnedHtml);
            setDeviceList();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }




}