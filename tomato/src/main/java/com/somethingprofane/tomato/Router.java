package com.somethingprofane.tomato;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
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
    int totalRam;
    int freeRam;
    List deviceList;

    /**
     *
     * @param url
     * @param usrname
     * @param pswrd
     */
    public Router (String url, String usrname, String pswrd){
        Parser tempParser = new Parser();
        String returnedHtml = null;
        HashMap <String, String> tempHashMap = tempParser.buildParamsMap("_http_id", tempParser.GetRouterHTTPId());

        try {
            returnedHtml = tempParser.PostToWebadress(url+"/status.jsx","root","admin", tempHashMap);
            //Set all the values with the returned HTML
            setRouterName(returnedHtml);
            setFreeRam(returnedHtml);
            setLanIpAddr(returnedHtml);
            setModelName(returnedHtml);
            setTotalRam(returnedHtml);
            setUptime(returnedHtml);
            setWanHwAddr(returnedHtml);

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

        String pattern = "router_name: '(.*?)'";
        Pattern r = Pattern.compile(pattern, Pattern.DOTALL);
        Matcher m = r.matcher(html);
        if(m.find()){
            System.out.println(m.group(1));
            routerName = m.group(1);
        }
    }

    public String getWanHwAddr() {
        return wanHwAddr;
    }

    /**
     *
     * @param html string of html regex is run on to extract wanAddress
     */
    public void setWanHwAddr(String html) {
        String pattern = "wan_hwaddr: '(.*?)'";
        Pattern r = Pattern.compile(pattern, Pattern.DOTALL);
        Matcher m = r.matcher(html);
        if(m.find()){
            System.out.println(m.group(1));
            wanHwAddr = m.group(1);
        }
    }

    public String getLanIpAddr() {
        return lanIpAddr;
    }

    /**
     *
     * @param html string of html regex is run on to extract lan ip address
     */
    public void setLanIpAddr(String html) {
        String pattern = "lan_ipaddr: '(.*?)'";
        Pattern r = Pattern.compile(pattern, Pattern.DOTALL);
        Matcher m = r.matcher(html);
        if(m.find()){
            System.out.println(m.group(1));
            lanIpAddr = m.group(1);
        }
    }


    public String getModelName() {
        return modelName;
    }

    /**
     *
     * @param html - string of html regex is run on to extract modelName
     */
    public void setModelName(String html) {
        String pattern = "t_model_name: '(.*?)'";
        Pattern r = Pattern.compile(pattern, Pattern.DOTALL);
        Matcher m = r.matcher(html);
        if(m.find()){
            System.out.println(m.group(1));
            modelName = m.group(1);
        }
    }

    public String getUptime() {
        return uptime;
    }

    /**
     *
     * @param html string of html regex is run on to extract setuptime
     */
    public void setUptime(String html) {
        String pattern = "uptime_s: '(.*?)'";
        Pattern r = Pattern.compile(pattern, Pattern.DOTALL);
        Matcher m = r.matcher(html);
        if(m.find()){
            System.out.println(m.group(1));
            uptime = m.group(1);
        }
    }

    public int getTotalRam() {
        return totalRam;
    }

    /**
     *
     * @param html - string of html regex is run on to extract totalRam
     */
    public void setTotalRam(String html) {
        String pattern = "freeram: (.*?),";
        Pattern r = Pattern.compile(pattern, Pattern.DOTALL);
        Matcher m = r.matcher(html);
        if(m.find()){
            System.out.println(m.group(1));
            totalRam = Integer.parseInt(m.group(1));
        }
    }

    public int getFreeRam() {
        return freeRam;
    }

    public void setFreeRam(String html) {
        String pattern = "\\sfreeram: (.*?),";
        Pattern r = Pattern.compile(pattern, Pattern.DOTALL);
        Matcher m = r.matcher(html);
        if(m.find()){
            System.out.println(m.group(1));
            freeRam = Integer.parseInt(m.group(1));
        }
    }

    public List getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(List deviceList) {
        this.deviceList = deviceList;
    }


}

