// TELA DO JOGO EM SI
package SpaceInvaders;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
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
    private AnchorPane pane;
    
    @FXML
    private Text score;
    
    private Player player;
    private Inimigos inimigos;
    

    protected static Integer finalScore = 0;//O atributo está estático para possibilitar o acesso na tela de gameover    
    private Rectangle[] stars = new Rectangle[200];
    private Timeline timeline;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        player = new Player(pane);
        inimigos = new Inimigos(pane);
        makeStars(stars);        
        Platform.runLater(() -> { 
            pane.getScene().setOnKeyPressed(event -> {
                player.mover(event);             
            });
            pane.getScene().setOnMouseClicked(event ->{
                player.atirar(event, pane);
            });
        });
            timeline = new Timeline( //Repete o seguinte bloco de funções a cada 26 milisegundos(dá o efeito de animação)
            new KeyFrame(Duration.millis(26), e -> {
                player.cooldown();
                inimigos.createEnemies(pane);
                player.moverDisparos();
                inimigos.disparoEnemie(pane);
                inimigos.moverDisparos();
                inimigos.mover();
                player.hitThePlayer(inimigos.getDisparos(), timeline, lifes);
                inimigos.hitTheEnemie(player, score);
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
}
