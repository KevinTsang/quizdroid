package kevts.washington.edu.quizdroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.AdapterView;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;


public class TopicSelection extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_selection);
        ListView listView = (ListView)findViewById(R.id.Topics);
        final QuizApp instance = (QuizApp)getApplication();
        ArrayList<Topic> topicArray = instance.getTopics();
        CustomArrayAdapter adapter = new CustomArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, topicArray);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TopicSelection.this, FragManager.class);
                switch (((TextView)((LinearLayout)view).getChildAt(1)).getText().toString()) {
                    case "Math- Fundamental mathematical operations":
                        instance.setCurrentTopic(0);
                        break;
                    case "Physics- Basic mechanics":
                        instance.setCurrentTopic(1);
                        break;
                    case "Marvel Super Heroes- Questions on both comic books and movies":
                        instance.setCurrentTopic(2);
                        break;
                }
                startActivityForResult(intent, 1);
            }
        });
    }
}
