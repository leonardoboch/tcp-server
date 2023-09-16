import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    private final int port = 12345;
    public void TCPServer () {
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
