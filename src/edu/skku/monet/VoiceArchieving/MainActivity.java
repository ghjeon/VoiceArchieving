package edu.skku.monet.VoiceArchieving;

import android.app.Activity;
import android.widget.LinearLayout;
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

import java.util.Calendar;
import java.io.IOException;

public class MainActivity extends Activity {
    private static final String LOG_TAG = "AudioRecordTest";
    private static String mFileName = null;

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
        mRecorder = new MediaRecorder();  // MediaRecord 객체 생성
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC); // 음성을 입력할 소스 선택.
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP); // 음성을 저장할 포맷 선택. DEFAULT = 3GP
        mRecorder.setOutputFile(mFileName); // Class 생성시에 Constructor 에서 지정한 mFileName 이용해 출력 파일 설정
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB); // 음성을 저장할 코텍 선택

        try {
            mRecorder.prepare(); // 레코더 초기화
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start(); // 레코딩 시작
    }

    private void stopRecording() {
        /* 녹음 기능이 비활성화 되었을때 */
        mRecorder.stop(); //
        mRecorder.release();
        mRecorder = null;
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
                    setText("Start playing"); // 버튼의 텍스트 세팅
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
        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath(); // 실행중인 디바이스의 External Storage 경로를 절대경로로 확보
        mFileName += getPackageName() +  "/" + Calendar.YEAR + Calendar.MONTH + Calendar.DATE + Calendar.HOUR + Calendar.SECOND + ".3GP";
        // 파일 이름 설정. External Storage Root 의 monet.VoiceArchieveing/{YEAR}{MONTH}{DATE}{HOUR}{SECOND}.mp4 형식으로 저장함
    }

    @Override
    public void onCreate(Bundle icicle) {
        /* Activity Initialize Event 발생시 수행되는 이벤트. UI Layout 관련 처리를 주로 수행함 */
        super.onCreate(icicle);

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
        setContentView(ll); // 어플리케이션 화면에 레이아웃 출력
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
}
