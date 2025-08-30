// TELA DO JOGO EM SI
package SpaceInvaders;

import java.io.IOException;
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
    @FXML
    private Text score;

    protected static Integer scores = 0;//O atributo está estático para possibilitar o acesso na tela de gameover
    protected Integer life = 3;
    private Rectangle[] stars = new Rectangle[200];
    private LinkedList<Rectangle> enemies = new LinkedList<>();
    private LinkedList<Circle> tirosPlayer = new LinkedList<>();
    private LinkedList<Circle> tirosEnemie = new LinkedList<>();
    private Timeline timeline;
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
                moverPlayer(event);             
            });
            pane.getScene().setOnMouseClicked(event ->{
                atirar(event);
            });
        });
            timeline = new Timeline( //Repete o seguinte bloco de funções a cada 26 milisegundos(da o efeito de animação)
            new KeyFrame(Duration.millis(26), e -> {
                makeEnemies(enemies);
                moverDisparos(tirosPlayer);
                disparoEnemie(enemies, tirosEnemie);
                moverDispEnemie(tirosEnemie);
                moverEnemies(enemies);
                hitThePlayer(player, tirosEnemie); 
                hitTheEnemie(enemies, tirosPlayer);
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
    
    private void makeEnemies(LinkedList<Rectangle> inimigos){ //coloca o conjunto de inimigos na tela
        if(inimigos.isEmpty()){
            double x;
            x=82.5;            
            for(int i=0; i<48; i++){// cria um bloco 8x6 de inimigos
                Rectangle inimigo = new Rectangle();
                inimigo.setWidth(35);
                inimigo.setHeight(25);
                if (i<8){
                    inimigo.setFill(Color.BLUE);
                    inimigo.setLayoutX(x);
                    inimigo.setLayoutY(125);
                    x+=60;
                    if (i==7)
                        x = 82.5;
                }
                if (i>7 && i<16){
                    inimigo.setFill(Color.RED);
                    inimigo.setLayoutX(x);
                    inimigo.setLayoutY(160);
                    x+=60;
                    if (i==15)
                        x = 82.5;
                }
                if (i>15 && i<24){
                    inimigo.setFill(Color.PURPLE);
                    inimigo.setLayoutX(x);
                    inimigo.setLayoutY(195);
                    x+=60;
                    if (i==23)
                        x = 82.5;
                }
                if (i> 23 && i<32){
                    inimigo.setFill(Color.YELLOW);
                    inimigo.setLayoutX(x);
                    inimigo.setLayoutY(230);
                    x+=60;
                    if (i==31)
                        x = 82.5;
                }
                if (i>31 && i<40){
                    inimigo.setFill(Color.GREEN);
                    inimigo.setLayoutX(x);
                    inimigo.setLayoutY(265);
                    x+=60;
                    if (i==39)
                        x = 82.5;
                }
                if (i>39 && i<48){
                    inimigo.setFill(Color.DEEPPINK);
                    inimigo.setLayoutX(x);
                    inimigo.setLayoutY(300);
                    x+=60;
                }
                pane.getChildren().add(inimigo);
                enemies.add(inimigo);
            }
        }
    }

    @FXML
    private void moverPlayer(KeyEvent event) {
        if(player.getLayoutX()>0 && player.getLayoutX()<637){
            if(event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.A){
                if(player.getLayoutX()-13 > 0)//se a movimentação não ultrapassar o limite da tela ele move para a esquerda
                    player.setLayoutX(player.getLayoutX()-7);
            }
            if(event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.D){
                if(player.getLayoutX()+13 < 637)//se a movimentação não ultrapassar o limite da tela ele move para a direita
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
        var it = disparos.iterator();
        while(it.hasNext()){
            Circle bala = it.next();
            bala.setCenterY(bala.getCenterY()-3);
            if(bala.getCenterY() < 0){
                it.remove();
                bala.setVisible(false);
            }
        }
    }
    /*A função de disparos do inimigo é randomizada pois devido ao timeline, se não houvesse a randomização
    o inimigo teria um disparo a cada 26 milisegundos, além de ser um fator que pode alterar a dificuldade 
    do jogo quando se quiser    
    */
    private void disparoEnemie(LinkedList<Rectangle> enemies, LinkedList<Circle> dispEnemies){
        int fire = (int)(Math.random()*70); 
        if(fire == 1){
            int swt = (int)(Math.random()*enemies.size());
            try{
                Rectangle escolhido = enemies.get(swt); //escolhe um inimigo vivo para ser o atirador
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
        var it = tiros.iterator();
        while(it.hasNext()){
            Circle bala = it.next();
            bala.setCenterY(bala.getCenterY()+2);
            if(bala.getCenterY() > 500){
                it.remove();
                bala.setVisible(false);
            }
        }
    }
    
    private void moverEnemies(LinkedList<Rectangle> enemies){ 
        int rnd = (int) (Math.random()*100);//A justificativa para essa randomização é a mesma de disparoInimigo               
        if (rnd == 0){//Move para a direita
            for(Rectangle inimigo: enemies){
                if (inimigo.getLayoutX()+10 > 615){//Verifica se algum inimigo ultrapassa o limite da tela caso seja movido
                    return;//Se algum inimigo ultrapassar o limite da tela apenas retorna
                }    
            }            
            for(Rectangle inimigo: enemies){
                inimigo.setLayoutX(inimigo.getLayoutX()+10);//Caso todos os inimigos possam ser movidos o faz
            }
        }
        if (rnd == 1){//Move para a esquerda
            for(Rectangle inimigo: enemies){
                if(inimigo.getLayoutX()-10 < 0){
                    return;                    
                }
            }
            for(Rectangle inimigo: enemies){
                inimigo.setLayoutX(inimigo.getLayoutX()-10);
            }
        }
        if (rnd == 2){//Move para baixo
            for(Rectangle inimigo: enemies){
                if(inimigo.getLayoutY()+10 > 400){
                    return;                    
                }
            } 
            for(Rectangle inimigo: enemies){
                inimigo.setLayoutY(inimigo.getLayoutY()+10);
            }
        }
        if (rnd == 3){//Move para cima
            for(Rectangle inimigo: enemies){
                if(inimigo.getLayoutY()-10 < 60){
                    return;
                }
            }
            for(Rectangle inimigo: enemies){
                inimigo.setLayoutY(inimigo.getLayoutY()-10);
            }
        }
    }
    
    private void hitThePlayer(Rectangle player, LinkedList<Circle> bullets){
        double x0 = player.getLayoutX();
        double x = x0 + player.getWidth();
        double y0 = player.getLayoutY();
        double y = y0 + player.getHeight();

        var itBullets = bullets.iterator();
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
                    stopGameLoop();
                    try {
                        App.setRoot("gameOver");
                    } catch (IOException ex) {
                        ex.getMessage();
                    }                    
                }
            }
        }
}
    
    private void hitTheEnemie(LinkedList<Rectangle> enemies, LinkedList<Circle> bullets){
        var itBullets = bullets.iterator();
        while(itBullets.hasNext()){ //Testa cada disparo do player em cada inimigo
            Circle bullet = itBullets.next();
            double raio = bullet.getRadius();
            double cx = bullet.getCenterX();
            double cy = bullet.getCenterY();

            var itEnemies = enemies.iterator();
            while(itEnemies.hasNext()){
                Rectangle inimigo = itEnemies.next();
                double x0 = inimigo.getLayoutX();
                double x = x0 + inimigo.getWidth();
                double y0 = inimigo.getLayoutY();
                double y = y0 + inimigo.getHeight();

                if(x0 < cx+raio && x > cx-raio && y0 < cy+raio && y > cy-raio){//Verifica se houve colisão           
                    //SE HOUVE COLISÃO
                    scores += 10;
                    score.setText(scores.toString());                
                    itEnemies.remove();
                    inimigo.setVisible(false);                
                    itBullets.remove();//Remove o disparo do jogo
                    bullet.setVisible(false);
                    break;
                }
            }
        }    
        if(enemies.isEmpty()){//Se todos os inimigos forem derrotados retira todos os disparos do jogo até o novo bloco de inimigos ser colocado
            for(Circle bala : bullets){
                bala.setVisible(false);
            }
            bullets.clear();
        }
    }
    
    public void stopGameLoop() {//Para a timeline do jogo(Utilizado para resolver um bug de o jogo rodar sem estar na tela de jogo)
        if (timeline != null) {
            timeline.stop();
        }
    }
}

    
