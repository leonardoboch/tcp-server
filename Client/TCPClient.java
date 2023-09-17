import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;
import java.io.PrintWriter;

public class TCPClient {
    public static void main(String[] args) {
        String serverAddress = "127.0.0.1"; // Endereço IP do servidor
        int serverPort = 12345; // Porta do servidor
        Scanner scanner = new Scanner(System.in);
        
        try {
            Socket socket = new Socket(serverAddress, serverPort);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String serverMessage = in.readLine();
            System.out.println(serverMessage);
            String clientMessage = null;
            while(true) {
                System.out.println("Digite uma mensagem para enviar ao servidor:");
                clientMessage = new BufferedReader(new InputStreamReader(System.in)).readLine();
                if(clientMessage.equalsIgnoreCase("sair")) {
                    break;
                }
                out.println(clientMessage);
            }

            
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
