import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;

public class myBankClientHandler implements Runnable {

    // will allow multiple clients for the bank server
    public static ArrayList<myBankClientHandler> myBankClientHandlers = new ArrayList<>();
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private String agentUsername;
    Agent agent;

    public myBankClientHandler(Socket socket) {
        try {
            this.socket = socket;
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
            myBankClientHandlers.add(this);
            agent = (Agent) inputStream.readObject();
          //  broadcastMessage("SERVER: " + agentUsername + " has entered the chat.");
        } catch (IOException e) {
            closeEverything(socket, bufferedReader,bufferedWriter);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        while (socket.isConnected()) {
            storeAndPrintClientInfo();
        }
    }

    public void storeAndPrintClientInfo() {
        System.out.println(agent.name + " has connected with an initial balance of: " + agent.initialBalance);
        returnAccNumber();
    }

    public void returnAccNumber() {
        agent.setAccountID(69);
        System.out.println(agent.accountID);
    }

    public void sendAccountNumber() {
    }

 /**   public void returnAccountNumber(Agent a) {
        a.setAccountID(1);
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
    }*/
    /**public void broadcastMessage(String msgToSend) {

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
    } */

    public void removeClientHandler() {
        myBankClientHandlers.remove(this);
      //  broadcastMessage("SERVER: " + agentUsername + " has left the bank server");
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
}
