package com.somethingprofane.tomato;

import android.util.Base64;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by somethingPr0fane on 3/4/14.
 */
public class Connection {

    public String PostToWebadress(String website, String username, String password, HashMap parameterMap) throws IOException {
        String responseHTML = "";
        String base64login = GetBase64Login(username, password);
        /**
         * This is just for testing purposes. //TODO implement the data parameters a lot better. Possibly a map?
         */
        //TODO Remove httpID from this
        Document doc = Jsoup.connect(website)
                .header("Authorization", "Basic " + base64login)
                .userAgent("Mozilla")
                .data(parameterMap)
                .timeout(10000)
                .post();
        responseHTML = doc.text();
        return responseHTML;
    }

    public String GetHTMLFromURL(String website, String username, String password){
        String returnHTML = "";
        String properSite = ValidateWebAddress(website);
        Document doc = null;
        String base64login = GetBase64Login(username, password);
        if(ValidateAuthentication(website, base64login)){
            doc = GetDocumentFromAddress(properSite, base64login);
        }
        returnHTML = doc.html();
        return returnHTML;
    }

    private boolean ValidateAuthentication(String website, String base64login) {
        Boolean validated = false;

        int responseCode = GetResponseCodeFromAddress(website, base64login);

        if(responseCode == 200){
            validated =  true;
        }

        return validated;
    }

    private static Document GetDocumentFromAddress(String address, String base64login){

        Document doc = null;
        // if(IsReachable(address)){
        try {
            doc = Jsoup.connect(address).header("Authorization", "Basic " + base64login).get();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //}
        return doc;
    }

    private static String ValidateWebAddress(String website) {
        String formattedWebsite = website;
        if(!website.startsWith("http")){
            formattedWebsite = "http://" + website;
        }
        return formattedWebsite;
    }

    public int GetResponseCodeFromAddress(String url, String base64login){
        int response = 0;
        org.jsoup.Connection.Response urlResponse = null;
        try {
            urlResponse = Jsoup.connect(ValidateWebAddress(url))
                    .header("Authorization", "Basic " + base64login)
                    .userAgent("Mozilla")
                    .timeout(3000)
                    .execute();
            response = urlResponse.statusCode();
        } catch (HttpStatusException error401){
            response = error401.getStatusCode();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    public String GetBase64Login(String username, String password) {
        String loginCreds = username + ":" + password;
        String base64login = new String(Base64.encodeToString(loginCreds.getBytes(), Base64.DEFAULT));
        base64login = base64login.trim();
        if(!base64login.endsWith("=")){
            base64login += "==";
        }
        return base64login;
    }

}
