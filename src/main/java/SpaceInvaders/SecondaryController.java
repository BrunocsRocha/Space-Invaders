/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package SpaceInvaders;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
/**
 * FXML Controller class
 *
 * @author bcbru
 */
public class SecondaryController implements Initializable {


    @FXML
    private Text lifes;
    @FXML
    private Rectangle player;
    @FXML
    private AnchorPane pane;
    private int life=3;
    private Rectangle[] stars = new Rectangle[200];
    private LinkedList<Rectangle> enemies = new LinkedList<>();
    private LinkedList<Circle> tirosPlayer = new LinkedList<>();
    private LinkedList<Circle> tirosEnemie = new LinkedList<>();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        makeStars(stars);
        makeEnemies(enemies);
        Platform.runLater(() -> {
            pane.getScene().setOnKeyPressed(event -> {
                movePlayer(event);             
            });
            pane.getScene().setOnMouseClicked(event ->{
                atirar(event);
            });
        });
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.millis(26), e -> {
                moverDisparos(tirosPlayer);
                disparoInimigo(enemies, tirosEnemie);
                moverDispEnemie(tirosEnemie);
            })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
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
    
    private void makeEnemies(LinkedList<Rectangle> inimigos){
        double x, y;
        x=82.5;
        y=125;
        for(int i=0; i<66; i++){
            Rectangle inimigo = new Rectangle();
            inimigo.setWidth(35);
            inimigo.setHeight(25);
            if (i<11){
                inimigo.setFill(Color.BLUE);
                inimigo.setLayoutX(x);
                inimigo.setLayoutY(125);
                x+=45;
                if (i==10)
                    x = 82.5;
            }
            if (i>10 && i<22){
                inimigo.setFill(Color.RED);
                inimigo.setLayoutX(x);
                inimigo.setLayoutY(160);
                x+=45;
                if (i==21)
                    x = 82.5;
            }
            if (i>21 && i<33){
                inimigo.setFill(Color.PURPLE);
                inimigo.setLayoutX(x);
                inimigo.setLayoutY(195);
                x+=45;
                if (i==32)
                    x = 82.5;
            }
            if (i> 32 && i<44){
                inimigo.setFill(Color.YELLOW);
                inimigo.setLayoutX(x);
                inimigo.setLayoutY(230);
                x+=45;
                if (i==43)
                    x = 82.5;
            }
            if (i>43 && i<55){
                inimigo.setFill(Color.GREEN);
                inimigo.setLayoutX(x);
                inimigo.setLayoutY(265);
                x+=45;
                if (i==54)
                    x = 82.5;
            }
            if (i>54 && i<66){
                inimigo.setFill(Color.DEEPPINK);
                inimigo.setLayoutX(x);
                inimigo.setLayoutY(300);
                x+=45;
            }
            pane.getChildren().add(inimigo);
            enemies.add(inimigo);
        }
    }

    @FXML
    private void movePlayer(KeyEvent event) {
        if(player.getLayoutX()>0 && player.getLayoutX()<637){
            if(event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.A){
                if(player.getLayoutX()-13 > 0)
                    player.setLayoutX(player.getLayoutX()-7);
            }
            if(event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.D){
                if(player.getLayoutX()+13 < 637)
                    player.setLayoutX(player.getLayoutX()+7);
            }
        }
    }
    
    private void atirar(MouseEvent event){
        if(event.getButton() == MouseButton.PRIMARY){
            Circle bala = new Circle();
            bala.setRadius(2);
            bala.setFill(Color.rgb(90,219,255));
            bala.setCenterX(player.getLayoutX()+(player.getWidth()/2));
            bala.setCenterY(player.getLayoutY());
            tirosPlayer.add(bala);
            pane.getChildren().add(bala);
        }
    }
    
    private void moverDisparos(LinkedList<Circle> disparos){
        for(Circle bala: disparos){            
            bala.setCenterY(bala.getCenterY()-3);
            if(bala.getCenterY()<0)
                disparos.remove(bala);
        }        
    }
    
    private void disparoInimigo(LinkedList<Rectangle> enemies, LinkedList<Circle> dispEnemies){
        int fire = (int)(Math.random()*70);
        if(fire == 1){
            int swt = (int)(Math.random()*enemies.size());
            try{
                Rectangle escolhido = enemies.get(swt);
                Circle disparo = new Circle();
                disparo.setFill(Color.RED);
                disparo.setRadius(4);
                disparo.setStroke(Color.WHITE);
                disparo.setCenterX(escolhido.getLayoutX()+escolhido.getWidth()/2);
                disparo.setCenterY(escolhido.getLayoutY()+escolhido.getHeight());
                dispEnemies.add(disparo);
                pane.getChildren().add(disparo);
            }catch(NullPointerException e){
                System.out.println("Erro:" + e.getMessage());
            }
        }
    }
    
    private void moverDispEnemie(LinkedList<Circle> tiros){
        for(Circle bala: tiros){
            bala.setCenterY(bala.getCenterY()+2);
            if(bala.getCenterY() > 500){
                tiros.remove(bala);
                bala.setVisible(false);
            }
        }
    }
}