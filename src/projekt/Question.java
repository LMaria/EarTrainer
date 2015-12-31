package projekt;

import java.util.ArrayList;
import java.util.List;

public class Question {

    private String question;

    private String correctAnswer;

    private List<String> answers;

    private String musicTrack;


    public Question(String question, List<String> answers, String correctAnswer, String musicTrack) {
        this.question = question;
        this.answers = new ArrayList<> (answers);
        this.correctAnswer = correctAnswer;
        this.musicTrack = musicTrack;

    }

    public String getQuestion() {

        return question;
    }


    public List<String> getAnswers() {
        
        return answers;
    }

    public String getCorrectAnswer() {

        return correctAnswer;
    }

    public String getMusicTrack() {

        return musicTrack;
    }

}