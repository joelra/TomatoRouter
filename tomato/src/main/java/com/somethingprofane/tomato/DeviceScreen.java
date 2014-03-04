package com.somethingprofane.tomato;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class DeviceScreen extends ActionBarActivity {

    private ListView lv;


    @InjectView(R.id.device_screen_updateButton) Button updateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_device_screen);

        lv = (ListView) findViewById(R.id.devicescrn_listviewDevices);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                new disableClickedDevice().execute(i);
            }
        });

        ButterKnife.inject(this);

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
    @OnClick(R.id.device_screen_updateButton)
    public void updateClicked(Button updateButton){

        new populateDeviceList().execute(lv);

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

        @Override
        protected void onPostExecute(Router router){
            ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();

            HashMap<String,String> item;
            for(int i=0;i<router.getDeviceList().size();i++){
                item = new HashMap<String,String>();
                item.put( "line1", (router.getDeviceList().get(i).getDeviceName()));
                item.put( "line2", ("IP: "+router.getDeviceList().get(i).getDeviceIPAddr()));
                list.add(item);
            }

            SimpleAdapter sa = new SimpleAdapter(DeviceScreen.this, list,
                    android.R.layout.two_line_list_item ,
                    new String[] { "line1","line2" },
                    new int[] {android.R.id.text1, android.R.id.text2});


            lv.setAdapter(sa);

            if (dialog.isShowing()){
                dialog.dismiss();
            }
        }


    }
    public class disableClickedDevice extends AsyncTask <Integer, Void, Router>{
        int key;
        boolean isFailed = false;
        @Override
        protected Router doInBackground(Integer... integers) {
            key = integers[0];
            Router tempRouter = new Router("http://192.168.1.1","root","admin");
            String html;
            Parser parser = new Parser();
            HashMap<String, String> hashmap = new HashMap<String, String>();
            String htmlId = tempRouter.getHttpId();
            try {
                hashmap = parser.buildParamsMap("_service","restrict-restart","rrule2","1|-1|-1|127|"+tempRouter.getDeviceList().get(key).getDeviceMacAddr()+"|||0|Rule Description","f_enabled","on","f_desc","Test Description","f_sched_begin","1380","f_sched_end","240","f_sched_sun","on","f_sched_mon","on","f_sched_tue","on","f_sched_wed","on","f_sched_thu","on","f_type","on","f_comp_all","1","f_block_all","on","_http_id",htmlId);
                html = parser.PostToWebadress("http://192.168.1.1/tomato.cgi", "root", "admin", hashmap);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                isFailed=true;
            } catch (IOException e) {
                e.printStackTrace();
                isFailed=true;
            }
            return tempRouter;
        }

        protected void onPostExecute(Router router){
            if(isFailed){
                Toast.makeText(DeviceScreen.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
            }else{

            Toast.makeText(DeviceScreen.this, "" + router.getDeviceList().get(key).getDeviceName()+" has been blacklisted.", Toast.LENGTH_SHORT).show();
            
            }
        }
    }
}
