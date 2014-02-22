package com.somethingprofane.tomato;

import android.util.Base64;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Created by somethingPr0fane on 2/6/14.
 */
public class Parser {

    //TODO: Add check in login class for wifi connectivity.
    //TODO: 1. Clean up parser class
    //TODO: 2. Add POST method.
    //TODO: 3. Modify OpenNewPageActivity to display router information and controls

    public static void HTMLParse(){
        try {
            String username = "root";
            String password = "admin";
            String login = username + ":" + password;
            String base64login = new String(Base64.encodeToString(login.getBytes(), Base64.DEFAULT));
            System.out.println(base64login);
            Document doc = Jsoup.connect("http://192.168.1.1").header("Authorization", "Basic " + base64login).get();
            String result = doc.text();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // TODO Implement this class
    public String ParseHTMLFromString(String html){
        String returnHtml = "";



        return returnHtml;
    }

    /**
     * Grab the HTML from a web address and return that as a string.
     * @param website The URL of the web address that the user wishes to get the HTML for.
     * @return A string of the HTML from the web address.
     */
    public static String ParseHTMLFromURL(String website){
        String returnHTML = "";
        String properSite = ValidateWebAddress(website);
        Document doc = GetRequestFromAddress(properSite);
        // TODO Update where this will returned formatted HTML, not just the entire page.
        returnHTML = doc.body().html();
        return returnHTML;
    }

    /**
     * Grab the HTML from a web address with a given CSS Query. This will allow elements to be found by their names, or even class
     * names. For example, to get all the 'p' elements with ID of 'content', the cssQuery would be "p#content".
     * @param website The web address to grab the select HTML from.
     * @param cssQuery The cssQuery that is to be applied. For more information on valid CSS queries, see http://jsoup.org/cookbook/extracting-data/selector-syntax.
     * @return The string of all the selected elements that were passed in as the cssQuery.
     */
    public static String ParseHTMLFromURL(String website, String cssQuery){
        String returnHTML = "";
        String properSite = ValidateWebAddress(website);
        Document doc = GetRequestFromAddress(properSite);
        Elements elements = doc.select(cssQuery);
        @SuppressWarnings("unused")
        String[] strings = new String[elements.size()];
        returnHTML = elements.text();
        return returnHTML;
    }

    /**
     * Will open a connection to a website and do an HTTP GET request to the web address.
     * @param address The web address to perform the HTTP Get request on.
     * @return A Jsoup.Document object is returned;
     */
    private static Document GetRequestFromAddress(String address){
        Document doc = null;
        if(IsReachable(address)){
            try {
                doc = Jsoup.connect(address).get();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return doc;
    }

    /**
     * Tests connectivity to an address to ensure that the URL is reachable. A static timeout of 5 seconds is set.
     * @param url The URL to test connectivity to.
     * @return Will return <code>true</code> if the URL is reachable and <code>false</code> if the URL is not reachable.
     */
    private static boolean IsReachable(String url) {
        // Set the timeout to 5 seconds;
        int timeout = 5000;
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setConnectTimeout(timeout);
            connection.setReadTimeout(timeout);
            connection.setRequestMethod("HEAD");
            int responseCode = connection.getResponseCode();
            return (200 <= responseCode && responseCode <= 399);
        } catch (IOException exception) {
            return false;
        }
    }

    /**
     * Will take a website and verify that http:// is added to the beginning of the address.
     * @param website The website to validate.
     * @return A URL that contains http:// at the beginning of the address.
     */
    private static String ValidateWebAddress(String website) {
        String formattedWebsite = website;
        if(!website.startsWith("http")){
            formattedWebsite = "http://" + website;
        }
        return formattedWebsite;
    }
}
