package com.somethingprofane.tomato;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.HashMap;

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

    public boolean toDelete;

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

    public void setToDelete(boolean toDelete){ this.toDelete = toDelete;}

    public boolean isToDelete(){return toDelete;}

    public String getEnabledParam(){
        if (this.isEnabled()){
            return "1";
        }
        else {
            return "0";
        }
    }


    private int getDayValue(){
        int day = 0;
        if(isSunday()){
            day=day+1;
        }
        if(isMonday()){
            day=day+2;
        }
        if(isTuesday()){
            day=day+4;
        }
        if(isWednesday()){
            day=day+8;
        }
        if(isThursday()){
            day=day+16;
        }
        if(isFriday()){
            day=day+32;
        }
        if(isSaturday()){
            day=day+64;
        }


        return day;
    }

    public HashMap buildPostHashMap(String groupName, int rruleNum, String httpId, String macAddresses, boolean delete){

        HashMap<String,String> hashMap = new HashMap<String, String>();
        hashMap.put("_nextpage","restrict.asp");
        hashMap.put("_service", "restrict-restart");
        if (delete){
            hashMap.put("rrule" + rruleNum,"");
        }else {
            hashMap.put("rrule" + rruleNum, getEnabledParam()+"|"+String.valueOf(this.getStartTime())+"|"+String.valueOf(this.getFinishTime())+"|"+getDayValue()+"|" + macAddresses + "|||0|" + groupName);
        }
        if (this.isEnabled()){
            hashMap.put("f_enabled","on");
        }
        hashMap.put("f_desc", this.getDescription());
        hashMap.put("f_sched_begin", String.valueOf(this.getStartTime()));
        hashMap.put("f_sched_end", String.valueOf(this.getFinishTime()));
        if (this.isSunday()){
            hashMap.put("f_sched_sun","on");
        }

        if (this.isMonday()){
            hashMap.put("f_sched_mon","on");
        }

        if (this.isTuesday()){
            hashMap.put("f_sched_tue","on");
        }

        if (this.isWednesday()){
            hashMap.put("f_sched_wed","on");
        }

        if (this.isThursday()){
            hashMap.put("f_sched_thu","on");
        }

        if (this.isFriday()){
            hashMap.put("f_sched_fri","on");
        }

        if (this.isSaturday()){
            hashMap.put("f_sched_sat","on");
        }
        hashMap.put("f_type", "on");
        hashMap.put("f_comp_all", "1");
        hashMap.put("f_block_all", "on");
        hashMap.put("f_block_http","");
        hashMap.put("_http_id", httpId);


        return hashMap;
    }




}
