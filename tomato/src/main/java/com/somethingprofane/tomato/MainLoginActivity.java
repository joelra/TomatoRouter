package com.somethingprofane.tomato;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.os.Build;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

        // get the view by Id from the layout that you just inflated
        //TextView textview = (TextView) findViewById(R.id.textView);

        // now that you have the object you can access different methods on it, like setText("").
        //textview.setText("Joel this is how you set text");

        // Check to see if on Honeycomb for ActionBar API. Not sure why...
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            //Show the Up button in the action bar.
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @OnClick(R.id.login_main_loginbtn)
    public void LoginClicked(Button button){
        TextView username = (TextView) findViewById(R.id.login_main_usrname_text);
        TextView passwd = (TextView) findViewById(R.id.login_main_passwd_text);
        TextView ipAddr = (TextView) findViewById(R.id.login_main_ip_text);
        new LoginValidation().execute(username, passwd, ipAddr);
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

                Intent intent = new Intent(MainLoginActivity.this, MainScreen.class);
                intent.putExtra("passed_router", router);
                MainLoginActivity.this.startActivity(intent);
                dialog.dismiss();
                // Finish the activity;
                finish();
            } else {
                Context context = getApplicationContext();
                dialog.dismiss();
                CharSequence text = "Something went wrong...";
                int duration = Toast.LENGTH_SHORT;
                Toast.makeText(context, text, duration).show();
            }
        }


    }

}
