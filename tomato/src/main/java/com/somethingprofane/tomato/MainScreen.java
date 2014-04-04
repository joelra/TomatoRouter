package com.somethingprofane.tomato;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import java.io.IOException;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainScreen extends ActionBarActivity {

    @InjectView(R.id.mainscr_btnRouter)ImageButton routerButton;
    @InjectView(R.id.mainscr_btnDevices)ImageButton devicesButton;
    @InjectView(R.id.mainscr_btnBasic)ImageButton basicButton;
    @InjectView(R.id.mainscr_btnAdvanced)ImageButton advancedButton;
    @InjectView(R.id.mainscr_btnGroups)ImageButton groupsButton;
    @InjectView(R.id.mainscr_btnProfiles)ImageButton profilesButton;
    @InjectView(R.id.mainscr_btnLogout)ImageButton logoutButton;

    private static Router router;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        ButterKnife.inject(this);
        SharedPreferences prefs = getSharedPreferences("user_prefs", MainScreen.MODE_PRIVATE);

        // Update the router object only if the bundle contains a key named 'Router'
        Bundle b = getIntent().getExtras();
        if (b != null && b.containsKey("Router")) {
            router = b.getParcelable("Router");
        }
        //router = (Router) i.getParcelableExtra("passed_router"); <- This is less effective. Router will be null;

        // Resetting the font of the Title in the main screen. May not be working...
        Typeface sensationFont = Typeface.createFromAsset(getAssets(), "Lato-BlaIta.ttf");
        TextView title = (TextView) findViewById(R.id.mainscr_Title);
        title.setTypeface(sensationFont);

        // On touch listener for Router ImageButton
        routerButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    routerButton.setBackgroundResource(R.drawable.router_icon_flat_pressed);
                } else if (motionEvent.getAction() == motionEvent.ACTION_UP){
                    routerButton.setBackgroundResource(R.drawable.router_icon_flat);
                }
                return false;
            }
        });

        devicesButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    devicesButton.setBackgroundResource(R.drawable.devices_icon_flat_pressed);
                } else if (motionEvent.getAction() == motionEvent.ACTION_UP){
                    devicesButton.setBackgroundResource(R.drawable.devices_icon_flat);
                }
                return false;
            }
        });

        basicButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    basicButton.setBackgroundResource(R.drawable.basic_icon_flat_pressed);
                } else if (motionEvent.getAction() == motionEvent.ACTION_UP){
                    basicButton.setBackgroundResource(R.drawable.basic_icon_flat);
                }
                return false;
            }
        });

        advancedButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    advancedButton.setBackgroundResource(R.drawable.advanced_icon_flat_pressed);
                } else if (motionEvent.getAction() == motionEvent.ACTION_UP){
                    advancedButton.setBackgroundResource(R.drawable.advanced_icon_flat);
                }
                return false;
            }
        });

        groupsButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    groupsButton.setBackgroundResource(R.drawable.groups_icon_flat_pressed);
                } else if (motionEvent.getAction() == motionEvent.ACTION_UP){
                    groupsButton.setBackgroundResource(R.drawable.groups_icon_flat);
                }
                return false;
            }
        });

        profilesButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    profilesButton.setBackgroundResource(R.drawable.profiles_icon_flat_pressed);
                } else if (motionEvent.getAction() == motionEvent.ACTION_UP){
                    profilesButton.setBackgroundResource(R.drawable.profiles_icon_flat);
                }
                return false;
            }
        });

        logoutButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    logoutButton.setBackgroundResource(R.drawable.logout_icon_flat_pressed);
                } else if (motionEvent.getAction() == motionEvent.ACTION_UP){
                    logoutButton.setBackgroundResource(R.drawable.logout_icon_flat);
                }
                return false;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_screen, menu);
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

    @OnClick(R.id.mainscr_btnRouter)
    public void LoginClicked(ImageButton routerButton){
        new moveToRouter().execute(router);
    }

    private class moveToRouter extends AsyncTask<Router, Void, String> {

        @Override
        protected String doInBackground(Router... routers) {
            Intent intent = new Intent(MainScreen.this, RouterOverviewActivity.class);
            intent.putExtra("passed_router", routers[0]);
            MainScreen.this.startActivity(intent);
            // Finish the activity;
            return null;
        }
    }

    @OnClick(R.id.mainscr_btnDevices)
    public void devicesClicked (ImageButton devicesButton){
        new moveToDevices().execute();
    }

    private class moveToDevices extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            Intent intent = new Intent(MainScreen.this, DeviceScreen.class);
            Bundle b = new Bundle();
            b.putParcelable("Router", router);
            intent.putExtras(b);
            MainScreen.this.startActivity(intent);
            return null;
        }
    }

    @OnClick(R.id.mainscr_btnAdvanced)
    public void advancedClicked (ImageButton advancedButton){
        Intent intent = new Intent(MainScreen.this, AdvancedConfigActivity.class);
        MainScreen.this.startActivity(intent);
    }

    @OnClick(R.id.mainscr_btnBasic)
    public void basicClicked (ImageButton basicButton){ new moveToBasic().execute(router);
    }
        private class moveToBasic extends AsyncTask<Router, Void, String> {

            @Override
            protected String doInBackground(Router... routers) {
                Intent intent = new Intent(MainScreen.this, BasicConfiguration.class);
                intent.putExtra("basic_router", routers[0]);
                MainScreen.this.startActivity(intent);
                return null;
            }
    }


    @OnClick(R.id.mainscr_btnGroups)
    public void groupsClicked (ImageButton groupsButton){
        Intent intent = new Intent (MainScreen.this, GroupsActivity.class);
        MainScreen.this.startActivity(intent);
    }

    @OnClick(R.id.mainscr_btnLogout)
    public void logoutClicked (ImageButton logoutButton){
        new logoutApplication().execute();
    }

    private class logoutApplication extends AsyncTask<Void, Void, Void> {
        Connection conn = new Connection();
        private ProgressDialog progressDialog = new ProgressDialog(MainScreen.this);

        @Override
        protected void onPreExecute(){
            this.progressDialog.setMessage("Logging out of device and closing application...");
            this.progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                conn.PostToWebadress("http://" + TomatoMobile.getInstance().getIpaddress() + "/logout.asp", conn.buildParamsMap("_http_id",router.getHttpId()));
            } catch (IOException e) {
                e.printStackTrace();
                finish();
            }
            if(progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            finish();
            return null;
        }
    }

    /**
     * Setter for the router object that is contained on the main screen. This is to update the router object.
     * @param router The router object
     */
    public static void setRouter(Router router) {
        MainScreen.router = router;
    }
}
