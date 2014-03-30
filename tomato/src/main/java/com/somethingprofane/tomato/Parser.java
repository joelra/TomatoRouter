package com.somethingprofane.tomato;

import android.util.Base64;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by somethingPr0fane on 2/6/14.
 */
public class Parser {

    public String parserRouterName(String html){
        String pattern = "router_name: '(.*?)'";
        String routerName = null;
        Pattern r = Pattern.compile(pattern, Pattern.DOTALL);
        Matcher m = r.matcher(html);
        if(m.find()){
            System.out.println(m.group(1));
            routerName = m.group(1);
        }
        return routerName;
    }

    public String parseWanHwAddr(String html){
        String wanHwAddr = null;
        String pattern = "wan_hwaddr: '(.*?)'";
        Pattern r = Pattern.compile(pattern, Pattern.DOTALL);
        Matcher m = r.matcher(html);
        if(m.find()){
            System.out.println(m.group(1));
            wanHwAddr = m.group(1);
        }
        return wanHwAddr;
    }

    public String parseLanIpAddr(String html){
        String lanIpAddr = null;
        String pattern = "lan_ipaddr: '(.*?)'";
        Pattern r = Pattern.compile(pattern, Pattern.DOTALL);
        Matcher m = r.matcher(html);
        if(m.find()){
            System.out.println(m.group(1));
            lanIpAddr = m.group(1);
        }
        return lanIpAddr;
    }

    public String parseModelName(String html){
        String modelName = null;
        String pattern = "t_model_name: '(.*?)'";
        Pattern r = Pattern.compile(pattern, Pattern.DOTALL);
        Matcher m = r.matcher(html);
        if(m.find()){
            System.out.println(m.group(1));
            modelName = m.group(1);
        }
        return modelName;
    }

    public String parseUptime(String html){
        String uptime = null;
        String pattern = "uptime_s: '(.*?)'";
        Pattern r = Pattern.compile(pattern, Pattern.DOTALL);
        Matcher m = r.matcher(html);
        if(m.find()){
            System.out.println(m.group(1));
            uptime = m.group(1);
        }
        return uptime;
    }

    public String parseTotalRam(String html){
        String totalRam = null;
        String pattern = "freeram: (.*?),";
        Pattern r = Pattern.compile(pattern, Pattern.DOTALL);
        Matcher m = r.matcher(html);
        if(m.find()){
            System.out.println(m.group(1));
            double totalRamNum = Integer.parseInt(m.group(1));
            totalRamNum = totalRamNum / 1000;
            totalRam = totalRamNum + "kb";
        }
        return totalRam;
    }

    public String parseFreeRam(String html){
        String freeRam = null;
        String pattern = "\\sfreeram: (.*?),";
        Pattern r = Pattern.compile(pattern, Pattern.DOTALL);
        Matcher m = r.matcher(html);
        if(m.find()){
            System.out.println(m.group(1));
            double freeRamNum = Integer.parseInt(m.group(1));
            freeRamNum = freeRamNum / 1000;
            freeRam = freeRamNum + "kb";
        }
        return freeRam;
    }

    public String parseSsid(String html){
        String ssid = null;
        String brian = "Brian";
        String regex = "wl_ssid: '(.*?)'";
        Pattern r = Pattern.compile(regex, Pattern.DOTALL);
        Matcher m = r.matcher(html);
        if(m.find()){
            System.out.println("HEY! SSID! " + m.group(1));
            ssid = m.group(1);
        }
        return brian;
    }
    public String parseSubnet(String html){
        String subnet = null;
        return subnet;
    }
    public String parseDhcpPool(String html){
        String dPool = null;
        return dPool;
    }
    public String parseDhcpLeaseTime(String html){
        String dTime = null;
        return dTime;
    }
    public String parseSharedKey(String html){
        String sKey = null;
        return sKey;
    }
    public String parseEncryption(String html){
        String encrypt = null;
        return encrypt;
    }
    public String parseSecurity(String html){
        String security = null;
        return security;
    }
    public String parseWireless(String html){
        String wireLess = null;
        return wireLess;
    }




    public ArrayList<Device> parseDeviceList(String deviceHTML){
        ArrayList<Device> deviceList = new ArrayList<Device>();

        deviceList = parseDeviceDHCPLeaseInfo(deviceList, deviceHTML);
        deviceList = parseWIFIConnectivityInfo(deviceList, deviceHTML);

        return deviceList;
    }

    private ArrayList<Device> parseWIFIConnectivityInfo(ArrayList<Device> deviceList, String deviceHTML) {
        String[] deviceInfoArray;
        String pattern = "wldev([^;]*)";
        Pattern r = Pattern.compile(pattern, Pattern.DOTALL);
        Matcher m = r.matcher(deviceHTML);
        if(m.find()){
            pattern = "(?<=\\[)(.*?)(?=\\])";
            Pattern r2 = Pattern.compile(pattern, Pattern.DOTALL);
            Matcher m2 = r2.matcher(m.group(1));
            while(m2.find()){
                deviceInfoArray = m2.group(1).trim().replaceAll("[\\[']", "").split(",");
                for(Device device : deviceList){
                    if(device.getDeviceMacAddr().equals(deviceInfoArray[1])){
                        device.setDeviceType("wireless");
                        device.setDeviceWifiConnected(true);
                    }
                }
            }
        }
        return deviceList;
    }

    private ArrayList<Device> parseDeviceDHCPLeaseInfo(ArrayList<Device> deviceList, String deviceHTML) {
        String[] deviceInfoArray;
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

                // Set these as assuming the device is not wireless.
                //
                if(device.getDeviceType().equals("") && !device.getDeviceType().equals("wireless")) {
                    device.setDeviceWifiConnected(false);
                    device.setDeviceType("wired");
                }
                deviceList.add(device);
            }
        }
        return deviceList;
    }
}
