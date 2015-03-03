package kevts.washington.edu.quizdroid;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by kevin on 2/15/15.
 */
public class Topic implements Serializable {

    private String topic;
    private String shortDescription;
    private String longDescription;
    private ArrayList<Quiz> questions;
    private int currentCorrect;

    public Topic() {
        questions = new ArrayList<Quiz>();
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String s) {
        topic = s;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String s) {
        shortDescription = s;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String s) {
        longDescription = s;
    }

    public ArrayList<Quiz> getQuestions () {
        return questions;
    }

    public void setQuestions(ArrayList<Quiz> qs) {
        questions.addAll(qs);
    }

    public int getCurrentCorrect() {
        return currentCorrect;
    }

    public void setCurrentCorrect(int i) {
        currentCorrect = i;
    }

    @Override
    public String toString() {
        return topic + "- " + shortDescription;
    }
}
