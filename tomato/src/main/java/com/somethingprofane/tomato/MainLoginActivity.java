package com.somethingprofane.tomato;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
        setContentView(R.layout.login_main);

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



    }

    private class LoginValidation extends AsyncTask<TextView, Void, String>{

        TextView usrnameTextView;
        TextView pswrdTextView;
        TextView ipTextView;
        String results = "N/A";
        private Parser parserClass = new Parser();

        @Override
        protected String doInBackground(TextView... textViews) {
            this.usrnameTextView = textViews[0];
            this.pswrdTextView = textViews[1];
            this.ipTextView = textViews[2];
            return validateCredentials();
        }

        final String validateCredentials() {
            String url = "http://www.duoh.com/404";//String.valueOf(mEdit.getText());
            results = parserClass.getHTMLFromURL(url);
            return results;
        }

        protected void onPostExecute(String html) {

            // access the activity thread
            inTextView.setText(html);
        }


    }

}
