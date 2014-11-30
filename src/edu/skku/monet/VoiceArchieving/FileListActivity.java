package edu.skku.monet.VoiceArchieving;

import android.app.Activity;
import android.app.ListActivity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
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

    private Context thisContext = null;
    public AdapterView.OnItemClickListener listViewListener;
    public AdapterView.OnItemLongClickListener listViewLongListener;
    public List<String> items = new ArrayList<String>();
    private Archive a;
    private int keywordId = -1;

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.filelist);

        this.thisContext = this.getApplicationContext();

        a = new Archive(this.getApplicationContext());

        try {
            this.keywordId = this.getIntent().getIntExtra("keywordId", -1);
        } catch (Exception e) {

        }

        if(this.keywordId == -1) {
            List<Archive> l = a.findBy(1, 1000);
            for(int i = 0; i < l.size(); i++) {
                items.add(l.get(i).getTitle());
            }
        } else {
            ArchiveKeywords ak = new ArchiveKeywords(thisContext);
            List<ArchiveKeywords> archiveKeywords = ak.findByKeyword(this.keywordId);
            for (int i = 0; i < archiveKeywords.size(); i++) {
                items.add(archiveKeywords.get(i).getArchiveName());
            }
        }

        listViewLongListener = new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                String fileName = items.get(i);
                String id = (a.findByFileName(fileName)).getId();

                Intent keywordListIntent = new Intent(thisContext, KeywordListActivity.class);
                keywordListIntent.putExtra("archiveId", id);
                startActivity(keywordListIntent);
                return true;
            }
        };

        listViewListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FLACPlayer fp = new FLACPlayer(thisContext, MainActivity.mFilePath + "/" + items.get(i) + ".flac");
                fp.start();
            }
        };
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, items);
        ListView listContent = (ListView)findViewById(R.id.fileList);
        listContent.setAdapter(adapter);
        listContent.setOnItemClickListener(listViewListener);
        listContent.setOnItemLongClickListener(listViewLongListener);
    }

    @Override
    public void onResume() {
        super.onResume();


    }



}
