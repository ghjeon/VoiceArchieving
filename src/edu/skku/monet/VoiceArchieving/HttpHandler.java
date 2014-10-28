package edu.skku.monet.VoiceArchieving;

/**
 * Created by Gyuhyeon on 2014. 10. 14..
 */

public abstract class HttpHandler {

    public abstract void onResponse(String result);

    public void execute(){
        new AsyncHttpTask(this).execute();
    }
}