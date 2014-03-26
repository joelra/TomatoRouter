package com.somethingprofane.tomato;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.somethingprofane.db.DatabaseManager;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class NewGroupActivity extends ActionBarActivity {

    @InjectView(R.id.activity_new_group_saveGroupButton) Button saveButton;
    @InjectView(R.id.activity_new_group_deleteGroupButton) Button deleteButton;

    private EditText groupName;
    private TextView groupNameTxtView;
    private DeviceGroup deviceGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group);

        groupName = (EditText) findViewById(R.id.activity_new_group_groupNameTxt);
        groupNameTxtView = (TextView) findViewById(R.id.activity_new_group_groupNameLabel);

        ButterKnife.inject(this);

        deleteButton.setVisibility(View.INVISIBLE);

        setupDeviceGroup();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_group, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDeviceGroup(){
        Bundle bundle = getIntent().getExtras();
        if (null!=bundle && bundle.containsKey("deviceGroupID")){
            int deviceGroupId = bundle.getInt("deviceGroupID");
            deviceGroup = DatabaseManager.getInstance().getDeviceGroupWithId(deviceGroupId);
            groupName.setText(deviceGroup.getGroupName());
            deleteButton.setVisibility(View.VISIBLE);
            groupNameTxtView.setText("Group Name:");
        }
    }

    @OnClick(R.id.activity_new_group_saveGroupButton)
    public void onSaveButtonClick(Button saveButton){
        String name = groupName.getText().toString();
        if (null!=name && name.length()>0) {
            if (null!=deviceGroup){
                updateDeviceGroup(name);
            } else {
                createDeviceGroup(name);
            }
            finish();
        } else {

        }
    }

    @OnClick(R.id.activity_new_group_deleteGroupButton)
    public void onDeleteButtonClick(Button deleteButton){
         if(null!=deviceGroup){
             DatabaseManager.getInstance().deleteDeviceGroup(deviceGroup);
             finish();
         }
    }

    private void updateDeviceGroup(String name){
        if (null!=deviceGroup){
            deviceGroup.setGroupName(name);
            DatabaseManager.getInstance().updateDeviceGroup(deviceGroup);
        }
    }

    private void createDeviceGroup(String name){
        DeviceGroup dg = new DeviceGroup();
        dg.setGroupName(name);
        DatabaseManager.getInstance().addDeviceGroup(dg);
    }

}
