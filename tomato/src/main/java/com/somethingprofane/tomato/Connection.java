package com.somethingprofane.tomato;

import android.util.Base64;
import android.util.Log;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by somethingPr0fane on 3/4/14.
 */
public class Connection {

    public String PostToWebadress(String website, HashMap parameterMap) throws IOException {
        String responseHTML = "";
        String base64login = GetBase64Login(TomatoMobile.getInstance().getUsername(), TomatoMobile.getInstance().getPassword());
        Document doc = Jsoup.connect(website)
                .header("Authorization", "Basic " + base64login)
                .userAgent("Mozilla")
                .data(parameterMap)
                .timeout(10000)
                .post();
        responseHTML = doc.text();
        return responseHTML;
    }

    public HashMap buildParamsMap(String ... param){
        HashMap <String, String> paramsMap = new HashMap<String, String>();

        for ( int i = 0; i<param.length-1; i++){
            if (i%2==0){
                paramsMap.put(param[i],param[i+1]);
            }
        }

        return paramsMap;
    }

    public String GetHTMLFromURL(String website){
        String returnHTML = "";
        String properSite = ValidateWebAddress(website);
        Document doc = null;
        doc = GetDocumentFromAddress(properSite);
        returnHTML = doc.html();
        return returnHTML;
    }

    /**
     * Grab the HTML from a web address with a given CSS Query. This will allow elements to be found by their names, or even class
     * names. For example, to get all the 'p' elements with ID of 'content', the cssQuery would be "p#content".
     * @param website The web address to grab the select HTML from.
     * @param cssQuery The cssQuery that is to be applied. For more information on valid CSS queries, see http://jsoup.org/cookbook/extracting-data/selector-syntax.
     * @return The string of all the selected elements that were passed in as the cssQuery.
     */
    public String GetHTMLFromURL(String website, String cssQuery){
        String returnHTML = "";
        String properSite = ValidateWebAddress(website);
        String base64login = GetBase64Login(TomatoMobile.getInstance().getUsername(), TomatoMobile.getInstance().getPassword());
        Document doc = GetDocumentFromAddress(properSite);
        Elements elements = doc.select(cssQuery);
        returnHTML = elements.text();
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

    private Document GetDocumentFromAddress(String address){
        address = ValidateWebAddress(address);
        Document doc = null;
        try {
            doc = Jsoup.connect(address).header("Authorization", "Basic " + GetBase64Login(TomatoMobile.getInstance().getUsername(), TomatoMobile.getInstance().getPassword())).timeout(10000).get();
        } catch (SocketTimeoutException socketTimeout){
            socketTimeout.printStackTrace();
            Log.d("SocketTimeout", "Retrying to connect to router.");
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                    .timeout(7000)
                    .execute();
            response = urlResponse.statusCode();
        } catch (SocketTimeoutException socketTimeout){
            socketTimeout.printStackTrace();
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

    public String GetRouterHTTPId() {
        String http_id = "";
        String website = TomatoMobile.getInstance().getIpaddress();
        String basic64login = GetBase64Login(TomatoMobile.getInstance().getUsername(), TomatoMobile.getInstance().getPassword());
        Document doc = GetDocumentFromAddress(website);
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

}
