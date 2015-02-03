package kevts.washington.edu.quizdroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class AnswerSummary extends Activity {

    private static int currentCorrect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_summary);
        Intent parentCall = getIntent();
        currentCorrect = parentCall.getIntExtra("answersCorrect", 0);
        int userAnswer = parentCall.getIntExtra("selectedAnswer", 0);
        int previousQuestion = parentCall.getIntExtra("previousQuestion", 0);
        String topic = parentCall.getStringExtra("topic");
        int nextQuestion = parentCall.getIntExtra("currentQuestion", 0);
        switch (topic) {
            case "math":
                QuestionAnswer[] mathQuestions = QuizInit.initializeMath();
                displayQuestionResult(mathQuestions, previousQuestion, userAnswer);
                break;
            case "physics":
                QuestionAnswer[] physicsQuestions = QuizInit.initializePhysics();
                displayQuestionResult(physicsQuestions, previousQuestion, userAnswer);
                break;
            case "msh":
                QuestionAnswer[] mshQuestions = QuizInit.initializeMSH();
                displayQuestionResult(mshQuestions, previousQuestion, userAnswer);
                break;
        }

        final Button next = (Button)findViewById(R.id.next);
        if (nextQuestion == -1) {
            next.setText("Finish");
        } else {
            next.setText("Next");
        }
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent parentCall = getIntent();
                int nextQuestion = parentCall.getIntExtra("currentQuestion", 0);
                if (next.getText().equals("Next")) {
                    Intent intent = new Intent(AnswerSummary.this, QuestionPage.class);
                    intent.putExtra("topic", parentCall.getStringExtra("topic"));
                    intent.putExtra("questionNumber", nextQuestion);
                    intent.putExtra("answersCorrect", currentCorrect);
                    startActivityForResult(intent, 1);
                    finish();
                } else {
                    currentCorrect = 0;
                    Intent intent = new Intent(AnswerSummary.this, TopicSelection.class);
                    startActivityForResult(intent, 1);
                    finish();
                }

            }
        });
    }

    public void displayQuestionResult(QuestionAnswer[] questions, int previousQuestion, int userAnswer) {
        TextView previousAnswer = (TextView)findViewById(R.id.previousAnswer);
        previousAnswer.setText(questions[previousQuestion].getAnswers()[userAnswer]);
        TextView correctAnswer = (TextView)findViewById(R.id.correctAnswer);
        int correctAnswerIndex = questions[previousQuestion].getCorrectAnswerIndex();
        correctAnswer.setText(questions[previousQuestion].getAnswers()[correctAnswerIndex]);
        TextView correctAnswers = (TextView)findViewById(R.id.correctAnswers);
        if (previousAnswer.getText().equals(correctAnswer.getText())) {
            currentCorrect++;
        }
        correctAnswers.setText(currentCorrect + "");
        TextView totalAnswers = (TextView)findViewById(R.id.totalAnswers);
        totalAnswers.setText(questions.length + "");
    }
}
