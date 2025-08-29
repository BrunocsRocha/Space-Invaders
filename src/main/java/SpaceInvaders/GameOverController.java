/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package SpaceInvaders;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author bcbru
 */
public class GameOverController implements Initializable {

    @FXML
    private Text hScore;
    @FXML
    protected Text score;
    @FXML
    private Button rerun;
    @FXML
    private Button menu;
    @FXML
    private Button exit;
    
    private Rectangle[] stars = new Rectangle[200];
    protected Integer aScore=0;
    @FXML
    private AnchorPane pane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        makeStars(stars);
        setScore(SecondaryController.scores);
        base();
    }    
    
    public void setScore(Integer score) {
        this.aScore = score;
        updateScore(); 
    }

    private void updateScore() {
        if(aScore == null) aScore = 0; 
        if(aScore > App.highScore){
            App.highScore = aScore;
        }
        hScore.setText(App.highScore.toString());
        score.setText(aScore.toString());
    }
    
    private void base(){
        hScore.setText(App.highScore.toString());
        score.setText(aScore.toString());
    }

    @FXML
    private void again(ActionEvent event) throws IOException {
        SecondaryController.scores =0;
        App.setRoot("secondary"); 
    }

@FXML
    private void init(ActionEvent event) throws IOException {
        SecondaryController.scores =0;
        App.setRoot("primary");
    }

@FXML
    private void sair(ActionEvent event) {
        Platform.exit();
    }
    
     private void makeStars(Rectangle[] stars){
        for (int i = 0; i < stars.length; i++) {
            Rectangle star = new Rectangle();
            star.setFill(Color.WHITE);
            star.setWidth(2);
            star.setHeight(2);
            star.setLayoutX(Math.random() * 651);
            star.setLayoutY(Math.random() * 501);
            stars[i] = star;

            pane.getChildren().add(star);
        }    
    }
    
}
