//TELA INICIAL DO JOGO
package SpaceInvaders;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * FXML Controller class
 *
 * @author bcbru
 **/
public class PrimaryController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private Rectangle[] stars = new Rectangle[200];
    @FXML
    private AnchorPane pane;
    @FXML
    private Button btPlay;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        makeStars(stars);
    }    
    
    private void makeStars(Rectangle[] stars){ // cria pixels brancos na tela para simular estrelas, utilizado nas outras janelas tambem
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

    @FXML
    private void play(ActionEvent event) throws IOException{
        App.setRoot("secondary");
    }
}
