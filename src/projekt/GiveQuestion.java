package projekt;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class GiveQuestion {

    public String testChoice;

    public int currentQuestionNumber = 0;

    public ArrayList<Question> listOfQuestions = new ArrayList<>();

    public String userAnswered;

    public int correctAnswerNum;


    /*Luuakse Question objektid vastavalt Question klassi konstruktorile, objektid pannakse ArrayListi.
    Fail valitakse vastavalt kasutaja tehtud eelistusele.
    */
    public void createQuestions() {

        FileReader fileReader;

        try {
            if (testChoice.equals("Helilaadid")) {
                fileReader = new FileReader("questions.txt");
            } else {
                fileReader = new FileReader("questions2.txt");
            }

            BufferedReader reader = new BufferedReader(fileReader);


            String fileLine = reader.readLine();

            while (fileLine != null) {

                String[] separate = fileLine.split(",");

                String questionText = separate[0];

                List<String> possibleAnswers = Arrays.asList(separate[1], separate[2], separate[3]);

                String correctAnswerText = separate[4];

                String trackAsText = separate[5];

                Question questionRead = new Question(questionText, possibleAnswers, correctAnswerText, trackAsText);

                listOfQuestions.add(questionRead);

                fileLine = reader.readLine();
            }


            reader.close();
        }


        catch (FileNotFoundException notfound) {
            System.out.println("faili ei leitud");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //Tagastab käesoleva Question objekti ArrayListist vastavalt käesoleva küsimuse numbrile.

    public Question getCurrentQuestion(int n) {

        return listOfQuestions.get(currentQuestionNumber);

    }

    //Tagastab käesoleva küsimuse teksti.
    public String getQuestionText() {

        return getCurrentQuestion(currentQuestionNumber).getQuestion();
    }

    //Tagastab käesoleva küsimuse vastusevariandid, mille vahel testi sooritaja saab valida.
    public List<String> getAnswerOptions() {

        return getCurrentQuestion(currentQuestionNumber).getAnswers();

    }

    //Tagastab käesoleva küsimuse juurde kuuluva mp3.
    public String getQurrentMusicTrack() {

        return "lib/" + getCurrentQuestion(currentQuestionNumber).getMusicTrack();

    }

    //Annab kasuatajale tagasisidet, kas tema valitud vastus oli õige või vale.
    public String answerFeedback() {

        if (userAnswered.equals(getCurrentQuestion(currentQuestionNumber).getCorrectAnswer())) {

            return "Õige vastus!";
        } else {
            return "Vale vastus";
        }

    }

    //Õigete vastuste arv loetakse kokku.
    public String evaluateAnswer() {

        if (userAnswered.equals(getCurrentQuestion(currentQuestionNumber).getCorrectAnswer())) {
            correctAnswerNum++;
        }

        return String.valueOf(correctAnswerNum);
    }

}

