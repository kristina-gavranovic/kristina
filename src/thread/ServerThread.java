/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dusan
 */
public class ServerThread extends Thread {

    private final ServerSocket serverSocket;
    private final List<ClientThread> clients;

    public ServerThread() throws IOException {
        serverSocket = new ServerSocket(9000);
        clients = new ArrayList<>();
    }

    @Override
    public void run() {
        while (!serverSocket.isClosed()) {
            System.out.println("Waiting client");
            try {
                Socket socket = serverSocket.accept();
                ClientThread thread = new ClientThread(socket);
                clients.add(thread);
                thread.start();

                System.out.println("Client connected");
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }

        stopAllThreads();
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    private void stopAllThreads() {
        for (ClientThread client : clients) {
            try {
                client.getSocket().close();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

  
}
