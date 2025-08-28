import sys
from PyQt5 import QtWidgets
from PyQt5.QtCore import QTimer, Qt

from MainWindow import Ui_MainWindow

class MainWindow(QtWidgets.QMainWindow, Ui_MainWindow):
    def __init__(self, *args, obj=None, **kwargs):
        super(MainWindow, self).__init__(*args, **kwargs)
        self.setupUi(self)
        self.setWindowTitle ("Tiro ao alvo")
        self.timer=QTimer()
        self.timer.timeout.connect(self.label.moveTarget)
        self.timer.start (36)

    #def slotAtirar (self):
        #self.label.shoot()

    def keyPressEvent(self, event):
        print("tecla tecla tecla")
        key = event.key()
        print(f"tecla pressionada: {key}")
        # move esquerda
        if key == Qt.Key_A or key == Qt.Key_Left:
            self.label.player_dx = -1 # 

        # move direita
        elif key == Qt.Key_D or key == Qt.Key_Right:
            self.label.player_dx = 1 

        # Atirar
        elif key == Qt.Key_Space:
            print("espaçoooooo")
            self.label.shoot()

    def keyReleaseEvent(self, event):
        key = event.key()

        
        if key == Qt.Key_A or key == Qt.Key_Left or key == Qt.Key_D or key == Qt.Key_Right:
            self.label.player_dx = 0
app = QtWidgets.QApplication(sys.argv)

window = MainWindow()
window.show()
app.exec()
