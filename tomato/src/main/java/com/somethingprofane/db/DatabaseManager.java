package com.somethingprofane.db;

/**
 * Created by garrett on 3/25/2014.
 */
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.somethingprofane.tomato.Device;
import com.somethingprofane.tomato.DeviceGroup;
import com.somethingprofane.tomato.Rule;

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
     * Created by JRA & some by Garrett
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

    public List<Device> getNonGroupDevices(){
        List<Device> deviceList = null;
        try{
            QueryBuilder<Device, String> queryBuilder = getHelper().getDeviceDao().queryBuilder();
            queryBuilder.where().isNull("deviceGroup_id");
            PreparedQuery<Device> preparedQuery = queryBuilder.prepare();
            deviceList = getHelper().getDeviceDao().query(preparedQuery);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return deviceList;
    }

    public List<Device> getDevicesByGroup(DeviceGroup deviceGroup){
        List<Device> deviceList = null;
        try{
            deviceList = getHelper().getDeviceDao().queryForEq("deviceGroup_id", deviceGroup.getId());
        }catch (SQLException e){
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
            System.out.println("Device "+device.getDeviceName()+" has officially been updated to group "+device.getDeviceGroup());
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

    public void addDeviceList(ArrayList<Device> deviceList){
        try{
            for(Device device : deviceList){
                addDevice(device);
            }
        } catch (Exception e){
            Log.d("SQL Error", "There was an error adding a device list to the database");
        }
    }

    /**
     * These following methods are for the Rule table.
     * Created by Garrett
     */

    public List<Rule> getAllRules(){
        List<Rule> ruleList = null;
        try {
            ruleList = getHelper().getRuleDao().queryForAll();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return ruleList;
    }

    public void addRule(Rule rule){
        try{
            getHelper().getRuleDao().create(rule);
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void updateRule(Rule rule){
        try{
            getHelper().getRuleDao().update(rule);
        } catch (SQLException e){
            Log.d("SQL Error", "There was an error updating a rule in the database");
            e.printStackTrace();
        }
    }

    public void deleteRule(Rule rule){
        try{
            getHelper().getRuleDao().delete(rule);
        } catch (SQLException e){
            Log.d("SQL Error", "There was an error deleting a rule from the database");
            e.printStackTrace();
        }
    }
}

