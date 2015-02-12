package kevts.washington.edu.quizdroid;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


public class FragmentManager extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_manager);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new OverviewFragment())
                    .commit();
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class OverviewFragment extends Fragment {

        public OverviewFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.activity_topic_overview, container, false);
            Intent parentCall = getActivity().getIntent();
            String topic = parentCall.getStringExtra("topic");
            TextView topicTitle = (TextView) rootView.findViewById(R.id.topicTitle);
            TextView briefDescription = (TextView) rootView.findViewById(R.id.briefDescription);
            TextView numOfQuestions = (TextView) rootView.findViewById(R.id.numOfQuestions);
            if (topic.equals("math")) {
                topicTitle.setText("Math");
                briefDescription.setText("This is a quiz on fundamental mathematical operations." +
                        " There will be questions testing addition, subtraction, multiplication, and division.");
                numOfQuestions.setText(getString(R.string.totalQuestions));
            } else if (topic.equals("physics")) {
                topicTitle.setText("Physics");
                briefDescription.setText("This is a quiz on fundamental physics concepts." +
                        " There will be questions testing basic mechanics, (e.g. momentum, force, energy).");
                numOfQuestions.setText(getString(R.string.totalQuestions));
            } else if (topic.equals("msh")) {
                topicTitle.setText("Marvel Super Heroes");
                briefDescription.setText("This is a quiz on Marvel Super Heroes." +
                        " There will be questions testing from comic books and the movies.");
                numOfQuestions.setText(getString(R.string.totalQuestions));
            } // add more topics here
            Button beginButton = (Button) rootView.findViewById(R.id.beginButton);
            beginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*Intent intent = new Intent(TopicOverview.this, QuestionPage.class);
                    Intent oldIntent = TopicOverview.this.getIntent();
                    String topic = oldIntent.getStringExtra("topic");
                    intent.putExtra("topic", topic);
                    intent.putExtra("questionNumber", 0);
                    startActivityForResult(intent, 1);
                    finish();*/
                }
            });
            return rootView;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class QuestionFragment extends Fragment implements OnClickListener {

        private static int nextQuestionIndex;
        private static int selectedAnswer;
        private View rootView;

        public QuestionFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.activity_question_page, container, false);
            Intent parentCall = getActivity().getIntent();
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

            final Button submit = (Button) rootView.findViewById(R.id.submit);
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*Intent intent = new Intent(QuestionPage.this, AnswerSummary.class);
                    int answersCorrect = rootView.getIntent().getIntExtra("answersCorrect", -1);
                    if (answersCorrect != -1) {
                        intent.putExtra("answersCorrect", answersCorrect);
                    }
                    intent.putExtra("selectedAnswer", selectedAnswer);
                    intent.putExtra("previousQuestion", currentQuestion);
                    intent.putExtra("currentQuestion", nextQuestionIndex);
                    String topic = rootView.getIntent().getStringExtra("topic");
                    intent.putExtra("topic", topic);
                    startActivityForResult(intent, 1);*/
                }
            });
            return rootView;
        }

        public void populate(QuestionAnswer[] array, int i) {
            TextView question = (TextView) rootView.findViewById(R.id.question);
            question.setText(array[i].getQuestion());
            for (int x = 0; x < array.length; x++) {
                RadioGroup choices = (RadioGroup) rootView.findViewById(R.id.choices);
                RadioButton choice = new RadioButton(getActivity());
                choice.setText(array[i].getAnswers()[x]);
                choice.setId(x);
                choice.setOnClickListener(this);
                choices.addView(choice, x);
            }
        }

        @Override
        public void onClick(View v) {
            Button submit = (Button)rootView.findViewById(R.id.submit);
            submit.setVisibility(View.VISIBLE);
            RadioGroup choices = (RadioGroup) rootView.findViewById(R.id.choices);
            selectedAnswer = choices.getCheckedRadioButtonId();
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class SummaryFragment extends Fragment {

        private int currentCorrect = 0;
        private View rootView;

        public SummaryFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.activity_answer_summary, container, false);
            Intent parentCall = getActivity().getIntent();
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

            final Button next = (Button) rootView.findViewById(R.id.next);
            if (nextQuestion == -1) {
                next.setText("Finish");
            } else {
                next.setText("Next");
            }
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*Intent parentCall = rootView.getIntent();
                    int nextQuestion = parentCall.getIntExtra("currentQuestion", 0);
                    if (next.getText().equals("Next")) {
                        Intent intent = new Intent(AnswerSummary.this, QuestionPage.class);
                        intent.putExtra("topic", parentCall.getStringExtra("topic"));
                        intent.putExtra("questionNumber", nextQuestion);
                        intent.putExtra("answersCorrect", currentCorrect);
                        startActivityForResult(intent, 1);
                        //finish();
                    } else {
                        currentCorrect = 0;
                        Intent intent = new Intent(AnswerSummary.this, TopicSelection.class);
                        startActivityForResult(intent, 1);
                        //finish();
                    }*/

                }
            });
            return rootView;
        }

        public void displayQuestionResult(QuestionAnswer[] questions, int previousQuestion, int userAnswer) {
            TextView previousAnswer = (TextView) rootView.findViewById(R.id.previousAnswer);
            previousAnswer.setText(questions[previousQuestion].getAnswers()[userAnswer]);
            TextView correctAnswer = (TextView) rootView.findViewById(R.id.correctAnswer);
            int correctAnswerIndex = questions[previousQuestion].getCorrectAnswerIndex();
            correctAnswer.setText(questions[previousQuestion].getAnswers()[correctAnswerIndex]);
            TextView correctAnswers = (TextView) rootView.findViewById(R.id.correctAnswers);
            if (previousAnswer.getText().equals(correctAnswer.getText())) {
                currentCorrect++;
            }
            correctAnswers.setText(currentCorrect + "");
            TextView totalAnswers = (TextView) rootView.findViewById(R.id.totalAnswers);
            totalAnswers.setText(questions.length + "");
        }
    }
}
