from PyQt5 import QtCore, QtGui, QtWidgets

class Bullet:
    def __init__ (self, x_inicial = 0.5):
        self.y = 0.9
        self.x = x_inicial - 0.01

    def inc (self):
        self.y -= 0.01

    def getY(self):  
        return self.y

    def getX(self):
        return self.x

class Alvo:
    def __init__(self, x, y):
        self.x = x
        self.y = y
        self.ativo = True
        

class MyLabel(QtWidgets.QLabel):
    def __init__(self,parms) -> None:
        super().__init__(parms)
        self.setText ("MyLabel")
        self.alvos = []
        linhas = 4
        colunas = 8
        for i in range(linhas):
            for j in range(colunas):
                pos_x = 0.1 + i * 0.15
                pos_y = 0.1 + j * 0.1
                novo_alvo = Alvo(pos_x, pos_y)
                self.alvos.append(novo_alvo)
        self.paint = True
        self.direcaodetodos = 0.01
        #self.direcaovertical = 0.005
        self.bullet = []
        self.player_x = 0.5 
        self.player_dx = 0   

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
            for alvo in self.alvos:
                if(alvo.ativo == True):
                    br = QtGui.QBrush(QtGui.QColor(255,0,0,255))
                    qp.setBrush(br)
                    qp.drawEllipse (
                        int (alvo.x * width) - 15, 
                        int (alvo.y * height) - 15, 
                        10, 10
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


        br = QtGui.QBrush(QtGui.QColor(0, 0, 255, 255)) 
        qp.setBrush(br)
        qp.drawEllipse(
            int(self.player_x * width) - 15,  
            height - 35,                     
            17, 17  
        )

        # teste de colisao
        for alvo in self.alvos:
            for b in self.bullet:
                if (int (abs(b.getX() - alvo.x) * width) < 15) and \
                (int (abs(b.getY() - alvo.y) * height) < 15):
                    alvo.ativo = False

    def moveTarget (self):
        direcaohorizontal = False
        #direcaovertical = False
        for alvo in self.alvos:
            if (alvo.ativo ==  True):
                if (self.direcaodetodos > 0 and alvo.x >= 1) or \
                (self.direcaodetodos < 0 and alvo.x <= 0.05):
                    direcaohorizontal = True
                    break
                # if (self.direcaovertical > 0 and alvo.y >= 0.7) or \
                #    (self.direcaovertical < 0 and alvo.y <= 0.1):
                #     direcaovertical = True
                #     break
        if (direcaohorizontal == True):
            self.direcaodetodos *= -1 
        # if (direcaovertical == True):
        #     self.direcaovertical *= -1
        for alvo in self.alvos:
            alvo.x += self.direcaodetodos
            # alvo.y += self.direcaovertical

        velocidade_jogador = 0.01
        self.player_x += self.player_dx * velocidade_jogador
    
        if self.player_x < 0.02: self.player_x = 0.02
        if self.player_x > 1: self.player_x = 1
        
        for b in self.bullet:
            b.inc()
            if b.getY() <= 0:
                self.bullet.remove(b)
        self.repaint()

    def shoot (self):
        self.bullet.append (Bullet(self.player_x))