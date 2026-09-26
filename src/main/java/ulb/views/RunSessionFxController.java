package views;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

/**
 * This class permits to display a session of exercises on real time with a timer, to put pauses between exercises
 * and pass to the next exercise after the pause.
 */
public class RunSessionFxController implements Initializable, Colorable {
    @FXML
    private AnchorPane parent;
    @FXML
    private Label titleExo;
    @FXML
    private ImageView imageExo;
    @FXML
    private TextArea descriptionExo;
    @FXML
    private Button finishButton;
    @FXML
    private Button previousButton;
    @FXML
    private Button pauseButton;
    @FXML
    private Button skipButton;
    @FXML
    private Label timerLabel;
    private boolean isPause;
    private Timeline timeLine;
    private ViewListener listener;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        finishButton.setOnAction(actionEvent -> listener.showHome());
        previousButton.setOnAction(actionEvent -> listener.getPrevious());
        pauseButton.setOnAction(actionEvent -> startPause());
        skipButton.setOnAction(actionEvent -> listener.getNext());
        this.descriptionExo.setEditable(false);
    }

    private void startTimer(int duration) {
        ObjectProperty<java.time.Duration> remainingDuration
                = new SimpleObjectProperty<>(java.time.Duration.ofSeconds(duration));
        configureTimer(remainingDuration);
        timeLine.play();
    }

    private void configureTimer(ObjectProperty<java.time.Duration> remainingDuration) {
        // Binding with media time format (hh:mm:ss):
        timerLabel.textProperty().bind(Bindings.createStringBinding(() ->
                        String.format("%02d:%02d:%02d",
                                remainingDuration.get().toHours(),
                                remainingDuration.get().toMinutesPart(),
                                remainingDuration.get().toSecondsPart()),
                remainingDuration));

        // Create time line to lower remaining duration every second:
        timeLine = new Timeline(new KeyFrame(Duration.seconds(1), (ActionEvent event) ->
                remainingDuration.setValue(remainingDuration.get().minus(1, ChronoUnit.SECONDS))));

        // Set number of cycles (remaining duration in seconds):
        timeLine.setCycleCount((int) remainingDuration.get().getSeconds());
    }

    private void startPause() {
        if (isPause) {
            timeLine.play();
        } else {
            isPause = true;
            timeLine.pause();
        }
    }


    /**
     * Sets the information for the exercise including an image.
     *
     * @param nameExercice The name of the exercise.
     * @param description  The description of the exercise.
     * @param duration     The duration of the exercise in seconds.
     * @param image        The image representing the exercise.
     */
    public void setInformation(String nameExercice, String description, int duration, byte[] image) {
        titleExo.setText(nameExercice);
        descriptionExo.setText(description);
        startTimer(duration);
        Image img = new Image(new ByteArrayInputStream(image));
        imageExo.setImage(img);
    }

    @Override
    public void changeMode(boolean darkMode) {
        FxUtils.changeMode(darkMode, this.parent);
    }

    /**
     * Sets a listener for this view.
     *
     * @param listener The listener to be set.
     */
    public void setListener(ViewListener listener) {
        this.listener = listener;
    }

    public interface ViewListener {
        void getPrevious();

        void getNext();

        void showHome();

    }
}

