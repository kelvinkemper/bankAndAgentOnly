import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class myBankServer {

    private ServerSocket serverSocket;

    public myBankServer(ServerSocket serverSocket) {
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

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServerSocket serverSocket = new ServerSocket(9999);

        myBankServer myBankServer = new myBankServer(serverSocket);
        myBankServer.startServer();

        Socket socket = serverSocket.accept(); // blocking call, this will wait until a connection is attempted on this port.
        System.out.println("Connection from " + socket + "!");

        // get the input stream from the connected socket
        InputStream inputStream = socket.getInputStream();
        // create a DataInputStream so we can read data from it.
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

        Agent a = (Agent) objectInputStream.readObject();
        System.out.println("Name is: " + a.name);
        System.out.println("Initial balance is: " + a.initialBalance);

        System.out.println("Closing sockets");
       // ss.close();
       // socket.close();

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
}



