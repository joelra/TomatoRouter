package com.somethingprofane.tomato;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.os.Build;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.somethingprofane.db.DatabaseManager;

import java.net.NetworkInterface;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


/**
 * Created by somethingPr0fane on 2/22/14.
 */
public class MainLoginActivity extends ActionBarActivity {

    @InjectView(R.id.login_main_loginbtn) Button button;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // inflate your layout and set it to the screen
        setContentView(R.layout.activity_main_login);

        // Inject Button
        ButterKnife.inject(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            //Show the Up button in the action bar.
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        new checkWirelessConnection().execute();
    }


    @OnClick(R.id.login_main_loginbtn)
    public void LoginClicked(Button button){
        TextView username = (TextView) findViewById(R.id.login_main_usrname_text);
        TextView passwd = (TextView) findViewById(R.id.login_main_passwd_text);
        TextView ipAddr = (TextView) findViewById(R.id.login_main_ip_text);
        new LoginValidation().execute(username, passwd, ipAddr);
    }

    private class checkWirelessConnection extends AsyncTask<Void, Void, Boolean>{


        @Override
        protected Boolean doInBackground(Void... voids) {
            boolean wifiConnected = false;
                wifiConnected = checkWifi();
            return wifiConnected;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(!aBoolean){
                final AlertDialog.Builder alertBox = new AlertDialog.Builder(MainLoginActivity.this);
                alertBox.setMessage("Wifi doesn't seem enabled. Turn on wifi?");
                alertBox.setPositiveButton("Wifi Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                });
                alertBox.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Do nothing and close the alert window
                    }
                });
                alertBox.show();
            }
        }

        boolean checkWifi() {
            boolean wifiState = false;
            // Returns network related information
            ConnectivityManager connectivity = (ConnectivityManager) MainLoginActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
            if(connectivity != null){
                // Network information related to wifi
                try {
                    NetworkInfo networkInfo = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                    if (networkInfo != null && networkInfo.isConnected()) {
                        wifiState = true;
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            return wifiState;
        }
    }

    private class LoginValidation extends AsyncTask<TextView, Void, Router>{

        TextView usrnameTextView;
        TextView pswrdTextView;
        TextView ipTextView;
        String response = "N/A";
        int responseCode;
        private Connection conn = new Connection();
        private ProgressDialog dialog = new ProgressDialog(MainLoginActivity.this);
        Boolean correctResponse;

        @Override
        protected void onPreExecute(){
            this.dialog.setMessage("Logging in...");
            this.dialog.show();
            DatabaseManager.init(MainLoginActivity.this);
        }

        @Override
        protected Router doInBackground(TextView... textViews) {
            Router router = null;
            this.usrnameTextView = textViews[0];
            this.pswrdTextView = textViews[1];
            this.ipTextView = textViews[2];
            String ip = ipTextView.getText().toString();
            String username = usrnameTextView.getText().toString();
            String password = pswrdTextView.getText().toString();
            String base64login = conn.GetBase64Login(username,password);
            responseCode = conn.GetResponseCodeFromAddress(ip, base64login);
            response = Integer.toString(responseCode);

            if(response.equals("200")){
                correctResponse = true;
                router = new Router("http://"+ip, username, password);
            } else {
                correctResponse = false;
            }

            return router;

        }

        protected void onPostExecute(Router router) {
            // access the activity thread
            if(correctResponse == true){
                SharedPreferences prefs = getSharedPreferences("user_prefs", MainLoginActivity.this.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("username", usrnameTextView.getText().toString());
                editor.putString("password", pswrdTextView.getText().toString());
                editor.putString("IPAddress", ipTextView.getText().toString());
                editor.commit();
                dialog.dismiss();
                Intent intent = new Intent(MainLoginActivity.this, MainScreen.class);
                intent.putExtra("passed_router", router);
                MainLoginActivity.this.startActivity(intent);
                // Finish the activity;
                finish();
            } else {
                Context context = getApplicationContext();
                dialog.dismiss();

                if(checkWifi()){
                    CharSequence text;
                    text = "Could not authenticate to router. Check credentials and try again.";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    final AlertDialog.Builder alertBox = new AlertDialog.Builder(MainLoginActivity.this);
                    alertBox.setMessage("Wifi doesn't seem enabled. Turn on wifi?");
                    alertBox.setPositiveButton("Wifi Settings", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                        }
                    });
                    alertBox.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // Do nothing and close the alert window
                        }
                    });
                    alertBox.show();
                }
            }
        }

        boolean checkWifi() {
            boolean wifiState = false;
            // Returns network related information
            ConnectivityManager connectivity = (ConnectivityManager) MainLoginActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
            if(connectivity != null){
                // Network information related to wifi
                try {
                    NetworkInfo networkInfo = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                    if (networkInfo != null && networkInfo.isConnected()) {
                        wifiState = true;
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            return wifiState;
        }


    }

}
