package com.somethingprofane.tomato;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by User1 on 3/30/2014.
 */
public class Basic implements Parcelable {

    String ssid;
    String subnet;
    String dhcpPool;
    String sharedKey;
    String encryption;
    String security;
    String dhcpLeaseTime;

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
    ArrayList<Basic> basicList = new ArrayList<Basic>();

    public Basic() {

        this.url = "http://" + TomatoMobile.getInstance().getIpaddress();
        this.usrname = TomatoMobile.getInstance().getUsername();
        this.pswrd = TomatoMobile.getInstance().getPassword();
        Connection conn = new Connection();
        String basicHtml = null;
        setHttpId(conn.GetRouterHTTPId());
        HashMap<String, String> tempHashMap = conn.buildParamsMap("_http_id", getHttpId());

        try {
            basicHtml = conn.PostToWebadress(url + "/status-data.jsx", tempHashMap);
            //Set all the values with the returned HTML
            setSsid(basicHtml);
            setSubnet(basicHtml);
            setDhcpPool(basicHtml);
            setSharedKey(basicHtml);
            setEncryption(basicHtml);
            setDhcpLeaseTime(basicHtml);
            setSecurity(basicHtml);
//            setBasicList();
            System.out.println("Returned Html Basic " + basicHtml);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

public String getMyName(){
    String myName="Brian";
    return myName;
}
public String getUrl() {
        return url;
    }

    public String getPswrd() {
        return pswrd;
    }

    public String getUsrname() {
        return usrname;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String html) {

        ssid = new Parser().parserRouterName(html);

    }

    public String getSubnet() {
        return subnet;
    }

    public void setSubnet(String html) {

        subnet = new Parser().parseWanHwAddr(html);

    }

    public String getDhcpPool() {
        return dhcpPool;
    }

    public void setDhcpPool(String html) {

        dhcpPool = new Parser().parseLanIpAddr(html);

    }


    public String getSharedKey() {
        return sharedKey;
    }

    public void setSharedKey(String html) {

        sharedKey = new Parser().parseModelName(html);

    }

    public String getEncryption() {
        return encryption;
    }

    public void setEncryption(String html) {

        encryption = new Parser().parseUptime(html);

    }

    public String getSecurity() {
        return security;
    }

    public void setSecurity(String html) {

        security = new Parser().parseTotalRam(html);

    }

    public String getDhcpLeaseTime() {
        return dhcpLeaseTime;
    }

    public void setDhcpLeaseTime(String html) {

        dhcpLeaseTime = new Parser().parseFreeRam(html);
    }

    public ArrayList<Basic> getBasicList() {
        return basicList;
    }

//            public void setBasicList() {
//
//                Connection conn = new Connection();
//                String basicString = "";
//                basicString = conn.GetHTMLFromURL("http://192.168.1.1/basic-network.asp", "root", "admin");
//                basicList = new Parser().parseBasicList(basicString);
//            }

    /**
     * Used to refresh router information - namely FreeRam, TotalRam, Uptime, and DeviceList
     */
    public void refresh() {
        Connection conn = new Connection();
        String basicHtml = null;
        setHttpId(conn.GetRouterHTTPId());
        HashMap<String, String> tempHashMap = conn.buildParamsMap("_http_id", getHttpId());

        try {
            basicHtml = conn.PostToWebadress(url + "/status-data.jsx", tempHashMap);
            //Set all the values with the returned HTML
            setSsid(basicHtml);
            setSecurity(basicHtml);
            setSharedKey(basicHtml);
            setDhcpPool(basicHtml);
            setEncryption(basicHtml);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static final Parcelable.Creator<Basic> CREATOR
            = new Parcelable.Creator<Basic>() {
        public Basic createFromParcel(Parcel in) {
            return new Basic(in);
        }

        public Basic[] newArray(int size) {
            return new Basic[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeString(ssid);
        out.writeString(subnet);
        out.writeString(sharedKey);
        out.writeString(security);
        out.writeString(encryption);
        out.writeString(dhcpPool);
        out.writeString(dhcpLeaseTime);
        out.writeString(usrname);
        out.writeString(url);
        out.writeString(pswrd);
        out.writeString(httpId);
//                out.writeList(basicList);

    }

    private Basic(Parcel in) {
//                basicList = new ArrayList<Device>();
        ssid = in.readString();
        subnet = in.readString();
        sharedKey = in.readString();
        security = in.readString();
        dhcpLeaseTime = in.readString();
        dhcpPool = in.readString();
        encryption = in.readString();
        usrname = in.readString();
        url = in.readString();
        pswrd = in.readString();
        httpId = in.readString();
//        in.readList(basicList, getClass().getClassLoader());
    }


}