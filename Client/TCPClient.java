import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;
import java.util.concurrent.atomic.AtomicBoolean;

public class TCPClient {
    public static void main(String[] args) {
        String serverAddress = "127.0.0.1"; // Endereço IP do servidor localhost
        int serverPort = 12345; // Porta do servidor
        Scanner scanner = new Scanner(System.in);
        Socket socket = null;
        
        try {
            socket = new Socket(serverAddress, serverPort);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            ExecutorService readerThread = Executors.newSingleThreadExecutor();
            readerThread.execute(() -> {
                try{
                    String serverMessage;
                    while(((serverMessage = in.readLine()) != null)) {
                        System.out.println(serverMessage);
                        
                    }

                }catch(IOException e) {
                    e.printStackTrace();
                }
            });
            String clientMessage = "";
            while(!clientMessage.equalsIgnoreCase("sair")) { 
                System.out.println("Digite uma mensagem para enviar ao servidor:");
                clientMessage = scanner.nextLine();
                out.println(clientMessage);
            }
            in.close();
            out.close();
            readerThread.shutdownNow();
            socket.close();
            
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
