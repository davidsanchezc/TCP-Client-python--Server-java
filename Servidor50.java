import java.util.Scanner;
import java.lang.System;
public class Servidor50 {
    TCPServer50 mTcpServer;
    Scanner sc;
    long startTime = 0;
    long stopTime = 0;
    public static void main(String[] args) {
        Servidor50 objser = new Servidor50();
        objser.iniciar();
    }
    void iniciar() {
        new Thread(
                new Runnable() {
            @Override
            public void run() {
                mTcpServer = new TCPServer50(
                        new TCPServer50.OnMessageReceived() {
                    @Override
                    public void messageReceived(String message) {
                        synchronized (this) { 
                            ServidorRecibe(message);
                        }
                        //                          
                        stopTime = System.currentTimeMillis();
                        // System.out.println("Tiempo transcurrido " + (stopTime - startTime) + " milisegundos.");
                        
                    }
                    
                }
                );
                mTcpServer.run();
            }
        }
        ).start();
        
        String salir = "n";
        sc = new Scanner(System.in);
        System.out.println("Servidor bandera 01");
        while (!salir.equals("s")) {
            salir = sc.nextLine();
            ServidorEnvia(salir);
        }
        System.out.println("Servidor bandera 02");

    }
    int contarcliente = 0;
    int rptacli[] = new int[20];
    int sumclient = 0;

    void ServidorRecibe(String llego) {
        if (llego != null && !llego.equals("")) {
                String arrString[] = llego.split("\\s+");
                int indexCli = Integer.parseInt(arrString[0]); //"1003"
                String regre = arrString[1]; //“y=0.071428571+0.839285714x”
                System.out.println("rpta "+indexCli+" "+regre);
        }
    }

    void ServidorEnvia(String envia) {//El servidor tiene texto de envio
        if (envia != null) {
            startTime = System.currentTimeMillis();
            if (envia.trim().contains("enviar")) {
                System.out.println("\nEntra a ServidorEnvia");
                if (mTcpServer != null) {
                    mTcpServer.sendMessageTCPServerGenerar(envia);
                }
            } else {
                System.out.println("No entra a ServidorEnvia");
            }
        }
    }
}
