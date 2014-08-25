package edu.skku.monet.VoiceArchieving;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.os.Message;
import android.os.Handler;
import android.os.Bundle;
import android.os.Environment;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Context;
import android.util.Log;
import android.media.MediaRecorder;
import android.media.MediaPlayer;
import android.widget.TextView;
import android.widget.Toast;

import android.media.AudioFormat;

import java.io.*;
import java.util.Calendar;

import com.uraroji.garage.android.mp3recvoice.RecMicToMp3;

public class MainActivity extends Activity implements OnClickListener {

    private RecMicToMp3 mRecMicToMp3 = null;

    private final int FREQUENCY = 11025;
    private final int CUSTOM_FREQ_SOAP = 1;
    private final int OUT_FREQUENCY = FREQUENCY * CUSTOM_FREQ_SOAP;
    private final int CHANNEL_CONTIGURATION = AudioFormat.CHANNEL_IN_STEREO;
    private final int AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;

    private final int GOOGLE_STT = 1000, MY_UI=1001, ID3=1002;				//requestCode. 구글음성인식, 내가 만든 Activity
    private ArrayList<String> mResult;									//음성인식 결과 저장할 list
    private ArrayList<String> totalResult;
    String finalResult = "";
    private String mSelectedString;										//결과 list 중 사용자가 선택한 텍스트
    private TextView mResultTextView;									//최종 결과 출력하는 텍스트 뷰


    private static final String LOG_TAG = "AudioRecordTest";
    private static String mFileName = null;
    private static String mRFileName = null;

    private RecordButton mRecordButton = null;
    private MediaRecorder mRecorder = null;

    private PlayButton   mPlayButton = null;
    private MediaPlayer   mPlayer = null;



    private void onRecord(boolean start) {
        /* 녹음 버튼에 대한 onClick Event Process func */
        if (start) {
            startRecording();
        } else {
            stopRecording();
        }
    }

    private void onPlay(boolean start) {
        /* 재생 버튼에 대한 onClick Event Process func */
        if (start) {
            startPlaying();
        } else {
            stopPlaying();
        }
    }

    private void startPlaying() {
        /* 재생 기능이 활성화 되었을때 */
        mPlayer = new MediaPlayer(); // MediaPlayer 객체 생성
        try {
            mPlayer.setDataSource(mFileName); // 재생할 파일 할당
            mPlayer.prepare(); // 초기화
            mPlayer.start(); // 재생
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }

    private void stopPlaying() {
        /* 재생 기능이 비활성화 되었을때 */
        mPlayer.release(); // 할당 해제
        mPlayer = null; // Object Destroy
    }

    private void startRecording() {
        /* 녹음 기능이 활성화 되었을때 */
        /*
        mRecorder = new MediaRecorder();  // MediaRecord 객체 생성
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC); // 음성을 입력할 소스 선택.
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4); // 음성을 저장할 포맷 선택. DEFAULT = 3GP
        mRecorder.setOutputFile(mFileName); // Class 생성시에 Constructor 에서 지정한 mFileName 이용해 출력 파일 설정
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC); // 음성을 저장할 코텍 선택

        try {
            mRecorder.prepare(); // 레코더 초기화
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start(); // 레코딩 시작
        */
        mRecMicToMp3.start();
    }

    private void stopRecording() {
        /* 녹음 기능이 비활성화 되었을때 */
        /*
        mRecorder.stop(); //
        mRecorder.reset();
        mRecorder.release();
        mRecorder = null;
        */
        mRecMicToMp3.stop();
    }

    class RecordButton extends Button {
        /* RecordButton의 기능 정의. Button 으로부터 상속받아 구현됨 */

        boolean mStartRecording = true; // 현재 레코딩 상황을 보관함

        /* EventListener. Button Object에 대한 onClick Event, 즉 사용자가 버튼을 눌렀을 경우에 관한 Event를 통제하는 Listener. */
        OnClickListener clicker = new OnClickListener() {
            public void onClick(View v) {
                onRecord(mStartRecording); //상단에 정의한 녹음 기능 처리를 위한 부분
                if (mStartRecording) {
                    setText("Stop recording"); // 버튼의 텍스트 세팅
                } else {
                    setText("Start recording"); // 버튼의 텍스트 세팅
                }
                mStartRecording = !mStartRecording; // 현재 레코딩 상태를 변경함.
            }
        };

        public RecordButton(Context ctx) { // Record Button Constructor
            super(ctx);
            setText("Start recording");
            setOnClickListener(clicker);
        }
    }

    class PlayButton extends Button {
        /* PlayButton의 기능 정의. Button 으로부터 상속받아 구현됨 */

        boolean mStartPlaying = true; // 현재 레코딩 상황을 보관함

        /* EventListener. Button Object에 대한 onClick Event, 즉 사용자가 버튼을 눌렀을 경우에 관한 Event를 통제하는 Listener. */
        OnClickListener clicker = new OnClickListener() {
            public void onClick(View v) {
                onPlay(mStartPlaying); //상단에 정의한 재생 기능 처리를 위한 부분
                if (mStartPlaying) {
                    setText("Stop playing"); // 버튼의 텍스트 세팅
                } else {
                    setText("Start playingg"); // 버튼의 텍스트 세팅
                }
                mStartPlaying = !mStartPlaying; // 현재 재생 상태를 변경함.
            }
        };

        public PlayButton(Context ctx) {  // Play Button Constructor
            super(ctx);
            setText("Start playing");
            setOnClickListener(clicker);
        }
    }

    public MainActivity() {
        Calendar c = Calendar.getInstance();
        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath(); // 실행중인 디바이스의 External Storage 경로를 절대경로로 확보
        mFileName = mFileName + "edu.skku.VA/" + c.get(Calendar.YEAR) + c.get(Calendar.MONTH) + c.get(Calendar.DATE) + c.get(Calendar.HOUR_OF_DAY) + c.get(Calendar.MINUTE) + c.get(Calendar.MILLISECOND) + ".mp3";
        mRecMicToMp3 = new RecMicToMp3(
                mFileName, 8000);
        // 파일 이름 설정. External Storage Root 의 monet.VoiceArchieveing/{YEAR}{MONTH}{DATE}{HOUR}{MILLISECOND}.mp4 형식으로 저장함
    }

    @Override
    public void onCreate(Bundle icicle) {
        /* Activity Initialize Event 발생시 수행되는 이벤트. UI Layout 관련 처리를 주로 수행함 */
        super.onCreate(icicle);
        mRecordButton = new RecordButton(this);
        mPlayButton = new PlayButton(this);
        setContentView(R.layout.main);



        /*
        LinearLayout ll = new LinearLayout(this); // 레이아웃 종류 선택
        mRecordButton = new RecordButton(this);  // 레코드 버튼 객체 생성
        ll.addView(mRecordButton,
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        0)); // 앞서 선안한 레이아웃에 버튼 추가함
        mPlayButton = new PlayButton(this);  // 재생 버튼 객체 생성
        ll.addView(mPlayButton,
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        0)); // 앞서 선안한 레이아웃에 버튼 추가함
                        */
        findViewById(R.id.mPlayButton).setOnClickListener(this);
        findViewById(R.id.mRecordButton).setOnClickListener(this);
        findViewById(R.id.show).setOnClickListener(this);
        //findViewById(R.id.btnID3Activity).setOnClickListener(this);
        //findViewById(R.id.btnID3ReadActivity).setOnClickListener(this);
        //setContentView(ll); // 어플리케이션 화면에 레이아웃 출력

        mRecMicToMp3.setHandle(new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case RecMicToMp3.MSG_REC_STARTED:
                        break;
                    case RecMicToMp3.MSG_REC_STOPPED:
                        break;
                    case RecMicToMp3.MSG_ERROR_GET_MIN_BUFFERSIZE:
                        Toast.makeText(MainActivity.this,
                                "BUFFER_FAILED",
                                Toast.LENGTH_LONG).show();
                        break;
                    case RecMicToMp3.MSG_ERROR_CREATE_FILE:
                        Toast.makeText(MainActivity.this, "WRITE_NOT_ALLOWED",
                                Toast.LENGTH_LONG).show();
                        break;
                    case RecMicToMp3.MSG_ERROR_REC_START:
                        Toast.makeText(MainActivity.this, "REC_INIT_FAILED",
                                Toast.LENGTH_LONG).show();
                        break;
                    case RecMicToMp3.MSG_ERROR_AUDIO_RECORD:
                        Toast.makeText(MainActivity.this, "REC_WORKING_FAILED",
                                Toast.LENGTH_LONG).show();
                        break;
                    case RecMicToMp3.MSG_ERROR_AUDIO_ENCODE:
                        Toast.makeText(MainActivity.this, "AUDIO_ENCODE_FAILED",
                                Toast.LENGTH_LONG).show();
                        break;
                    case RecMicToMp3.MSG_ERROR_WRITE_FILE:

                        Toast.makeText(MainActivity.this, "WRITE_FILE_FAILED",
                                Toast.LENGTH_LONG).show();
                        break;
                    case RecMicToMp3.MSG_ERROR_CLOSE_FILE:
                        Toast.makeText(MainActivity.this, "FILE_CLOSE_FAILED",
                                Toast.LENGTH_LONG).show();
                        break;
                    default:
                        break;
                }
            }
        });


    }

    @Override
    public void onClick (View v) {
        int view = v.getId();
        if(view == R.id.show){
            startActivityForResult(new Intent(this, CustomUIActivity.class), MY_UI);			//내가 만든 activity 실행
        }
        else if(view == R.id.mPlayButton)
        {
            mPlayButton.callOnClick();
        }
        else if(view == R.id.mRecordButton)
        {
            mRecordButton.callOnClick();
        }
        /*
        else if(view == R.id.btnID3Activity)
        {
            Intent id3Intent = new Intent(this, TagControlActivity.class);
            try
            {
                id3Intent.putExtra("ID3TagController", mFileName);
                startActivityForResult(id3Intent, ID3);

            } catch (Exception e)
            {
                e.getMessage();
            }
        }
        else if(view == R.id.btnID3ReadActivity)
        {
            Intent id3Intent = new Intent(this, TagControlActivity.class);
            try
            {
                id3Intent.putExtra("ID3TagController", mRFileName);
                startActivityForResult(id3Intent, ID3);

            } catch (Exception e)
            {
                e.getMessage();
            }
        }
        */
    }

    @Override
    public void onPause() {
        //Activity가 Focus를 상실한 경우에 발생하는 이벤트 처리
        super.onPause();
        if (mRecorder != null) {
            mRecorder.release();
           mRecorder = null;
        }

        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        if( resultCode == RESULT_OK  && (requestCode == GOOGLE_STT || requestCode == MY_UI) ){		//결과가 있으면

            TextView resultview = (TextView)findViewById(R.id.resulttext);


            Log.d("Tag", "print result");
            String key = "";
            if(requestCode == GOOGLE_STT)					//구글음성인식이면
                key = RecognizerIntent.EXTRA_RESULTS;	//키값 설정
            else if(requestCode == MY_UI)					//내가 만든 activity 이면
                key = SpeechRecognizer.RESULTS_RECOGNITION;	//키값 설정

            mResult = data.getStringArrayListExtra(key);		//인식된 데이터 list 받아옴.
            String[] result = new String[mResult.size()];			//배열생성. 다이얼로그에서 출력하기 위해
            mResult.toArray(result);									//	list 배열로 변환

            Log.d("Tag", result[0]);
            //totalResult.add(result[0]);

            finalResult += result[0];
            finalResult += "\n";

            resultview.setText(result[0]);

            startActivityForResult(new Intent(this, CustomUIActivity.class), MY_UI);
            //showSelectDialog(requestCode, data);				//결과를 다이얼로그로 출력.
        }
        else{															//결과가 없으면 에러 메시지 출력
            String msg = null;

            TextView resultview = (TextView)findViewById(R.id.resulttext);
            //내가 만든 activity에서 넘어오는 오류 코드를 분류
            switch(resultCode){
                case SpeechRecognizer.ERROR_AUDIO:
                    msg = "오디오 입력 중 오류가 발생했습니다.";
                    break;
                case SpeechRecognizer.ERROR_CLIENT:
                    msg = "단말에서 오류가 발생했습니다.";
                    break;
                case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                    msg = "권한이 없습니다.";
                    break;
                case SpeechRecognizer.ERROR_NETWORK:
                case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                    msg = "네트워크 오류가 발생했습니다.";
                    break;
                case SpeechRecognizer.ERROR_NO_MATCH:
                    msg = "일치하는 항목이 없습니다.";
                    Log.i("tag","last");




                    /*
                    for(int i = 0; totalResult.get(i)!=null ; i++) {
                        finalResult += totalResult.get(i);
                    }
*/
                    resultview.setText(finalResult);
                    break;
                case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                    msg = "음성인식 서비스가 과부하 되었습니다.";
                    break;
                case SpeechRecognizer.ERROR_SERVER:
                    msg = "서버에서 오류가 발생했습니다.";
                    break;
                case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                    msg = "입력이 없습니다.";


                    resultview.setText(finalResult);
                    break;
            }

            if(msg != null)		//오류 메시지가 null이 아니면 메시지 출력
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        }
    }

    //결과 list 출력하는 다이얼로그 생성
    private void showSelectDialog(int requestCode, Intent data){
        String key = "";
        if(requestCode == GOOGLE_STT)					//구글음성인식이면
            key = RecognizerIntent.EXTRA_RESULTS;	//키값 설정
        else if(requestCode == MY_UI)					//내가 만든 activity 이면
            key = SpeechRecognizer.RESULTS_RECOGNITION;	//키값 설정

        mResult = data.getStringArrayListExtra(key);		//인식된 데이터 list 받아옴.
        String[] result = new String[mResult.size()];			//배열생성. 다이얼로그에서 출력하기 위해
        mResult.toArray(result);									//	list 배열로 변환

        //1개 선택하는 다이얼로그 생성
        AlertDialog ad = new AlertDialog.Builder(this).setTitle("선택하세요.")
                .setSingleChoiceItems(result, -1, new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which) {
                        mSelectedString = mResult.get(which);		//선택하면 해당 글자 저장
                    }
                })
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which) {
                        mResultTextView.setText("인식결과 : "+mSelectedString);		//확인 버튼 누르면 결과 출력
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which) {
                        mResultTextView.setText("");		//취소버튼 누르면 초기화
                        mSelectedString = null;
                    }
                }).create();
        ad.show();
    }
}
