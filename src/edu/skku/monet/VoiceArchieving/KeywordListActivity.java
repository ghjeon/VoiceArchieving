package edu.skku.monet.VoiceArchieving;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import edu.skku.monet.VoiceArchieving.Archive.ArchiveKeywords;
import edu.skku.monet.VoiceArchieving.Archive.Keyword;

import java.util.ArrayList;
import java.util.List;

/**
 * Project IntelliJ IDEA
 * Module edu.skku.monet.VoiceArchieving
 * User: Gyuhyeon
 * Date: 2014. 8. 25.
 * Time: 오후 4:33
 */
public class KeywordListActivity extends Activity {

    private Context thisContext = null;
    private String archiveId = null;
    public AdapterView.OnItemClickListener listViewListener;
    public List<String> items = new ArrayList<String>();
    private Keyword k;

    public KeywordListActivity() {

    }

    public KeywordListActivity(String archiveId) {
        this.archiveId = archiveId;
    }

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.filelist);
        this.thisContext = this.getApplicationContext();

        k = new Keyword(this.getApplicationContext());

        try {
           this.archiveId = this.getIntent().getStringExtra("archiveId");
        } catch(Exception e) {

        }

        if(this.archiveId == null) {

            List<Keyword> l = k.findBy(1, 1000);
            for (int i = 0; i < l.size(); i++) {
                items.add(l.get(i).getKeyword());
            }
        } else {
            ArchiveKeywords ak = new ArchiveKeywords(thisContext);
            List<ArchiveKeywords> archiveKeywords = ak.findByArchive(archiveId);
            for (int i = 0; i < archiveKeywords.size(); i++) {
                items.add(archiveKeywords.get(i).getKeywordName());
            }
        }

        listViewListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String keywordName = items.get(i);
                int id = (k.findByKeyword(keywordName)).getId();

                Intent archiveListIntent = new Intent(thisContext, FileListActivity.class);
                archiveListIntent.putExtra("keywordId", id);
                startActivity(archiveListIntent);
            }
        };
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, items);
        ListView listContent = (ListView) findViewById(R.id.fileList);
        listContent.setAdapter(adapter);
        listContent.setOnItemClickListener(listViewListener);
    }

    @Override
    public void onResume() {
        super.onResume();


    }



}
