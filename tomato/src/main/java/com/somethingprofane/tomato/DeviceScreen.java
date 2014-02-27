package com.somethingprofane.tomato;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DeviceScreen extends ActionBarActivity {

    private ListView lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_screen);

        lv = (ListView) findViewById(R.id.devicescrn_listviewDevices);



        new populateDeviceList().execute(lv);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.device_screen, menu);
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

    public class populateDeviceList extends AsyncTask <ListView, Void, Router>{

        ListView listView;

        private ProgressDialog dialog = new ProgressDialog(DeviceScreen.this);

        @Override
        protected void onPreExecute(){
            this.dialog.setMessage("Getting devices...");
            this.dialog.show();
        }


        @Override
        protected Router doInBackground(ListView... listViews) {

            listView = listViews[0];
            Router tempRouter = new Router("http://192.168.1.1", "root", "admin");

            return tempRouter;
        }

        protected void onPostExecute(Router router){

            lv.setAdapter(null);
            ArrayAdapter<Device> arrayAdapter = new ArrayAdapter<Device>(
                    DeviceScreen.this,
                    android.R.layout.simple_list_item_1,
                    router.getDeviceListNames()
                    );
            lv.setAdapter(arrayAdapter);

            if (dialog.isShowing()){
                dialog.dismiss();
            }
        }


    }

}
