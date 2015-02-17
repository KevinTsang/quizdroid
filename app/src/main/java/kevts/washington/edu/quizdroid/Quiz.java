package kevts.washington.edu.quizdroid;

import java.io.Serializable;
import java.util.ArrayList;

public class Quiz implements Serializable {

    private String question;
    private ArrayList<String> answers;
    private int correctAnswer;

    public Quiz() {
        answers = new ArrayList<String>();
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String s) {
        question = s;
    }

    public String[] getAnswers() {
        String[] array = new String[answers.size()];
        array = answers.toArray(array);
        return array;
    }

    public void addAnswer(String s) {
        answers.add(s);
    }

    public void setAnswers(String[] a) {
        for (int i = 0; i < a.length; i++) {
            answers.add(a[i]);
        }
    }

    public String getCorrectAnswer() {
        return answers.get(correctAnswer);
    }

    public void setCorrectAnswerIndex(int i) {
        correctAnswer = i;
    }
}
