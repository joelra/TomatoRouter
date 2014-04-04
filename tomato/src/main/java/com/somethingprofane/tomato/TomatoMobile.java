package com.somethingprofane.tomato;

import android.app.Application;

/**
 * Created by somethingPr0fane on 4/4/2014.
 */
public class TomatoMobile extends Application {

    private String username;
    private String password;
    private String ipaddress;

    private static TomatoMobile singleton;

    public static TomatoMobile getInstance(){
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
    }

    /**
     * Returns the username of the application
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username for the duration of the application
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the password of the user of the application
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password for the user of the application
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the IP address of the router
     * @return
     */
    public String getIpaddress() {
        return ipaddress;
    }

    /**
     * Sets the IP address of the router for the duration of the application
     * @param ipaddress
     */
    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

}
