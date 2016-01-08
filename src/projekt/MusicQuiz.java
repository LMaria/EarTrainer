

package projekt;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class MusicQuiz {

    Label questionText = new Label();

    Label answer = new Label();

    RadioButton choice1 = new RadioButton();

    RadioButton choice2 = new RadioButton();

    RadioButton choice3 = new RadioButton();

    RadioButton[] answerButtons = {choice1, choice2, choice3};

    ToggleGroup answers = new ToggleGroup();

    TilePane buttons = new TilePane();

    Button next = new Button("JÄRGMINE");

    Button play = new Button("PLAY");

    Button giveAnswer = new Button("VASTA");

    Button again = new Button("MÄNGI UUESTI");

    RadioButton usersChoice;

    AudioClip music;

    Pane innerPane = new Pane();

    Stage stage;

    VBox selectQuiz = new VBox();

    ChoiceBox<String> quizSelection;

    //Maksimaalne testiküsimuste arv, alustades lugemist nullist.
    public int max = 9;

    GiveQuestion quiz = new GiveQuestion();

    //Luuakse kasutajaliides ning kasutaja saab valida kahe erinevat tüüpi testi vahel
    public void showWindow() {

        Image pilt = new Image("projekt/lib/music.jpg");
        ImageView showImage = new ImageView(pilt);
        showImage.setTranslateY(20);
        showImage.setTranslateX(170);

        stage = new Stage();
        Pane pane = new Pane();
        pane.setPrefSize(800, 500);

        BackgroundImage background = new BackgroundImage(new Image("projekt/lib/gradient.jpg", 800, 500, false, true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        pane.setBackground(new Background(background));


        innerPane = new Pane();
        innerPane.setPrefSize(600, 370);
        innerPane.setLayoutY(90);
        innerPane.setLayoutX(100);
        innerPane.setStyle("-fx-background-color: #FFFFFF;");

        pane.getChildren().add(innerPane);

        Scene scene = new Scene(pane, 800, 500);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        quizSelection = new ChoiceBox<>();
        quizSelection.getItems().add("Helilaadid");
        quizSelection.getItems().add("Intervallid");
        quizSelection.setValue("Helilaadid");
        Button select = new Button("Vali");
        select.setOnAction(event -> getChoice(quizSelection));
        quizSelection.setMinWidth(200);
        selectQuiz = new VBox(40);
        selectQuiz.getChildren().addAll(quizSelection, select);
        selectQuiz.setPrefSize(300, 200);
        selectQuiz.setTranslateX(190);
        selectQuiz.setTranslateY(150);

        innerPane.getChildren().addAll(showImage, selectQuiz);

    }

    //Kasutaja tehtud valik saadetakse GiveQuestion klassi ning selle põhjal valitakse, millist liiki küsimused laadida.
    public void getChoice(ChoiceBox<String> quizSelection) {

        quiz.testChoice = quizSelection.getValue();
        startQuiz();
    }

    //Luuakse testi keskkond koos küsimuse, vastusevariantide ning muusikaga.
    public void startQuiz() {

        innerPane.getChildren().remove(selectQuiz);
        quiz.createQuestions();
        questionText.setFont(new Font("Cambria", 26));
        questionText.setTranslateY(170);
        questionText.setTranslateX(190);
        questionText.setText(quiz.getQuestionText());

        HBox answerOptions = new HBox();
        answerOptions.setTranslateY(260);
        answerOptions.setTranslateX(150);
        answerOptions.setSpacing(20);
        setButtonText(quiz.currentQuestionNumber);

        choice1.setToggleGroup(answers);
        choice2.setToggleGroup(answers);
        choice3.setToggleGroup(answers);

        answerOptions.getChildren().addAll(choice1, choice2, choice3);

        giveAnswer.setTranslateX(230);
        giveAnswer.setTranslateY(330);
        giveAnswer.setMaxWidth(Double.MAX_VALUE);

        giveAnswer.setOnAction(e -> userAnswer());
        giveAnswer.setStyle("-fx-background-color: #b3e6ff;");
        giveAnswer.setTranslateX(500);
        giveAnswer.setTranslateY(170);

        answer.setTranslateX(240);
        answer.setTranslateY(220);
        answer.setFont(new Font("Cambria", 16));

        play.setTranslateY(90);
        play.setTranslateX(300);
        play.setStyle("-fx-background-color: #00CCFF;");

        music = new AudioClip(getClass().getResource(quiz.getQurrentMusicTrack()).toString());
        play.setOnAction((event) -> {
            music.play();

        });

        //Kasutaja saab minna järgmise küsimuse juurde.
        next.setOnAction(e -> nextQuestion());
        next.setMaxWidth(Double.MAX_VALUE);
        next.setStyle("-fx-background-color: #b3e6ff;");

        //Kasutaja saab küsimuse juurde kuuluvat muusikaklippi uuesti kuulata
        again.setStyle("-fx-background-color: #b3e6ff;");
        again.setOnAction((event) -> {
            music.play();
        });


        buttons.setTranslateY(300);
        buttons.setTranslateX(190);
        buttons.setHgap(5);

        buttons.getChildren().addAll(again, next);

        innerPane.getChildren().addAll(questionText, play, answerOptions, buttons, answer, giveAnswer);

    }

    //Pannakse paika valikvastused
    public void setButtonText(int n) {
        for (int i = 0; i < answerButtons.length; i++) {
            answerButtons[i].setText(quiz.getAnswerOptions().get(i));

        }
    }

    /*Meetod käivitatakse, kui kasutaja on vajutanud nuppu "Vasta". Kui kõik vastusevariandid on tühjaks jäetud,
    ilmub aken, milles tuletatakse kasutajale meelde, et ta ühe neist valiks. Juhul, kui kasutaja on oma valiku teinud,
    antakse talle teada, kas vastus oli õige või vale.
     */

    public void userAnswer() {

        if ((!(choice1.isSelected()) && (!(choice2.isSelected())) && (!(choice3.isSelected())))) {
            Stage stage1 = new Stage();
            stage1.initModality(Modality.APPLICATION_MODAL);
            stage1.setTitle("Viga");
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

            usersChoice = (RadioButton) answers.getSelectedToggle();
            quiz.userAnswered = usersChoice.getText();
            quiz.evaluateAnswer();
            answer.setText(quiz.answerFeedback());
            answers.selectToggle(null);
            choice1.setDisable(true);
            choice2.setDisable(true);
            choice3.setDisable(true);

        }

    }

    /*Meetod käivitatakse, kui kasutaja soovib liikuda järgmise küsimuse juurde. Kui on veel küsimusi alles,
     siis vastavalt küsimuseindeksile esitatakse kasutajale järgmine küsimus koos selle juurde kuuluvate
     vastusevariantide ning muusikaga. Kui küsimused on otsas, liigutakse edasi meetodi juurde, mis lõpetab testi.
     */

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

    /*Meetod käivitub, kui küsimused on otsas. Kasutajale antakse teada, et test on läbi ning tema antud õigete vastuste
     arv. Ta saab valida, kas soovib testi lõpetada, mille korral test suletakse, või testi uuesti teha, sel juhul
     algab test otsast peale ning kasutajale antakse uuesti valida kahe erinevat tüüpi muusikatesti vahel.
     */

    public void endQuiz() {

        innerPane.setVisible(false);

        Stage stage2 = new Stage();
        stage2.initModality(Modality.APPLICATION_MODAL);
        stage2.setTitle("Test on läbi!");
        stage2.setMinWidth(500);
        stage2.setMinHeight(200);
        Label correctAnswers = new Label();
        correctAnswers.setText("Õigeid vastuseid oli " + String.valueOf(quiz.correctAnswerNum));
        Button closeButton = new Button("Lõpeta test");
        closeButton.setOnAction(event -> {
            stage2.close();
            stage.close();

        });

        Button againButton = new Button("Tee test uuesti");
        againButton.setOnAction(event -> {

            innerPane.setVisible(true);
            stage2.close();
            stage.close();
            StartQuiz newQuiz = new StartQuiz();
            newQuiz.musicQuiz = new MusicQuiz();
            newQuiz.musicQuiz.showWindow();

        });


        VBox vBox2 = new VBox(30);
        vBox2.setAlignment(Pos.CENTER);
        vBox2.getChildren().addAll(correctAnswers, closeButton, againButton);

        Scene scene = new Scene(vBox2);
        stage2.setScene(scene);
        stage2.showAndWait();

    }

}





