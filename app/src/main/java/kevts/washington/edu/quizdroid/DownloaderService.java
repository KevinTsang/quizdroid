package kevts.washington.edu.quizdroid;

import android.app.DownloadManager;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;

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

        DownloadManager downloadManager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setMimeType("json");
        downloadManager.enqueue(request);
    }
}
