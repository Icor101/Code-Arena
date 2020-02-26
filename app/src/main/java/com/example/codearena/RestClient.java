package com.example.codearena;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;

class RestClient {
    public enum RequestMethod {
        GET,
        POST
    }

    private int responseCode = 0;
    private String message, response;

    void Execute(RequestMethod method, String url, ArrayList<NameValuePair> headers, ArrayList<NameValuePair> params) throws Exception {
        switch (method) {
            case GET: {
                //add params
                String combinedParams = "";
                if (params != null) {
                    combinedParams += "?";
                    for (NameValuePair p : params) {
                        String paramString = p.getName() + "=" + URLEncoder.encode(p.getValue(), "UTF-8");
                        if (combinedParams.length() > 1)
                            combinedParams += "&" + paramString;
                        else
                            combinedParams += paramString;
                    }
                }
                HttpGet request = new HttpGet(url + combinedParams);
                //add headers
                if (headers != null) {
                    headers = addCommonHeaderField(headers);
                    for (NameValuePair h : headers)
                        request.addHeader(h.getName(), h.getValue());
                }
                executeRequest(request, url);
                break;
            }
            case POST: {
                HttpPost request = new HttpPost(url);
                //add headers
                if (headers != null) {
                    headers = addCommonHeaderField(headers);
                    for (NameValuePair h : headers)
                        request.addHeader(h.getName(), h.getValue());
                }
                if (params != null)
                    request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                executeRequest(request, url);
                break;
            }
        }
    }

    private ArrayList<NameValuePair> addCommonHeaderField(ArrayList<NameValuePair> _header) {
        _header.add(new BasicNameValuePair("Content-Type", "application/x-www-form-urlencoded"));
        return _header;
    }

    private void executeRequest(HttpUriRequest request, String url) {
        HttpClient client = new DefaultHttpClient();
        HttpResponse httpResponse;
        //Log.d("Checking","Check Successful");
        try {
            httpResponse = client.execute(request);
            Log.d("Checking","Outside Check Successful");
            responseCode = httpResponse.getStatusLine().getStatusCode();
            message = httpResponse.getStatusLine().getReasonPhrase();
            HttpEntity entity = httpResponse.getEntity();

            if (entity != null) {
                FileWriter file = new FileWriter("C:\\Users\\riyam\\Desktop\\check.txt");
                file.write(responseCode+"\n");
                InputStream instream = entity.getContent();
                response = convertStreamToString(instream);
                Log.d("Checking","Check Successful");
                instream.close();
            }
            else
                Log.d("Checking","Check Successful");
        } catch (Exception e) {
            Log.d("Debug", e.toString());
        }
    }

    private static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
        } catch (IOException e) {
            Log.d("Debug", e.getMessage());
        }
        return sb.toString();
    }
}
