package com.somethingprofane.tomato;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainScreen extends ActionBarActivity {

    @InjectView(R.id.mainscr_btnRouter)ImageButton routerButton;
    @InjectView(R.id.mainscr_btnDevices)ImageButton devicesButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_main);

        ButterKnife.inject(this);

        SharedPreferences prefs = getSharedPreferences("user_prefs", MainScreen.MODE_PRIVATE);
        Intent i = getIntent();
        Router router = (Router) i.getParcelableExtra("passed_router");

        Typeface sensationFont = Typeface.createFromAsset(getAssets(), "SourceSansPro-Regular.otf");
        TextView title = (TextView) findViewById(R.id.mainscr_Title);
        title.setTypeface(sensationFont);


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
        new moveToRouter().execute();
    }


    private class moveToRouter extends AsyncTask<TextView, Void, String> {

        @Override
        protected String doInBackground(TextView... textViews) {
            Intent intent = new Intent(MainScreen.this, RouterOverviewActivity.class);
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
            MainScreen.this.startActivity(intent);
            return null;
        }
    }

}
