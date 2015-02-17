package kevts.washington.edu.quizdroid;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by kevin on 2/17/15.
 */
public class CustomArrayAdapter extends ArrayAdapter<Topic> {

    private List<Topic> topicList;

    public CustomArrayAdapter(Context context, int resource, int textViewResourceId, List<Topic> topics) {
        super(context, resource, textViewResourceId, topics);
        topicList = topics;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout ll = new LinearLayout(getContext());
        ll.setOrientation(LinearLayout.HORIZONTAL);
        ImageView icon = new ImageView(getContext());
        icon.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_launcher));
        TextView topic = new TextView(getContext());
        topic.setText(topicList.get(position).getTopic() + "- " + topicList.get(position).getShortDescription());
        ll.addView(icon);
        ll.addView(topic);
        return ll;
    }
}
