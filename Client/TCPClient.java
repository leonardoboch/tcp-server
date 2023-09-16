import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class TCPClient {
    public static void main(String[] args) {
        String serverAddress = "127.0.0.1"; // Endereço IP do servidor
        int serverPort = 12345; // Porta do servidor
        
        try (Socket socket = new Socket(serverAddress, serverPort);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            
            // Ler e exibir a mensagem recebida do servidor
            String message = in.readLine();
            System.out.println("Mensagem do servidor: " + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
