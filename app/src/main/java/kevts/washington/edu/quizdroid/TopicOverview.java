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
        switch (topic) {
            case "math":
                TextView topicTitle = (TextView)findViewById(R.id.topicTitle);
                TextView briefDescription = (TextView)findViewById(R.id.briefDescription);
                TextView numOfQuestions = (TextView)findViewById(R.id.numOfQuestions);
                topicTitle.setText("Math");
                briefDescription.setText("This is a quiz on fundamental mathematical operations." +
                        " There will be questions testing addition, subtraction, multiplication, and division.");
                numOfQuestions.setText("4 questions total");
                break;
            case "physics":
                break;
            case "msh":
                break;
        }
        Button beginButton = (Button)findViewById(R.id.beginButton);
        beginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TopicOverview.this, QuestionPage.class);
                startActivityForResult(intent, 1);
            }
        });
    }

}
