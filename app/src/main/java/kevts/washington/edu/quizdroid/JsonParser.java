package kevts.washington.edu.quizdroid;

import android.util.JsonReader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevin on 3/1/15.
 */
public class JsonParser {

    public static ArrayList<Topic> readJson(JsonReader reader) throws IOException {
        ArrayList<Topic> list = new ArrayList<Topic>();
        reader.beginArray();
        while (reader.hasNext()) {
            list.add(readTopic(reader));
        }
        reader.endArray();
        return list;
    }

    public static Topic readTopic(JsonReader reader) throws IOException {
        reader.beginObject();
        Topic topic = new Topic();
        topic.setTopic(reader.nextString()); // title
        topic.setShortDescription(reader.nextString()); // description
        reader.beginArray();
        while (reader.hasNext()) {
            readQuestion(reader);
        }
        reader.endArray();
        reader.endObject();
        return topic;
    }

    public static Quiz readQuestion(JsonReader reader) throws IOException {
        reader.beginObject();
        Quiz quiz = new Quiz();
        quiz.setQuestion(reader.nextString()); // question
        quiz.setCorrectAnswerIndex(reader.nextInt()); // answer index

        reader.beginArray();
        while (reader.hasNext()) {
            quiz.addAnswer(reader.nextString());
        }
        reader.endArray();
        reader.endObject();
        return quiz;
    }

    public static String readJson(FileInputStream fileInputStream) throws IOException {
        int size = fileInputStream.available();
        byte[] buffer = new byte[size];
        fileInputStream.read(buffer);
        fileInputStream.close();

        return new String(buffer, "UTF-8");
    }
}
