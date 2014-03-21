package com.somethingprofane.tomato;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.HashMap;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by garrett on 2/25/14.
 */
public class RouterOverviewActivity extends Activity {

    TextView routerNameTxt;
    TextView routerMacTxt;
    TextView routerIPTxt;
    TextView routerModelTxt;
    TextView routerUptimeTxt;
    TextView routerRamTxt;

    Router router;
    ProgressDialog dialog;

    @InjectView(R.id.router_overview_retrieveRouter) Button routerButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dialog = new ProgressDialog(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_router_overview);

        routerNameTxt = (TextView)findViewById(R.id.router_main_txtRouter);
        routerMacTxt = (TextView)findViewById(R.id.router_main_txtMAC);
        routerIPTxt = (TextView)findViewById(R.id.trouter_main_txtIP);
        routerModelTxt = (TextView)findViewById(R.id.router_main_txtModel);
        routerUptimeTxt = (TextView) findViewById(R.id.router_main_txtUptime);
        routerRamTxt = (TextView) findViewById(R.id.router_main_txtRAM);
        Intent i = getIntent();
        router = (Router) i.getParcelableExtra("passed_router");
        new refreshRouterInfo().execute(router);
        System.out.println(router.getRouterName() + "OnCreate");

        ButterKnife.inject(this);

        // new retrieveRouterInfo().execute(routerNameTxt, routerMacTxt,routerIPTxt,routerModelTxt, routerUptimeTxt,routerRamTxt);
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

    @OnClick(R.id.router_overview_retrieveRouter)
    public void refreshClicked(Button routerButton){

        //new retrieveRouterInfo().execute(routerNameTxt, routerMacTxt,routerIPTxt,routerModelTxt, routerUptimeTxt,routerRamTxt);

        if (!routerNameTxt.getText().toString().equals(router.getRouterName())){
            dialog.setMessage("Updating router...");
            dialog.show();
            new updateRouterInfo().execute(routerNameTxt.getText().toString());
        }
        else{
            dialog.setMessage("Refreshing router...");
            dialog.show();
            new refreshRouterInfo().execute(router);
        }
    }

    private class refreshRouterInfo extends AsyncTask<Router, Void, Router> {

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
            RouterOverviewActivity.this.router = router;
            routerNameTxt.setText(router.getRouterName());
            routerMacTxt.setText(router.getWanHwAddr());
            routerIPTxt.setText(router.getLanIpAddr());
            routerModelTxt.setText(router.getModelName());
            routerUptimeTxt.setText(router.getUptime());
            routerRamTxt.setText(router.getTotalRam());

            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }

    private class updateRouterInfo extends AsyncTask<String,Void,String> {


        boolean isFailed = false;

        @Override
        protected void onPreExecute() {


        }

        @Override
        protected String doInBackground(String... strings) {
            Connection conn = new Connection();
            String returnedHTML = null;
            HashMap<String, String> tempHashMap = conn.buildParamsMap("_http_id", RouterOverviewActivity.this.router.getHttpId(), "router_name", strings[0]);
            try {
                returnedHTML = conn.PostToWebadress(RouterOverviewActivity.this.router.getUrl() + "/tomato.cgi", RouterOverviewActivity.this.router.getUsrname(), RouterOverviewActivity.this.router.getPswrd(), tempHashMap);
            } catch (IOException e) {
                isFailed = true;
                e.printStackTrace();
            }
            return returnedHTML;
        }

        protected void onPostExecute(String string) {
            if (isFailed) {
                Toast.makeText(RouterOverviewActivity.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
            } else {

                Toast.makeText(RouterOverviewActivity.this, "Router has been updated.", Toast.LENGTH_SHORT).show();

            }


            new RouterOverviewActivity.refreshRouterInfo().execute(RouterOverviewActivity.this.router);

        }
    }
}
