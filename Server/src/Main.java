import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        int port = 12345;
        System.out.println("Starting server...");
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            InetAddress inet = serverSocket.getInetAddress();
            System.out.println("Host Address:: " + inet.getHostAddress());
            System.out.println("Host Name:: " + inet.getHostName());
            System.out.println("Servidor TCP aguardando conexões na porta:: " + serverSocket.getLocalPort());
            while(true) {
                Socket clientSocket = serverSocket.accept();
                InetAddress inetClient = clientSocket.getInetAddress();
                System.out.println("Cliente conectado:: " + inetClient);
                System.out.println(inetClient.getHostAddress());
                System.out.println(inetClient.getHostName());
                Thread clientThread = new Thread(new ClientHandler(clientSocket));
                clientThread.start();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}