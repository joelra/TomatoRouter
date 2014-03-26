package com.somethingprofane.db;

/**
 * Created by garrett on 3/25/2014.
 */
import java.sql.SQLException;
import java.util.List;

import android.content.Context;

import com.somethingprofane.tomato.DeviceGroup;

public class DatabaseManager {

    static private DatabaseManager instance;

    static public void init(Context ctx) {
        if (null==instance) {
            instance = new DatabaseManager(ctx);
        }
    }

    static public DatabaseManager getInstance() {
        return instance;
    }

    private DatabaseHelper helper;
    private DatabaseManager(Context ctx) {
        helper = new DatabaseHelper(ctx);
    }

    private DatabaseHelper getHelper() {
        return helper;
    }

    public List<DeviceGroup> getAllDeviceGroups() {
        List<DeviceGroup> deviceGroups = null;
        try {
            deviceGroups = getHelper().getDeviceGroupDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deviceGroups;
    }

    public DeviceGroup getDeviceGroupWithId(int deviceGroupId){
        DeviceGroup deviceGroup = null;
        try {
            deviceGroup = getHelper().getDeviceGroupDao().queryForId(deviceGroupId);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return  deviceGroup;
    }

    public void addDeviceGroup(DeviceGroup deviceGroup){
        try{
            getHelper().getDeviceGroupDao().create(deviceGroup);
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void updateDeviceGroup(DeviceGroup deviceGroup){
        try{
            getHelper().getDeviceGroupDao().update(deviceGroup);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteDeviceGroup(DeviceGroup deviceGroup){
        try{
            getHelper().getDeviceGroupDao().delete(deviceGroup);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

