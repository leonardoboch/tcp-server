import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        int port = 12345;
        System.out.println("Starting server...");
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Servidor TCP aguardando conexões na porta");
            while(true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado:: " + clientSocket.getInetAddress());
                Thread clientThread = new Thread(new ClientHandler(clientSocket));
                clientThread.start();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}