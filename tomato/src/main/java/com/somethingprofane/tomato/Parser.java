package com.somethingprofane.tomato;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;

/**
 * Created by somethingPr0fane on 2/6/14.
 */
public class Parser {

    public static void main(String[] args){
        //TODO: Add check in login class for wifi connectivity.
    }

    public void parse() {
        Document doc = null;
        try{
            // Get the HTML
            doc = Jsoup.connect("http://google.com").userAgent("Mozilla").get();

            String title = doc.title();
            System.out.println("Title: " + title);

            Elements elementArray;
            elementArray = doc.getAllElements();

            for(Element element : elementArray){
                System.out.println(element.text());
            }

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public String getHTMLFromURL(String url) {
        if(!validateURLAddress(url)){
            url = String.format("http://%s", url);
        }
            BufferedReader inStream = null;
            String results = "";
            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpGet httpRequest = new HttpGet(url);
                HttpResponse response = httpClient.execute(httpRequest);
                inStream = new BufferedReader(
                        new InputStreamReader(
                                response.getEntity().getContent()));

                StringBuffer buffer = new StringBuffer("");
                String line = "";
                String NL = System.getProperty("line.separator");
                while ((line = inStream.readLine()) != null) {
                    buffer.append(line + NL);
                }
                inStream.close();

                results = buffer.toString();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                if (inStream != null) {
                    try {
                        inStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return results;
    }

    private void jSoupParser(String html){
        Document doc = Jsoup.parse(html);
        // Parse though document and place all elements in a map.
;
    }

    private boolean validateURLAddress(String url) {
        String urlSub = url.substring(0,4);
        if(urlSub.equals("http")){
            return true;
        }else {
            return false;
        }
    }
}

// JAnderson Note: Implement all this code into this class in a bomb awesome way.
// TODO implement this shizz baby!


//    public static void main(String[] args){
//        System.out.println("Hello World");
//        String HTML = "";
//        HTML = ExampleJsoup.ParseHTMLFromURL("joelra.github.io", "p");
//    }
//
//    // JAnderson note: This is for reference currently.
//    private static void HTMLParse(){
//        try {
//            String username = "root";
//            String password = "admin";
//            String login = username + ":" + password;
//            String base64login = new String(Base64.encodeBase64(login.getBytes()));
//            System.out.println(base64login);
//            Document doc = Jsoup.connect("http://192.168.1.1").header("Authorization", "Basic " + base64login).get();
//
//            ExampleJsoup.GetElementsFromBody(doc.body());
//
////			System.out.println(doc.title());
////			Elements elements = doc.select("div[id=content]");
////			for(Element el : elements){
////				System.out.println(el.text());
////			}
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
//}
//
//
//class ExampleJsoup {
//    public static Map<String, ArrayList<String>> GetElementsFromBody(Document inDoc){
//        return GetElementsFromBody(inDoc.body());
//    }
//
//    public static Map<String, ArrayList<String>> GetElementsFromBody(Element body) {
//        // TODO Auto-generated method stub
//        Map<String, ArrayList<String>> myList = new HashMap<String, ArrayList<String>>();
//
//        Elements inBody = body.getAllElements();
//        for(Element el : inBody){
//            System.out.println(el.tagName());
//            if(myList.containsKey(el.tagName())){
//                ArrayList<String> current = myList.get(el.tagName());
//                current.add(el.text());
//            } else {
//                ArrayList<String> toAdd = new ArrayList<String>();
//                toAdd.add(el.text());
//                myList.put(el.tagName(), toAdd);
//            }
//            // Place in tag name as Key; Value will be text
//            // If there are multiple tags, take value and add to an array
//            //System.out.println("Test me");
//        }
//        return myList;
//    }
//
//    // TODO Implement this class
//    public String ParseHTMLFromString(String html){
//        String returnHtml = "";
//
//
//
//        return returnHtml;
//    }
//
//    /**
//     * Grab the HTML from a web address and return that as a string.
//     * @param website The URL of the web address that the user wishes to get the HTML for.
//     * @return A string of the HTML from the web address.
//     */
//    public static String ParseHTMLFromURL(String website){
//        String returnHTML = "";
//        String properSite = ValidateWebAddress(website);
//        Document doc = GetRequestFromAddress(properSite);
//        // TODO Update where this will returned formatted HTML, not just the entire page.
//        returnHTML = doc.body().html();
//        return returnHTML;
//    }
//
//    /**
//     * Grab the HTML from a web address with a given CSS Query. This will allow elements to be found by their names, or even class
//     * names. For example, to get all the 'p' elements with ID of 'content', the cssQuery would be "p#content".
//     * @param website The web address to grab the select HTML from.
//     * @param cssQuery The cssQuery that is to be applied. For more information on valid CSS queries, see http://jsoup.org/cookbook/extracting-data/selector-syntax.
//     * @return The string of all the selected elements that were passed in as the cssQuery.
//     */
//    public static String ParseHTMLFromURL(String website, String cssQuery){
//        String returnHTML = "";
//        String properSite = ValidateWebAddress(website);
//        Document doc = GetRequestFromAddress(properSite);
//        Elements elements = doc.select(cssQuery);
//        @SuppressWarnings("unused")
//        String[] strings = new String[elements.size()];
//        returnHTML = elements.text();
//        return returnHTML;
//    }
//
//    /**
//     * Will open a connection to a website and do an HTTP GET request to the web address.
//     * @param address The web address to perform the HTTP Get request on.
//     * @return A Jsoup.Document object is returned;
//     */
//    private static Document GetRequestFromAddress(String address){
//        Document doc = null;
//        if(IsReachable(address)){
//            try {
//                doc = Jsoup.connect(address).get();
//
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//        return doc;
//    }
//
//    /**
//     * Tests connectivity to an address to ensure that the URL is reachable. A static timeout of 5 seconds is set.
//     * @param url The URL to test connectivity to.
//     * @return Will return <code>true</code> if the URL is reachable and <code>false</code> if the URL is not reachable.
//     */
//    private static boolean IsReachable(String url) {
//        // Set the timeout to 5 seconds;
//        int timeout = 5000;
//        try {
//            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
//            connection.setConnectTimeout(timeout);
//            connection.setReadTimeout(timeout);
//            connection.setRequestMethod("HEAD");
//            int responseCode = connection.getResponseCode();
//            return (200 <= responseCode && responseCode <= 399);
//        } catch (IOException exception) {
//            return false;
//        }
//    }
//
//    /**
//     * Will take a website and verify that http:// is added to the beginning of the address.
//     * @param website The website to validate.
//     * @return A URL that contains http:// at the beginning of the address.
//     */
//    private static String ValidateWebAddress(String website) {
//        String formattedWebsite = website;
//        if(!website.startsWith("http")){
//            formattedWebsite = "http://" + website;
//        }
//        return formattedWebsite;
//    }
//}
