package edu.skku.monet.VoiceArchieving;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.filelist);
        Keyword k = new Keyword(this.getApplicationContext());
        List<Keyword> l = k.findBy(1, 1000);
        List<String> s = new ArrayList<String>();
        for(int i = 0; i < l.size(); i++) {
            s.add(l.get(i).getKeyword());
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
