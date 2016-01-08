package projekt;

import javafx.application.Application;
import javafx.stage.Stage;

public class StartQuiz extends Application {

    MusicQuiz musicQuiz;

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    //Käivitatakse muusikatest
    public void start(Stage primaryStage) throws Exception {

        musicQuiz = new MusicQuiz();
        musicQuiz.showWindow();


    }
}


