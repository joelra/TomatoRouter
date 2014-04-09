package com.somethingprofane.tomato;

/**
 * Created by Brian Black on 3/1/14.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class BasicConfiguration extends Activity {

    /**
     * This class will include editable username/password, change SSID
     * set static DNS, change DHCP server lease time and ip address range,
     * assigning a static ip to a MAC address
     */

    Router router;
    Basic basic;
    Device device;
    String[] securityOptions;

    TextView routerNameView;
    TextView wirelessMacView;
    TextView routerIPView;
    TextView routerUsrView;
    TextView routerPwdView;
    TextView wirelessSubnetView;
    TextView dhcpStartView;
    TextView dhcpEndView;
    TextView securityView;
    TextView encryptionView;
    TextView ssidView;

//    LinkedHashMap<String, String> changeLink = new LinkedHashMap<String, String>();
    ArrayList<String> changeList = new ArrayList<String>();
    ArrayList<String> changeKey = new ArrayList<String>();
    ArrayList<String> wireLessList = new ArrayList<String>();
    ProgressDialog progDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_configuration);

          routerNameView = (TextView)findViewById(R.id.router_name_view);
          wirelessMacView = (TextView)findViewById(R.id.router_wireMac_view);
          routerIPView = (TextView)findViewById(R.id.router_ip_view);
          routerUsrView = (TextView)findViewById(R.id.router_username_view);
          routerPwdView = (TextView)findViewById(R.id.router_password_view);
          wirelessSubnetView = (TextView)findViewById(R.id.router_subnet_view);
          dhcpStartView = (TextView)findViewById(R.id.router_dhcpStart_view);
          dhcpEndView = (TextView)findViewById(R.id.router_dhcpEnd_view);
          securityView = (TextView)findViewById(R.id.router__security_view);
          encryptionView = (TextView)findViewById(R.id.router__encrypt_view);
          ssidView = (TextView)findViewById(R.id.router__ssid_view);

        Intent b = getIntent();
        router = (Router) b.getParcelableExtra("basic_router");
        progDialog = new ProgressDialog(this);

        new routerInfo().execute(router);


//Encryption spinner - filled in on datalist.xml
//        Spinner spinner = (Spinner) findViewById(R.id.spinner);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
//                this, R.array.securityOptionsList, android.R.layout.simple_spinner_item);
//
//        String myString = router.getSecurity(); //the value you want the position for
//        int spinnerPosition = adapter.getPosition(myString);
//        System.out.println(myString + "MY STRING!");
//        System.out.println(spinnerPosition + "SPINNER POSITION");
////set the default according to value
//        spinner.setSelection(spinnerPosition);
//        String name = router.getSecurity();
//        System.out.println(router.getSecurity() + " getSecurity!");
//        int index = adapter.getPosition(name);
//        System.out.println(index + " INDEX!");
//        if (index != -1) spinner.setSelection(index);
//            spinner.setSelection(3);
//
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
//
//            public void onItemSelected(AdapterView<?> arg0, View arg1,
//                                       int arg2, long arg3){
//
//                int index = arg0.getSelectedItemPosition();
//// storing string resources into Array
//                securityOptions = getResources().getStringArray(R.array.securityOptionsList);
//                Toast.makeText(getBaseContext(), "You have selected : " + securityOptions[index],
//                        Toast.LENGTH_SHORT).show();
//            }
//            public void onNothingSelected(AdapterView<?> arg0) {
//// do nothing
//            }
//        });

        System.out.println("Username " + TomatoMobile.getInstance().getUsername());
        System.out.println("Password " + TomatoMobile.getInstance().getPassword());
        ButterKnife.inject(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem basicItem){
        //TODO:Specify the parent activity in android manifest

        int id = basicItem.getItemId();
        if (id ==R.id.action_settings){
            return true;
        }
        return super.onOptionsItemSelected(basicItem);
    }

    @OnClick(R.id.admin_save_button)
    public void saveClicked(Button saveButton){

        if (!routerNameView.getText().toString().equals(router.getRouterName())){
            changeList.add(routerNameView.getText().toString());
            changeKey.add("router_name");
        }
        if (!wirelessSubnetView.getText().toString().equals(router.getSubnet())){
            changeList.add(wirelessSubnetView.getText().toString());
            changeKey.add("lan_netmask");
        }
        if (!dhcpStartView.getText().toString().equals(router.getDhcpPool1())){
            changeList.add(dhcpStartView.getText().toString());
            changeKey.add("dhcpd_startip");
        }
        if (!dhcpEndView.getText().toString().equals(router.getDhcpPool2())){
            changeList.add(dhcpEndView.getText().toString());
            changeKey.add("dhcpd_endip");
        }
        if (!ssidView.getText().toString().equals(router.getSsid())){
            changeList.add(ssidView.getText().toString());
            changeKey.add("wl_ssid");

        }
        if (!routerIPView.getText().toString().equals(router.getLanIpAddr())){
            changeList.add(routerIPView.getText().toString());
            changeKey.add("lan_ipaddr");
            changeKey.add("_moveip");
            changeList.add("1");
//
        }
//        if (!routerUsrView.getText().toString().equals(TomatoMobile.getInstance().getUsername())){
//            TomatoMobile.getInstance().setUsername(routerUsrView.getText().toString());
//        }
//        if (!routerPwdView.getText().toString().equals(TomatoMobile.getInstance().getPassword())){
//            TomatoMobile.getInstance().setPassword(routerPwdView.getText().toString());
//        }
        else{
            progDialog.setMessage("Refreshing Information");
            progDialog.show();
            new routerInfo().execute(router);
        }
            progDialog.setMessage("Saving Changes");
            progDialog.show();
        new updateInfo().execute();
    }

    @OnClick(R.id.admin_cancel_button)
    public void cancelClicked(){
            Intent intent = new Intent(BasicConfiguration.this, MainScreen.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
    }

    private class routerInfo extends AsyncTask<Router, Void, Router> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Router doInBackground(Router... routers) {
            routers[0].refresh();
            return routers[0];
        }

        @Override
        protected void onPostExecute(Router router) {
            BasicConfiguration.this.router = router;
            routerNameView.setText(router.getRouterName());
            wirelessMacView.setText(router.getWanHwAddr());
            routerIPView.setText(router.getLanIpAddr());
            routerUsrView.setText(TomatoMobile.getInstance().getUsername());
            routerPwdView.setText(TomatoMobile.getInstance().getPassword());
            wirelessSubnetView.setText(router.getSubnet());
            dhcpStartView.setText(router.getDhcpPool1());
            dhcpEndView.setText(router.getDhcpPool2());
            securityView.setText(router.getSecurity());
            encryptionView.setText(router.getEncryption());
            ssidView.setText(router.getSsid());

            if (progDialog.isShowing()) {
                progDialog.dismiss();
            }
            }
        }
//Update changed settings
    private class updateInfo extends AsyncTask<String,Void,String> {
        boolean isFailed = false;

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... strings) {
            String returnedHTML = null;
            Connection conn = new Connection();

            for ( int i = 0; i<changeList.size(); i++) {
                System.out.println(changeList.get(i) + " ChangeList" + i);
                System.out.println(changeList.size() + " ChangeSize");

                String key = null;
                String value = changeList.get(i);
                key = changeKey.get(i);
                System.out.println(key + " Key" + i);


                HashMap<String, String> tempHashMap = conn.buildParamsMap("_http_id", BasicConfiguration.this.router.getHttpId(), key, value, "_service", "*");
                try {
                    returnedHTML = conn.PostToWebadress("http://" + TomatoMobile.getInstance().getIpaddress() + "/tomato.cgi", tempHashMap);
                } catch (IOException e) {
                    isFailed = true;
                    e.printStackTrace();
                }
            }
           return returnedHTML;
        }

        protected void onPostExecute(String string) {
            if (progDialog.isShowing()) {
                progDialog.dismiss();
            }
            if (isFailed) {
                Toast.makeText(BasicConfiguration.this, "Unable to Save Changes", Toast.LENGTH_SHORT).show();
            } else {

                Toast.makeText(BasicConfiguration.this, "Router is Updated", Toast.LENGTH_SHORT).show();
                TomatoMobile.getInstance().setIpaddress(routerIPView.getText().toString());
//                System.out.println(TomatoMobile.getInstance().getIpaddress() + " NEW IP ADDRESS!");
            }

            new BasicConfiguration.routerInfo().execute(BasicConfiguration.this.router);
           }
        }

    }
