import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientAgent {

    private Socket socket;
    private BufferedReader bufferedReader;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    public OutputStream outputStream;
    private BufferedWriter bufferedWriter;
    private int accountNumber;

    public ClientAgent(Socket socket) {
        try {
            this.socket = socket;
            this.outputStream = socket.getOutputStream();
            this.objectOutputStream = new ObjectOutputStream(outputStream);
            //this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            //this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            closeEverything(socket,objectOutputStream,objectInputStream);
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter name:");
        String name = sc.nextLine();
        System.out.println("Enter initial balance");
        int balance = Integer.parseInt(sc.nextLine());
        Socket socket = new Socket("localhost",9999);
        ClientAgent clientAgent = new ClientAgent(socket);
        Agent newAgent = new Agent(name,balance);
        clientAgent.sendInitialInfoToBank(newAgent);
    }

    public void sendInitialInfoToBank(Agent agent) {
        try {
            while (socket.isConnected()) {
                objectOutputStream.writeObject(agent);
                objectOutputStream.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void listenForMsg() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String msgFromGroupChat;
                while (socket.isConnected()){
                    try {
                        msgFromGroupChat = bufferedReader.readLine();
                        System.out.println(msgFromGroupChat);
                    } catch (IOException e) {
                        closeEverything(socket,objectOutputStream,objectInputStream);
                    }
                }
            }
        }).start();
    }




    public void closeEverything(Socket socket, ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream) {
        try {
            if (objectOutputStream != null) {
                objectOutputStream.close();
            }
            if (socket != null) {
                socket.close();
            }
            if (objectInputStream != null) {
                objectInputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
