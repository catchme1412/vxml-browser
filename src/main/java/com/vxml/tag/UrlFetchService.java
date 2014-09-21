package com.vxml.tag;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Logger;

public class UrlFetchService {

    private static Logger logger = Logger.getLogger(UrlFetchService.class.getName());

    public InputStream fetchInputStream(URI uri) throws IOException {
        URL url = uri.toURL();
        CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
        URLConnection connection = url.openConnection();
        String serverCookies = connection.getHeaderField("Set-Cookie");
        if(serverCookies != null){
            String[] cookies = serverCookies.split(";");
            for(String s : cookies){
                s = s.trim();
                if(s.split("=")[0].equals("JSESSIONID")){
                    String jsessionId = s.split("=")[1];
                    System.out.println(jsessionId);
                    break;
                }
            }
        }
        logger.info("URL: " + url.toExternalForm());
        return connection.getInputStream();
    }
    
    public StringBuilder fetch(String url) {
        StringBuilder builder = new StringBuilder();
        try {
            CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
            URLConnection connection = new URL(url).openConnection();
            String serverCookies = connection.getHeaderField("Set-Cookie");
            if(serverCookies != null){
                String[] cookies = serverCookies.split(";");
                for(String s : cookies){
                    s = s.trim();
                    if(s.split("=")[0].equals("JSESSIONID")){
                        String jsessionId = s.split("=")[1];
                        System.out.println(jsessionId);
                        break;
                    }
                }
            }
            String line;
            InputStream in = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return builder;
    }

    public InputStream fetchInputStream(URL url) throws IOException {
        CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
        URLConnection connection = url.openConnection();
        connection.setRequestProperty("User-Agent", "(movie-arbiter.rhcloud.com catchme1412@gmail.com)");
        connection.setRequestProperty("Referrer", "movie-arbiter.rhcloud.com");
        connection.setRequestProperty("contact", "catchme1412@gmail.com");
        logger.info("URL: " + url.toExternalForm());
        return connection.getInputStream();
    }

}
