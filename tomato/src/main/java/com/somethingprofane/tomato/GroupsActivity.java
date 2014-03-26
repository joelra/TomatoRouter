package com.somethingprofane.tomato;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.somethingprofane.db.DatabaseManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class GroupsActivity extends ActionBarActivity {
    @InjectView(R.id.activity_groups_addGroupButton)Button addGroupButton;

    ListView groupsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseManager.init(this);
        setContentView(R.layout.activity_groups);


        groupsListView = (ListView) findViewById(R.id.activity_groups_groupsListView);

        ButterKnife.inject(this);


    }

    @Override
    protected void onStart(){
        super.onStart();
        setupGroupListView(groupsListView);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.groups, menu);
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

    @OnClick(R.id.activity_groups_addGroupButton)
    public void addGroupButtonClicked (Button addGroupButton){
        Intent intent = new Intent (GroupsActivity.this, NewGroupActivity.class);
        GroupsActivity.this.startActivity(intent);
    }

    private void setupGroupListView(ListView lv) {
        final List<DeviceGroup> groupList = DatabaseManager.getInstance().getAllDeviceGroups();
        if (null!=groupList) {
            List<String> groupNames = new ArrayList<String>();
            for (DeviceGroup group : groupList) {
                groupNames.add(group.getGroupName());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, groupNames);

            lv.setAdapter(adapter);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    DeviceGroup deviceGroup = groupList.get(position);
                    Intent intent = new Intent(GroupsActivity.this, NewGroupActivity.class);
                    intent.putExtra("deviceGroupID", deviceGroup.getId());
                    startActivity(intent);
                }
            });
        }
    }

}
