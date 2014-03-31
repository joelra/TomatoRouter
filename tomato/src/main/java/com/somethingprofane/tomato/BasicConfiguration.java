package com.somethingprofane.tomato;

/**
 * Created by Brian Black on 3/1/14.
 */

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.TextView;
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


public class BasicConfiguration extends Activity {

    /**
     * This class will include editable username/password, change SSID
     * set static DNS, change DHCP server lease time and ip address range,
     * assigning a static ip to a MAC address
     */

    Router router;
    Basic basic;
    Device device;

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

    ArrayList<String> wireLessList = new ArrayList<String>();

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

        Intent b = getIntent();
        router = (Router) b.getParcelableExtra("basic_router");


        new routerInfo().execute(router);
        System.out.println(router.getRouterName() + "ROUTER NAME HERE!");

        System.out.println("SSID " + router.getSsid());
        System.out.println("Subnet " + router.getSubnet());
        System.out.println("Start IP " + router.getDhcpPool1());
        System.out.println("End IP " + router.getDhcpPool2());
        System.out.println("Lease Time " + router.getDhcpLeaseTime());
        System.out.println("Shared Key " + router.getSharedKey());
        System.out.println("Encryption " + router.getEncryption());
        System.out.println("Security " + router.getSecurity());

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


//    public String getWireLessInfo() {
//    //Get the html from the basic page
//    String wireLessHtml = null;
//    Connection conn = new Connection();
//    String basicHtml = "";
//    basicHtml = conn.GetHTMLFromURL("http://192.168.1.1/basic-network.asp", "root", "admin");
//    wireLessHtml = new Parser().parseWireless(basicHtml);
//    System.out.println("Basic HTML " + basicHtml);
//        return basicHtml;
//}

    private class routerInfo extends AsyncTask<Router, Void, Router> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Router doInBackground(Router... routers) {
//            getWireLessInfo();

            routers[0].refresh();
            return routers[0];
        }

        @Override
        protected void onPostExecute(Router router) {
            BasicConfiguration.this.router = router;
            routerNameView.setText(router.getRouterName());
            wirelessMacView.setText(router.getWanHwAddr());
            routerIPView.setText(router.getLanIpAddr());
            routerUsrView.setText(router.getUsrname());
            routerPwdView.setText(router.getPswrd());
            wirelessSubnetView.setText(router.getSubnet());
            dhcpStartView.setText(router.getDhcpPool1());
            dhcpEndView.setText(router.getDhcpPool2());
            securityView.setText(router.getSecurity());
            encryptionView.setText(router.getEncryption());

            }
        }

    }
