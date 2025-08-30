
package SpaceInvaders;
import java.io.IOException;
import java.util.*;
import javafx.animation.Timeline;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 *
 * @author bcbru
 */
public class Player {
    private Integer life;
    private Rectangle corpo;
    private LinkedList<Circle> disparos;    
    protected Integer scores;
    private Integer cooldown;    
    
    public Player(AnchorPane pane){
        disparos = new LinkedList<>();        
        cooldown = 0;
        life = 3;
        scores =0;
        corpo = new Rectangle();
        corpo.setLayoutX(320);
        corpo.setLayoutY(451);
        corpo.setWidth(13);
        corpo.setHeight(35);
        corpo.setFill(Color.rgb(138,138,118));        
        pane.getChildren().add(corpo);
    }
    
    public LinkedList<Circle> getDisparos(){
        return disparos;
    }
    
    public void atirar(MouseEvent event, AnchorPane pane){
        if(event.getButton() == MouseButton.PRIMARY && cooldown == 0){
            cooldown = 20;//Caso queira diminuir o spam de tiros é só aumentar
            Circle bala = new Circle();
            bala.setRadius(2);
            bala.setFill(Color.rgb(90,219,255));
            bala.setCenterX(corpo.getLayoutX()+(corpo.getWidth()/2));
            bala.setCenterY(corpo.getLayoutY());
            disparos.add(bala);
            pane.getChildren().add(bala);
        }
    }
    
    public void mover(KeyEvent event) {
        if(corpo.getLayoutX()>0 && corpo.getLayoutX()<637){
            if(event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.A){
                if(corpo.getLayoutX()-13 > 0)//se a movimentação não ultrapassar o limite da tela ele move para a esquerda
                    corpo.setLayoutX(corpo.getLayoutX()-7);
            }
            if(event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.D){
                if(corpo.getLayoutX()+13 < 637)//se a movimentação não ultrapassar o limite da tela ele move para a direita
                    corpo.setLayoutX(corpo.getLayoutX()+7);
            }
        }
    }
    
    public void moverDisparos(){
        Iterator<Circle> it = disparos.iterator();
        while(it.hasNext()){
            Circle bala = it.next();
            bala.setCenterY(bala.getCenterY()-3);
            if(bala.getCenterY() < 0){//Se o disparo sair da tela ele é removido
                it.remove();
                bala.setVisible(false);
            }
        }
    }
    
    public void hitThePlayer(LinkedList<Circle> bullets, Timeline timeline, Text lifes){
        double x0 = corpo.getLayoutX();
        double x = x0 + corpo.getWidth();
        double y0 = corpo.getLayoutY();
        double y = y0 + corpo.getHeight();

        Iterator<Circle> itBullets = bullets.iterator();
        while(itBullets.hasNext()){//Analisa cada disparo do inimigo
            Circle bullet = itBullets.next();
            if(x0 < bullet.getCenterX()+bullet.getRadius() && 
               x  > bullet.getCenterX()-bullet.getRadius() &&
               y0 < bullet.getCenterY()+bullet.getRadius() && 
               y  > bullet.getCenterY()-bullet.getRadius()){ //Verifica se houve colisão entre o player e o disparo do inimigo
                //SE HOUVE COLISÃO
                life--;
                lifes.setText(life.toString());      
                itBullets.remove();//Remove o disparo do jogo
                bullet.setVisible(false);  
                if(life<=0){//Testa se houve fim de jogo
                    SecondaryController.finalScore = scores;
                    stopGameLoop(timeline);
                    try {
                        App.setRoot("gameOver");
                    } catch (IOException ex) {
                        ex.getMessage();
                    }                    
                }
            }
        }
    }
           
    private void stopGameLoop(Timeline timeline){//Para a timeline do jogo(Utilizado para resolver um bug de o jogo rodar sem estar na tela de jogo)
        if (timeline != null) {
            timeline.stop();
        }
    }
    
    
    public void cooldown(){
        if(cooldown != 0){
            cooldown--;           
        }
    }
}
