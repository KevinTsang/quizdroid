package kevts.washington.edu.quizdroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.AdapterView;
import android.view.View;


public class TopicSelection extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_selection);
        ListView listView = (ListView)findViewById(R.id.Topics);
        String[] topics = new String[] {"Math", "Physics", "Marvel Super Heroes"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, topics);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TopicSelection.this, TopicOverview.class);
                switch (((TextView)view).getText().toString()) {
                    case "Math": intent.putExtra("topic", "math");
                        startActivityForResult(intent, 1);
                        break;
                    case "Physics": intent.putExtra("topic", "physics");
                        startActivityForResult(intent, 2);
                        break;
                    case "Marvel Super Heroes": intent.putExtra("topic", "msh");
                        startActivityForResult(intent, 3);
                        break;
                }
            }
        });
    }
}
