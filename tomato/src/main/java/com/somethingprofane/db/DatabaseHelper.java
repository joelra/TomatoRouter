package com.somethingprofane.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.field.FieldConverter;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.somethingprofane.tomato.Device;
import com.somethingprofane.tomato.DeviceGroup;

/**
 * Created by garrett on 3/24/2014.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "tomatoDB.sqlite";

    private static final int DATABASE_VERSION = 3;

    //DAO objects used to access the table
    private Dao<DeviceGroup, Integer> deviceGroupDao = null;

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource){
        try{

            TableUtils.createTable(connectionSource, DeviceGroup.class);
        }
        catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion){
        try{
            Log.i(DatabaseHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, DeviceGroup.class, true);
            onCreate(db, connectionSource);
            }catch (java.sql.SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }

    public Dao<DeviceGroup, Integer> getDeviceGroupDao() {
        if (null == deviceGroupDao){
            try {
                deviceGroupDao = getDao(DeviceGroup.class);
            }catch (java.sql.SQLException e){
                e.printStackTrace();
            }
        }
        return deviceGroupDao;
    }



}
