package kevts.washington.edu.quizdroid;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class PreferencesActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        final SharedPreferences sharedPreferences = this.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String defaultURL = getString(R.string.default_url);
        String defaultInterval = getString(R.string.default_interval);

        String url = sharedPreferences.getString(getString(R.string.url_key), defaultURL);
        final String interval = sharedPreferences.getString(getString(R.string.interval_key), defaultInterval);

        final EditText urlTextBox = (EditText)findViewById(R.id.questionURL);
        final EditText intervalTextBox = (EditText)findViewById(R.id.interval);

        urlTextBox.setText(url);
        intervalTextBox.setText(interval);

        final Button savePreferencesButton = (Button)findViewById(R.id.preferenceButton);
        savePreferencesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String updatedURL = urlTextBox.getText().toString();
                    String updatedInterval = intervalTextBox.getText().toString();
                    if (Integer.parseInt(updatedInterval) > 0) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(getString(R.string.url_key), updatedURL);
                        editor.putString(getString(R.string.interval_key), updatedInterval);
                        editor.commit();
                    } else {
                        Toast.makeText(PreferencesActivity.this,
                                "Please input a number above 0 for an interval.", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(PreferencesActivity.this,
                            "Please input a number above 0 for an interval.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
