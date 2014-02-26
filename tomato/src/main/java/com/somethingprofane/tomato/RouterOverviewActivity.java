package com.somethingprofane.tomato;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

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

    @InjectView(R.id.router_overview_retrieveRouter) Button routerButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_router_overview);

        ButterKnife.inject(this);
         routerNameTxt = (TextView)findViewById(R.id.router_main_txtRouter);
         routerMacTxt = (TextView)findViewById(R.id.router_main_txtMAC);
         routerIPTxt = (TextView)findViewById(R.id.trouter_main_txtIP);
         routerModelTxt = (TextView)findViewById(R.id.router_main_txtModel);
         routerUptimeTxt = (TextView) findViewById(R.id.router_main_txtUptime);
         routerRamTxt = (TextView) findViewById(R.id.router_main_txtRAM);
        new retrieveRouterInfo().execute(routerNameTxt, routerMacTxt,routerIPTxt,routerModelTxt, routerUptimeTxt,routerRamTxt);

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

        new retrieveRouterInfo().execute(routerNameTxt, routerMacTxt,routerIPTxt,routerModelTxt, routerUptimeTxt,routerRamTxt);

    }

    private class retrieveRouterInfo extends AsyncTask<TextView, Void, Router> {

        TextView routerNameTxt;
        TextView routerMacTxt;
        TextView routerIPTxt;
        TextView routerModelTxt;
        TextView routerUptimeTxt;
        TextView routerRamTxt;

        @Override
        protected Router doInBackground(TextView... textViews) {


            Router tempRouter = new Router("http://192.168.1.1", "root", "admin");

            routerNameTxt = textViews[0];
            routerMacTxt = textViews[1];
            routerIPTxt = textViews[2];
            routerModelTxt = textViews[3];
            routerUptimeTxt = textViews[4];
            routerRamTxt = textViews[5];
            tempRouter.setDeviceList();
            return tempRouter;
        }

        @Override
        protected void onPostExecute(Router router){
            routerNameTxt.setText(router.getRouterName());
            routerMacTxt.setText(router.getWanHwAddr());
            routerIPTxt.setText(router.getLanIpAddr());
            routerModelTxt.setText(router.getModelName());
            routerUptimeTxt.setText(router.getUptime());
            routerRamTxt.setText(router.getTotalRam());
        }
    }
}
