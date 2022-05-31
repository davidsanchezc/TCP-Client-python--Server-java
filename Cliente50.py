import time
import threading
from TCPClient50 import *
class Cliente50:
    
    def iniciar(self):
        print("Cliente bandera 01")
        self.mTcpClient=TCPClient50("192.168.18.10")#Cliente50.mTcpClient.enviarData())
        
        
        #THREADS DESDE EL CLIENTE
        t=threading.Thread(target=self.mTcpClient.run(self.mTcpClient))
        t.start()
        t.join()    

    def clienteRecibe(self,llego,a,sock):
        if(len(llego)!=0):
            print("Cliente50\n El mensaje:: "+str(llego))
            nroCli, elementos = llego.split(" ")
            listaX, listaY=elementos.split("-")
            lx = listaX.split(";")
            ly = listaY.split(";")
            nX = []
            nY = []
            for x, y in zip(lx,ly):
                x = float(x)
                nX.append(x)
                y = float(y)
                nY.append(y)
            
            self.procesar(nroCli,nX,nY,a,sock)
        
    def clienteEnvia(self,envia,a,sock):
        print(envia)
        print()
        a.sendMessage(envia,sock)
        
    #los argumentos son las listas enviadas desde el servidor
    #Regresión lineal
    def procesar(self,nrCli,listaX,listaY,a,sock):
        n = len(listaX)
        xy=0
        Sx=0
        Sy=0
        Sx2=0
        for x, y in zip(listaX, listaY):
            xy = xy + x*y
            Sx = Sx + x
            Sy = Sy + y
            Sx2 = Sx2 + x**2

        m = (n*xy - Sx*Sy)/(n*Sx2-Sx**2)
        b = (Sy/n) - m*(Sx/n)
        
        if m<0 :
            self.clienteEnvia(nrCli+" y=" + str(b) + str(m) +"x",a,sock)
        else:
            self.clienteEnvia(nrCli+" y=" + str(b) + "+" + str(m) +"x",a,sock)
        

if __name__ == '__main__':
    objcli = Cliente50()
    objcli.iniciar()
