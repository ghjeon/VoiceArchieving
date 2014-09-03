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

import edu.skku.monet.VoiceArchieving.Archive.*;

/**
 * Project IntelliJ IDEA
 * Module edu.skku.monet.VoiceArchieving
 * User: Gyuhyeon
 * Date: 2014. 7. 16.
 * Time: 오후 11:38
 ///
 */
public class TagControlActivity extends Activity implements OnClickListener {

    Archive archive = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.id3tagui);
        findViewById(R.id.btnSave).setOnClickListener(this);

        try
        {
            Archive dbController = new Archive(this.getApplicationContext());
            archive = dbController.findByFileName(getIntent().getStringExtra("fileName"));
            ((TextView)findViewById(R.id.txtTagCategory)).setText("");
            ((TextView)findViewById(R.id.txtTagComposer)).setText("");
            ((TextView)findViewById(R.id.txtTagDateTime)).setText(Long.toString(this.archive.getDatetime()));
            ((TextView)findViewById(R.id.txtTagLength)).setText(Long.toString(this.archive.getLength()));
            ((TextView)findViewById(R.id.txtTagLocation)).setText(this.archive.getLocation());
            ((TextView)findViewById(R.id.txtTagTag)).setText("");
            ((TextView)findViewById(R.id.txtTagTitle)).setText(this.archive.getTitle());
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Override
    public void onClick(View v){
        int view = v.getId();

        if(view == R.id.btnSave) {
            try
            {
              this.archive.getConnection(this.getApplicationContext());
              this.archive.Initialize(this.archive.getId(), ((TextView)findViewById(R.id.txtTagTitle)).getText().toString(), this.archive.getComment(), this.archive.getLocation(), this.archive.getKeywordCount(), this.archive.getDatetime(), this.archive.getLength(), this.archive.getPopularity(), this.archive.getFileName());
              this.archive.update();
            } catch (Exception e) {
                e.getMessage();
            }
        }
    }
}