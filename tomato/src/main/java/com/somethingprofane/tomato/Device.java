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
    String deviceMacAddr = "";
    String deviceName = "";
    String deviceIPAddr = "";
    String deviceConnTime = "";

    /**
     * Constructor
     */
    public Device(){
        Parser parser = new Parser();
        //parser.PostToWebadress(routerIP, username, password,);
    }
}
