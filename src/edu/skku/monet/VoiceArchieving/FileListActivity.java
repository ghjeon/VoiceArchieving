package edu.skku.monet.VoiceArchieving;

import android.app.Activity;
import android.app.ListActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import edu.skku.monet.VoiceArchieving.Archive.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Project IntelliJ IDEA
 * Module edu.skku.monet.VoiceArchieving
 * User: Gyuhyeon
 * Date: 2014. 8. 25.
 * Time: 오후 4:33
 */
public class FileListActivity extends Activity {

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.filelist);
        Archive a = new Archive(this.getApplicationContext());
        List<Archive> l = a.findBy(1, 1000);
        List<String> s = new ArrayList<String>();
        for(int i = 0; i < l.size(); i++) {
            s.add(l.get(i).getTitle());
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, s);
        ListView listContent = (ListView)findViewById(R.id.fileList);
        listContent.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();


    }



}
