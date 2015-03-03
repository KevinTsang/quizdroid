package kevts.washington.edu.quizdroid;


import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Application;
import android.app.DownloadManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.JsonReader;
import android.util.JsonWriter;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.*;
import java.util.ArrayList;

public class QuizApp extends Application implements TopicRepository {

    private static final String TAG = "QuizApp";
    private static QuizApp instance;
    private static ArrayList<Topic> topics = new ArrayList<Topic>();
    private int currentTopic = -1;
    private int currentQuestion = 0;
    private static final int ALARM_ID = 3333;
    private boolean activeAlarm;

    @Override
    public void onCreate() {
        super.onCreate();
        initialize();
        Log.i(TAG, "QuizApp is loaded and is currently running.");
    }

    public QuizApp() {

    }

    public void initialize() {
        if (instance == null) {
            instance = new QuizApp();
        } else {
            Log.e(TAG, "Multiple instances of QuizDroid detected.");
            throw new RuntimeException("There can only be one available instance of quizdroid,"
                    + " please close other instances before proceeding.");
        }
        readData();
        setAlarmOnStartUp();
    }

    public static boolean haveNetworkConnection(Context context) {
        boolean wifiOn = false;
        boolean mobileOn = false;
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    wifiOn = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    mobileOn = true;
        }
        return wifiOn || mobileOn;
    }

    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static boolean isAirplaneModeOn(Context context) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.System.getInt(context.getContentResolver(),
                    Settings.System.AIRPLANE_MODE_ON, 0) != 0;
        } else {
            return Settings.Global.getInt(context.getContentResolver(),
                    Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
        }

    }

    private void setAlarmOnStartUp() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String intervalString = sharedPreferences.getString(getString(R.string.interval_key),
                getString(R.string.default_interval));
        int interval = Integer.parseInt(intervalString);

        Intent downloadReceiverIntent = new Intent(getApplicationContext(), DownloadReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), ALARM_ID,
                downloadReceiverIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        activeAlarm = (PendingIntent.getBroadcast(getApplicationContext(), ALARM_ID,
                new Intent(this, DownloadReceiver.class), PendingIntent.FLAG_NO_CREATE) != null);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
                interval * 60000, pendingIntent);
    }

    public void readData() {
        try {
            FileInputStream fis = openFileInput("quizdata.json");
            JsonReader jsonReader = new JsonReader(new InputStreamReader(fis));
            ArrayList<Topic> topicsList = JsonParser.readJson(jsonReader);
            topics.addAll(topicsList);
            jsonReader.close();
            fis.close();
        } catch (IOException e) {
            Log.e(TAG, "Missing quiz file.");
            Topic mathTopic = initializeMathTopic();
            Topic physicsTopic = initializePhysicsTopic();
            Topic mshTopic = initializeMSHTopic();
            topics.add(mathTopic);
            topics.add(physicsTopic);
            topics.add(mshTopic);
        }
    }

    public boolean getAlarm() {
        return activeAlarm;
    }

    public static QuizApp getInstance() {
        return instance;
    }

    public void setCurrentTopic(int index) {
        currentTopic = index;
    }

    public ArrayList<Topic> getTopics() {
        return topics;
    }

    public String getTopic() {
        return topics.get(currentTopic).getTopic();
    }

    public void setTopic(String s) {
        topics.get(currentTopic).setTopic(s);
    }

    public String getShortDescription() {
        return topics.get(currentTopic).getShortDescription();
    }

    public void setShortDescription(String s) {
        topics.get(currentTopic).setShortDescription(s);
    }

    public String getLongDescription() {
        return topics.get(currentTopic).getLongDescription();
    }

    public void setLongDescription(String s) {
        topics.get(currentTopic).setLongDescription(s);
    }

    public ArrayList<Quiz> getQuestions() {
        return topics.get(currentTopic).getQuestions();
    }

    public void setQuestions(ArrayList<Quiz> qs) {
        topics.get(currentTopic).setQuestions(qs);
    }

    public String getQuestion() {
        return topics.get(currentTopic).getQuestions().get(currentQuestion).getQuestion();
    }

    public void setQuestion(String s) {
        topics.get(currentTopic).getQuestions().get(currentQuestion).setQuestion(s);
    }

    public String[] getAnswers() {
        return topics.get(currentTopic).getQuestions().get(currentQuestion).getAnswers();
    }

    public void addAnswer(String s) {
        topics.get(currentTopic).getQuestions().get(currentQuestion).addAnswer(s);
    }

    public String getCorrectAnswer() {
        return topics.get(currentTopic).getQuestions().get(currentQuestion).getCorrectAnswer();
    }

    public void setCorrectAnswerIndex(int i) {
        topics.get(currentTopic).getQuestions().get(currentQuestion).setCorrectAnswerIndex(i);
    }

    public int getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(int i) {
        currentQuestion = i;
    }

    public int getCurrentCorrect() {
        return topics.get(currentTopic).getCurrentCorrect();
    }

    public void setCurrentCorrect(int i) {
        topics.get(currentTopic).setCurrentCorrect(i);
    }

    public static ArrayList<Quiz> initializeMathQuestions() {
        ArrayList<Quiz> quizzes = new ArrayList<Quiz>();
        Quiz mathQuestion1 = new Quiz();
        mathQuestion1.setQuestion("2+7=?");
        mathQuestion1.setCorrectAnswerIndex(1);
        mathQuestion1.setAnswers(new String[] {"5", "9", "14", "20"});
        quizzes.add(mathQuestion1);

        Quiz mathQuestion2 = new Quiz();
        mathQuestion2.setQuestion("20-3=?");
        mathQuestion2.setCorrectAnswerIndex(0);
        mathQuestion2.setAnswers(new String[] {"17", "6", "12", "42"});
        quizzes.add(mathQuestion2);

        Quiz mathQuestion3 = new Quiz();
        mathQuestion3.setQuestion("3*5=?");
        mathQuestion3.setCorrectAnswerIndex(3);
        mathQuestion3.setAnswers(new String[] {"8", "16", "12", "15"});
        quizzes.add(mathQuestion3);

        Quiz mathQuestion4 = new Quiz();
        mathQuestion4.setQuestion("42/7=?");
        mathQuestion4.setCorrectAnswerIndex(2);
        mathQuestion4.setAnswers(new String[] {"5", "9", "6", "10"});
        quizzes.add(mathQuestion4);

        return quizzes;
    }

    public static ArrayList<Quiz> initializePhysicsQuestions() {
        ArrayList<Quiz> quizzes = new ArrayList<Quiz>();
        Quiz physicsQuestion1 = new Quiz();
        physicsQuestion1.setQuestion("What properties of an object are used to determine its momentum?");
        physicsQuestion1.setCorrectAnswerIndex(0);
        physicsQuestion1.setAnswers(new String[] {"mass and velocity", "mass and acceleration", "force and weight", "your mom and gravity"});
        quizzes.add(physicsQuestion1);

        Quiz physicsQuestion2 = new Quiz();
        physicsQuestion2.setQuestion("Angular velocity describes an object moving:");
        physicsQuestion2.setCorrectAnswerIndex(2);
        physicsQuestion2.setAnswers(new String[]{"in and out", "up and down", "in a circle", "like Schrodinger's cat"});
        quizzes.add(physicsQuestion2);

        Quiz physicsQuestion3 = new Quiz();
        physicsQuestion3.setQuestion("What is Newton's second law?");
        physicsQuestion3.setCorrectAnswerIndex(1);
        physicsQuestion3.setAnswers(new String[] {"what comes around goes around", "F=ma", "V=IR", "craziness is proportional to hotness"});
        quizzes.add(physicsQuestion3);

        Quiz physicsQuestion4 = new Quiz();
        physicsQuestion4.setQuestion("Impulse measures which of the following?");
        physicsQuestion4.setCorrectAnswerIndex(3);
        physicsQuestion4.setAnswers(new String[] {"what I do when I'm drunk", "work", "heartbeats", "change in momentum"});
        quizzes.add(physicsQuestion4);

        return quizzes;
    }

    public static ArrayList<Quiz> initializeMSHQuestions() {
        ArrayList<Quiz> quizzes = new ArrayList<Quiz>();
        Quiz mshQuestion1 = new Quiz();
        mshQuestion1.setQuestion("Which of the following is NOT part of X-Men?");
        mshQuestion1.setCorrectAnswerIndex(2);
        mshQuestion1.setAnswers(new String[] {"Storm", "Cyclops", "Batman", "Professor X"});
        quizzes.add(mshQuestion1);

        Quiz mshQuestion2 = new Quiz();
        mshQuestion2.setQuestion("In 'The Avengers' movie, who is the actor who plays Loki?");
        mshQuestion2.setCorrectAnswerIndex(3);
        mshQuestion2.setAnswers(new String[] {"Chris Hemsworth", "Robert Downey Jr.", "Chris Evans", "Tom Hiddleston"});
        quizzes.add(mshQuestion2);

        Quiz mshQuestion3 = new Quiz();
        mshQuestion3.setQuestion("What is the name of Thor's homeland?");
        mshQuestion3.setCorrectAnswerIndex(0);
        mshQuestion3.setAnswers(new String[] {"Asgard", "Earth", "Gotham", "Metropolis"});
        quizzes.add(mshQuestion3);

        Quiz mshQuestion4 = new Quiz();
        mshQuestion4.setQuestion("What mutant will appear in the 'Avengers: Age of Ultron' movie?");
        mshQuestion4.setCorrectAnswerIndex(1);
        mshQuestion4.setAnswers(new String[] {"Wolverine", "Quicksilver", "Gambit", "Magneto"});
        quizzes.add(mshQuestion4);

        return quizzes;
    }

    public static Topic initializeMathTopic() {
        Topic mathTopic = new Topic();
        mathTopic.setTopic("Math");
        mathTopic.setShortDescription("Fundamental mathematical operations");
        mathTopic.setLongDescription("This is a quiz on fundamental mathematical operations.\n" +
                        "There will be questions testing addition, subtraction, multiplication, and division.");
        mathTopic.setQuestions(initializeMathQuestions());
        return mathTopic;
    }

    public static Topic initializePhysicsTopic() {
        Topic physicsTopic = new Topic();
        physicsTopic.setTopic("Physics");
        physicsTopic.setShortDescription("Basic mechanics");
        physicsTopic.setLongDescription("This is a quiz on fundamental physics concepts.\n" +
                        "There will be questions testing basic mechanics, (e.g. momentum, force, energy).");
        physicsTopic.setQuestions(initializePhysicsQuestions());
        return physicsTopic;
    }

    public static Topic initializeMSHTopic() {
        Topic mshTopic = new Topic();
        mshTopic.setTopic("Marvel Super Heroes");
        mshTopic.setShortDescription("Questions on both comic books and movies");
        mshTopic.setLongDescription("This is a quiz on Marvel Super Heroes.\n" +
                        "There will be questions testing from both the comic books and the movies.");
        mshTopic.setQuestions(initializeMSHQuestions());
        return mshTopic;
    }
}
