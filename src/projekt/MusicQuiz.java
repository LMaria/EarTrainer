package projekt;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class MusicQuiz extends Application {

    Label questionText = new Label();

    Label answer = new Label();

    RadioButton choice1 = new RadioButton();

    RadioButton choice2 = new RadioButton();

    RadioButton choice3 = new RadioButton();

    RadioButton[] answerButtons = {choice1, choice2, choice3};

    ToggleGroup answers = new ToggleGroup();

    Button next = new Button("Järgmine");

    Button play = new Button("Play");

    Button vasta = new Button("Vasta");

    Button again = new Button("Mängi uuesti");

    RadioButton kasutajaVastus;


    TilePane buttons = new TilePane();

    AudioClip music;

    Pane pane;
    public int max = 2;
    Stage stage;
    GiveQuestion quiz = new GiveQuestion();
    VBox vBox = new VBox();
    ChoiceBox<String> choiceBox;
    Scene scene;


    public static void main(String[] args) {

        launch(args);
    }

    @Override

    public void start(Stage primaryStage) throws Exception {
        showWindow(primaryStage);

    }

    public void showWindow(Stage primaryStage) {

        Image pilt = new Image("projekt/music.jpg");
        ImageView showImage = new ImageView(pilt);

        showImage.setTranslateY(110);
        showImage.setTranslateX(250);


        stage = new Stage();
        pane = new Pane();
        pane.setStyle("-fx-background-color: #FFFFFF;");
        Scene scene = new Scene(pane, 800, 500);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        choiceBox = new ChoiceBox<>();
        choiceBox.getItems().add("Valik1");
        choiceBox.getItems().add("Valik2");
        choiceBox.setValue("Helistikud");
        Button button = new Button("Vali");
        button.setOnAction(event -> getChoice(choiceBox));
        choiceBox.setMinWidth(200);
        vBox = new VBox(40);
        vBox.getChildren().addAll(choiceBox, button);
        vBox.setPrefSize(300, 200);
        vBox.setTranslateX(200);
        vBox.setTranslateY(230);

        pane.getChildren().addAll(showImage, vBox);
    }

    public void getChoice(ChoiceBox<String> choiceBox){

        quiz.testiValik = choiceBox.getValue();
        startQuiz();
    }




    public void startQuiz(){
        pane.getChildren().remove(vBox);
        quiz.createQuestions();


        questionText.setFont(new Font("Cambria", 26));
        questionText.setTranslateY(230);
        questionText.setTranslateX(230);
        questionText.setText(quiz.getQuestionText());

        HBox vastusteAla = new HBox();
        vastusteAla.setTranslateY(330);
        vastusteAla.setTranslateX(260);
        vastusteAla.setSpacing(20);


        setButtonText(quiz.currentQuestionNumber);

        choice1.setToggleGroup(answers);
        choice2.setToggleGroup(answers);
        choice3.setToggleGroup(answers);

        vasta.setText("Vasta");
        vasta.setMaxWidth(Double.MAX_VALUE);
        vasta.setOnAction(e -> kysimuseVastus());

        vasta.setTranslateX(550);
        vasta.setTranslateY(330);


        answer.setTranslateY(280);
        answer.setTranslateX(360);
        answer.setFont(new Font("Cambria", 16));
        vastusteAla.getChildren().addAll(choice1, choice2, choice3);




        play.setTranslateY(185);
        play.setTranslateX(370);
        play.setStyle("-fx-background-color: #00CCFF;");

        music = new AudioClip(getClass().getResource(quiz.getQurrentMusicTrack()).toString());
        play.setOnAction((event) -> {

            music.play();

        });


        next.setOnAction(e -> nextQuestion());
        next.setMaxWidth(Double.MAX_VALUE);


        again.setOnAction((event) -> {
            music.play();
        });

        TilePane nupud = new TilePane();

        nupud.setTranslateY(400);
        nupud.setTranslateX(280);
        nupud.setHgap(5);


        nupud.getChildren().addAll(again, next);

        vasta.setTranslateX(550);
        vasta.setTranslateY(330);




        pane.getChildren().addAll(questionText, vastusteAla, play, nupud, answer, vasta);

    }

    public void setButtonText(int n) {
        for (int i = 0; i < answerButtons.length; i++) {
            answerButtons[i].setText(quiz.getAnswerOptions().get(i));

        }
    }


    public void kysimuseVastus() {

        if ((!(choice1.isSelected()) && (!(choice2.isSelected())) && (!(choice3.isSelected())))) {
            Stage stage1 = new Stage();

            stage1.initModality(Modality.APPLICATION_MODAL);
            stage1.setTitle("Error");
            stage1.setMinHeight(170);
            stage1.setMinWidth(300);

            Label label1 = new Label();
            label1.setText("Vasta küsimusele");
            Button oKButton = new Button("OK");
            oKButton.setOnAction(event -> {
                stage1.close();
            });
            VBox vBox = new VBox(30);
            vBox.setAlignment(Pos.CENTER);
            vBox.getChildren().addAll(label1, oKButton);


            Scene scene = new Scene(vBox);
            stage1.setScene(scene);
            stage1.showAndWait();


        } else {

            kasutajaVastus = (RadioButton) answers.getSelectedToggle();// objekt  radio button'ks
            quiz.userAnswered = kasutajaVastus.getText();
            quiz.evaluateAnswer();
            answer.setText(quiz.answerFeedback() + String.valueOf(quiz.correctAnswerNum));
            answers.selectToggle(null);
            choice1.setDisable(true);
            choice2.setDisable(true);
            choice3.setDisable(true);

        }

}
    public void nextQuestion() {

        if (quiz.currentQuestionNumber != max) {

            quiz.currentQuestionNumber = quiz.currentQuestionNumber + 1;
            questionText.setText(quiz.getQuestionText());
            setButtonText(quiz.currentQuestionNumber);
            music = new AudioClip(getClass().getResource(quiz.getQurrentMusicTrack()).toString());
            answers.selectToggle(null);
            answer.setText(" ");
            choice1.setDisable(false);
            choice2.setDisable(false);
            choice3.setDisable(false);


        } else {
            endQuiz();
        }


    }


    public void endQuiz(){

       pane.setVisible(false);
        Stage stage2 = new Stage();

        stage2.initModality(Modality.APPLICATION_MODAL);
        stage2.setTitle("Test on läbi!");
        stage2.setMinWidth(500);
        stage2.setMinHeight(200);

        Label label2 = new Label();


            label2.setText("Õigeid vastuseid oli " + String.valueOf(quiz.correctAnswerNum));

        Button closeButton = new Button("Lõpeta test");
        closeButton.setOnAction(event -> {
            stage2.close();
            stage.close();
        });

        Button againButton = new Button ("Tee test uuesti");
        againButton.setOnAction(event -> {
            quiz.currentQuestionNumber = -1;
            quiz.correctAnswerNum = 0;
            nextQuestion();
            pane.setVisible(true);
            stage2.close();

        });


        VBox vBox2 = new VBox(30);
        vBox2.setAlignment(Pos.CENTER);
        vBox2.getChildren().addAll(label2, closeButton, againButton);

        Scene scene = new Scene(vBox2);
        stage2.setScene(scene);
        stage2.showAndWait();
    }

    }






