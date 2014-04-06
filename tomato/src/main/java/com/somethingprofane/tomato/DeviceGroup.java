package com.somethingprofane.tomato;

import java.util.ArrayList;
import java.util.List;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by garrett on 3/24/2014.
 */
@DatabaseTable(tableName="deviceGroups")
public class DeviceGroup {

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    String groupName;
    @ForeignCollectionField
    private ForeignCollection<Device> devices;

    public Rule getRule() {
        return rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    @DatabaseField(foreign=true,foreignAutoRefresh=true)
    Rule rule;

    public DeviceGroup(){}

    public void setId (int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public void setGroupName(String groupName){this.groupName = groupName;}
    public String getGroupName(){return groupName;}

    public void setDevices(ForeignCollection<Device> devices){
        this.devices = devices;
    }

    public List<Device> getDevices(){
        ArrayList<Device> deviceList = new ArrayList<Device>();
        for (Device device: devices) {
            deviceList.add(device);
        }
        return deviceList;
    }




}