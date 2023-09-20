import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;
import java.util.concurrent.atomic.AtomicBoolean;

public class TCPClient {
    private static Socket socket = null;
    public static void main(String[] args) {
        String serverAddress = "127.0.0.1"; // Endereço IP do servidor localhost
        int serverPort = 12345; // Porta do servidor
        Scanner scanner = new Scanner(System.in);
        
        
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
                        if(serverMessage.startsWith("Hash SHA-256 do Arquivo:")) {
                            saveFile(socket.getInputStream(), "arquivo.txt");
                            break;
                        }
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
            readerThread.shutdownNow();
            socket.close();
            out.close();
            in.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void saveFile(InputStream inputStream, String fileName) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
            }
            fileOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
