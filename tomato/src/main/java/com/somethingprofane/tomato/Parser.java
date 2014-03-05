package com.somethingprofane.tomato;

import android.util.Base64;

import org.json.JSONArray;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by somethingPr0fane on 2/6/14.
 */
public class Parser {

    //TODO: Add check in login class for wifi connectivity.
    //TODO: 1. Clean up parser class
    //TODO: 2. Add POST method.
    //TODO: 3. Modify RouterInformationActivity to display router information and controls

    public String ParseHTMLFromString(String html){
        //TODO Implement this class
        throw null;
//        String returnHtml = "";
//        return returnHtml;
    }

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

}
