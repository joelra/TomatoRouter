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
