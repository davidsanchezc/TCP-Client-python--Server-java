import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.*;

public class TCPServer50 {
    private String message;
    
    int nrcli = 0;

    public static final int SERVERPORT = 4444;
    private OnMessageReceived messageListener = null;
    private boolean running = false;
    TCPServerThread50[] sendclis = new TCPServerThread50[10];

    PrintWriter mOut;
    BufferedReader in;
    
    ServerSocket serverSocket;

    public TCPServer50(OnMessageReceived messageListener) {
        this.messageListener = messageListener;
    }
    
    public OnMessageReceived getMessageListener(){/////¨
        return this.messageListener;
    }
    
    public void sendMessageTCPServerGenerar(String message){   
        // Para más de un cliente
        for (int i = 1; i < nrcli; i++) {
            Random rand = new Random();
            List<String> numeros = new ArrayList<String>();
            for(int cont = 0; cont<7; cont++){
                Float value = Math.round(rand.nextFloat()*100)/10f;
                String aux = String.valueOf(value);
                numeros.add(aux);
            }
            
            String str = String.join(";", numeros);
            str += "-";

            List<String> numeros2 = new ArrayList<String>();
            for(int cont = 0; cont<7; cont++){
                Float value = Math.round(rand.nextFloat()*100)/10f;
                String aux = String.valueOf(value);
                numeros2.add(aux);
            }
            str += String.join(";", numeros2);
            
            sendclis[i].sendMessage(i+" "+str);   
            System.out.println(message + " tarea" + (i) + " " + str);
        }
        // Para un cliente
        Random rand = new Random();
            List<String> numeros = new ArrayList<String>();
            for(int cont = 0; cont<7; cont++){
                Float value = Math.round(rand.nextFloat()*100)/10f;
                String aux = String.valueOf(value);
                numeros.add(aux);
            }
            
            String str = String.join(";", numeros);
            str += "-";
            
            List<String> numeros2 = new ArrayList<String>();
            for(int cont = 0; cont<7; cont++){
                Float value = Math.round(rand.nextFloat()*100)/10f;
                String aux = String.valueOf(value);
                numeros2.add(aux);
            }
            str += String.join(";", numeros2);

        sendclis[nrcli].sendMessage(nrcli+" "+str);   
        System.out.println(message + " tarea" + (nrcli) + " " + str);
    }    
    public void run(){
        running = true;
        try{
            System.out.println("TCP Server"+"S : Connecting...");
            serverSocket = new ServerSocket(SERVERPORT);
            
            while(running){
                Socket client = serverSocket.accept();
                System.out.println("TCP Server"+"S: Receiving...");
                nrcli++;
                System.out.println("Engendrado " + nrcli);
                sendclis[nrcli] = new TCPServerThread50(client,this,nrcli,sendclis);
                Thread t = new Thread(sendclis[nrcli]);
                Thread.sleep(10);//Retardo de 10 milisegundos
                t.start();
                System.out.println("Nuevo conectado:"+ nrcli+" jugadores conectados");
                
            }
            
        }catch( Exception e){
            System.out.println("Error"+e.getMessage());
        }finally{

        }
    }
    public  TCPServerThread50[] getClients(){
        return sendclis;
    } 

    public  interface OnMessageReceived {
        public void messageReceived(String message);
    }
}
