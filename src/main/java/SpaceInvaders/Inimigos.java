package SpaceInvaders;

import java.util.*;
import java.util.LinkedList;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 *
 * @author bcbru
 */
public class Inimigos {
    private LinkedList<Rectangle> tropa;
    private LinkedList<Circle> disparos;
    private final Color[] coresLinha = {
        Color.BLUE, Color.RED, Color.PURPLE,
        Color.YELLOW, Color.GREEN, Color.DEEPPINK
    };
    private final double posYInicial = 125;
    private final double espacamentoY = 35; 
    private final double posXInicial = 82.5;
    private final double espacamentoX = 60; 
    
    public Inimigos(AnchorPane pane){
        tropa = new LinkedList<>();
        disparos = new LinkedList<>();
        createEnemies(pane);
    }
    
    public void createEnemies(AnchorPane pane){
        if(tropa.isEmpty()){
            int colunas = 8;
            int linhas = 6;

            for(int i = 0; i < linhas; i++){
                for(int j = 0; j < colunas; j++){
                    Rectangle inimigo = new Rectangle(35, 25);
                    inimigo.setFill(coresLinha[i]);
                    inimigo.setLayoutX(posXInicial + j * espacamentoX);
                    inimigo.setLayoutY(posYInicial + i * espacamentoY);

                    pane.getChildren().add(inimigo);
                    tropa.add(inimigo);
                }
            }
        }
    }
    
    public LinkedList<Circle> getDisparos(){
        return disparos;
    }        
    
    /*A função de disparos do inimigo é randomizada pois devido ao timeline, se não houvesse a randomização
    o inimigo teria um disparo a cada 26 milisegundos, além de ser um fator que pode alterar a dificuldade 
    do jogo quando se quiser    
    */
    public void disparoEnemie(AnchorPane pane){
        int fire = (int)(Math.random()*70); 
        if(fire == 1){
            int swt = (int)(Math.random()*tropa.size());
            try{
                Rectangle escolhido = tropa.get(swt); //escolhe um inimigo vivo para ser o atirador
                Circle disparo = new Circle();
                disparo.setFill(Color.RED);
                disparo.setRadius(4);
                disparo.setStroke(Color.WHITE);
                disparo.setCenterX(escolhido.getLayoutX()+escolhido.getWidth()/2);
                disparo.setCenterY(escolhido.getLayoutY()+escolhido.getHeight());
                disparos.add(disparo);
                pane.getChildren().add(disparo);
            }catch(NullPointerException e){
                System.out.println("Erro:" + e.getMessage());
            }
        }
    }
    
    public void moverDisparos(){
        Iterator<Circle> it = disparos.iterator();
        while(it.hasNext()){
            Circle bala = it.next();
            bala.setCenterY(bala.getCenterY()+2);
            if(bala.getCenterY() > 500){//Se o disparo sair da tela é removido 
                it.remove();
                bala.setVisible(false);
            }
        }
    }
    
    public void mover(){ 
        int rnd = (int) (Math.random()*100);//A justificativa para essa randomização é a mesma dos disparos           
        if (rnd == 0){//Move para a direita
            for(Rectangle inimigo: tropa){
                if (inimigo.getLayoutX()+10 > 615){//Verifica se algum inimigo ultrapassa o limite da tela caso seja movido
                    return;//Se algum inimigo ultrapassar o limite da tela apenas retorna
                }    
            }            
            for(Rectangle inimigo: tropa){
                inimigo.setLayoutX(inimigo.getLayoutX()+10);//Caso todos os inimigos possam ser movidos o faz
            }
        }
        if (rnd == 1){//Move para a esquerda
            for(Rectangle inimigo: tropa){
                if(inimigo.getLayoutX()-10 < 0){
                    return;                    
                }
            }
            for(Rectangle inimigo: tropa){
                inimigo.setLayoutX(inimigo.getLayoutX()-10);
            }
        }
        if (rnd == 2){//Move para baixo
            for(Rectangle inimigo: tropa){
                if(inimigo.getLayoutY()+10 > 400){
                    return;                    
                }
            } 
            for(Rectangle inimigo: tropa){
                inimigo.setLayoutY(inimigo.getLayoutY()+10);
            }
        }
        if (rnd == 3){//Move para cima
            for(Rectangle inimigo: tropa){
                if(inimigo.getLayoutY()-10 < 60){
                    return;
                }
            }
            for(Rectangle inimigo: tropa){
                inimigo.setLayoutY(inimigo.getLayoutY()-10);
            }
        }
    }
    
    public void hitTheEnemie(Player player, Text score){
        Iterator<Circle> itBullets = player.getDisparos().iterator();
        while(itBullets.hasNext()){ //Testa cada disparo do player em cada inimigo
            Circle bullet = itBullets.next();
            double raio = bullet.getRadius();
            double cx = bullet.getCenterX();
            double cy = bullet.getCenterY();

            Iterator<Rectangle> itEnemies = tropa.iterator();
            while(itEnemies.hasNext()){
                Rectangle inimigo = itEnemies.next();
                double x0 = inimigo.getLayoutX();
                double x = x0 + inimigo.getWidth();
                double y0 = inimigo.getLayoutY();
                double y = y0 + inimigo.getHeight();

                if(x0 < cx+raio && x > cx-raio && y0 < cy+raio && y > cy-raio){//Verifica se houve colisão           
                    //SE HOUVE COLISÃO
                    player.scores += 10;
                    score.setText(player.scores.toString());                
                    itEnemies.remove();
                    inimigo.setVisible(false);                
                    itBullets.remove();//Remove o disparo do jogo
                    bullet.setVisible(false);
                    break;
                }
            }
        }    
        if(tropa.isEmpty()){//Se todos os inimigos forem derrotados retira todos os disparos do jogo até o novo bloco de inimigos ser colocado
            for(Circle bala : disparos){
                bala.setVisible(false);
            }
            disparos.clear();
        }
    }
    
}

