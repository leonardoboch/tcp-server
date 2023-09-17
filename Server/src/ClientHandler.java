import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLOutput;

public class ClientHandler implements Runnable {
    private Socket clientSocket = null;
    private boolean exit = false;
    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }
    @Override
    public void run() {
        try{
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            out.println("Bem vindo ao servidor! Digite suas mensagens:");
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String message = in.readLine();
            while(message != null && !message.equals("sair")){
                System.out.println("Mensagem do Cliente: " + message);
                if(message.trim().toLowerCase().contains("arquivo")) {
                    System.out.println("Cliente esta requisitando um arquivo");
                    findFile(message);

                }
                message = in.readLine();
            }
            System.out.println("Conexão com o cliente encerrada.");
            this.clientSocket.close();
            this.stop();




        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void stop() {
        this.exit = true;
    }
    public void findFile(String path) {
        String fileName = path.trim();
        System.out.println(path.substring(7).trim());
        //Path filePath = Paths.get(path);
        //System.out.println(filePath);
    }
}
