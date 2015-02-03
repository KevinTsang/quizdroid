package kevts.washington.edu.quizdroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


public class QuestionPage extends Activity {

    private int nextQuestionIndex;

    private int selectedAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_page);
        Intent parentCall = getIntent();
        String topic = parentCall.getStringExtra("topic");
        int currentQuestion = parentCall.getIntExtra("questionNumber", 0);
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
                intent.putExtra("selectedAnswer", selectedAnswer);
                intent.putExtra("currentQuestion", nextQuestionIndex);
            }
        });


        final RadioGroup choices = (RadioGroup)findViewById(R.id.choices);
        choices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit.setVisibility(View.VISIBLE);
                selectedAnswer = choices.getCheckedRadioButtonId();
            }
        });
    }

    public void populate(QuestionAnswer[] array, int i) {
        TextView question = (TextView)findViewById(R.id.question);
        question.setText(array[i].getQuestion());
        for (String s : array[i].getAnswers()) {
            RadioGroup choices = (RadioGroup)findViewById(R.id.choices);
            RadioButton choice = new RadioButton(this);
            choice.setText(s);
            choice.setTag("radioButtons");
            choices.addView(choice, i);
        }
    }

}
