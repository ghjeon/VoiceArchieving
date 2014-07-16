package edu.skku.monet.VoiceArchieving;

import java.util.*;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * Project IntelliJ IDEA
 * Module edu.skku.monet.VoiceArchieving
 * User: Gyuhyeon
 * Date: 2014. 7. 16.
 * Time: 오후 11:38
 */
public class TagControlActivity {
    /*

    public ID3TagController TagController = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.id3tagui);
        findViewById(R.id.btnSave).setOnClickListener(this);

        try
        {
            TagController = new ID3TagController(getIntent().getStringExtra("ID3TagController"));
            ((TextView)findViewById(R.id.txtTagCategory)).setText(this.TagController.getCategories().toString());
            ((TextView)findViewById(R.id.txtTagComposer)).setText(this.TagController.getSpeakers().toString());
            ((TextView)findViewById(R.id.txtTagDateTime)).setText(this.TagController.getDateTime().toString());
            ((TextView)findViewById(R.id.txtTagLength)).setText(this.TagController.getLength().toString());
            ((TextView)findViewById(R.id.txtTagLocation)).setText(this.TagController.getLocation());
            ((TextView)findViewById(R.id.txtTagTag)).setText(this.TagController.getTags().toString());
            ((TextView)findViewById(R.id.txtTagTitle)).setText(this.TagController.getTitle());
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
              this.TagController.setCategories(((TextView)findViewById(R.id.txtTagCategory)).getText().toString(), true);
              this.TagController.setSpeakers(((TextView)findViewById(R.id.txtTagComposer)).getText().toString(), true);
              //this.TagController.setDateTime(Integer.getInteger(((TextView)findViewById(R.id.txtTagDateTime)).getText().toString()));
              //this.TagController.setLength(Integer.getInteger(((TextView)findViewById(R.id.txtTagLength)).getText().toString()));
              //this.TagController.setLocation(((TextView)findViewById(R.id.txtTagLocation)).getText().toString());
              //this.TagController.setTags(((TextView)findViewById(R.id.txtTagTag)).getText().toString(), true);
              //this.TagController.setTitle(((TextView)findViewById(R.id.txtTagTitle)).getText().toString());
            } catch (Exception e) {
                e.getMessage();
            }
        }
    }
    */
}