import java.util.Scanner;
public class Servidor50 {
    TCPServer50 mTcpServer;
    Scanner sc;
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
            System.out.println("Soy Server y envio " + envia);
            if (envia.trim().contains("enviar")) {
                System.out.println("Entra a ServidorEnvia");
                if (mTcpServer != null) {
                    mTcpServer.sendMessageTCPServerGenerar(envia);
                }
            } else {
                System.out.println("No entra a Servidor Envia");
            }
        }
    }
}
