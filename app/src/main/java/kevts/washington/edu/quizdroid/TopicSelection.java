package kevts.washington.edu.quizdroid;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.AdapterView;
import android.view.View;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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
        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        registerReceiver(this.broadcastReceiver, filter);
        ArrayList<Topic> topicArray = instance.getTopics();
        CustomArrayAdapter adapter = new CustomArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, topicArray);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TopicSelection.this, FragManager.class);
                instance.setCurrentTopic(position);
                startActivityForResult(intent, 1);
            }
        });
        if (!instance.haveNetworkConnection(getApplicationContext())) {
            if (instance.isAirplaneModeOn(getApplicationContext())) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("No Internet Connectivity");
                builder.setMessage("Would you like to turn off airplane mode?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent settingsIntent = new Intent(Settings.ACTION_SETTINGS);
                        settingsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivityForResult(settingsIntent, 0);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog airplaneDialog = builder.create();
                airplaneDialog.show();
            } else {
                Toast.makeText(getApplicationContext(), "No signal, please try again later.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction())) {
                DownloadManager downloadManager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
                long downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
                if (downloadId != 0) {
                    DownloadManager.Query query = new DownloadManager.Query();
                    query.setFilterById(downloadId);
                    Cursor c = downloadManager.query(query);
                    if (c.moveToFirst()) {
                        int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
                        switch (status) {
                            case DownloadManager.STATUS_PAUSED:
                            case DownloadManager.STATUS_PENDING:
                            case DownloadManager.STATUS_RUNNING:
                                break;
                            case DownloadManager.STATUS_SUCCESSFUL:
                                try {
                                    ParcelFileDescriptor file = downloadManager.openDownloadedFile(downloadId);
                                    FileInputStream fis = new FileInputStream(file.getFileDescriptor());
                                    String json = JsonParser.readJson(fis);
                                    FileOutputStream fos = openFileOutput("quizdata.json", MODE_PRIVATE);
                                    fos.write(json.getBytes());
                                    fos.close();
                                } catch (IOException e) {
                                    Log.e("TopicSelection", "Invalid download location.");
                                    e.printStackTrace();
                                }
                                break;
                            case DownloadManager.STATUS_FAILED:
                                Log.e("TopicSelection", "Download failed.");
                                AlertDialog.Builder builder = new AlertDialog.Builder(TopicSelection.this);
                                builder.setTitle("Download failed");
                                builder.setMessage("Would you like to try downloading again now or quit and try again later?");
                                builder.setPositiveButton("Now", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                builder.setNegativeButton("Later", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        TopicSelection.this.finish();
                                    }
                                });
                                builder.create().show();
                                break;
                        }
                    }
                }
            }
        }
    };

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(this.broadcastReceiver);
    }
}
