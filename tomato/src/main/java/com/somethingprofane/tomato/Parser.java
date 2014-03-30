package com.somethingprofane.tomato;

import android.app.Activity;
import android.content.Context;
import android.util.Base64;

import com.j256.ormlite.dao.DaoManager;
import com.somethingprofane.db.DatabaseHelper;
import com.somethingprofane.db.DatabaseManager;

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

    public ArrayList<Device> parseDeviceList(String deviceHTML){
        ArrayList<Device> deviceListDHCP = new ArrayList<Device>();
        ArrayList<Device> deviceListWIFI = new ArrayList<Device>();

        deviceListDHCP = parseDeviceDHCPLeaseInfo(deviceHTML);
        deviceListWIFI = parseWIFIConnectivityInfo(deviceHTML);

        deviceListDHCP = compareWiredWirelessDevices(deviceListDHCP, deviceListWIFI);

        // ---- This is where the error takes place. The instance is null! ---- //
        //TODO fix this error. For some reason the instance is null!
        //DatabaseManager.getInstance().addDeviceList(deviceListDHCP);
        return deviceListDHCP;
    }

    private ArrayList<Device> compareWiredWirelessDevices(ArrayList<Device> deviceListDHCP, ArrayList<Device> deviceListWIFI) {
        for(Device device : deviceListDHCP){
            for(Device deviceWifi: deviceListWIFI){
                if(deviceWifi.getDeviceMacAddr().equals(device.getDeviceMacAddr())){
                    device.setDeviceType("wireless");
                    device.setDeviceWifiConnected(true);
                }
            }
        }
        return deviceListDHCP;
    }

    private ArrayList<Device> parseDeviceDHCPLeaseInfo(String deviceHTML) {
        String[] deviceInfoArray;
        ArrayList<Device> deviceList = new ArrayList<Device>();
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
                //TODO check the database and see if the device is in the db to set the device type.
                if(verifyWifiToDB(device)){
                    device.setDeviceType("wireless");
                    device.setDeviceWifiConnected(false);
                }else{
                    device.setDeviceType("wired");
                    device.setDeviceWifiConnected(false);
                }
                deviceList.add(device);
            }
        }
        return deviceList;
    }

    private boolean verifyWifiToDB(Device device) {
        Device device2;
        if(DatabaseManager.getInstance() != null) {
            device2 = DatabaseManager.getInstance().getDeviceById(device.getDeviceMacAddr());
            if(device2.getDeviceMacAddr().equals(device.getDeviceMacAddr())){
                return true;
            }
        }
        return false;
    }

    private ArrayList<Device> parseWIFIConnectivityInfo(String deviceHTML) {
        String[] deviceInfoArray;
        ArrayList<Device> deviceList = new ArrayList<Device>();
        String pattern = "wldev([^;]*)";
        Pattern r = Pattern.compile(pattern, Pattern.DOTALL);
        Matcher m = r.matcher(deviceHTML);
        if(m.find()){
            pattern = "(?<=\\[)(.*?)(?=\\])";
            Pattern r2 = Pattern.compile(pattern, Pattern.DOTALL);
            Matcher m2 = r2.matcher(m.group(1));
            while(m2.find()){
                deviceInfoArray = m2.group(1).trim().replaceAll("[\\[']", "").split(",");
                Device device = new Device();
                device.setDeviceMacAddr(deviceInfoArray[1]);
                deviceList.add(device);
            }
        }
        return deviceList;
    }
}
