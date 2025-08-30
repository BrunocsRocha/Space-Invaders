from PyQt5 import QtCore, QtGui, QtWidgets

class Bullet:
    def __init__ (self, x_inicial = 0.5):
        self.y = 0.9
        self.x = x_inicial - 0.01
        self.baladestruida = False
    def inc (self):
        self.y -= 0.01

    def getY(self):  
        return self.y

    def getX(self):
        return self.x

class Alvo: #classe para os inimigos
    def __init__(self, x, y):
        self.x = x
        self.y = y
        self.ativo = True

class MyLabel(QtWidgets.QLabel):
    def __init__(self,parms) -> None:
        super().__init__(parms)
        self.setText ("MyLabel")
        self.alvos = [] #lista dos inimigos
        linhas = 4 # número de linhas
        colunas = 8 # número de colunas
        for i in range(linhas): # loop pelas linhas
            for j in range(colunas): # loop pelas colunas
                pos_x = 0.1 + j * 0.07
                pos_y = 0.1 + i * 0.05
                novo_alvo = Alvo(pos_x, pos_y) 
                self.alvos.append(novo_alvo) 
        self.paint = True
        self.direcaodetodos = 0.01 #velocidade horizontal dos inimigos
        self.bullet = []
        self.player_x = 0.5 #posição do jogador
        self.player_dx = 0   #move do jogador
        self.cooldowndotiro = 0 #tempo de recarga do tiro

    def paintEvent (self, event):
        super().paintEvent(event)
        qp = QtGui.QPainter(self)
        br = QtGui.QBrush(QtGui.QColor(255,255,255,255))
        height = self.height()
        width = self.width()

        #clear
        qp.setBrush(br)
        qp.drawRect(0,0,width,height)

        # target
        if (self.paint):
            for alvo in self.alvos: #pinta todos os inimigos
                if(alvo.ativo == True): #verifica se o inimigo está "vivo"
                    br = QtGui.QBrush(QtGui.QColor(255,0,0,255))
                    qp.setBrush(br)
                    qp.drawEllipse (
                        int (alvo.x * width) - 15, 
                        int (alvo.y * height) - 15, 
                        13, 13
                    )

        # bullets
        for b in self.bullet:
            br = QtGui.QBrush(QtGui.QColor(0,0,0,255))
            qp.setBrush(br)
            qp.drawEllipse (
                int (b.getX() * width) - 2, 
                int (b.getY() * height) - 2, 
                4,4
            )


        br = QtGui.QBrush(QtGui.QColor(0, 0, 255, 255)) #jogador
        qp.setBrush(br)
        qp.drawEllipse(
            int(self.player_x * width) - 15,  
            height - 35,                     
            17, 17  
        )

        # teste de colisao
        for b in self.bullet: #percorre as balas 
            for alvo in self.alvos: #percorre os inimigos
                if (alvo.ativo == True): 
                        if (int (abs(b.getX() - alvo.x) * width) < 15) and \
                        (int (abs(b.getY() - alvo.y) * height) < 15):
                            alvo.ativo = False
                            b.baladestruida = True #marca a bala como destruída
                            break
        balasnaodestruidas = [] #lista de balas não destruídas
        for b in self.bullet:
            if (b.baladestruida == False and b.getY() > 0): #verifica se a bala não foi destruída e ainda está na tela
                balasnaodestruidas.append(b) #adiciona a bala à lista de balas não destruídas
        self.bullet = balasnaodestruidas #atualiza a lista de balas

    def moveTarget (self):
        direcaoedesce = False #unifiquei as direções, já que sempre que ele topar na parede ele vai inverter a direção e ao mesmo tempo vai descer
        if self.cooldowndotiro > 0: 
            self.cooldowndotiro -= 1 #"cronometro" para o cooldown dos tiros
        for alvo in self.alvos: 
            if (alvo.ativo ==  True):
                if (self.direcaodetodos > 0 and alvo.x >= 0.95): #verifica se algum inimigo chegou na borda
                    direcaoedesce = True
                    break
                elif (self.direcaodetodos < 0 and alvo.x <= 0.05):
                    direcaoedesce = True
                    break
        if (direcaoedesce == True):
            self.direcaodetodos *= -1  #move os inimigos na direção horizontal 
            for alvo in self.alvos: #move todos os inimigos para baixo
                alvo.y += 0.02
        
        for alvo in self.alvos: #muda de lugar de todos os inimigos
            alvo.x += self.direcaodetodos

        velocidade_jogador = 0.01
        self.player_x += self.player_dx * velocidade_jogador
    
        if self.player_x < 0.02: self.player_x = 0.02 #limites do jogador
        if self.player_x > 1: self.player_x = 1 #limites do jogador

        for b in self.bullet:
            b.inc()
            if b.getY() <= 0:
                self.bullet.remove(b)
        self.repaint()

    def shoot (self):
        if (self.cooldowndotiro == 0): #verifica se já pode atirar
            self.bullet.append (Bullet(self.player_x))
            self.cooldowndotiro = 10 #tempo de recarga do tiro, começa a contagem do cronometro em 10