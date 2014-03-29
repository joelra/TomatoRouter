package com.somethingprofane.db;

/**
 * Created by garrett on 3/25/2014.
 */
import java.sql.SQLException;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.somethingprofane.tomato.Device;
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

    /**
     * These following methods are for the Device table.
     * Created by JRA
     */

    /**
     * Return all devices from the Device table within the database.
     * @return List of device objects;
     */
    public List<Device> getAllDevices(){
        List<Device> deviceList = null;
        try {
            deviceList = getHelper().getDeviceDao().queryForAll();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return deviceList;
    }

    /**
     * The device MAC address is actually the ID for the device.
     * See the Device.java class for the list of column names in the device table.
     * @return A device object
     */
    public Device getDeviceById(String macAddress){
        Device device = null;
        try{
            device = getHelper().getDeviceDao().queryForId(macAddress);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return device;
    }

    /**
     * Add a new device object to the device table
     * @param device The device to be added to the database
     */
    public void addDevice(Device device){
        try{
            getHelper().getDeviceDao().create(device);
        } catch(SQLException e){
            Log.d("SQL Error", "There was an error adding a device to the Database");
            e.printStackTrace();
        }
    }

    /**
     * Update a device within the database
     * @param device The device to update
     */
    public void updateDevice(Device device){
        try{
            getHelper().getDeviceDao().update(device);
        } catch (SQLException e){
            Log.d("SQL Error", "There was an error updating a device in the database");
            e.printStackTrace();
        }
    }

    /**
     * Delete a single device from the database
     * @param device The device to be deleted
     */
    public void deleteDevice(Device device){
        try{
            getHelper().getDeviceDao().delete(device);
        } catch (SQLException e){
            Log.d("SQL Error", "There was an error deleting a device from the database");
            e.printStackTrace();
        }
    }
}

