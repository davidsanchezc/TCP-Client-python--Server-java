import socket
import Cliente50
import threading
class TCPClient50:
    SERVERPORT = 0
    mRun = False
    sock = socket.socket()

    def __init__(self, ip):
        TCPClient50.SERVERPORT = 4444
        TCPClient50.mRun = False
        self.serverIP = ip
        #self.listener = listener
        self.data = ""
        TCPClient50.inst = Cliente50.Cliente50()

    def sendMessage(self, message,sock):
        # threadLock = threading.Lock()
        # threadLock.acquire()
        if(sock.sendall(message.encode())==None):
            # threadLock.release()
            print("Mensaje enviado con éxito")
        
    def stopClient(self):
        TCPClient50.mRun=False

    def run(self,a):
        TCPClient50.mRun=True
        try:
            '''
            El argumento AF_INET indica que estás solicitando un socket de Protocolo de Internet (IP),
            específicamente IPv4. 
            El segundo argumento es el tipo de protocolo de transporte SOCK_STREAM para sockets TCP
            '''
            sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
            print("TCP Client "+ "C: Conectando...")
            sock.connect((self.serverIP,TCPClient50.SERVERPORT))            
            
            try:
                
                print("TCP Client "+ "C: Sent.")
                print("TCP Client "+ "C: Done.")
                #Recibir datos de entrada
                bytesC = sock.recv(1024)
                stringData = bytesC.decode('utf-8')
                
                if(len(stringData)!=0):
                    print(stringData)
                    threadLock = threading.Lock()
                    
                    TCPClient50.inst.clienteRecibe(stringData,a,sock)
                    
            except NameError:
                print("TCP"+ "S: Error " + str(NameError))
            finally:
                sock.close()
        except NameError:
            print("TCP"+ "C: Error " + str(NameError))