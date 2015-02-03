package kevts.washington.edu.quizdroid;

import java.util.ArrayList;

public class QuestionAnswer {

    private String question;
    private ArrayList<String> answers;
    private int correctAnswer;

    public QuestionAnswer(String q, String[] a) {
        question = q;
        answers = new ArrayList<String>();
        for (String s : a) {
            answers.add(s);
        }
        correctAnswer = 0;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getAnswers() {
        String[] array = new String[answers.size()];
        array = answers.toArray(array);
        return array;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswer;
    }

    public void addAnswer(String s) {
        answers.add(s);
    }

    public void setCorrectAnswer(int i) {
        correctAnswer = i;
    }
}
