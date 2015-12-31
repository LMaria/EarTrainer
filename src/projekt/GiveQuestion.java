package projekt;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class GiveQuestion {

    public String testiValik;
    public int currentQuestionNumber = 0;

    public ArrayList<Question> listOfQuestions = new ArrayList<>();

    public String userAnswered;

    public int correctAnswerNum;


public void createQuestions(){

    FileReader fileReader;

        try
        {
            if(testiValik.equals("Valik1")){
                fileReader = new FileReader("testikysimused.txt");
            }else{
                fileReader = new FileReader ("testikysimused2.txt");
            }

            BufferedReader reader = new BufferedReader(fileReader);


           

        String fileLine = reader.readLine();

        while (fileLine != null)
        {

            String[] separate = fileLine.split(",");

            String questionText = separate[0];

            List <String> possibleAnswers = Arrays.asList(separate[1], separate[2], separate[3]);

            String correctAnswerText = separate[4];

            String trackAsText = separate [5];

            Question questionRead = new Question(questionText, possibleAnswers, correctAnswerText, trackAsText);

            listOfQuestions.add(questionRead);

            fileLine = reader.readLine();
        }


        reader.close();
    }

    // handle exceptions
    catch (FileNotFoundException notfound)
    {
        System.out.println("faili ei leitud");
    }

    catch (IOException e)
    {
        e.printStackTrace();
    }

}

    //tagastab ühe Küsimuse objekti

    public Question getCurrentQuestion(int n) {

        return listOfQuestions.get(currentQuestionNumber);

    }


    public String getQuestionText() {

        return getCurrentQuestion(currentQuestionNumber).getQuestion();
    }


    public List<String> getAnswerOptions() {

                return getCurrentQuestion(currentQuestionNumber).getAnswers();

        }


    public String getQurrentMusicTrack() {

        return getCurrentQuestion(currentQuestionNumber).getMusicTrack();

    }

    public String answerFeedback() {

        if (userAnswered.equals(getCurrentQuestion(currentQuestionNumber).getCorrectAnswer())) {

            return "Õige vastus!";
        } else {
            return "Vale vastus";
        }

    }


    public String evaluateAnswer(){

        if (userAnswered.equals(getCurrentQuestion(currentQuestionNumber).getCorrectAnswer())) {
            correctAnswerNum++;
        }

        return String.valueOf(correctAnswerNum);
    }

}

