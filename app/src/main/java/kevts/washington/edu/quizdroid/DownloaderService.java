package kevts.washington.edu.quizdroid;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by kevin on 2/24/15.
 */
public class DownloaderService extends IntentService {
    public DownloaderService() {
        super("DownloaderService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String url = intent.getStringExtra("URL");

        // Do the download here
    }
}
