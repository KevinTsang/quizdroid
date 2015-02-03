package kevts.washington.edu.quizdroid;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


public class QuestionPage extends Activity implements OnClickListener {

    private int currentQuestion;
    private int nextQuestionIndex;
    private int selectedAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_page);
        Intent parentCall = getIntent();
        String topic = parentCall.getStringExtra("topic");
        final int currentQuestion = parentCall.getIntExtra("questionNumber", 0);
        switch (topic) {
            case "math":
                QuestionAnswer[] mathQuestions = QuizInit.initializeMath();
                switch (currentQuestion) {
                    case 0:
                        populate(mathQuestions, 0);
                        nextQuestionIndex = 1;
                        break;
                    case 1:
                        populate(mathQuestions, 1);
                        nextQuestionIndex = 2;
                        break;
                    case 2:
                        populate(mathQuestions, 2);
                        nextQuestionIndex = 3;
                        break;
                    case 3:
                        populate(mathQuestions, 3);
                        nextQuestionIndex = -1;
                        break;
                }
                break;
            case "physics":
                QuestionAnswer[] physicsQuestions = QuizInit.initializePhysics();
                switch (currentQuestion) {
                    case 0:
                        populate(physicsQuestions, 0);
                        nextQuestionIndex = 1;
                        break;
                    case 1:
                        populate(physicsQuestions, 1);
                        nextQuestionIndex = 2;
                        break;
                    case 2:
                        populate(physicsQuestions, 2);
                        nextQuestionIndex = 3;
                        break;
                    case 3:
                        populate(physicsQuestions, 3);
                        nextQuestionIndex = -1;
                        break;
                }
                break;
            case "msh":
                QuestionAnswer[] mshQuestions = QuizInit.initializeMSH();
                switch (currentQuestion) {
                    case 0:
                        populate(mshQuestions, 0);
                        nextQuestionIndex = 1;
                        break;
                    case 1:
                        populate(mshQuestions, 1);
                        nextQuestionIndex = 2;
                        break;
                    case 2:
                        populate(mshQuestions, 2);
                        nextQuestionIndex = 3;
                        break;
                    case 3:
                        populate(mshQuestions, 3);
                        nextQuestionIndex = -1;
                        break;
                }
                break;
        }

        final Button submit = (Button)findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuestionPage.this, AnswerSummary.class);
                int answersCorrect = getIntent().getIntExtra("answersCorrect", -1);
                if (answersCorrect != -1) {
                    intent.putExtra("answersCorrect", answersCorrect);
                }
                intent.putExtra("selectedAnswer", selectedAnswer);
                intent.putExtra("previousQuestion", currentQuestion);
                intent.putExtra("currentQuestion", nextQuestionIndex);
                String topic = getIntent().getStringExtra("topic");
                intent.putExtra("topic", topic);
                startActivityForResult(intent, 1);
            }
        });
    }

    public void populate(QuestionAnswer[] array, int i) {
        currentQuestion = i;
        TextView question = (TextView)findViewById(R.id.question);
        question.setText(array[i].getQuestion());
        for (int x = 0; x < array.length; x++) {
            RadioGroup choices = (RadioGroup)findViewById(R.id.choices);
            RadioButton choice = new RadioButton(this);
            choice.setText(array[i].getAnswers()[x]);
            choice.setId(x);
            choice.setOnClickListener(this);
            choices.addView(choice, x);
        }
    }

    @Override
    public void onClick(View v) {
        Button submit = (Button)findViewById(R.id.submit);
        submit.setVisibility(View.VISIBLE);
        RadioGroup choices = (RadioGroup)findViewById(R.id.choices);
        selectedAnswer = choices.getCheckedRadioButtonId();
    }

}
