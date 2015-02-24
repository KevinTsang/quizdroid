package kevts.washington.edu.quizdroid;


import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


public class FragManager extends ActionBarActivity {

    protected static QuizApp app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_manager);
        app = (QuizApp)getApplication();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new OverviewFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_preferences, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, PreferencesActivity.class);
            startActivityForResult(intent, 0);
            return true;
        }

        return super.onOptionsItemSelected(item);
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
            TextView topicTitle = (TextView) rootView.findViewById(R.id.topicTitle);
            TextView briefDescription = (TextView) rootView.findViewById(R.id.briefDescription);
            TextView numOfQuestions = (TextView) rootView.findViewById(R.id.numOfQuestions);
            topicTitle.setText(app.getTopic());
            briefDescription.setText(app.getLongDescription());
            numOfQuestions.setText(app.getQuestions().length + " questions total");

            Button beginButton = (Button) rootView.findViewById(R.id.beginButton);
            beginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    app.setCurrentQuestion(0);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.setCustomAnimations(R.animator.slide_out_left, R.animator.slide_in_right);
                    ft.replace(R.id.container, new QuestionFragment());
                    ft.addToBackStack("Overview");
                    ft.commit();
                }
            });
            return rootView;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class QuestionFragment extends Fragment implements OnClickListener {

        private View rootView;
        private int selectedAnswer;

        public QuestionFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.activity_question_page, container, false);
            TextView question = (TextView) rootView.findViewById(R.id.question);
            question.setText(app.getQuestion());
            for (int i = 0; i < app.getAnswers().length; i++) {
                RadioGroup choices = (RadioGroup) rootView.findViewById(R.id.choices);
                RadioButton choice = new RadioButton(getActivity());
                choice.setText(app.getAnswers()[i]);
                choice.setId(i);
                choice.setOnClickListener(this);
                choices.addView(choice, i);
            }

            final Button submit = (Button) rootView.findViewById(R.id.submit);
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RadioGroup choices = (RadioGroup)rootView.findViewById(R.id.choices);
                    selectedAnswer = choices.getCheckedRadioButtonId();
                    Bundle questionToSummary = new Bundle();
                    questionToSummary.putInt("selectedAnswer", selectedAnswer);
                    SummaryFragment sf = new SummaryFragment();
                    sf.setArguments(questionToSummary);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.setCustomAnimations(R.animator.slide_out_left, R.animator.slide_in_right);
                    ft.replace(R.id.container, sf);
                    ft.addToBackStack("Question");
                    ft.commit();
                }
            });
            return rootView;
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

        private View rootView;

        public SummaryFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.activity_answer_summary, container, false);
            int selectedAnswerIndex = getArguments().getInt("selectedAnswer", 0);
            String userAnswer = app.getQuestions()[app.getCurrentQuestion()].getAnswers()[selectedAnswerIndex];

            TextView previousAnswer = (TextView) rootView.findViewById(R.id.previousAnswer);
            previousAnswer.setText(userAnswer);
            TextView correctAnswer = (TextView) rootView.findViewById(R.id.correctAnswer);
            correctAnswer.setText(app.getCorrectAnswer());
            TextView correctAnswers = (TextView) rootView.findViewById(R.id.correctAnswers);
            if (previousAnswer.getText().equals(correctAnswer.getText())) {
                app.setCurrentCorrect(app.getCurrentCorrect() + 1);
            }
            correctAnswers.setText(app.getCurrentCorrect() + "");
            TextView totalAnswers = (TextView) rootView.findViewById(R.id.totalAnswers);
            totalAnswers.setText(app.getQuestions().length + "");

            final Button next = (Button) rootView.findViewById(R.id.next);
            if (app.getCurrentQuestion() + 1 < app.getQuestions().length) {
                next.setText("Next");
            } else {
                next.setText("Finish");
            }
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (app.getCurrentQuestion() + 1 < app.getQuestions().length) {
                        app.setCurrentQuestion(app.getCurrentQuestion() + 1);
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.setCustomAnimations(R.animator.slide_out_left, R.animator.slide_in_right);
                        ft.replace(R.id.container, new QuestionFragment());
                        ft.addToBackStack("Question");
                        ft.commit();
                    } else {
                        app.setCurrentCorrect(0);
                        Intent intent = new Intent(getActivity(), TopicSelection.class);
                        startActivityForResult(intent, 1);
                        getActivity().finish();
                        app.setCurrentTopic(-1);
                    }

                }
            });
            return rootView;
        }
    }
}
