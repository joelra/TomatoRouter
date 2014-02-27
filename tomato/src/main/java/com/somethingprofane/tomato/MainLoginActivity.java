package com.somethingprofane.tomato;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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

    private class LoginValidation extends AsyncTask<TextView, Void, String>{

        TextView usrnameTextView;
        TextView pswrdTextView;
        TextView ipTextView;
        String response = "N/A";
        int responseCode;
        private Parser parserClass = new Parser();
        private ProgressDialog dialog = new ProgressDialog(MainLoginActivity.this);

        @Override
        protected void onPreExecute(){
            this.dialog.setMessage("Logging in...");
            this.dialog.show();
        }

        @Override
        protected String doInBackground(TextView... textViews) {
            this.usrnameTextView = textViews[0];
            this.pswrdTextView = textViews[1];
            this.ipTextView = textViews[2];
            String ip = ipTextView.getText().toString();
            String username = usrnameTextView.getText().toString();
            String password = pswrdTextView.getText().toString();
            String base64login = parserClass.GetBase64Login(username,password);
            responseCode = parserClass.GetResponseCodeFromAddress(ip,base64login);
            response = Integer.toString(responseCode);
            return response;
        }

        protected void onPostExecute(String response) {
            // access the activity thread
            if(response.equals("200")){
                Intent intent = new Intent(MainLoginActivity.this, MainScreen.class);
                MainLoginActivity.this.startActivity(intent);
                // Finish the activity;
                finish();
            } else {
                Context context = getApplicationContext();
                CharSequence text = "Something went wrong...";
                int duration = Toast.LENGTH_SHORT;
                Toast.makeText(context, text, duration).show();
            }
        }


    }

}
