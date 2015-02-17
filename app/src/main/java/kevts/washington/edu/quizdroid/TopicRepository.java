package kevts.washington.edu.quizdroid;

/**
 * Created by kevin on 2/15/15.
 */
public interface TopicRepository {

    // Topic Region
    public Topic[] getTopics();

    public String getTopic();

    public void setTopic(String s);

    public String getShortDescription();

    public void setShortDescription(String s);

    public String getLongDescription();

    public void setLongDescription(String s);

    public Quiz[] getQuestions ();

    public void setQuestions(Quiz[] qs);

    // Quiz Region
    public String getQuestion();

    public void setQuestion(String s);

    public String[] getAnswers();

    public void addAnswer(String s);

    public String getCorrectAnswer();

    public void setCorrectAnswerIndex(int i);

    public int getCurrentQuestion();

    public void setCurrentQuestion(int i);

    public int getCurrentCorrect();

    public void setCurrentCorrect(int i);
}
