import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    ServerSocket serverSocket;
    private ClientThread connectedClient = null;
    //List<ClientThread> clients=new ArrayList<>();;

    Server(int port){
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*void listen(){
        System.out.println("Listening...");
        while(true) {
            Socket clientSocket;
            try {
                clientSocket = serverSocket.accept();
                System.out.println("New client connected");
                ClientThread clientThread = new ClientThread(clientSocket, this);
                clients.add(clientThread);
                clientThread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }*/

    // для одного
    public void listen(){
        System.out.println("Oczekiwanie...");
        while(true){
            try {
                Socket clientSocket = serverSocket.accept();

                synchronized (this) {
                    if (connectedClient != null) {
                        PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
                        writer.println("Server busy. Please try again later.");
                        clientSocket.close();
                    } else {
                        ClientThread thread = new ClientThread(clientSocket, this);
                        connectedClient = thread;
                        thread.start();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    public void broadcast(String msg) {
        /*for (ClientThread client : clients) {
            client.send(msg);
        }*/
    }
}
