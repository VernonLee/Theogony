package com.nodlee.amumu.util;

import android.net.Uri;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Vernon Lee on 15-11-23.
 */
public class HttpFetchr {
    private static final int SOCKET_TIME_OUT = 10 * 1000;

    public byte[] getUrlBytes(Uri urlSpec) throws IOException {
        URL url = new URL(urlSpec.toString());
        HttpURLConnection connection = null;

        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(SOCKET_TIME_OUT);
            connection.setReadTimeout(SOCKET_TIME_OUT);

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                Log.e("xxx", "服务器请求错误，错误码=" + connection.getResponseCode());
                return null;
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();

            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();

            return out.toByteArray();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public String getUrl(Uri urlSpec) {
        try {
            byte[] response = getUrlBytes(urlSpec);
            if (response != null) {
                return new String(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
