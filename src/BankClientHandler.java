/**import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class BankClientHandler implements Runnable {

    // will allow multiple clients for the bank server
    public static ArrayList<myBankClientHandler> myBankClientHandlers = new ArrayList<>();
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String agentUsername;

    public BankClientHandler(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.agentUsername = bufferedReader.readLine();
            BankClientHandlers.add(this);
            broadcastMessage("SERVER: " + agentUsername + " has entered the chat.");
        } catch (IOException e) {
            closeEverything(socket, bufferedReader,bufferedWriter);
        }
    }

    @Override
    public void run() {
        String msgFromClient;

        while (socket.isConnected()) {
            try {
                msgFromClient = bufferedReader.readLine();
                broadcastMessage(msgFromClient);
            } catch (IOException e) {
                closeEverything(socket, bufferedReader, bufferedWriter);
                break;
            }
        }

    }
    public void broadcastMessage(String msgToSend) {

        for(myBankClientHandler clientHandler : myBankClientHandlers) {
            try {
                if (!clientHandler.agentUsername.equals(agentUsername)) {
                    clientHandler.bufferedWriter.write(msgToSend);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
                }
            } catch (IOException e) {
                closeEverything(socket, bufferedReader, bufferedWriter);
            }
        }
    }

    public void removeClientHandler() {
        myBankClientHandlers.remove(this);
        broadcastMessage("SERVER: " + agentUsername + " has left the bank server");
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        removeClientHandler();
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
} */
