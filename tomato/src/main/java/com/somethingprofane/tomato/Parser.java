package com.somethingprofane.tomato;

import android.util.Base64;

import org.json.JSONArray;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by somethingPr0fane on 2/6/14.
 */
public class Parser {

    //TODO: Add check in login class for wifi connectivity.
    //TODO: 1. Clean up parser class
    //TODO: 2. Add POST method.
    //TODO: 3. Modify OpenNewPageActivity to display router information and controls

    public String ParseHTMLFromString(String html){
        //TODO Implement this class
        throw null;
//        String returnHtml = "";
//        return returnHtml;
    }

    /**
     * Grab the HTML from a web address and return that as a string.
     * @param website The URL of the web address that the user wishes to get the HTML for.
     * @return A string of the HTML from the web address.
     */
    public String ParseHTMLFromURL(String website, String username, String password){
        String returnHTML = "";
        String properSite = ValidateWebAddress(website);
        Document doc = null;
        String base64login = GetBase64Login(username, password);
        if(ValidateAuthentication(website, base64login)){
            doc = GetDocumentFromAddress(properSite, base64login);
        }
        returnHTML = doc.body().html();
        return returnHTML;
    }

    public static String GetBase64Login(String username, String password) {
        String loginCreds = username + password;
        String base64login = new String(Base64.encodeToString(loginCreds.getBytes(), Base64.DEFAULT));
        return base64login;
    }

    private boolean ValidateAuthentication(String website, String base64login) {
        Boolean validated = false;

        int responseCode = GetResponseCodeFromAddress(website, base64login);

        if(responseCode == 200){
            validated =  true;
        }

        return validated;
    }

    /**
     * Grab the HTML from a web address with a given CSS Query. This will allow elements to be found by their names, or even class
     * names. For example, to get all the 'p' elements with ID of 'content', the cssQuery would be "p#content".
     * @param website The web address to grab the select HTML from.
     * @param cssQuery The cssQuery that is to be applied. For more information on valid CSS queries, see http://jsoup.org/cookbook/extracting-data/selector-syntax.
     * @return The string of all the selected elements that were passed in as the cssQuery.
     */
    public static String ParseHTMLFromURL(String website, String username, String password, String cssQuery){
        String returnHTML = "";
        String properSite = ValidateWebAddress(website);
        String base64login = GetBase64Login(username, password);
        Document doc = GetDocumentFromAddress(properSite, base64login);
        Elements elements = doc.select(cssQuery);
        returnHTML = elements.text();
        return returnHTML;
    }

    /**
     * Will open a connection to a website and do an HTTP GET request to the web address.
     * @param address The web address to perform the HTTP Get request on.
     * @return A Jsoup.Document object is returned;
     */
    private static Document GetDocumentFromAddress(String address, String base64login){

        Document doc = null;
        if(IsReachable(address)){
            try {
                doc = Jsoup.connect("http://192.168.1.1").header("Authorization", "Basic " + base64login).get();

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

    /**
     * Will send a post request to a specified address and return the html if the post if successful. Currently, this doesn't accept the right number of parameters.
     * @param website The website to post to.
     * @param username The username to authenticate with.
     * @param password The password to authentication with.
     * @param parameterString The parameter to send in. Currently not implemented.
     * @return Returns a string of the HTML that came from the post request.
     * @throws IOException
     */
    public String PostToWebadress(String website, String username, String password, String parameterString) throws IOException {
        String responseHTML = "";
        String base64login = GetBase64Login(username, password);
        /**
         * This is just for testing purposes. //TODO implement the data parameters a lot better. Possibly a map?
         */
        String httpID = "_http_id";
        String id = GetRouterHTTPId();
        Document doc = Jsoup.connect(website)
                .data(parameterString)
                .header("Authorization", "Basic " + base64login)
                .userAgent("Mozilla")
                .post();
        responseHTML = doc.text();
        return responseHTML;
    }

    private String GetRouterHTTPId() {
        String http_id = "";
        String website = "http://192.168.1.1";
        String basic64login = GetBase64Login("root", "admin");
        Document doc = GetDocumentFromAddress(website, basic64login);
        String scriptTag = doc.getElementsByTag("head").html();
        String pattern = "http_id=(.*?)\"";
        Pattern r = Pattern.compile(pattern, Pattern.DOTALL);
        Matcher m = r.matcher(scriptTag);
        if(m.find()){
            System.out.println(m.group(1));
            http_id = m.group(1);
        }
        return http_id;
    }

    public int GetResponseCodeFromAddress(String url, String base64login){
        int response;
        Connection.Response urlResponse = null;
        try {
            urlResponse = Jsoup.connect(ValidateWebAddress(url))
                    .header("Authorization", "Basic " + base64login)
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        response = urlResponse.statusCode();
        return response;
    }
}
