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
     * assigning a static ip to a MAC address, and possibly port forwarding
     */
    Router router;

    String routerName;
    String routerUsername;
    String routerPassword;

    TextView routerNameView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_configuration);

        routerNameView = (TextView)findViewById(R.id.router_name_view);
        Intent i = getIntent();
        router = (Router) i.getParcelableExtra("passed_router");
//        new refreshRouterName().execute(router);

       // System.out.println(router.getRouterName() + "Router Name");
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

}
