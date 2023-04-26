import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Bank server that will listen for incoming
 */
public class BankServer {

   /** private ServerSocket serverSocket;

    public BankServer(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void startServer() {

        try{
            while (!serverSocket.isClosed()){
                System.out.println("Bank is up and running on port " + serverSocket.getLocalPort());

                //blocking method, halts until client connects
                Socket socket = serverSocket.accept();
                System.out.println("A client has connected");

                myBankClientHandler myBankClientHandler = new myBankClientHandler(socket);

                Thread thread = new Thread(myBankClientHandler);
                thread.start();

            }
        } catch (IOException e){
            closeServerSocket();
        }
    }

    public void closeServerSocket() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1234);
        BankServer bankServer = new BankServer(serverSocket);
        bankServer.startServer();
    } */
}
