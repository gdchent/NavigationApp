package com.funtsui.updatelib.utils;

import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpDownloadUtility {
    private static final int BUFFER_SIZE = 4096;

    private static final String TAG = "DownloadUtility";

    /**
     * Downloads a file from a URL
     *
     * @param fileURL HTTP URL of the file to be downloaded
     * @param saveDir path of the directory to save the file
     * @throws IOException
     */
    public static void downloadFile(String fileURL, String saveDir, ProgressListener progressListener)
            throws IOException {
        URL url = new URL(fileURL);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        int responseCode = httpConn.getResponseCode();
        // always check HTTP response code first
        if (responseCode == HttpURLConnection.HTTP_OK) {
            progressListener.onStart();
            String fileName = "";
            String disposition = httpConn.getHeaderField("Content-Disposition");
            String contentType = httpConn.getContentType();
            int contentLength = httpConn.getContentLength();

            if (disposition != null) {
                // extracts file name from header field
                String[] dispositions = disposition.split(";");
                for (String string : dispositions) {
                    if (string.indexOf("filename") > 0) {
                        fileName = string.split("=")[1].replace("\"", "");
                        break;
                    }
                }
            } else {
                // extracts file name from URL
                fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1);
            }

            Log.d(TAG, "Content-Type = " + contentType);
            Log.d(TAG, "Content-Disposition = " + disposition);
            Log.d(TAG, "Content-Length = " + contentLength);
            Log.d(TAG, "fileName = " + fileName);

            // opens input stream from the HTTP connection
            InputStream inputStream = httpConn.getInputStream();
            String saveFilePath = saveDir + File.separator + fileName;

            // opens an output stream to save into file
            FileOutputStream outputStream = new FileOutputStream(saveFilePath);

            byte[] buffer = new byte[BUFFER_SIZE];
            int current = 0;
            int bytesRead = inputStream.read(buffer);
            while (true) {
                outputStream.write(buffer, 0, bytesRead);
                current += bytesRead;
                progressListener.onProgress(current, contentLength);
                bytesRead = inputStream.read(buffer);
                if (bytesRead == -1) {
                    progressListener.onComplete(saveFilePath);
                    break;
                }
            }

            outputStream.close();
            inputStream.close();

            Log.d(TAG, "File downloaded");
        } else {
            progressListener.onError(responseCode);
            Log.d(TAG, "No file to download. Server replied HTTP code: " + responseCode);
        }
        httpConn.disconnect();
    }

    public interface ProgressListener {

        void onStart();

        void onProgress(int current, int total);

        void onComplete(String saveFilePath);

        void onError(int responseCode);
    }
}