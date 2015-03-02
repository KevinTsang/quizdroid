package kevts.washington.edu.quizdroid;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.AdapterView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;


public class TopicSelection extends ActionBarActivity {

    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
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
        if (!instance.haveNetworkConnection(getApplicationContext())) {
            if (instance.isAirplaneModeOn(getApplicationContext())) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                builder.setTitle("No Internet Connectivity");
                builder.setMessage("Would you like to turn off airplane mode?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                            Settings.System.putInt(getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0);
                        } else {
                            Settings.Global.putInt(getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, 0);
                        }
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
            } else {
                Toast.makeText(getApplicationContext(), "No signal, please try again later.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_preferences, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, PreferencesActivity.class);
            startActivityForResult(intent, 0);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
