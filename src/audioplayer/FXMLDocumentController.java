/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audioplayer;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;

/**
 *
 * @author Aniket
 */
public class FXMLDocumentController implements Initializable {

    public Slider volume;
    public Button start, pause, stop, play;
    public CheckBox loop;
    public Label playing;
    static MediaPlayer player;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        play.setDisable(true);
        stop.setDisable(true);
        pause.setDisable(true);
        volume.setDisable(true);
        loop.setDisable(true);
        playing.setWrapText(true);
        volume.valueProperty().addListener((ob, older, newer) -> {
            if (player != null) {
                player.setVolume(newer.doubleValue() / 100);
            }
        });
    }

    public void NewSong(ActionEvent e) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Choose which file to play");
        File f = fc.showOpenDialog(volume.getScene().getWindow());
        if (f != null) {
            String s = f.toURI().toString();
            if (player != null) {
                player.stop();
            }
            player = new MediaPlayer(new Media(s));
            player.setAutoPlay(true);
            player.setVolume(volume.getValue() / 100.0);
            player.setOnEndOfMedia(() -> {
                stop();
            });
            s = f.getAbsolutePath().substring(s.lastIndexOf(File.separator) + 1);
            playing.setText("Playing: " + s);
            pause.setDisable(false);
            stop.setDisable(false);
            volume.setDisable(false);
            loop.setDisable(false);
        }
    }

    public void Loop(ActionEvent e) {
        if (player != null) {
            if (loop.isSelected()) {
                player.setCycleCount(MediaPlayer.INDEFINITE);
            } else {
                player.setCycleCount(0);
            }
        }
    }

    public void pauseSong(ActionEvent e) {
        if (player != null) {
            player.pause();
            pause.setDisable(true);
            play.setDisable(false);
        }
    }

    public void Pause(ActionEvent e) {
        stop();
    }
    
    public void stop() {
        if (player != null) {
            player.stop();
            stop.setDisable(true);
            play.setDisable(false);
        }
    }

    public void Play(ActionEvent e) {
        if (player != null) {
            player.play();
            play.setDisable(true);
            pause.setDisable(false);
            stop.setDisable(false);
        }
    }
}
