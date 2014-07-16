package edu.skku.monet.VoiceArchieving;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SpeechToTextActivity extends Activity implements OnClickListener {
	private final int GOOGLE_STT = 1000, MY_UI=1001;				//requestCode. 구글음성인식, 내가 만든 Activity
	private ArrayList<String> mResult;									//음성인식 결과 저장할 list
	private String mSelectedString;										//결과 list 중 사용자가 선택한 텍스트
	private TextView mResultTextView;									//최종 결과 출력하는 텍스트 뷰
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_ui);

		findViewById(R.id.show).setOnClickListener(this);				//내가 만든 activity 이용.
		
		mResultTextView = (TextView)findViewById(R.id.result);		//결과 출력 뷰
	}

	@Override
	public void onClick(View v) {
		int view = v.getId();

		if(view == R.id.show){
			startActivityForResult(new Intent(this, CustomUIActivity.class), MY_UI);			//내가 만든 activity 실행
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		if( resultCode == RESULT_OK  && (requestCode == GOOGLE_STT || requestCode == MY_UI) ){		//결과가 있으면
			showSelectDialog(requestCode, data);				//결과를 다이얼로그로 출력.
		}
		else{															//결과가 없으면 에러 메시지 출력
			String msg = null;
			
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
					break;
				case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
					msg = "음성인식 서비스가 과부하 되었습니다.";
					break;
				case SpeechRecognizer.ERROR_SERVER:
					msg = "서버에서 오류가 발생했습니다.";
					break;
				case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
					msg = "입력이 없습니다.";
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