package edu.skku.monet.VoiceArchieving;

/**
 * Created by Gyuhyeon on 2014. 10. 14..
 */

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import edu.skku.monet.VoiceArchieving.HttpHandler;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;

public class AsyncHttpTask extends AsyncTask<String, Void, String> {

    private HttpHandler httpHandler;
    private String fileName;

    public AsyncHttpTask(HttpHandler httpHandler, String fileName) {
        this.httpHandler = httpHandler;
        this.fileName = fileName;
    }

    String language = "ko_KR";

    // Key obtained through Google Developer group
    String api_key = "AIzaSyB4yB2fBpMWCBaTcFyj4iKuoYgUu8dFqq4";

    // Name of the sound file (.flac)

    // URL for Google API
    String root = "https://www.google.com/speech-api/v2/recognize";
    String dwn = "down?maxresults=1&pair=";
    String API_DOWN_URL = root + dwn;
    String up_p1 = "up?lang=" + language
            + "&lm=dictation&client=chromium&pair=";
    String up_p2 = "&key=";

    String v2URL = "https://www.google.com/speech-api/v2/recognize";
    String v2Params = "?output=json&lang=" + language + "&key=" + api_key;
    String USER_AGENT = "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/535.2 (KHTML, like Gecko) Chrome/15.0.874.121 Safari/535.2";

    @Override
    protected String doInBackground(String... arg0) {
        String result = "";
        try {
            URL obj = new URL(v2URL + v2Params);
            HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setRequestProperty("Content-Type", "audio/x-flac; rate=44100");
            con.setRequestProperty("AcceptEncoding", "gzip,deflate,sdch");
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.write(IOUtils.readFile(fileName));
            wr.flush();
            wr.close();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            result = response.toString();
        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        httpHandler.onResponse(result);
    }

    //--------------------------------------------------------------------------------------------
    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }
}
