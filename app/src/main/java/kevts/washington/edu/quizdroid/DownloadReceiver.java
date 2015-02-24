package kevts.washington.edu.quizdroid;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

/**
 * Created by kevin on 2/24/15.
 */
public class DownloadReceiver extends BroadcastReceiver {
    public DownloadReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String url = sharedPreferences.getString(context.getString(R.string.url_key), context.getString(R.string.default_url));

        Toast.makeText(context, "Getting an update from " + url, Toast.LENGTH_SHORT).show();

        Intent worker = new Intent(context, DownloaderService.class);
        worker.putExtra("URL", url);
        context.startService(worker);
    }
}
