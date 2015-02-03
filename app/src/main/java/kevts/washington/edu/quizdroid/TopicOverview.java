package kevts.washington.edu.quizdroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class TopicOverview extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_overview);
        Intent parentCall = getIntent();
        String topic = parentCall.getStringExtra("topic");
        if (topic.equals("math")) {
            TextView topicTitle = (TextView) findViewById(R.id.topicTitle);
            TextView briefDescription = (TextView) findViewById(R.id.briefDescription);
            TextView numOfQuestions = (TextView) findViewById(R.id.numOfQuestions);
            topicTitle.setText("Math");
            briefDescription.setText("This is a quiz on fundamental mathematical operations." +
                    " There will be questions testing addition, subtraction, multiplication, and division.");
            numOfQuestions.setText("4 questions total");
        } else if (topic.equals("physics")) {
                TextView topicTitle = (TextView)findViewById(R.id.topicTitle);
                TextView briefDescription = (TextView)findViewById(R.id.briefDescription);
                TextView numOfQuestions = (TextView)findViewById(R.id.numOfQuestions);
                topicTitle.setText("Physics");
                briefDescription.setText("This is a quiz on fundamental physics concepts." +
                        " There will be questions testing basic mechanics, (e.g. momentum, force, energy).");
                numOfQuestions.setText("4 questions total");
        } else if (topic.equals("msh")) {
            TextView topicTitle = (TextView)findViewById(R.id.topicTitle);
            TextView briefDescription = (TextView)findViewById(R.id.briefDescription);
            TextView numOfQuestions = (TextView)findViewById(R.id.numOfQuestions);
            topicTitle.setText("Marvel Super Heroes");
            briefDescription.setText("This is a quiz on Marvel Super Heroes." +
                    " There will be questions testing from comic books and the movies.");
            numOfQuestions.setText("4 questions total");
        } // add more topics here
        Button beginButton = (Button)findViewById(R.id.beginButton);
        beginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = TopicOverview.this.getIntent();
                intent.putExtra("questionNumber", 1);
                startActivityForResult(intent, 1);
            }
        });
    }

}
