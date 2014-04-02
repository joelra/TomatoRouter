package com.somethingprofane.tomato;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import java.io.IOException;
import java.util.HashMap;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class DeviceScreen extends ActionBarActivity {

    private ListView lv;
    final Context context = this;

    @InjectView(R.id.device_screen_updateButton) Button updateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_device_screen);

        ButterKnife.inject(this);

        lv = (ListView) findViewById(R.id.devicescrn_listviewDevices);

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

        //new populateDeviceList().execute(lv);
        new createDeviceListView().execute(lv);

    }

    /**
     * Called from the DeviceListBaseAdapter. It will allow for an asynctask to be called to create
     * post information to the router.
     * @param deviceName
     */
    public void updateNetworkStatusForDevice(String deviceName) {
        Toast.makeText(context, deviceName + " from Activity", Toast.LENGTH_SHORT).show();
        new turnOffWiFiForDevice().execute(deviceName);
    }

    private class turnOffWiFiForDevice extends AsyncTask<String, Void, String>{

        @Override
        protected void onPreExecute(){

        }

        @Override
        protected String doInBackground(String... strings) {
//            HashMap<String, String> hashmap;
//            Connection conn = new Connection();
//            String htmlId = router.getHttpId();
//            if(isChecked()){
//                // Set the wifi of that particular device to off.
//                // Update the DB with this information change
//                hashmap = conn.buildParamsMap("_service","restrict-restart","rrule2","1|-1|-1|127|"+device.getDeviceMacAddr()+"|||0|Rule Description","f_enabled","on","f_desc","Test Description","f_sched_begin","1380","f_sched_end","240","f_sched_sun","on","f_sched_mon","on","f_sched_tue","on","f_sched_wed","on","f_sched_thu","on","f_type","on","f_comp_all","1","f_block_all","on","_http_id",htmlId);
//                try {
//                    conn.PostToWebadress("http://192.168.1.1/tomato.cgi", "root", "admin", hashmap);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                // Update the database that the device has been turned off.
//                device.setDeviceWifiConnected(false);
//                DatabaseManager.getInstance().updateDevice(device);
//            }else {
//                // Set the wifi to on of that particular device
//                // Update the DB with that information
//            }

            return null;
        }

        @Override
        protected void onPostExecute(String response){

        }
    }

    private class createDeviceListView extends AsyncTask<ListView, Integer, Router> {
        ListView deviceListView;
        private ProgressDialog progressDialog = new ProgressDialog(DeviceScreen.this);

        @Override
        protected void onPreExecute() {
            this.progressDialog.setMessage("Getting Devices...");
            this.progressDialog.show();
        }

        @Override
        protected Router doInBackground(ListView... listViews) {
            deviceListView = listViews[0];
            Router router = new Router("http://192.168.1.1", "root", "admin");
            return router;
        }

        @Override
        protected void onPostExecute(Router router) {
            if(progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            DeviceListBaseAdapter adapter = new DeviceListBaseAdapter(DeviceScreen.this, router.deviceList);
            deviceListView.setAdapter(adapter);

        }
    }


//    public class populateDeviceList extends AsyncTask <ListView, Void, List<Device>>{
//
//        ListView listView;
//
//        private ProgressDialog dialog = new ProgressDialog(DeviceScreen.this);
//
//        @Override
//        protected void onPreExecute(){
//            this.dialog.setMessage("Getting devices...");
//            this.dialog.show();
//        }
//
//
//        @Override
//        protected Router doInBackground(ListView... listViews) {
//
//            listView = listViews[0];
//            Router tempRouter = new Router("http://192.168.1.1", "root", "admin");
//
//            return tempRouter;
//        }
//
//        @Override
//        protected void onPostExecute(Router router){
//            ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
//
//            HashMap<String,String> item;
//            for(int i=0;i<router.getDeviceList().size();i++){
//                item = new HashMap<String,String>();
//                item.put( "line1", (router.getDeviceList().get(i).getDeviceName()));
//                item.put( "line2", ("IP: "+router.getDeviceList().get(i).getDeviceIPAddr()));
//                list.add(item);
//            }
//
//            SimpleAdapter sa = new SimpleAdapter(DeviceScreen.this, list,
//                    android.R.layout.two_line_list_item ,
//                    new String[] { "line1","line2" },
//                    new int[] {android.R.id.text1, android.R.id.text2});
//
//
//            lv.setAdapter(sa);
//
//            if (dialog.isShowing()){
//                dialog.dismiss();
//            }
//        }


    //}
//    public class disableClickedDevice extends AsyncTask <Integer, Void, Router>{
//        int key;
//        boolean isFailed = false;
//        @Override
//        protected Router doInBackground(Integer... integers) {
//            key = integers[0];
//            Router tempRouter = new Router("http://192.168.1.1","root","admin");
//            String html;
//            Connection conn = new Connection();
//            HashMap<String, String> hashmap = new HashMap<String, String>();
//            String htmlId = tempRouter.getHttpId();
//            try {
//                hashmap = conn.buildParamsMap("_service","restrict-restart","rrule2","1|-1|-1|127|"+tempRouter.getDeviceList().get(key).getDeviceMacAddr()+"|||0|Rule Description","f_enabled","on","f_desc","Test Description","f_sched_begin","1380","f_sched_end","240","f_sched_sun","on","f_sched_mon","on","f_sched_tue","on","f_sched_wed","on","f_sched_thu","on","f_type","on","f_comp_all","1","f_block_all","on","_http_id",htmlId);
//                html = conn.PostToWebadress("http://192.168.1.1/tomato.cgi", "root", "admin", hashmap);
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//                isFailed=true;
//            } catch (IOException e) {
//                e.printStackTrace();
//                isFailed=true;
//            }
//            return tempRouter;
//        }
//
//        protected void onPostExecute(Router router){
//            if(isFailed){
//                Toast.makeText(DeviceScreen.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
//            }else{
//
//            Toast.makeText(DeviceScreen.this, "" + router.getDeviceList().get(key).getDeviceName()+" has been blacklisted.", Toast.LENGTH_SHORT).show();
//
//            }
//        }
//    }
//
//    public class populateListView extends AsyncTask<ListView, Void, List<Device>> {
//        @Override
//        protected List<Device> doInBackground(ListView... listViews) {
//            return null;
//        }
//    }
}
