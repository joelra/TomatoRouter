package com.somethingprofane.tomato;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.reflect.Array;

/**
 * Created by somethingPr0fane on 2/6/14.
 */
public class Parser {

    public static void main(String[] args){

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
}
