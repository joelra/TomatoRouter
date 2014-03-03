package com.somethingprofane.tomato;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.ButterKnife;

/**
 * Created by Brian Black on 3/1/14.
 */
public class AdministrationConfiguration extends Activity {

    /**
     * This administration page is meant to allow the user to
     * change the router name, username, and password as well
     * as access settings
     */


    String routerName;
    String routerUsername;
    String routerPassword;

    TextView routerNameView;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administration_configuration);

        ButterKnife.inject(this);
        routerNameView = (TextView)findViewById(R.id.router_name_view);

    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_screen, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem adminItem){
        //TODO:Specify the parent activity in android manifest

        int id = adminItem.getItemId();
        if (id ==R.id.action_settings){
            return true;
        }
        return super.onOptionsItemSelected(adminItem);
    }


}
