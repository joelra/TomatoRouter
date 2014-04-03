package com.somethingprofane.tomato;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by garrett on 4/2/2014.
 */

@DatabaseTable
public class Rule {

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    public boolean enabled;
    @DatabaseField
    public String description;
    @DatabaseField
    public boolean sunday;
    @DatabaseField
    public boolean monday;
    @DatabaseField
    public boolean tuesday;
    @DatabaseField
    public boolean wednesday;
    @DatabaseField
    public boolean thursday;
    @DatabaseField
    public boolean friday;
    @DatabaseField
    public boolean saturday;
    @DatabaseField
    public int startTime;
    @DatabaseField
    public int finishTime;

    public ForeignCollection<DeviceGroup> getDevicesGroup() {
        return devicesGroup;
    }

    public void setDevicesGroup(ForeignCollection<DeviceGroup> devicesGroup) {
        this.devicesGroup = devicesGroup;
    }

    @ForeignCollectionField
    private ForeignCollection<DeviceGroup> devicesGroup;

    public int getTomatoRuleId() {
        return tomatoRuleId;
    }

    public void setTomatoRuleId(int tomatoRuleId) {
        this.tomatoRuleId = tomatoRuleId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isSunday() {
        return sunday;
    }

    public void setSunday(boolean sunday) {
        this.sunday = sunday;
    }

    public boolean isMonday() {
        return monday;
    }

    public void setMonday(boolean monday) {
        this.monday = monday;
    }

    public boolean isTuesday() {
        return tuesday;
    }

    public void setTuesday(boolean tuesday) {
        this.tuesday = tuesday;
    }

    public boolean isWednesday() {
        return wednesday;
    }

    public void setWednesday(boolean wednesday) {
        this.wednesday = wednesday;
    }

    public boolean isThursday() {
        return thursday;
    }

    public void setThursday(boolean thursday) {
        this.thursday = thursday;
    }

    public boolean isFriday() {
        return friday;
    }

    public void setFriday(boolean friday) {
        this.friday = friday;
    }

    public boolean isSaturday() {
        return saturday;
    }

    public void setSaturday(boolean saturday) {
        this.saturday = saturday;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
    }

    @DatabaseField
    public int tomatoRuleId;


}
