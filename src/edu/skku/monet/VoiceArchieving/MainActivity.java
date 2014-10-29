package edu.skku.monet.VoiceArchieving;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpClient;

import edu.skku.monet.VoiceArchieving.Archive.*;
import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import com.loopj.android.http.*;
import org.apache.http.Header;
import java.util.*;

/**
 * 
 * @author Lucas Santana
 * 
 *         Activity responsible for recording and sending sound to Google API
 * 
 */

public class MainActivity extends Activity {

    private final int KEYWORD_LIST_UI = 1000,
                      KEYWORD_ARCHIVE_UI = 1001,
                      ARCHIVE_LIST_UI = 1002,
                      ARCHIVE_KEYWORD_UI = 1003;

    private static String mFilePath = null;
    private static String mShortFileName = null;
    private static String mRShortFileName = null;
    private static String mFileName = null;
    private static String mRFileName = null;

    // Language spoken
	// Obs: It requires Google codes: English(en_us), Portuguese(pt_br), Spanish
	// (es_es), etc
	String language = "ko_KR";

	// Key obtained through Google Developer group
	String api_key = "AIzaSyB4yB2fBpMWCBaTcFyj4iKuoYgUu8dFqq4";

	// URL for Google API
	String root = "https://www.google.com/speech-api/v2/recognize";
	String dwn = "down?maxresults=1&pair=";
	String API_DOWN_URL = root + dwn;
	String up_p1 = "up?lang=" + language
			+ "&lm=dictation&client=chromium&pair=";
	String up_p2 = "&key=";

    String v2URL = "https://www.google.com/speech-api/v2/recognize";
    String v2Params = "?output=json&lang=" + language + "&key=" + api_key;

	
	
	// Variables used to establish return code
	private static final long MIN = 10000000;
	private static final long MAX = 900000009999999L;
	long PAIR;

	// Constants
	private int mErrorCode = -1;
	private static final int DIALOG_RECORDING_ERROR = 0;
	// Rate of the recorded sound file
	int sampleRate;
	// Recorder instance
	private Recorder mRecorder;

	// Output for Google answer
	TextView txtView;
	Button recordButton, stopButton, listenButton;

	// Handler used for sending request to Google API
	Handler handler = new Handler();

	// Recording callbacks
	private Handler mRecordingHandler = new Handler(new Handler.Callback() {
		public boolean handleMessage(Message m) {
			switch (m.what) {
			case FLACRecorder.MSG_AMPLITUDES:
				FLACRecorder.Amplitudes amp = (FLACRecorder.Amplitudes) m.obj;

				break;

			case FLACRecorder.MSG_OK:
				// Ignore
				break;

			case Recorder.MSG_END_OF_RECORDING:

				break;

			default:
				mRecorder.stop();
				mErrorCode = m.what;
				showDialog(DIALOG_RECORDING_ERROR);
				break;
			}

			return true;
		}
	});

	/**************************************************************************************************************
	 * Implementation
	 **/

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        mFilePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFilePath = mFilePath + "/edu.skku.VA/";
        File dir = new File(mFilePath);
        dir.mkdirs();

		txtView = (TextView) this.findViewById(R.id.txtView);
		recordButton = (Button) this.findViewById(R.id.record);
		stopButton = (Button) this.findViewById(R.id.stop);
		stopButton.setEnabled(false);
		listenButton = (Button) this.findViewById(R.id.listen);
		listenButton.setEnabled(false);

		mRecorder = new Recorder(this, mRecordingHandler);

	}

	/***************************************************************************************************************
	 * Method related to recording in FLAC file
	 */

	public void recordButton(View v) {

        setFileName();

		mRecorder.start(mFileName);

		txtView.setText("");
		recordButton.setEnabled(false);
		stopButton.setEnabled(true);
		Toast.makeText(getApplicationContext(), "Recording...",
				Toast.LENGTH_LONG).show();

	}

	/***************************************************************************************************************
	 * Method that stops recording
	 */

	public void stopRecording(View v) {

        mRFileName = mFileName;
        mFileName = "";
        mRShortFileName = mShortFileName;
        mShortFileName = "";
        Archive dbObject = new Archive(this.getApplicationContext());

        dbObject.Initialize(null, mRShortFileName, "", "", 0, System.currentTimeMillis() / 1000, 0, 0, mRFileName);
        dbObject.set();


		Toast.makeText(getApplicationContext(), "Loading...", Toast.LENGTH_LONG)
				.show();
		recordButton.setEnabled(true);
		listenButton.setEnabled(true);

		sampleRate = mRecorder.mFLACRecorder.getSampleRate();
		getTranscriptionV2(sampleRate);
		mRecorder.stop();

	}

	/***************************************************************************************************************
	 * Method that listens to recording
	 */
	public void listenRecord(View v) {
		Context context = this;

		FLACPlayer mFlacPlayer = new FLACPlayer(context, mFileName);
		mFlacPlayer.start();

	}

    /***************************************************************************************************************
     * Method that listens to recording
     */

    public void showKeywordList(View v) {
        Intent keywordListIntent = new Intent(this, KeywordListActivity.class);
        try
        {
            startActivityForResult(keywordListIntent, KEYWORD_LIST_UI);
        } catch (Exception e) {
            e.getMessage();
        }

    }

    /***************************************************************************************************************
     * Method that listens to recording
     */

    public void showArchiveList(View v) {
        Intent archiveListIntent = new Intent(this, FileListActivity.class);
        try
        {
            startActivityForResult(archiveListIntent, ARCHIVE_LIST_UI);
        } catch (Exception e) {
            e.getMessage();
        }
    }

	/**************************************************************************************************************
	 * Method related to Google Voice Recognition
	 **/

    public void getTranscriptionV2(int sampleRate) {

        final Context dbContext = this.getApplicationContext();

        String ctype = "audio/x-flac; rate=" + sampleRate;
        AsyncHttpClient client = new AsyncHttpClient();
        FileInputStream myfile = null;
        try {
            myfile = new FileInputStream(mFileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        client.addHeader("Content-Type", ctype);

        try {
            HttpHandler handler = new HttpHandler(){

                @Override
                public void onResponse(String result) {

                    AsyncHttpClient client = new AsyncHttpClient();
                    RequestParams req = new RequestParams();
                    req.put("query", result);
                    client.post("http://back.palett.net:4434/Tagger", req, new AsyncHttpResponseHandler() {

                        @Override
                        public void onStart() {
                            // called before request is started
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                            List<String> keywords = new ArrayList<String>();
                            String result = new String(response, 0, response.length);
                            String[] results = result.split(",");
                            for(int i = 0; i < results.length; i++)
                                keywords.add(results[i]);

                            Archive dbObject = new Archive(dbContext);
                            Keyword kdbObject = new Keyword(dbContext);
                            ArchiveKeywords akdbObject = new ArchiveKeywords(dbContext);

                            String archiveId = dbObject.findByFileName(mRShortFileName).getId();
                            for(int i = 0; i < keywords.size(); i++)
                            {
                                Keyword fetchResult = kdbObject.findByKeyword(keywords.get(i));
                                if(fetchResult == null)
                                {
                                    kdbObject.Initialize(0, keywords.get(i));
                                    kdbObject.set();
                                    fetchResult = kdbObject.findByKeyword(keywords.get(i));
                                }
                                akdbObject.Initialize(archiveId, fetchResult.getId(), 0);
                                akdbObject.set();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                            // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                        }

                        @Override
                        public void onRetry(int retryNo) {
                            // called when request is retried
                        }
                    });
                    Log.v("VA", result);
                }

            };
            handler.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setFileName() {

        Calendar c = Calendar.getInstance();
        mShortFileName = "" + c.get(Calendar.YEAR) + (c.get(Calendar.MONTH)+1) + c.get(Calendar.DATE) + c.get(Calendar.HOUR_OF_DAY) + c.get(Calendar.MINUTE) + c.get(Calendar.MILLISECOND);
        mFileName = mFilePath + mShortFileName + ".flac";
    }

}
